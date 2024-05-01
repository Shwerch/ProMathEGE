package com.pro.math.EGE;

import java.util.Arrays;
import java.util.List;

import android.util.Log;

public class Console {
    private static final String tag = "FATAL EXCEPTION";
    static void L(Exception exception) {Log.d(tag, Arrays.deepToString(exception.getStackTrace()));}
    static <T> void L(List<T> list) {Log.d(tag, list.toString());}
    static <T> void L(T message) {Log.d(tag, String.valueOf(message));}
    static void L(String string, Object... args) {Log.d(tag,String.format(string,args));}
}
