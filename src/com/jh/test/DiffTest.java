package com.jh.test;

import com.jh.tools.*;
import com.jh.tools.diff_match_patch.*;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;

/**
 * Created by jianghe on 2017/7/7.
 */
public class DiffTest {
    public static String oldPath = "C:\\Users\\jianghe\\Desktop\\bundle\\old\\";
    public static String newPath = "C:\\Users\\jianghe\\Desktop\\bundle\\new\\";
    public static String diffPath = "C:\\Users\\jianghe\\Desktop\\bundle\\diff\\diff.pat";
    public static String diffZip = "C:\\Users\\jianghe\\Desktop\\bundle\\diff\\jj.zip";
    public static String bundleName = "index.android.bundle";

    public static void main(String[] arg) {
        System.out.println("新文件MD5：" + FileUtils.getMd5ByFile(newPath + bundleName));
        System.out.println("老文件MD5：" + FileUtils.getMd5ByFile(oldPath + bundleName));
        System.out.println("差异压缩包MD5：" + FileUtils.getMd5ByFile(diffZip));

//        String o = FileUtils.getFileString(oldPath + bundleName);
//        String n = FileUtils.getFileString(newPath + bundleName);
//        // 对比
//        diff_match_patch dmp = new diff_match_patch();
//        LinkedList<Diff> diffs = dmp.diff_main(o, n);
//
//        // 生成差异补丁包
//        LinkedList<Patch> patches = dmp.patch_make(diffs);
//
//        // 解析补丁包
//        String patchesStr = dmp.patch_toText(patches);
//
//        try {
//            // 将补丁文件写入到某个位置
//            Files.write(Paths.get(diffPath), patchesStr.getBytes());
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
    }
}
