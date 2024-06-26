package com.pro.math.EGE;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class AboutApp extends MyAppCompatActivity {
    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);
        final Button Back = findViewById(R.id.mainmenu);
        final TextView About = findViewById(R.id.about_text);
        final TextView Title = findViewById(R.id.title);
        final TextView Version = findViewById(R.id.app_version);
        Version.setText(Sources.GetAppVersion(this));
        About.setMovementMethod(new ScrollingMovementMethod());
        super.BackToMainMenu(Back);
        super.SetSizes(new Button[] {Back},Title);
    }
}
