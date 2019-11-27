package com.king.frame.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Matrix
import android.graphics.Paint
import android.graphics.PorterDuff
import android.graphics.PorterDuffXfermode
import android.graphics.Rect
import android.graphics.RectF
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.media.ExifInterface
import android.text.TextUtils
import android.util.Base64
import android.util.Log

import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.io.IOException
import java.io.InputStream
import java.lang.ref.WeakReference

/**
 * 图片处理工具类
 *
 * @author king
 * @date 2019-11-27 11:13
 */
object ImageUtil {

    /**
     * byte array转换为Bitmap
     *
     * @param b
     * @return
     */
    fun byteToBitmap(b: ByteArray?): Bitmap? {
        return if (b == null || b.size == 0) null else BitmapFactory.decodeByteArray(b, 0, b.size)
    }

    /**
     * Drawable 转换为 Bitmap
     *
     * @param d
     * @return
     */
    fun drawableToBitmap(d: Drawable?): Bitmap? {
        return if (d == null) null else (d as BitmapDrawable).bitmap
    }

    /**
     * Bitmap 转换为 Drawable
     *
     * @param b
     * @return
     */
    fun bitmapToDrawable(b: Bitmap?): Drawable? {
        return if (b == null) null else BitmapDrawable(b)
    }


    /**
     * 图片圆角 FIXME
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    fun toRoundCorner(bitmap: Bitmap, pixels: Int): Bitmap? {
        try {
            val output = Bitmap.createBitmap(bitmap.width, bitmap.height, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(output)
            val color = -0xbdbdbe
            val paint = Paint()
            val rect = Rect(0, 0, bitmap.width, bitmap.height)
            val rectF = RectF(rect)
            val roundPx = pixels.toFloat()
            paint.isAntiAlias = true
            canvas.drawARGB(0, 0, 0, 0)
            paint.color = color
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint)
            paint.xfermode = PorterDuffXfermode(PorterDuff.Mode.SRC_IN)
            val src = Rect(0, 0, bitmap.width, bitmap.height)
            canvas.drawBitmap(bitmap, src, rect, paint)
            return output
        } catch (e: Exception) {
            return bitmap
        }

    }

    /**
     *
     * 获取Bitmap大小
     *
     * @author king
     * @param bitmap
     * @return
     */
    fun getSizeOfBitmap(bitmap: Bitmap): Double {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)// 这里100的话表示不压缩质量
        return (baos.toByteArray().size / 1024).toDouble()
    }


    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    fun calculateInSampleSize(options: BitmapFactory.Options, reqWidth: Int, reqHeight: Int): Int {
        val height = options.outHeight
        val width = options.outWidth
        var inSampleSize = 1

        if (height > reqHeight || width > reqWidth) {
            val heightRatio = Math.round(height.toFloat() / reqHeight.toFloat())
            val widthRatio = Math.round(width.toFloat() / reqWidth.toFloat())
            inSampleSize = if (heightRatio < widthRatio) heightRatio else widthRatio
        }
        return inSampleSize
    }

    // 根据原始图片大小进行缩放
    fun computeSampleSize(options: BitmapFactory.Options, minSideLength: Int, maxNumOfPixels: Int): Int {
        val initialSize = calculateInSampleSize(options, minSideLength, maxNumOfPixels)

        var roundedSize: Int
        if (initialSize <= 8) {
            roundedSize = 1
            while (roundedSize < initialSize) {
                roundedSize = roundedSize shl 1
            }
        } else {
            roundedSize = (initialSize + 7) / 8
        }

        return roundedSize
    }


    /**
     *
     * 获取根据条件压缩图片并获取其路径
     *
     * @param filePath  原图路径
     * @param descDir  压缩后图片存放目录
     * @param isCompress  是否需要压缩
     * @return
     */
    fun getCompressPicPath(filePath: String, descDir: String, isCompress: Boolean): String {
        val file = File(filePath)
        val toPath = descDir + "compress_" + file.name
        // 如果是gif，直接原图
        if (isGif(filePath)) {
            return filePath
        }
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(filePath, options)
        if (!isCompress) {
            //原图
            return filePath
        } else {
            // 图片小于200k，直接发原图
            if (file.length() <= 200 * 1024) {
                return filePath
            } else {
                options.inSampleSize = computeSampleSize(options, -1, 1280 * 720)
            }
        }
        options.inJustDecodeBounds = false
        var bitmap: Bitmap? = null
        try {
            bitmap = BitmapFactory.decodeFile(filePath, options)
        } catch (error: OutOfMemoryError) {
            //内存不足，加大压缩力度
            options.inSampleSize = computeSampleSize(options, -1, 320 * 480)
            bitmap = BitmapFactory.decodeFile(filePath, options)
        }

        val degree = readPictureDegree(filePath)
        if (degree != 0 && bitmap != null) {// 旋转照片角度 处理某些手机拍照角度旋转的问题
            bitmap = rotateBitmap(bitmap, degree)
        }

        var out: FileOutputStream? = null
        try {
            out = FileOutputStream(toPath)
            if (isPNG(toPath)) {
                bitmap!!.compress(Bitmap.CompressFormat.PNG, 100, out)
            } else {
                bitmap!!.compress(Bitmap.CompressFormat.JPEG, 100, out)
            }
        } catch (e: Exception) {
            Log.e("CompressBitmap", e.message)
        } finally {
            try {
                out!!.flush()
                out.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

            bitmap?.recycle()
        }

        return toPath
    }

    /**
     *
     * 判断照片角度
     *
     * @param path
     * @return
     */
    fun readPictureDegree(path: String): Int {
        var degree = 0
        try {
            val exifInterface = ExifInterface(path)
            val orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL)
            when (orientation) {
                ExifInterface.ORIENTATION_ROTATE_90 -> degree = 90
                ExifInterface.ORIENTATION_ROTATE_180 -> degree = 180
                ExifInterface.ORIENTATION_ROTATE_270 -> degree = 270
            }
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return degree
    }


    /**
     *
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    fun rotateBitmap(bitmap: Bitmap?, degress: Int): Bitmap? {
        var bitmap = bitmap
        if (bitmap != null) {
            val m = Matrix()
            m.postRotate(degress.toFloat())
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, m, true)
            return bitmap
        }
        return bitmap
    }

    /**
     *
     * 判断是否gif图
     *
     *
     *
     * @author king
     * @param filepath
     * @return
     */
    fun isGif(filepath: String): Boolean {
        try {
            if (TextUtils.isEmpty(filepath)) {
                return false
            }
            val type = getPicType(filepath)
            if ("gif" == type) {
                return true
            }
        } catch (e: Exception) {
        }

        return false
    }

    /**
     *
     * 判断是否png格式图
     *
     *
     *
     * @author king
     * @param filepath
     * @return
     */
    fun isPNG(filepath: String): Boolean {
        try {
            if (TextUtils.isEmpty(filepath)) {
                return false
            }
            val type = getPicType(filepath)
            if ("png" == type) {
                return true
            }
        } catch (e: Exception) {
        }

        return false
    }

    /**
     * 获取图片类型
     *
     * @author king
     * @param filename
     * @return
     */
    fun getPicType(filename: String): String {
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length)
    }

    /**
     * 将图片转换成base64字符串
     *
     * @param imgFilePath
     * @return
     */
    fun ImageToBase64Str(imgFilePath: String): String? {
        var data: ByteArray? = null
        // 读取图片字节数组
        try {
            val `in` = FileInputStream(imgFilePath)
            data = ByteArray(`in`.available())
            `in`.read(data)
            `in`.close()
        } catch (e: IOException) {
            e.printStackTrace()
        }

        return if (data != null) {
            Base64.encodeToString(data, Base64.DEFAULT)
        } else null
    }

    /**
     * 图片文件转为bitmap
     * @author liyezhen
     * @param path
     * @return
     * @throws
     */
    fun convertToBitmap(path: String): Bitmap? {
        val opts = BitmapFactory.Options()
        var output: Bitmap? = null

        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888
        // 返回为空
        BitmapFactory.decodeFile(path, opts)
        val width = opts.outWidth
        val height = opts.outHeight
        opts.inJustDecodeBounds = false
        val weak = WeakReference(BitmapFactory.decodeFile(path, opts))
        try {
            output = Bitmap.createScaledBitmap(weak.get(), width, height, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return output
    }

    /**
     * 图片文件转成bitmap，控制图片宽高
     * @author June
     * @param path
     * @param wh
     * @return
     * @throws
     */
    fun convertToBitmap(path: String, wh: Int): Bitmap? {
        val opts = BitmapFactory.Options()
        var output: Bitmap? = null
        opts.inJustDecodeBounds = true
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888
        BitmapFactory.decodeFile(path, opts)
        val realWidth = opts.outWidth
        val realHeight = opts.outHeight
        var width: Int
        var height: Int
        if (realWidth < realHeight) {
            height = wh
            width = realWidth * wh / realHeight
            if (width < wh / 2) {
                width = wh / 2
            }
            opts.outHeight = wh
            opts.outWidth = width

            val scale = 0f
            // if (realHeight > wh) {
            // scale = realHeight / wh;
            // }
            opts.inJustDecodeBounds = false
            opts.inSampleSize = scale.toInt()
        } else {
            width = wh
            height = realHeight * wh / realWidth
            if (height < wh / 2) {
                height = wh / 2
            }
            opts.outWidth = wh
            opts.outHeight = height
            val scale = 0f

            opts.inJustDecodeBounds = false
            opts.inSampleSize = scale.toInt()
        }
        val weak = WeakReference(BitmapFactory.decodeFile(path, opts))
        try {
            output = Bitmap.createScaledBitmap(weak.get(), width, height, true)
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return output
    }
}
