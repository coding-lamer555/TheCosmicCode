package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thecosmiccode.model.Object;
import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class VoyageSavingActivity extends AppCompatActivity {

    private ArrayList<Object> objects;
    private TextView voyageName;
    private EditText nameEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_saving);

        objects = (ArrayList<Object>) getIntent().getSerializableExtra("OBJECTS");

        voyageName = findViewById(R.id.voyageName);
        nameEdit = findViewById(R.id.nameEdit);
    }

    public void onSaveClick(View view) {
        String voyageNameValue = nameEdit.getText().toString();
        if (voyageNameValue.equals("")) {
            voyageName.setText("Название (номер) рейса не может быть пустым!");
        } else {
            Voyage voyage = new Voyage(-1, voyageNameValue, WelcomeActivity.currentUser, objects);
            WelcomeActivity.dbConnector.insert(voyage);

            Intent intent = new Intent(this, WelcomeActivity.class);
            startActivity(intent);
        }
    }
}
