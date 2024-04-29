package com.pro.math.EGE;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

public class Shop extends MyAppCompatActivity {
    private ListView List;
    private void SetupList() {
        List.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, Database.GetShop(this).toArray()));
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.shop);

        final Button MainMenu = findViewById(R.id.mainmenu);
        final TextView Title = findViewById(R.id.title);
        final Button Points = findViewById(R.id.points);
        super.BackToMainMenu(MainMenu);
        super.SetSizes(new Button[]{MainMenu,Points},Title);

        super.ChangePoints(Points);

        List = findViewById(R.id.list);
        SetupList();
        List.setOnItemClickListener((parent, view, position, id) -> {
            int[] attributes = Database.ShopAttributes.get(position);
            if (Database.BuySubTopic(this,attributes[0],attributes[1])) {
                Toast.makeText(this,getResources().getString(R.string.successful_purchase),Toast.LENGTH_SHORT).show();
                SetupList();
            } else
                Toast.makeText(this,getResources().getString(R.string.not_enough_points),Toast.LENGTH_SHORT).show();
        });
    }
}