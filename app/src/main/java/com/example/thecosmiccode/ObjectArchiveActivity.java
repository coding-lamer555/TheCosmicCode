package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.thecosmiccode.model.Voyage;

public class ObjectArchiveActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ObjectRVAdapter adapter;
    private ScrollView scrollView;
    private ConstraintLayout topLayout;
    private ConstraintLayout recyclerViewLayout;
    private ConstraintLayout bottomLayout;
    private TextView result;
    private Voyage voyage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_object_archive);

        voyage = (Voyage) getIntent().getSerializableExtra("VOYAGE");

        scrollView = findViewById(R.id.scrollView);
        topLayout = findViewById(R.id.top_layout);
        recyclerViewLayout = findViewById(R.id.rv_layout);
        bottomLayout = findViewById(R.id.bottom_layout);
        recyclerView = findViewById(R.id.recycler_view);
        result = findViewById(R.id.result);

        scrollView.postDelayed(new Runnable() {
            public void run() {
                scrollView.fullScroll(ScrollView.FOCUS_UP);
            }
        }, 0);

        String resultText = "Рейс «" + voyage.getName() + "», как это было:";
        result.setText(resultText);

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        initializeAdapter();
    }

    private void initializeAdapter() {
        adapter = new ObjectRVAdapter(voyage.getObjects());
        recyclerView.setAdapter(adapter);
    }

    public void onBackClick(View view) {
        Intent intent = new Intent(this, VoyageArchiveActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.left_in, R.anim.right_out);
    }
}
