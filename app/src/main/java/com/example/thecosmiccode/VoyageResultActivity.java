package com.example.thecosmiccode;

import android.content.Intent;
import android.graphics.Point;
import android.os.Bundle;
import android.view.Display;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

public class VoyageResultActivity extends AppCompatActivity {

    private ScrollView scrollView;
    private RecyclerView recyclerView;
    private ObjectRVAdapter adapter;
    private ConstraintLayout topLayout;
    private ConstraintLayout recyclerViewLayout;
    private ConstraintLayout bottomLayout;
    private TextView result;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_voyage_result);

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

        String resultInfo = getResources().getString(R.string.voyage_result);
        StringBuilder stringBuilder = new StringBuilder(resultInfo);
        stringBuilder.insert(13, " (" + WelcomeActivity.currentProfit + ")");
        result.setText(stringBuilder.toString());

        //normalizeLayout();

        LinearLayoutManager llm = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(llm);
        recyclerView.setHasFixedSize(true);

        //initializeData();
        initializeAdapter();
    }

    private void initializeData() {

    }

    private void initializeAdapter() {
        adapter = new ObjectRVAdapter(WelcomeActivity.currentObjects);
        recyclerView.setAdapter(adapter);
    }

    private void normalizeLayout() {
        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;
        int needHeight = height;
        ConstraintLayout.LayoutParams topLayoutParams = (ConstraintLayout.LayoutParams) topLayout.getLayoutParams();
        needHeight -= topLayoutParams.height;
        ConstraintLayout.LayoutParams bottomLayoutParams = (ConstraintLayout.LayoutParams) bottomLayout.getLayoutParams();
        needHeight -= bottomLayoutParams.height;
        needHeight -= 16 + 16;
        ConstraintLayout.LayoutParams recyclerViewLayoutParams = (ConstraintLayout.LayoutParams) recyclerViewLayout.getLayoutParams();
        recyclerViewLayoutParams.height = needHeight;
        recyclerViewLayout.setLayoutParams(recyclerViewLayoutParams);
        ConstraintLayout.LayoutParams recyclerViewParams = (ConstraintLayout.LayoutParams) recyclerView.getLayoutParams();
        recyclerViewParams.height = needHeight;
        recyclerView.setLayoutParams(recyclerViewParams);
    }

    public void onGoBeginClick(View view) {
        Intent intent = new Intent(this, VoyageWorkerActivity.class);
        startActivity(intent);
    }

    public void onSaveClick(View view) {
        Intent intent = new Intent(this, VoyageSavingActivity.class);
        startActivity(intent);
    }
}
