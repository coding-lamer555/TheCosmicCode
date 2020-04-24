package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class VoyageWarningActivity extends AppCompatActivity {

    private TextView warning;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_warning);

        warning = findViewById(R.id.warning);
        warning.setText(getIntent().getStringExtra("WARNING"));
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, VoyageAdderActivity.class);
        startActivity(intent);
    }
}
