package com.pro.math.EGE;

public class Console {
    static void L(String message) {
        android.util.Log.d("FATAL EXCEPTION",message);
    }
    static void L(String message, String className) {
        android.util.Log.d("FATAL EXCEPTION",className+": "+message);
    }
    static void L(String message, String contextClassName, String className) {
        android.util.Log.d("FATAL EXCEPTION",contextClassName+" "+className+": "+message);
    }
}
