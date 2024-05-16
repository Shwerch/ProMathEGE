package com.pro.math.EGE;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Display;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class MyAppCompatActivity extends AppCompatActivity {
    protected void BackToMainMenu(Button button) {
        button.setOnClickListener(v -> startActivity(new Intent(this,MainActivity.class).addFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP)));
    }
    protected void Close() {
        this.finishAffinity();
    }
    protected void ChangePoints(Button button) {
        long points = Database.GetPoints();
        if (points == -1)
            button.setText(getString(R.string.undefined_points));
        else
            button.setText(Sources.GetRightPointsEnd(this,points));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Window window = getWindow();
        window.setNavigationBarColor(getResources().getColor(R.color.void0));
        window.setStatusBarColor(getResources().getColor(R.color.void0));
    }
    @Override
    public void startActivity(Intent intent) {
        super.startActivity(intent);
        overridePendingTransition(R.anim.fadein,R.anim.fadeout);
    }
    protected void SetSizes(Button[] Objects,@Nullable TextView Title) {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        double multiplier = (Math.min(size.x,size.y)/1500d-1)/2.5d+1;
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
