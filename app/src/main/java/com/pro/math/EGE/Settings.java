package com.pro.math.EGE;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Settings extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final Button MainMenu = findViewById(R.id.mainmenu);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu});

        ListView List = findViewById(R.id.settings_list);

        List.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(Settings.this,(String)List.getItemAtPosition(position),Toast.LENGTH_SHORT).show());
    }
}
