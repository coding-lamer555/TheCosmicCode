package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecosmiccode.model.Voyage;

import java.util.ArrayList;

public class VoyageArchiveActivity extends AppCompatActivity {

    private ArrayList<Voyage> voyages;
    private RecyclerView recyclerView;
    private VoyageRVAdapter adapter;
    private ConstraintLayout topLayout;
    private ConstraintLayout recyclerViewLayout;
    private ConstraintLayout bottomLayout;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_archive);

        topLayout = findViewById(R.id.top_layout);
        recyclerViewLayout = findViewById(R.id.rv_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        recyclerView = findViewById(R.id.recycler_view);
        result = findViewById(R.id.result);

        String resultString = getResources().getString(R.string.user_voyages) + " " + WelcomeActivity.currentUser + ":";
        result.setText(resultString);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initializeData();
        initializeAdapter();
    }

    private void initializeData() {
        voyages = new ArrayList<>();
        voyages = WelcomeActivity.dbConnector.selectAllByUserName(WelcomeActivity.currentUser);
    }

    private void initializeAdapter() {
        adapter = new VoyageRVAdapter(voyages, this);
        recyclerView.setAdapter(adapter);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, VoyageWorkerActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
