package com.example.thecosmiccode;

import android.content.Intent;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import com.example.thecosmiccode.model.Object;
import com.example.thecosmiccode.utils.DBVoyages;

import java.util.ArrayList;

public class WelcomeActivity extends AppCompatActivity {

    public static DBVoyages dbConnector;
    public static String currentUser;
    public static ArrayList<Object> currentObjects;
    public static int currentProfit;
    public static MediaPlayer player;
    public static int currentMusicSpinnerIndex = 4;
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
        player = MediaPlayer.create(this, R.raw.undertale_ruins);
        player.setLooping(true);
        player.start();

        dbConnector = new DBVoyages(this);
    }

    public void onBeginClick(View view) {
        if (currentUser == null) {
            Intent intent = new Intent(this, DescriptionActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        } else {
            Intent intent = new Intent(this, VoyageWorkerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        }
    }

    public void onInfoClick(View view) {
        new InfoDialog(this, welcome);
    }

    public void onSettingsClick(View view) {
        new SettingsDialog(this, welcome);
    }
}
