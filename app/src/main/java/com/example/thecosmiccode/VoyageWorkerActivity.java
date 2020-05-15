package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VoyageWorkerActivity extends AppCompatActivity {

    private TextView enterName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_worker);

        enterName = findViewById(R.id.enterName);
        String enterNameText = WelcomeActivity.currentUser + getResources().getString(R.string.voyage_info);
        enterName.setText(enterNameText);
    }

    public void onVoyageNewClick(View view) {
        Intent intent = new Intent(this, VoyageAdderActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void onVoyageArchiveClick(View view) {
        Intent intent = new Intent(this, VoyageArchiveActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.right_in, R.anim.left_out);
    }

    public void onGoBeginClick(View view) {
        Intent intent = new Intent(this, WelcomeActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.top_in, R.anim.bottom_out);
    }
}
