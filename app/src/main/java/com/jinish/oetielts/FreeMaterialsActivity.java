package com.jinish.oetielts;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

public class FreeMaterialsActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_free_materials);

        // Set click listeners for each tile
        findViewById(R.id.reading_tile).setOnClickListener(this);
        findViewById(R.id.writing_tile).setOnClickListener(this);
        findViewById(R.id.listening_tile).setOnClickListener(this);
        findViewById(R.id.speaking_tile).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Intent intent;

        switch (v.getId()) {
            case R.id.reading_tile:
                // Start ReadingTileActivity
                intent = new Intent(this, ReadingTileActivity.class);
                startActivity(intent);
                break;
            case R.id.writing_tile:
                // Start WritingTileActivity
                intent = new Intent(this, WritingTileActivity.class);
                startActivity(intent);
                break;
            case R.id.listening_tile:
                // Start ListeningTileActivity
                intent = new Intent(this, ListeningActivity.class);
                startActivity(intent);
                break;
            case R.id.speaking_tile:
                // Start SpeakingTileActivity
                intent = new Intent(this, SpeakingTileActivity.class);
                startActivity(intent);
                break;
        }
    }
}
