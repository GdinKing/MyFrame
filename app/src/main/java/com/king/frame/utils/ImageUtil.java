package com.king.frame.utils;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.media.ExifInterface;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.WeakReference;

import static android.text.TextUtils.isEmpty;

/**
 * @author king
 * @date 2017/3/2 11:13
 */

public class ImageUtil {

    /**
     *  byte array转换为Bitmap
     *
     * @param b
     * @return
     */
    public static Bitmap byteToBitmap(byte[] b) {
        return (b == null || b.length == 0) ? null : BitmapFactory.decodeByteArray(b, 0, b.length);
    }

    /**
     *  Drawable 转换为 Bitmap
     *
     * @param d
     * @return
     */
    public static Bitmap drawableToBitmap(Drawable d) {
        return d == null ? null : ((BitmapDrawable) d).getBitmap();
    }

    /**
     * Bitmap 转换为 Drawable
     *
     * @param b
     * @return
     */
    public static Drawable bitmapToDrawable(Bitmap b) {
        return b == null ? null : new BitmapDrawable(b);
    }


    /**
     * 图片圆角 FIXME
     *
     * @param bitmap
     * @param pixels
     * @return
     */
    public static Bitmap toRoundCorner(Bitmap bitmap, int pixels) {
        try {
            Bitmap output = Bitmap.createBitmap(bitmap.getWidth(), bitmap.getHeight(), Bitmap.Config.ARGB_8888);
            Canvas canvas = new Canvas(output);
            final int color = 0xff424242;
            final Paint paint = new Paint();
            final Rect rect = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            final RectF rectF = new RectF(rect);
            final float roundPx = pixels;
            paint.setAntiAlias(true);
            canvas.drawARGB(0, 0, 0, 0);
            paint.setColor(color);
            canvas.drawRoundRect(rectF, roundPx, roundPx, paint);
            paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
            final Rect src = new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight());
            canvas.drawBitmap(bitmap, src, rect, paint);
            return output;
        } catch (Exception e) {
            return bitmap;
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
    public static double getSizeOfBitmap(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 这里100的话表示不压缩质量
        return baos.toByteArray().length / 1024;
    }


    /**
     * 计算图片的缩放值
     *
     * @param options
     * @param reqWidth
     * @param reqHeight
     * @return
     */
    public static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据原始图片大小进行缩放
    public static int computeSampleSize(BitmapFactory.Options options, int minSideLength, int maxNumOfPixels) {
        int initialSize = calculateInSampleSize(options, minSideLength, maxNumOfPixels);

        int roundedSize;
        if (initialSize <= 8) {
            roundedSize = 1;
            while (roundedSize < initialSize) {
                roundedSize <<= 1;
            }
        } else {
            roundedSize = (initialSize + 7) / 8;
        }

        return roundedSize;
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
    public static String getCompressPicPath(String filePath,String descDir, boolean isCompress) {
        File file = new File(filePath);
        String toPath = descDir +  "compress_" + file.getName();
        // 如果是gif，直接原图
        if (isGif(filePath)) {
            return filePath;
        }
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);
        if (!isCompress) {
            //原图
            return filePath;
        } else {
            // 图片小于200k，直接发原图
            if (file.length() <= 200 * 1024) {
                return filePath;
            } else {
                options.inSampleSize = computeSampleSize(options, -1, 1280 * 720);
            }
        }
        options.inJustDecodeBounds = false;
        Bitmap bitmap = null;
        try {
            bitmap = BitmapFactory.decodeFile(filePath, options);
        } catch (OutOfMemoryError error) {
            //内存不足，加大压缩力度
            options.inSampleSize = computeSampleSize(options, -1, 320 * 480);
            bitmap = BitmapFactory.decodeFile(filePath, options);
        }
        int degree = readPictureDegree(filePath);
        if (degree != 0 && bitmap != null) {// 旋转照片角度 处理某些手机拍照角度旋转的问题
            bitmap = rotateBitmap(bitmap, degree);
        }

        FileOutputStream out = null;
        try {
            out = new FileOutputStream(toPath);
            if (isPNG(toPath)) {
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, out);
            } else {
                bitmap.compress(Bitmap.CompressFormat.JPEG, 100, out);
            }
        } catch (Exception e) {
            Log.e("CompressBitmap", e.getMessage());
        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (bitmap != null) {
                bitmap.recycle();
            }
        }

        return toPath;
    }

    /**
     *
     * 判断照片角度
     *
     * @param path
     * @return
     */
    public static int readPictureDegree(String path) {
        int degree = 0;
        try {
            ExifInterface exifInterface = new ExifInterface(path);
            int orientation = exifInterface.getAttributeInt(ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL);
            switch (orientation) {
                case ExifInterface.ORIENTATION_ROTATE_90:
                    degree = 90;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_180:
                    degree = 180;
                    break;
                case ExifInterface.ORIENTATION_ROTATE_270:
                    degree = 270;
                    break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return degree;
    }


    /**
     *
     * 旋转照片
     *
     * @param bitmap
     * @param degress
     * @return
     */
    public static Bitmap rotateBitmap(Bitmap bitmap, int degress) {
        if (bitmap != null) {
            Matrix m = new Matrix();
            m.postRotate(degress);
            bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), m, true);
            return bitmap;
        }
        return bitmap;
    }
    /**
     *
     * 判断是否gif图
     * <p>
     *
     * @author king
     * @param filepath
     * @return
     */
    public static boolean isGif(String filepath) {
        try {
            if (TextUtils.isEmpty(filepath)) {
                return false;
            }
            String type = getPicType(filepath);
            if ("gif".equals(type)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }
    /**
     *
     * 判断是否png格式图
     * <p>
     *
     * @author king
     * @param filepath
     * @return
     */
    public static boolean isPNG(String filepath) {
        try {
            if (isEmpty(filepath)) {
                return false;
            }
            String type = getPicType(filepath);
            if ("png".equals(type)) {
                return true;
            }
        } catch (Exception e) {
        }
        return false;
    }

    /**
     * 获取图片类型
     *
     * @author king
     * @param filename
     * @return
     */
    public static String getPicType(String filename) {
        return filename.substring(filename.lastIndexOf(".") + 1, filename.length());
    }

    /**
     * 将图片转换成base64字符串
     *
     * @param imgFilePath
     * @return
     */
    public static String ImageToBase64Str(String imgFilePath) {
        byte[] data = null;
        // 读取图片字节数组
        try {
            InputStream in = new FileInputStream(imgFilePath);
            data = new byte[in.available()];
            in.read(data);
            in.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        if (data != null) {
            return Base64.encodeToString(data, Base64.DEFAULT);
        }
        return null;
    }

    /**
     * 图片文件转为bitmap
     * @author liyezhen
     * @param path
     * @return
     * @throws
     */
    public static Bitmap convertToBitmap(String path) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap output = null;

        // 设置为ture只获取图片大小
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        // 返回为空
        BitmapFactory.decodeFile(path, opts);
        int width = opts.outWidth;
        int height = opts.outHeight;
        opts.inJustDecodeBounds = false;
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        try {
            output = Bitmap.createScaledBitmap(weak.get(), width, height, true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return output;
    }

    /**
     * 图片文件转成bitmap，控制图片宽高
     * @author June
     * @param path
     * @param wh
     * @return
     * @throws
     */
    public static Bitmap convertToBitmap(String path, int wh) {
        BitmapFactory.Options opts = new BitmapFactory.Options();
        Bitmap output = null;
        opts.inJustDecodeBounds = true;
        opts.inPreferredConfig = Bitmap.Config.ARGB_8888;
        BitmapFactory.decodeFile(path, opts);
        int realWidth = opts.outWidth, realHeight = opts.outHeight;
        int width, height;
        if (realWidth < realHeight) {
            height = wh;
            width = realWidth * wh / realHeight;
            if (width < (wh / 2)) {
                width = wh / 2;
            }
            opts.outHeight = wh;
            opts.outWidth = width;

            float scale = 0.f;
            // if (realHeight > wh) {
            // scale = realHeight / wh;
            // }
            opts.inJustDecodeBounds = false;
            opts.inSampleSize = (int) scale;
        } else {
            width = wh;
            height = realHeight * wh / realWidth;
            if (height < (wh / 2)) {
                height = wh / 2;
            }
            opts.outWidth = wh;
            opts.outHeight = height;
            float scale = 0.f;

            opts.inJustDecodeBounds = false;
            opts.inSampleSize = (int) scale;
        }
        WeakReference<Bitmap> weak = new WeakReference<Bitmap>(BitmapFactory.decodeFile(path, opts));
        try {
            output = Bitmap.createScaledBitmap(weak.get(), width, height, true);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return output;
    }
}
