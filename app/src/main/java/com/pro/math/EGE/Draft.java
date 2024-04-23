package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.slider.RangeSlider;

public class Draft extends MyAppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draft);
        DrawView paint = findViewById(R.id.draw_view);
        RangeSlider rangeSlider = findViewById(R.id.rangebar);
        Button Undo = findViewById(R.id.undo);
        Button Clear = findViewById(R.id.clear);
        Button Stroke = findViewById(R.id.stroke);
        Button Draw = findViewById(R.id.draw);
        Button Move = findViewById(R.id.move);
        TextView textView = findViewById(R.id.text);

        final int Text;
        try {
            Text = (int) getIntent().getSerializableExtra("Text");
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), R.string.error_whe_getting_topic, Toast.LENGTH_LONG).show();
            startActivity(new Intent(this, MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
            return;
        }
        textView.setText(Text);

        final Button[] activeButton = {Draw};
        Draw.setBackgroundColor(getResources().getColor(R.color.MiniAssents));
        Undo.setOnClickListener(v -> paint.undo());
        Clear.setOnClickListener(v -> paint.clearDrawing());
        Stroke.setOnClickListener(v -> {
            if (rangeSlider.getVisibility() == View.VISIBLE)
                rangeSlider.setVisibility(View.GONE);
            else
                rangeSlider.setVisibility(View.VISIBLE);
        });
        Draw.setOnClickListener(v -> {
            if (paint.DrawMode(DrawView.DRAW)) {
                Draw.setBackgroundColor(getResources().getColor(R.color.MiniAssents));
                activeButton[0].setBackgroundColor(getResources().getColor(R.color.void0));
                activeButton[0] = Draw;
            }
        });
        Move.setOnClickListener(v -> {
            if (paint.DrawMode(DrawView.MOVE)) {
                Move.setBackgroundColor(getResources().getColor(R.color.MiniAssents));
                activeButton[0].setBackgroundColor(getResources().getColor(R.color.void0));
                activeButton[0] = Move;
            }
        });
        super.SetSizes(new Button[] {Undo,Clear,Stroke,Draw,Move},null);

        rangeSlider.setValueFrom(0);
        rangeSlider.setValueTo(100);
        rangeSlider.setMinSeparation(0.1f);
        rangeSlider.setBackgroundColor(getResources().getColor(R.color.MiniAssents));
        rangeSlider.addOnChangeListener((slider, value, fromUser) -> paint.setStrokeWidth(value));
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