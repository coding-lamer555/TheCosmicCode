package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thecosmiccode.model.Object;

import java.util.ArrayList;
import java.util.Collections;

public class VoyageAdderActivity extends AppCompatActivity {

    private ArrayList<Object> objects = new ArrayList<>();
    private EditText nameEdit;
    private EditText weightEdit;
    private EditText costEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_adder);

        nameEdit = findViewById(R.id.nameEdit);
        weightEdit = findViewById(R.id.weightEdit);
        costEdit = findViewById(R.id.costEdit);
    }

    public void onAddClick(View view) {
        if (nameEdit.getText().toString().equals("") || weightEdit.getText().toString().equals("") || costEdit.getText().toString().equals("")) {
            openWarning(getResources().getString(R.string.not_blank));
        } else if (Integer.parseInt(weightEdit.getText().toString()) > 30) {
            openWarning(WelcomeActivity.currentUser + getResources().getString(R.string.too_much_weight));
        } else {
            try {
                objects.add(new Object(nameEdit.getText().toString(), Integer.parseInt(weightEdit.getText().toString()), Integer.parseInt(costEdit.getText().toString())));
                nameEdit.setText("");
                weightEdit.setText("");
                costEdit.setText("");
            } catch (NumberFormatException exception) {
                openWarning(WelcomeActivity.currentUser + getResources().getString(R.string.incorrect_data));
            }
        }
    }

    public void onCalculateClick(View view) {
        calc();
    }

    public void openWarning(String warning) {
        Intent intent = new Intent(this, VoyageWarningActivity.class);
        intent.putExtra("WARNING", warning);
        startActivity(intent);
    }

    public void calc() {
        int count = objects.size();
        int maxWeight = 30;

        int[] weights = new int[count + 1];
        int[] costs = new int[count + 1];
        for (int i = 1; i <= count; i++) {
            weights[i] = objects.get(i - 1).getWeight();
        }
        for (int i = 1; i <= count; i++) {
            costs[i] = objects.get(i - 1).getCost();
        }

        int[][] table = new int[count + 1][maxWeight + 1];
        for (int i = 1; i <= count; i++) {
            for (int j = 1; j <= maxWeight; j++) {
                int min = table[i - 1][j];
                int max;
                if (j >= weights[i]) {
                    max = table[i - 1][j - weights[i]] + costs[i];
                } else {
                    max = 0;
                }
                table[i][j] = Math.max(min, max);
            }
        }

        if (table[count][maxWeight] < 500) {
            openWarning(getResources().getString(R.string.small_profit));
        } else {
            WelcomeActivity.currentProfit = table[count][maxWeight];

            ArrayList<Integer> objectIds = new ArrayList<>();
            for (int i = count; i >= 1; i--) {
                if (table[i][maxWeight] != table[i - 1][maxWeight]) {
                    objectIds.add(i);
                }
            }
            Collections.reverse(objectIds);

            ArrayList<Object> resultObjects = new ArrayList<>();
            for (int i = 0; i < objectIds.size(); i++) {
                resultObjects.add(new Object(objects.get(objectIds.get(i) - 1).getName(), objects.get(objectIds.get(i) - 1).getWeight(), objects.get(objectIds.get(i) - 1).getCost()));
            }

            WelcomeActivity.currentObjects = resultObjects;

            Intent intent = new Intent(this, VoyageResultActivity.class);
            startActivity(intent);
        }
    }
}
