package com.pro.math.EGE;

import java.util.Arrays;

public class Console {
    static void L(String message) {
        android.util.Log.d("FATAL EXCEPTION",message);
    }
    static void L(Exception exception) {
        android.util.Log.d("FATAL EXCEPTION", Arrays.deepToString(exception.getStackTrace()));
    }
}
