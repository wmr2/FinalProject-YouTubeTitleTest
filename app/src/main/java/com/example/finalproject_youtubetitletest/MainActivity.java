package com.example.finalproject_youtubetitletest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Establish entities
        Button clearButton = findViewById(R.id.clearButton);
        Button submitButton = findViewById(R.id.submitButton);
        TextView inputBox = findViewById(R.id.inputBox);
        String emptyString = "";

        // Update YouTube data
        callYouTubeAPI();

        // Button clear text from box
        clearButton.setOnClickListener(v -> inputBox.setText(emptyString));

        // Button submit text
        submitButton.setOnClickListener(v -> submitTitle());
    }

    private void submitTitle() {
        // For testing
        TextView inputBox = findViewById(R.id.inputBox);
        String inputTitle = inputBox.getText().toString();
        double score;
        if (inputTitle.equals("good")) {
            score = 90.0;
        } else if (inputTitle.equals("bad")) {
            score = 40.0;
        } else {
            score = 0.0;
        }
        // -----------

        TextView scoreText = findViewById(R.id.scoreText);
        String finalScore = Double.toString(score) + "%";
        scoreText.setText(finalScore);
        setBoxColor(score);
    }

    private void setBoxColor(double score) {
        // Initiate result box
        LinearLayout resultBox = findViewById(R.id.resultBox);

        // Initiate colors
        int purple = Color.parseColor("#ffaa66cc");
        int darkGreen = Color.parseColor("#ff669900");
        int lightGreen = Color.parseColor("#ff99cc00");
        int yellow = Color.parseColor("#ffffbb33");
        int orange = Color.parseColor("#ffff8800");
        int red = Color.parseColor("#ffcc0000");

        // Handle score
        if (score >= 80.0) {
            resultBox.setBackgroundColor(darkGreen);
        } else if (score >= 60.0) {
            resultBox.setBackgroundColor(lightGreen);
        } else if (score >= 40.0) {
            resultBox.setBackgroundColor(yellow);
        } else if (score >= 20.0) {
            resultBox.setBackgroundColor(orange);
        } else if (score > 0.0){
            resultBox.setBackgroundColor(red);
        } else {
            resultBox.setBackgroundColor(purple);
        }
    }


}
