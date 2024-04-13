package com.pro.math.EGE;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;

public class Shop extends MyAppCompatActivity {
    private void BuyEnded() {
        startActivity(new Intent(this,Shop.class).addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu},Title);

        ListView List = findViewById(R.id.list);
        List.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, ShopDataBase.ShopSubTopicsList.toArray()));
        List.setOnItemClickListener((parent, view, position, id) -> {
            if (Database.BuySubTopic(this,ShopDataBase.ShopTopicsList.get(position),ShopDataBase.ShopSubTopicsList.get(position))) {
                Toast.makeText(this,getResources().getString(R.string.successful_purchase),Toast.LENGTH_SHORT).show();
                BuyEnded();
            } else {
                Toast.makeText(this,getResources().getString(R.string.not_enough_points),Toast.LENGTH_SHORT).show();
            }
        });
    }
}