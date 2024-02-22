package com.pro.math.EGE;

import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.widget.Button;
import android.widget.TextView;

public class AboutApp extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);
        final Button Back = findViewById(R.id.mainmenu);
        final TextView About = findViewById(R.id.about_text);
        final TextView Title = findViewById(R.id.title);
        About.setMovementMethod(new ScrollingMovementMethod());
        super.BackToMainMenu(Back);
        super.SetSizes(new Button[]{Back},Title);
    }
}
