package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

public class Theory extends MyAppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.theory);
        final Button MainMenu = findViewById(R.id.mainmenu);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu});

        String[] Chapters = getResources().getStringArray(R.array.Topics);

        ListView List = findViewById(R.id.list);

        ArrayAdapter<String> Adapter = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item, Chapters);
        List.setAdapter(Adapter);

        List.setOnItemClickListener((parent, view, position, id) -> {
            Intent intent = new Intent(Theory.this,TheoryActivity.class);
            intent.putExtra("Chapter",position);
            intent.putExtra("ChapterName",Chapters[position]);
            startActivity(intent);
        });
    }
}

