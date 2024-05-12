package com.pro.math.EGE;

import java.util.Arrays;
import java.util.List;

import android.util.Log;

public class Console {
    private static final String tag = "FATAL EXCEPTION (LOG)";
    public static void L(Exception exception) {Log.d(tag, Arrays.toString(exception.getStackTrace()));}
    public static <T> void L(List<T> list) {Log.d(tag, list.toString());}
    public static <T> void L(T message) {Log.d(tag, String.valueOf(message));}
    public static void L(Object[] message) {Log.d(tag, Arrays.deepToString(message));}
    public static void L(String string, Object... args) {Log.d(tag,String.format(string,args));}
}
