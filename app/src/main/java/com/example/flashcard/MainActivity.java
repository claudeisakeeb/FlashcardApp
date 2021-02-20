package com.example.flashcard;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final boolean[] isShowingAnswers = {true};

        // User can click on question to display answer
        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.question)).setVisibility(View.INVISIBLE);
                ((TextView) findViewById(R.id.real_answer)).setVisibility(View.VISIBLE); // Make answer visible
            }
        });

        // User can click on answer to display question again
        findViewById(R.id.real_answer).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.real_answer)).setVisibility(View.INVISIBLE);
                ((TextView) findViewById(R.id.question)).setVisibility(View.VISIBLE); // Make answer visible
            }
        });

        // If user chooses friday, it's wrong
        findViewById(R.id.answer1).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        // If user chooses saturday, it's wrong
        findViewById(R.id.answer2).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.red));
            }
        });

        // If user chooses sunday, it's correct
        // If user chooses friday, it's wrong
        findViewById(R.id.answer3).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.green));
            }
        });

        // Reset answer colors when background is clicked
        findViewById(R.id.parent).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.orange));
                ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.orange));
                ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.orange));
            }
        });

        // Hide answer options
        findViewById(R.id.toggle_choices_visibility).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (isShowingAnswers[0]) {
                    ((TextView) findViewById(R.id.answer1)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.answer2)).setVisibility(View.INVISIBLE);
                    ((TextView) findViewById(R.id.answer3)).setVisibility(View.INVISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.show_visibility);
                } else {
                    ((TextView) findViewById(R.id.answer1)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.answer2)).setVisibility(View.VISIBLE);
                    ((TextView) findViewById(R.id.answer3)).setVisibility(View.VISIBLE);
                    ((ImageView) findViewById(R.id.toggle_choices_visibility)).setImageResource(R.drawable.hide_visibility);
                }
                isShowingAnswers[0] = !isShowingAnswers[0];
            }
        });
    }
}