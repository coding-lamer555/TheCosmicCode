package com.example.thecosmiccode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.util.Log;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thecosmiccode.utils.DBVoyages;

public class WelcomeActivity extends AppCompatActivity {

    public static MediaPlayer player;
    public static DBVoyages dbConnector;
    public static String currentUser;
    public static int currentProfit;
    private ConstraintLayout welcome;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        welcome = findViewById(R.id.welcome);

        if (player != null) {
            player.stop();
            player = null;
        }
        player = MediaPlayer.create(this, R.raw.music);
        player.setLooping(true);
        player.start();

        dbConnector = new DBVoyages(this);
    }

    public void onBeginClick(View view) {
        if (currentUser == null) {
            Intent intent = new Intent(this, DescriptionActivity.class);
            startActivity(intent);
        } else {
            Intent intent = new Intent(this, VoyageWorkerActivity.class);
            startActivity(intent);
        }
    }

    public void onInfoClick(View view) {
        new InfoDialog(this, welcome);
    }

    public void onSettingsClick(View view) {
        new SettingsDialog(this, welcome);
    }
}
