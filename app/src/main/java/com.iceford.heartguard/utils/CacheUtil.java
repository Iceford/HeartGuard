package com.iceford.heartguard.utils;

import android.content.Context;
import android.os.Environment;

import java.io.File;

public class CacheUtil {
    // 清理应用内部缓存
    public static void clearInternalCache(Context context) {
        File cacheDir = context.getCacheDir();
        deleteDir(cacheDir);
    }

    // 清理应用外部缓存
    public static void clearExternalCache(Context context) {
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            File externalCacheDir = context.getExternalCacheDir();
            deleteDir(externalCacheDir);
        }
    }

    // 删除文件或目录
    private static boolean deleteDir(File file) {
        if (file != null && file.isDirectory()) {
            String[] children = file.list();
            assert children != null;
            for (String child : children) {
                boolean success = deleteDir(new File(file, child));
                if (!success) {
                    return false;
                }
            }
        }
        assert file != null;
        return file.delete();
    }

}


