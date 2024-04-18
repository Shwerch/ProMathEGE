package com.pro.math.EGE;

import android.content.ContentValues;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;

import androidx.annotation.NonNull;

import com.google.android.material.slider.RangeSlider;

import java.io.OutputStream;
import java.util.Arrays;

public class Draft extends MyAppCompatActivity {
    private DrawView paint;
    private RangeSlider rangeSlider;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draft);
        paint = findViewById(R.id.draw_view);
        rangeSlider = findViewById(R.id.rangebar);
        Button Undo = findViewById(R.id.undo);
        Button Clear = findViewById(R.id.clear);
        Button Stroke = findViewById(R.id.stroke);
        super.SetSizes(new Button[] {Undo,Clear,Stroke},null);

        paint.setColor(getResources().getColor(R.color.TextColor));
        Undo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.undo();
            }
        });
        Clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                paint.clearDrawing();
                /*Bitmap bmp = paint.save();
                OutputStream imageOutStream;
                ContentValues cv = new ContentValues();
                cv.put(MediaStore.Images.Media.DISPLAY_NAME, "draft in Pro Math EGE.png");
                cv.put(MediaStore.Images.Media.MIME_TYPE, "image/png");
                cv.put(MediaStore.Images.Media.RELATIVE_PATH, Environment.DIRECTORY_PICTURES);
                Uri uri = getContentResolver().insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, cv);
                try {
                    if (uri == null)
                        throw new Exception();
                    imageOutStream = getContentResolver().openOutputStream(uri);
                    if (imageOutStream == null)
                        throw new Exception();
                    bmp.compress(Bitmap.CompressFormat.PNG, 100, imageOutStream);
                    imageOutStream.close();
                } catch (Exception e) {
                    Console.L(Arrays.toString(e.getStackTrace()));
                }*/
            }
        });
        Stroke.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (rangeSlider.getVisibility() == View.VISIBLE)
                    rangeSlider.setVisibility(View.GONE);
                else
                    rangeSlider.setVisibility(View.VISIBLE);
            }
        });

        rangeSlider.setValueFrom(0);
        rangeSlider.setValueTo(100);
        rangeSlider.setBackgroundColor(getResources().getColor(R.color.Background));
        rangeSlider.setDrawingCacheBackgroundColor(getResources().getColor(R.color.Background));
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            rangeSlider.setOutlineSpotShadowColor(getResources().getColor(R.color.Background));
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.P) {
            rangeSlider.setOutlineAmbientShadowColor(getResources().getColor(R.color.Background));
        }
        rangeSlider.addOnChangeListener(new RangeSlider.OnChangeListener() {
            @Override
            public void onValueChange(@NonNull RangeSlider slider, float value, boolean fromUser) {
                paint.setStrokeWidth((int)value);
            }
        });
        ViewTreeObserver vto = paint.getViewTreeObserver();
        vto.addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                paint.getViewTreeObserver().removeOnGlobalLayoutListener(this);
                int width = paint.getMeasuredWidth();
                int height = paint.getMeasuredHeight();
                paint.init(height, width);
            }
        });
    }
}

/*public class Draft extends MyAppCompatActivity {
    DrawingView drawView;
    @Override
    protected void onCreate(android.os.Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (drawView == null)
            drawView = new DrawingView(this);
        setContentView(drawView);
    }
}*/