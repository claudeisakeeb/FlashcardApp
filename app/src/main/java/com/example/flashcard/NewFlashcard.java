package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class NewFlashcard extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_flashcard);

        String currentQuestion = getIntent().getStringExtra("current_question");
        String currentAnswer = getIntent().getStringExtra("current_answer");
        String FWA = getIntent().getStringExtra("first_wrong_answer");
        String SWA = getIntent().getStringExtra("second_wrong_answer");
        ((EditText) findViewById(R.id.new_card_question)).setText(currentQuestion);
        ((EditText) findViewById(R.id.new_card_answer)).setText(currentAnswer);
        ((EditText) findViewById(R.id.new_card_wrong_answer1)).setText(FWA);
        ((EditText) findViewById(R.id.new_card_wrong_answer2)).setText(SWA);

        findViewById(R.id.exit_new_flashcard).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                finish();
            }
        });

        findViewById(R.id.save_new_flashcard).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                String newQuestion = ((EditText) findViewById(R.id.new_card_question)).getText().toString();
                String newAnswer = ((EditText) findViewById(R.id.new_card_answer)).getText().toString();
                String firstWrongAnswer = ((EditText) findViewById(R.id.new_card_wrong_answer1)).getText().toString();
                String secondWrongAnswer = ((EditText) findViewById(R.id.new_card_wrong_answer2)).getText().toString();
                if (newQuestion.equals("") || newAnswer.equals("") || firstWrongAnswer.equals("") || secondWrongAnswer.equals("")) {
                    Toast toast = Toast.makeText(getApplicationContext(), "Invalid input: empty string(s)", Toast.LENGTH_LONG);
                    toast.setGravity(Gravity.CENTER_VERTICAL, 0, 0);
                    toast.show();
                } else {
                    Intent data = new Intent();
                    data.putExtra("new_question", newQuestion);
                    data.putExtra("new_answer", newAnswer);
                    data.putExtra("first_wrong_answer", firstWrongAnswer);
                    data.putExtra("second_wrong_answer", secondWrongAnswer);
                    setResult(RESULT_OK, data);
                    finish();
                }
            }
        });
    }
}