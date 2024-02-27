package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

public class Shop extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        ListView List = findViewById(R.id.list);
    }
}

