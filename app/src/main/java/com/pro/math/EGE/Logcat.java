package com.pro.math.EGE;

import android.util.Log;

public class Logcat {
    static void Log(String message) {
        Log.d("DevLog",message);
    }
    static void Log(String message,String className) {
        Log.d("DevLog",className+": "+message);
    }
    static void Log(String message,String contextClassName,String className) {
        Log.d("DevLog",contextClassName+" "+className+": "+message);
    }
}
