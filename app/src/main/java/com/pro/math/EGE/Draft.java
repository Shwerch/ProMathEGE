package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.core.content.ContextCompat;

import com.google.android.material.slider.RangeSlider;

public class Draft extends MyAppCompatActivity {
    @SuppressLint({"SetTextI18n", "UseCompatLoadingForDrawables"})
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.draft);
        DrawView paint = findViewById(R.id.draw_view);
        RangeSlider rangeSlider = findViewById(R.id.rangebar);
        ImageButton Undo = findViewById(R.id.undo);
        ImageButton Clear = findViewById(R.id.clear);
        ImageButton Stroke = findViewById(R.id.stroke);
        ImageButton Draw = findViewById(R.id.draw);
        ImageButton Move = findViewById(R.id.move);
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

        final ImageButton[] activeButton = {Draw};
        Move.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.ButtonStrokeColor));
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
                activeButton[0].setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.ButtonStrokeColor));
                Draw.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.TextColor));
                activeButton[0] = Draw;
            }
        });
        Move.setOnClickListener(v -> {
            if (paint.DrawMode(DrawView.MOVE)) {
                activeButton[0].setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.ButtonStrokeColor));
                Move.setBackgroundTintList(ContextCompat.getColorStateList(this,R.color.TextColor));
                activeButton[0] = Move;
            }
        });
        super.SetSizes(new Button[] {},null);

        rangeSlider.setValueFrom(0);
        rangeSlider.setValueTo(100);
        rangeSlider.setMinSeparation(0.1f);
        rangeSlider.setBackground(getResources().getDrawable(R.drawable.round));
        rangeSlider.setHaloRadius(35);
        rangeSlider.setThumbRadius(25);
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