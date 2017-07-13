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
    public static String zipname = "jianghe" + System.currentTimeMillis() + ".zip";
    public static String oldname = "index.android3.bundle";
    public static String newname = "index.android4.bundle";
    public static String path = System.getProperty("user.dir") + "/bundle/file/";
    public static String diffPath = System.getProperty("user.dir") + "/bundle/diff/yasuo/jianghe/diff.pat";
    public static String zipFile = System.getProperty("user.dir") + "/bundle/diff/yasuo";
    public static String diffZip = System.getProperty("user.dir") + "/bundle/diff/zip/";

    public static void main(String[] arg) {
        System.out.println(1);
        String o = FileUtils.getFileString(path + oldname);
        String n = FileUtils.getFileString(path + newname);
        // 对比
        diff_match_patch dmp = new diff_match_patch();
        LinkedList<Diff> diffs = dmp.diff_main(o, n);
        // 生成差异补丁包
        LinkedList<Patch> patches = dmp.patch_make(diffs);
        // 解析补丁包
        String patchesStr = dmp.patch_toText(patches);
        //初始化保存差异文件的文件夹
        FileUtils.initFile(diffPath, true);
        try {
            // 将补丁文件写入到某个位置
            Files.write(Paths.get(diffPath), patchesStr.getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //压缩补丁包到
        ZipTest zca = new ZipTest(diffZip + zipname);
        zca.compress(zipFile);

        System.out.println("新文件MD5：" + FileUtils.getMd5ByFile(path + newname));
        System.out.println("老文件MD5：" + FileUtils.getMd5ByFile(path + oldname));
        System.out.println("差异压缩包MD5：" + FileUtils.getMd5ByFile(diffZip + zipname));
    }
}
