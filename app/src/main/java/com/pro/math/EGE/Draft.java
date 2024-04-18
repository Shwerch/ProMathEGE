package com.pro.math.EGE;

import android.graphics.Paint;
import android.os.Bundle;

public class Draft extends MyAppCompatActivity {
    DrawingView drawView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (drawView == null)
            drawView = new DrawingView(this);
        setContentView(drawView);
    }
}
