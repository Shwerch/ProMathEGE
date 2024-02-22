package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Settings extends MyAppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.settings);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        ListView List = findViewById(R.id.settings_list);

        List.setOnItemClickListener((parent, view, position, id) ->
                Toast.makeText(Settings.this,(String)List.getItemAtPosition(position),Toast.LENGTH_SHORT).show());
    }
}
