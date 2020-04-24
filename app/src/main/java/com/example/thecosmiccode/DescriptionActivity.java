package com.example.thecosmiccode;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.example.thecosmiccode.utils.AnimatedTextView;

public class DescriptionActivity extends AppCompatActivity {

    private AnimatedTextView description;
    private Button next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_description);

        description = findViewById(R.id.description);
        next = findViewById(R.id.next);

        description.setDuration(3000);
        description.setIsVisible(false);
        description.toggle();
        //description.setMovementMethod(new ScrollingMovementMethod());
    }

    public void onNextClick(View view) {
        description.setText(R.string.description2);
        description.setDuration(3000);
        description.setIsVisible(false);
        description.toggle();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(DescriptionActivity.this, EnterActivity.class);
                startActivity(intent);
            }
        });
    }
}
