package com.pro.math.EGE;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    protected void BackToMainMenu(Button button) {
        button.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)));/*startActivity(new Intent(this,MainActivity.class))*/
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setNavigationBarColor(getResources().getColor(R.color.Background));
        getWindow().setStatusBarColor(getResources().getColor(R.color.Background));
        if (Build.VERSION.SDK_INT >= 34) {
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_OPEN,R.anim.fadein,R.anim.fadeout);
            overrideActivityTransition(Activity.OVERRIDE_TRANSITION_CLOSE,R.anim.fadeout,R.anim.fadein);
        }
    }
    protected void SetSizes(Button[] Objects, TextView Title) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        double multiplier = (Math.min(size.x,size.y)/1250d-1d)/1.3d+1d;
        double textSize = multiplier-0.1d;

        if (Title != null) {
            Title.setTextSize(0,(float)(Title.getTextSize()*multiplier));
        }

        for (Button object : Objects) {
            ViewGroup.LayoutParams params = object.getLayoutParams();
            params.width = (int)(params.width * multiplier);
            params.height = (int)(params.height * multiplier);
            object.setLayoutParams(params);
            object.setTextSize(TypedValue.COMPLEX_UNIT_PX, (float)(object.getTextSize() * textSize));
        }
    }
}
