package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

public class AboutApp extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.about_app);
        final Button Back = findViewById(R.id.mainmenu);
        super.BackToMainMenu(Back);
        super.SetSizes(new Button[]{Back});
    }
}
