package com.example.finalproject_youtubetitletest;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.List;

import static com.example.finalproject_youtubetitletest.ParseData.getNames;

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
        //callAPI.main();

        // Button clear text from box
        clearButton.setOnClickListener(v -> inputBox.setText(emptyString));

        // Button submit text
        submitButton.setOnClickListener(v -> submitTitle());
    }

    private void submitTitle() {
        // For testing
        TextView inputBox = findViewById(R.id.inputBox);
        String inputTitle = inputBox.getText().toString();
        double score = 0;
        double scoreWordLength = 0;
        double scoreLength = 0;
        try {
            System.out.println("dude man");
            List<String> names = getNames(getAssets().open("Data.txt"));
            System.out.println(names);
            long length = 0;
            long words = 0;
            for (int i = 0; i < names.size(); i++) {
                length = length + names.get(i).length();
                words = words + names.get(i).split(" ").length;
            }
            double avgLength = (float) length / names.size();
            double avgWords = (float) words / names.size();
            double avgWordLength = avgLength / avgWords;

            // scoring for word length
            double inputWords = inputTitle.split(" ").length;
            double inputWordLength = (double) inputTitle.length() / inputWords ;
            System.out.println(inputWordLength);

            double scoreCalcWordLength = java.lang.Math.abs(inputWordLength - avgWordLength) / avgWordLength;
            if (scoreCalcWordLength > 1) {
                scoreCalcWordLength = 1;
            }
            scoreWordLength = 50 - 50 * scoreCalcWordLength;
            System.out.println(scoreCalcWordLength);
            System.out.println(scoreWordLength);

            double scoreCalcLength = java.lang.Math.abs(inputTitle.length() - avgLength) / avgLength;
            if (scoreCalcLength > 1) {
                scoreCalcLength = 1;
            }
            scoreLength = 50 - 50 * scoreCalcLength;


            score = scoreWordLength + scoreLength;
        } catch (Exception e) {
            e.printStackTrace();
            score = -1;
            scoreWordLength = -1;
            scoreLength = -1;
        } finally {
            TextView scoreText = findViewById(R.id.scoreText);
            score = ((double) java.lang.Math.round(score * 10)) / 10;
            String finalScore = Double.toString(score) + "%";
            scoreText.setText(finalScore);
            setBoxColor(score);

            TextView scoreTextTotalLength = findViewById(R.id.scoreTotalLength);
            scoreLength = ((double) java.lang.Math.round(scoreLength * 10)) / 10;
            finalScore = "Total Length Score: " + Double.toString(scoreLength) + "%";
            scoreTextTotalLength.setText(finalScore);

            TextView scoreTextWordLength = findViewById(R.id.scoreWordLength);
            scoreWordLength = ((double) java.lang.Math.round(scoreWordLength * 10)) / 10;
            finalScore = "Word Length Score: " + Double.toString(scoreWordLength) + "%";

            scoreTextWordLength.setText(finalScore);
        }
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
