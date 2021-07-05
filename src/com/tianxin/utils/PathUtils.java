package com.tianxin.utils;

public class PathUtils {

    //private static final String P_PATH="heima_library2\\images\\";
    private static final String P_PATH = "images\\";

    public static String getRealPath(String relativePath) {

        return P_PATH + relativePath;
    }

}
