package com.jinish.oetielts;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import androidx.appcompat.app.AppCompatActivity;

public class HomeActivity extends AppCompatActivity {

    private LinearLayout freeMaterialsTile;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        freeMaterialsTile = findViewById(R.id.free_materials_tile);

        freeMaterialsTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(HomeActivity.this, FreeMaterialsActivity.class);
                startActivity(intent);
            }
        });

        Button subscribeButton = findViewById(R.id.subscribe_button);
        subscribeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Open the purchase page
                Intent intent = new Intent(HomeActivity.this, PurchaseActivity.class);
                startActivity(intent);
            }
        });

        // Community tile
        LinearLayout communityTile = findViewById(R.id.community_tile);
        communityTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch WhatsApp group
                String url = "https://chat.whatsapp.com/Kc9ktEhZKvVI2I22DdLq55";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        // Location tile
        LinearLayout locationTile = findViewById(R.id.location);
        locationTile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Launch Google Maps with the address
                String address = "D15XAA6";
                String uri = "geo:0,0?q=" + address;
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
                intent.setPackage("com.google.android.apps.maps");
                if (intent.resolveActivity(getPackageManager()) != null) {
                    startActivity(intent);
                }
            }
        });


    }
}
