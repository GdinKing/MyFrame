package com.king.frame.utils

import android.os.Build
import android.os.Environment
import android.os.StatFs

import com.king.frame.BaseApplication

import java.io.BufferedReader
import java.io.BufferedWriter
import java.io.File
import java.io.FileInputStream
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.FileWriter
import java.io.IOException
import java.io.InputStreamReader
import java.io.OutputStreamWriter
import java.text.DecimalFormat

/**
 * 文件相关工具类
 *
 * @author king
 * @date 2019-11-27 10:41
 */
class FileUtil {

    companion object {

        /**
         * 获取缓存目录
         *
         * @return
         */
        val cacheDir: String?
            get() {

                val file = BaseApplication.instance?.cacheDir
                return file?.absolutePath
            }

        /**
         * 获取/data/data/packagename/files目录
         *
         * @return
         */
        val fileDir: String?
            get() {
                val file = BaseApplication.instance?.filesDir
                return file?.absolutePath
            }

        /**
         * 文件是否存在
         *
         * @param filepath
         * @return
         */
        fun isExist(filepath: String): Boolean {
            val file = File(filepath)
            return file.exists()
        }

        /**
         * 删除缓存文件及目录
         *
         * @param filePath
         * @return
         */
        fun deleteFile(filePath: String) {
            val file = File(filePath)
            if (!file.exists())
                return
            if (file.isDirectory) {// 处理目录
                val files = file.listFiles()
                for (i in files.indices) {
                    deleteFile(files[i].absolutePath)
                }
            }

            if (!file.isDirectory) {// 如果是文件，删除
                file.delete()
            } else {// 目录
                if (file.listFiles().isEmpty()) {// 目录下没有文件或者目录，删除
                    file.delete()
                }
            }
        }

        /**
         * 内容写入文件
         *
         * @param filePath
         * @param data
         */
        @Throws(IOException::class)
        fun writeFile(filePath: String, data: String) {
            var fOut: FileOutputStream? = null
            var osw: OutputStreamWriter? = null
            val file = File(filePath)

            try {
                if (!file.isFile) {
                    val path = filePath.substring(0, filePath.lastIndexOf("/"))
                    val dirFile = File(path)
                    if (!dirFile.exists()) {
                        dirFile.mkdirs()
                    }
                    file.createNewFile()
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }

            try {
                fOut = FileOutputStream(file, false)
                osw = OutputStreamWriter(fOut, "UTF-8")
                osw.write(data)
                osw.flush()
            } catch (e: Exception) {
                e.printStackTrace()
            } finally {
                try {
                    osw?.close()
                    fOut?.close()
                } catch (e: IOException) {
                    e.printStackTrace()
                }

            }
        }

        /**
         * 获得文件长度
         *
         * @param filePath
         * @return
         */
        fun getFileLength(filePath: String): Int {
            if (!isExist(filePath)) {
                return 0
            }

            val file = File(filePath)
            return file.length().toInt()
        }

        /**
         * 追加文件内容
         * @param filePath
         * @param content
         */
        fun appendFileContent(filePath: String, content: String) {
            try {
                val writer = FileWriter(filePath, true)
                val bw = BufferedWriter(writer)
                bw.write(content)
                bw.newLine()
                bw.close()
                writer.close()
            } catch (e: IOException) {
                e.printStackTrace()
            }

        }

        /**
         * @return
         * @throws
         * @Title: isSDCardEnable
         * @Description: 判断SDCard是否可用
         */
        val isSDCardEnable: Boolean
            get() = Environment.getExternalStorageState() == Environment.MEDIA_MOUNTED

        /**
         * @return
         * @throws
         * @Title: getSDCardPath
         * @Description: 获取SD卡路径
         * @author liyezhen
         */
        val sdCardPath: String
            get() = Environment.getExternalStorageDirectory().absolutePath + File.separator

        /**
         * 获取SD卡剩余空间
         *
         * @return
         */
        val leftSpace: Long
            get() {
                val path = Environment.getExternalStorageDirectory()
                val stat = StatFs(path.path)
                val blockSize: Long
                val availableBlocks: Long
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
                    blockSize = stat.blockSizeLong
                    availableBlocks = stat.availableBlocksLong
                } else {
                    blockSize = stat.blockSize.toLong()
                    availableBlocks = stat.availableBlocks.toLong()
                }

                return blockSize * availableBlocks
            }

        /**
         * 转换文件大小
         *
         * @param size
         * @return
         */
        fun formatFileSize(size: Long): String {
            val df = DecimalFormat("#.00")
            var fileSizeString = ""
            val wrongSize = "0B"
            if (size == 0L) {
                return wrongSize
            }
            if (size < 1024) {
                fileSizeString = size.toString() + "B"
            } else if (size < 1048576) {
                fileSizeString = df.format(size.toDouble() / 1024) + "K"
            } else if (size < 1073741824) {
                fileSizeString = df.format(size.toDouble() / 1048576) + "M"
            } else {
                fileSizeString = df.format(size.toDouble() / 1073741824) + "G"
            }
            return fileSizeString
        }
    }
}
