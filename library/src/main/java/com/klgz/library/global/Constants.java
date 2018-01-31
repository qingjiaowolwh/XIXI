package com.klgz.library.global;


import android.os.Environment;

public class Constants {
    public static final String APP_NAME = "XiBao";
    public static final String HOST_COMMON = "http://101.200.75.18/";
    // 外部存储设备的根路径
    public static final String ExternalStorageRootPath = Environment.getExternalStorageDirectory().getPath();
    public static final String BasePath = ExternalStorageRootPath + "/"+APP_NAME+"/";
    // 文件存放路径
    public static final String FileCachePath = BasePath + "FileCache/";
    // 保存图片
    public static final String ImageCachePath = BasePath + "ImageCache/";
    // 下载存储地址
    public static final String DOWNLOADPath = BasePath + "download/";

}