package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;

public class EnterActivity extends AppCompatActivity {

    private TextView enterName;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enter);

        enterName = findViewById(R.id.enterName);
        nameEdit = findViewById(R.id.nameEdit);
    }

    public void onNextClick(View view) {
        if (nameEdit.getText().toString().equals("")) {
            enterName.setText(R.string.invalid_name);
        } else {
            WelcomeActivity.currentUser = nameEdit.getText().toString();
            Intent intent = new Intent(this, VoyageWorkerActivity.class);
            startActivity(intent);
            overridePendingTransition(R.anim.bottom_in, R.anim.top_out);
        }
    }
}
