package com.jh.tools;

import java.io.*;
import java.math.BigInteger;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.security.MessageDigest;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;


/**
 * 文件工具类
 * Created by Song on 2017/2/15.
 */
public class FileUtils {

    /**
     * 解压 ZIP 包
     * filePath是解压到哪
     */
    public static void unpack(String zippath, String filePath) throws Exception {

        //读取需要解压缩的压缩包文件
        ZipInputStream inZip = new ZipInputStream(new FileInputStream(zippath));
        ZipEntry zipEntry;
        String szName;
        while ((zipEntry = inZip.getNextEntry()) != null) {
            szName = zipEntry.getName();
            // 如果是个文件夹
            if (zipEntry.isDirectory()) {
                szName = szName.substring(0, szName.length() - 1);
                File folder = new File(filePath + File.separator + szName);
                folder.mkdirs();
            } else {
                File file1 = new File(filePath + File.separator + szName);
                file1.createNewFile();
                FileOutputStream fos = new FileOutputStream(file1);
                int len;
                byte[] buffer = new byte[1024];
                while ((len = inZip.read(buffer)) != -1) {
                    fos.write(buffer, 0, len);
                    fos.flush();
                }
                fos.close();
            }
        }
        inZip.close();
    }

    /**
     * 复制单个文件
     *
     * @param srcFileName  待复制的文件名
     * @param destFileName 目标文件名
     * @param overlay      如果目标文件存在，是否覆盖
     * @return 如果复制成功，则返回true，否则返回false
     */
    public static boolean copyFile(String srcFileName, String destFileName,
                                   boolean overlay) throws Exception {
        // 判断原文件是否存在
        File srcFile = new File(srcFileName);
        if (!srcFile.exists()) {
            System.out.println("复制文件失败：原文件" + srcFileName + "不存在！");
            return false;
        } else if (!srcFile.isFile()) {
            System.out.println("复制文件失败：" + srcFileName + "不是一个文件！");
            return false;
        }
        // 判断目标文件是否存在
        File destFile = new File(destFileName);
        if (destFile.exists()) {
            // 如果目标文件存在，而且复制时允许覆盖。
            if (overlay) {
                // 删除已存在的目标文件，无论目标文件是目录还是单个文件
                System.out.println("目标文件已存在，准备删除它！");
                if (!FileUtils.deleteFile(destFileName)) {
                    System.out.println("复制文件失败：删除目标文件" + destFileName + "失败！");
                    return false;
                }
            } else {
                System.out.println("复制文件失败：目标文件" + destFileName + "已存在！");
                return false;
            }
        } else {
            if (!destFile.getParentFile().exists()) {
                // 如果目标文件所在的目录不存在，则创建目录
                System.out.println("目标文件所在的目录不存在，准备创建它！");
                if (!destFile.getParentFile().mkdirs()) {
                    System.out.println("复制文件失败：创建目标文件所在的目录失败！");
                    return false;
                }
            }
        }
        // 准备复制文件
        int byteread = 0;// 读取的位数
        InputStream in = null;
        OutputStream out = null;
        // 打开原文件
        in = new FileInputStream(srcFile);
        // 打开连接到目标文件的输出流
        out = new FileOutputStream(destFile);
        byte[] buffer = new byte[1024];
        // 一次读取1024个字节，当byteread为-1时表示文件已经读完
        while ((byteread = in.read(buffer)) != -1) {
            // 将读取的字节写入输出流
            out.write(buffer, 0, byteread);
        }
        System.out.println("复制单个文件" + srcFileName + "至" + destFileName
                + "成功！");
        // 关闭输入输出流，注意先关闭输出流，再关闭输入流
        if (out != null) {
            out.close();
        }
        if (in != null) {
            in.close();
        }
        return true;
    }

    /**
     * 删除指定File
     *
     * @param filePath
     */
    public static boolean deleteFile(String filePath) {
        File patFile = new File(filePath);
        if (patFile.exists()) {
            return patFile.delete();
        }
        return true;
    }

    /**
     * 获取文件的MD5工具类
     *
     * @param file
     * @return
     * @throws FileNotFoundException
     */
    public static String getMd5ByFile(String file) {
        String value = "";
        FileInputStream in = null;
        try {
            File file1 = new File(file);
            if (file1.exists()) {
                in = new FileInputStream(file1);
                MappedByteBuffer byteBuffer = in.getChannel().map(FileChannel.MapMode.READ_ONLY, 0, file1.length());
                MessageDigest md5 = MessageDigest.getInstance("MD5");
                md5.update(byteBuffer);
                BigInteger bi = new BigInteger(1, md5.digest());
                value = bi.toString(16);
            } else {
                throw new Exception("文件不存在");
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (null != in) {
                try {
                    in.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return value;
    }

    /**
     * 解析文件为String
     *
     * @param filePath
     * @return
     */
    public static String getFileString(String filePath) {
        String result = "";
        InputStream is = null;
        try {
            is = new FileInputStream(filePath);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            result = new String(buffer, "UTF-8");
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return result;
    }

//    public static String getStringFromPat(String patPath) {
//
//        FileReader reader = null;
//        String result = "";
//
//        try {
//            reader = new FileReader(patPath);
//            int ch = reader.read();
//            StringBuilder sb = new StringBuilder();
//            while (ch != -1) {
//                sb.append((char) ch);
//                ch = reader.read();
//                reader.close();
//                result = sb.toString();
//            }
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//        return result;
//    }


    /**
     * 初始化文件（存在删除，不存在检查父路径是否存在）
     *
     * @param path
     */

    public static void initFile(String path) {
        try {
            // 判断目标文件是否存在
            File destFile = new File(path);
            if (!destFile.exists()) {
                if (!destFile.getParentFile().exists()) {
                    // 如果目标文件所在的目录不存在，则创建目录
                    if (!destFile.getParentFile().mkdirs()) {
                        throw new Exception("创建目标文件所在的目录失败,是否申请读写权限");
                    }
                }
            } else {
                // 删除已存在的目标文件，无论目标文件是目录还是单个文件
                if (!FileUtils.deleteFile(path)) {
                    throw new Exception("删除目标文件" + path + "失败！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
