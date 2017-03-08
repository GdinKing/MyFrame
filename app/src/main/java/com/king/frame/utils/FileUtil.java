package com.king.frame.utils;

import android.os.Build;
import android.os.Environment;
import android.os.StatFs;

import com.king.frame.BaseApplication;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.text.DecimalFormat;

/**
 * @author king
 * @date 2017/3/2 14:28
 */
public class FileUtil {

    /**
     * 获取缓存目录
     *
     * @return
     */
    public static String getCacheDir() {

        File file = BaseApplication.getInstance().getCacheDir();
        return file.getAbsolutePath();
    }

    /**
     * 获取/data/data/packagename/files目录
     *
     * @return
     */
    public static String getFileDir() {
        File file = BaseApplication.getInstance().getFilesDir();
        return file.getAbsolutePath();
    }

    /**
     * 文件是否存在
     *
     * @param filepath
     * @return
     */
    public static boolean isExist(String filepath) {
        File file = new File(filepath);
        return file.exists();
    }

    /**
     * 删除缓存文件及目录
     *
     * @param filePath
     * @return
     */
    public static void deleteFile(String filePath) {
        File file = new File(filePath);
        if (!file.exists())
            return;
        if (file.isDirectory()) {// 处理目录
            File files[] = file.listFiles();
            for (int i = 0; i < files.length; i++) {
                deleteFile(files[i].getAbsolutePath());
            }
        }

        if (!file.isDirectory()) {// 如果是文件，删除
            file.delete();
        } else {// 目录
            if (file.listFiles().length == 0) {// 目录下没有文件或者目录，删除
                file.delete();
            }
        }
    }


    /**
     * 读取文件内容
     *
     * @param filePath
     * @return
     */
    public String readFile(String filePath) {
        try {
            if (!isExist(filePath)) {
                return null;
            }

            File file = new File(filePath);
            FileInputStream fis = null;
            InputStreamReader isr = null;
            BufferedReader br = null;
            StringBuffer xml = new StringBuffer();
            try {
                fis = new FileInputStream(file);
                isr = new InputStreamReader(fis, "UTF-8");
                char[] b = new char[4096];
                for (int n; (n = isr.read(b)) != -1; ) {
                    xml.append(new String(b, 0, n));
                }
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (null != br) {
                    try {
                        br.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
            return xml.toString();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 内容写入文件
     *
     * @param filePath
     * @param data
     */
    public static void writeFile(String filePath, String data) throws IOException {
        FileOutputStream fOut = null;
        OutputStreamWriter osw = null;
        File file = new File(filePath);

        try {
            if (!file.isFile()) {
                String path = filePath.substring(0, filePath.lastIndexOf("/"));
                File dirFile = new File(path);
                if (!dirFile.exists()) {
                    dirFile.mkdirs();
                }
                file.createNewFile();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            fOut = new FileOutputStream(file, false);
            osw = new OutputStreamWriter(fOut, "UTF-8");
            osw.write(data);
            osw.flush();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                osw.close();
                fOut.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 获得文件长度
     *
     * @param filePath
     * @return
     */
    public static int getFileLength(String filePath) {
        if (!isExist(filePath)) {
            return 0;
        }

        File file = new File(filePath);
        int length = (int) file.length();
        return length;
    }

    /**
     * 追加文件内容
     * @param filePath
     * @param content
     */
    public static void appendFileContent(String filePath, String content) {
        try {
            FileWriter writer = new FileWriter(filePath, true);
            BufferedWriter bw = new BufferedWriter(writer);
            bw.write(content);
            bw.newLine();
            bw.close();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * @return
     * @throws
     * @Title: isSDCardEnable
     * @Description: 判断SDCard是否可用
     */
    public static boolean isSDCardEnable() {
        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);

    }

    /**
     * @return
     * @throws
     * @Title: getSDCardPath
     * @Description: 获取SD卡路径
     * @author liyezhen
     */
    public static String getSDCardPath() {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + File.separator;
    }

    /**
     * 获取SD卡剩余空间
     *
     * @return
     */
    public static long getLeftSpace() {
        File path = Environment.getExternalStorageDirectory();
        StatFs stat = new StatFs(path.getPath());
        long blockSize;
        long availableBlocks;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR2) {
            blockSize = stat.getBlockSizeLong();
            availableBlocks = stat.getAvailableBlocksLong();
        } else {
            blockSize = stat.getBlockSize();
            availableBlocks = stat.getAvailableBlocks();
        }

        return blockSize * availableBlocks;
    }

    /**
     * 转换文件大小
     *
     * @param size
     * @return
     */
    public static String formatFileSize(long size) {
        DecimalFormat df = new DecimalFormat("#.00");
        String fileSizeString = "";
        String wrongSize = "0B";
        if (size == 0) {
            return wrongSize;
        }
        if (size < 1024) {
            fileSizeString = size + "B";
        } else if (size < 1048576) {
            fileSizeString = df.format((double) size / 1024) + "K";
        } else if (size < 1073741824) {
            fileSizeString = df.format((double) size / 1048576) + "M";
        } else {
            fileSizeString = df.format((double) size / 1073741824) + "G";
        }
        return fileSizeString;
    }
}
