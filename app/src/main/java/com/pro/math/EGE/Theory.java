package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

public class Theory extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.theory);
        final Button MainMenu = findViewById(R.id.mainmenu);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu});

        ListView List = findViewById(R.id.list);

        List.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Theory.this,TheoryActivity.class);
                intent.putExtra("Chapter",position);
                intent.putExtra("ChapterName",(String) List.getItemAtPosition(position));
                startActivity(intent);
                Toast.makeText(getBaseContext(),"123", Toast.LENGTH_LONG).show();
            }
        });
    }
}

