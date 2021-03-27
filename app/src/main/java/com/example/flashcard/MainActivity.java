package com.example.flashcard;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.flashcard.lab3.Flashcard;
import com.example.flashcard.lab3.FlashcardDatabase;
import com.google.android.material.snackbar.Snackbar;
import com.plattysoft.leonids.ParticleSystem;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    // flashcard database
    FlashcardDatabase flashcardDatabase;
    // list of all flashcards from the database
    List<Flashcard> allFlashcards;
    // Current flashcard index
    int index = 0;
    // Showing question or answer
    boolean isShowingQuestion = true;
    //Countdown for each flashcard
    CountDownTimer countDownTimer;

    private void startTimer() {
        countDownTimer.cancel();
        countDownTimer.start();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize countdown timer
        countDownTimer = new CountDownTimer(16000, 1000) {
            public void onTick(long millisUntilFinished) {
                ((TextView) findViewById(R.id.timer)).setText("" + millisUntilFinished / 1000);
            }

            public void onFinish() {
            }
        };

        startTimer();

        // Initialize flashcard database
        flashcardDatabase = new FlashcardDatabase(getApplicationContext());
        // Get all flashcards from the database
        allFlashcards = flashcardDatabase.getAllCards();
        if (allFlashcards.size() > 0) {
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(0).getQuestion());
            ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(0).getAnswer());
            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(0).getWrongAnswer1());
            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(0).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(0).getAnswer());
        } else {
            ((TextView) findViewById(R.id.question)).setText("Add a flashcard to get started!");
            ((TextView) findViewById(R.id.real_answer)).setText("Sample answer");
            ((TextView) findViewById(R.id.answer1)).setText("Sample answer");
            ((TextView) findViewById(R.id.answer2)).setText("Sample answer");
            ((TextView) findViewById(R.id.answer3)).setText("Sample answer");
        }

        final boolean[] isShowingAnswers = {true};

        // User can click on question to display answer
        findViewById(R.id.question).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                View questionSideView = findViewById(R.id.question);
                View answerSideView = findViewById(R.id.real_answer);

                questionSideView.setCameraDistance(25000);
                answerSideView.setCameraDistance(25000);

                questionSideView.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        questionSideView.setVisibility(View.INVISIBLE);
                                        answerSideView.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        answerSideView.setRotationY(-90);
                                        answerSideView.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();

//                questionSideView.setVisibility(View.INVISIBLE);
//                answerSideView.setVisibility(View.VISIBLE);
                isShowingQuestion = false;
            }
        });

        // User can click on answer to display question again
        findViewById(R.id.real_answer).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                View questionSideView = findViewById(R.id.question);
                View answerSideView = findViewById(R.id.real_answer);

                questionSideView.setCameraDistance(25000);
                answerSideView.setCameraDistance(25000);

                answerSideView.animate()
                        .rotationY(90)
                        .setDuration(200)
                        .withEndAction(
                                new Runnable() {
                                    @Override
                                    public void run() {
                                        answerSideView.setVisibility(View.INVISIBLE);
                                        questionSideView.setVisibility(View.VISIBLE);
                                        // second quarter turn
                                        questionSideView.setRotationY(-90);
                                        questionSideView.animate()
                                                .rotationY(0)
                                                .setDuration(200)
                                                .start();
                                    }
                                }
                        ).start();

                isShowingQuestion = true;
            }
        });

        // User can go to previous flashcard
        findViewById(R.id.previous_icon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (index > 0) {

                    final Animation leftInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_in);
                    final Animation rightOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_out);

                    rightOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            if (isShowingAnswers[0]) {
                                findViewById(R.id.answer1).startAnimation(rightOutAnim);
                                findViewById(R.id.answer2).startAnimation(rightOutAnim);
                                findViewById(R.id.answer3).startAnimation(rightOutAnim);
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animation animation){
                            findViewById(R.id.question).startAnimation(leftInAnim);
                            if (isShowingAnswers[0]) {
                                findViewById(R.id.answer1).startAnimation(leftInAnim);
                                findViewById(R.id.answer2).startAnimation(leftInAnim);
                                findViewById(R.id.answer3).startAnimation(leftInAnim);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // we don't need to worry about this method
                        }
                    });

                    leftInAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            if (!isShowingQuestion) {
                                ((TextView) findViewById(R.id.real_answer)).setVisibility(View.INVISIBLE);
                                ((TextView) findViewById(R.id.question)).setVisibility(View.VISIBLE);
                                isShowingQuestion = true;
                            }
                            index --;
                            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(index).getQuestion());
                            ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(index).getAnswer());
                            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(index).getWrongAnswer1());
                            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(index).getWrongAnswer2());
                            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(index).getAnswer());
                            ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.orange));
                            ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.orange));
                            ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.orange));
                        }

                        @Override
                        public void onAnimationEnd(Animation animation){
                            startTimer();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // we don't need to worry about this method
                        }
                    });
                    if (!isShowingQuestion) {
                        ((TextView) findViewById(R.id.real_answer)).setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.question)).setVisibility(View.VISIBLE);
                        isShowingQuestion = true;
                    }
                    findViewById(R.id.question).startAnimation(rightOutAnim);
                }
            }
        });

        // User can delete current flashcard
        findViewById(R.id.remove_flashcard).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {
                if (allFlashcards.size() > 0) {
                    flashcardDatabase.deleteCard(allFlashcards.get(index).getQuestion());
                    allFlashcards = flashcardDatabase.getAllCards();
                    if (allFlashcards.size() > 0) {
                        ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(0).getQuestion());
                        ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(0).getAnswer());
                        ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(0).getWrongAnswer1());
                        ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(0).getWrongAnswer2());
                        ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(0).getAnswer());
                        ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.orange));
                        ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.orange));
                        ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.orange));
                    } else {
                        ((TextView) findViewById(R.id.question)).setText("Add a flashcard to get started!");
                        ((TextView) findViewById(R.id.real_answer)).setText("Sample answer");
                        ((TextView) findViewById(R.id.answer1)).setText("Sample answer");
                        ((TextView) findViewById(R.id.answer2)).setText("Sample answer");
                        ((TextView) findViewById(R.id.answer3)).setText("Sample answer");
                        ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.orange));
                        ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.orange));
                        ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.orange));
                    }
                }
            }
        });

        // User can go to next flashcard
        findViewById(R.id.next_icon).setOnClickListener(new View.OnClickListener() {
            public void onClick(View view) {

                if (index < allFlashcards.size() - 1) {
                    final Animation leftOutAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.left_out);
                    final Animation rightInAnim = AnimationUtils.loadAnimation(view.getContext(), R.anim.right_in);

                    rightInAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            index++;
                            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(index).getQuestion());
                            ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(index).getAnswer());
                            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(index).getWrongAnswer1());
                            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(index).getWrongAnswer2());
                            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(index).getAnswer());
                            ((TextView) findViewById(R.id.answer1)).setBackgroundColor(getResources().getColor(R.color.orange));
                            ((TextView) findViewById(R.id.answer2)).setBackgroundColor(getResources().getColor(R.color.orange));
                            ((TextView) findViewById(R.id.answer3)).setBackgroundColor(getResources().getColor(R.color.orange));
                        }

                        @Override
                        public void onAnimationEnd(Animation animation){
                            startTimer();
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // we don't need to worry about this method
                        }
                    });

                    leftOutAnim.setAnimationListener(new Animation.AnimationListener() {
                        @Override
                        public void onAnimationStart(Animation animation) {
                            if (isShowingAnswers[0]) {
                                findViewById(R.id.answer1).startAnimation(leftOutAnim);
                                findViewById(R.id.answer2).startAnimation(leftOutAnim);
                                findViewById(R.id.answer3).startAnimation(leftOutAnim);
                            }
                        }

                        @Override
                        public void onAnimationEnd(Animation animation){
                            findViewById(R.id.question).startAnimation(rightInAnim);
                            if (isShowingAnswers[0]) {
                                findViewById(R.id.answer1).startAnimation(rightInAnim);
                                findViewById(R.id.answer2).startAnimation(rightInAnim);
                                findViewById(R.id.answer3).startAnimation(rightInAnim);
                            }
                        }

                        @Override
                        public void onAnimationRepeat(Animation animation) {
                            // we don't need to worry about this method
                        }
                    });
                    if (!isShowingQuestion) {
                        ((TextView) findViewById(R.id.real_answer)).setVisibility(View.INVISIBLE);
                        ((TextView) findViewById(R.id.question)).setVisibility(View.VISIBLE);
                        isShowingQuestion = true;
                    }
                    findViewById(R.id.question).startAnimation(leftOutAnim);
                }
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
                new ParticleSystem(MainActivity.this, 100, R.drawable.confetti, 3000)
                        .setSpeedRange(0.2f, 0.5f)
                        .oneShot(findViewById(R.id.answer3), 100);
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
                startActivityForResult(intent, 100);
                overridePendingTransition(R.anim.right_in, R.anim.left_out);
            }
        });

        // User can edit current flashcard
        findViewById(R.id.edit_flashcard).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (allFlashcards.size() > 0) {
                    Intent intent = new Intent(MainActivity.this, NewFlashcard.class);
                    intent.putExtra("current_question", ((TextView) findViewById(R.id.question)).getText().toString());
                    intent.putExtra("current_answer", ((TextView) findViewById(R.id.real_answer)).getText().toString());
                    intent.putExtra("first_wrong_answer", ((TextView) findViewById(R.id.answer1)).getText().toString());
                    intent.putExtra("second_wrong_answer", ((TextView) findViewById(R.id.answer2)).getText().toString());
                    MainActivity.this.startActivityForResult(intent, 120);
                }
            }
        });
    }


    // Set the new question and answer
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        // User just added a card
        if (resultCode == RESULT_OK && requestCode == 100) { // this 100 needs to match the 100 we used when we called startActivityForResult!
            String newQuestion = data.getExtras().getString("new_question");
            String newAnswer = data.getExtras().getString("new_answer");
            String firstWrongAnswer = data.getExtras().getString("first_wrong_answer");
            String secondWrongAnswer = data.getExtras().getString("second_wrong_answer");
            // Insert a new flashcard
            flashcardDatabase.insertCard(new Flashcard(newQuestion, newAnswer, firstWrongAnswer, secondWrongAnswer));
            // Get all flashcards from the database
            allFlashcards = flashcardDatabase.getAllCards();
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(allFlashcards.size()-1).getQuestion());
            ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(allFlashcards.size()-1).getAnswer());
            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(allFlashcards.size()-1).getWrongAnswer1());
            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(allFlashcards.size()-1).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(allFlashcards.size()-1).getAnswer());
            index = allFlashcards.size()-1;
            Snackbar.make(findViewById(R.id.question),
                    "Successfully created flashcard",
                    Snackbar.LENGTH_SHORT)
                    .show();
        // User just edited a card
        } else if (resultCode == RESULT_OK && requestCode == 120) {
            String newQuestion = data.getExtras().getString("new_question");
            String newAnswer = data.getExtras().getString("new_answer");
            String firstWrongAnswer = data.getExtras().getString("first_wrong_answer");
            String secondWrongAnswer = data.getExtras().getString("second_wrong_answer");
            allFlashcards.get(index).setQuestion(newQuestion);
            allFlashcards.get(index).setAnswer(newAnswer);
            allFlashcards.get(index).setWrongAnswer1(firstWrongAnswer);
            allFlashcards.get(index).setWrongAnswer2(secondWrongAnswer);
            flashcardDatabase.updateCard(allFlashcards.get(index));
            allFlashcards = flashcardDatabase.getAllCards();
            ((TextView) findViewById(R.id.question)).setText(allFlashcards.get(index).getQuestion());
            ((TextView) findViewById(R.id.real_answer)).setText(allFlashcards.get(index).getAnswer());
            ((TextView) findViewById(R.id.answer1)).setText(allFlashcards.get(index).getWrongAnswer1());
            ((TextView) findViewById(R.id.answer2)).setText(allFlashcards.get(index).getWrongAnswer2());
            ((TextView) findViewById(R.id.answer3)).setText(allFlashcards.get(index).getAnswer());
            Snackbar.make(findViewById(R.id.question),
                    "Successfully edited flashcard",
                    Snackbar.LENGTH_SHORT)
                    .show();
        }
    }
}