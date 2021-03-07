package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.snackbar.Snackbar;

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

        // User can add a new flashcard
        findViewById(R.id.add_flashcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewFlashcard.class);
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });

        // User can edit current flashcard
        findViewById(R.id.edit_flashcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, NewFlashcard.class);
                intent.putExtra("current_question", ((TextView) findViewById(R.id.question)).getText().toString());
                intent.putExtra("current_answer", ((TextView) findViewById(R.id.real_answer)).getText().toString());
                intent.putExtra("first_wrong_answer", ((TextView) findViewById(R.id.answer1)).getText().toString());
                intent.putExtra("second_wrong_answer", ((TextView) findViewById(R.id.answer2)).getText().toString());
                MainActivity.this.startActivityForResult(intent, 100);
            }
        });
    }
    // Set the new question and answer
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String newQuestion = data.getExtras().getString("new_question"); // 'string1' needs to match the key we used when we put the string in the Intent
            String newAnswer = data.getExtras().getString("new_answer");
            String firstWrongAnswer = data.getExtras().getString("first_wrong_answer");
            String secondWrongAnswer = data.getExtras().getString("second_wrong_answer");
            ((TextView) findViewById(R.id.question)).setText(newQuestion);
            ((TextView) findViewById(R.id.real_answer)).setText(newAnswer);
            ((TextView) findViewById(R.id.answer1)).setText(firstWrongAnswer);
            ((TextView) findViewById(R.id.answer2)).setText(secondWrongAnswer);
            ((TextView) findViewById(R.id.answer3)).setText(newAnswer);
            Snackbar.make(findViewById(R.id.question),
                    "Successfully created flashcard",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}