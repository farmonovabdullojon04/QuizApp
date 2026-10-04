package com.abdullojon.quizapp;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullojon.quizapp.data.UserPreferences;
import com.abdullojon.quizapp.start.view.StartActivity;
import com.abdullojon.quizapp.view.CircularScoreView;

import java.util.ArrayList;

public class ResultActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_result);

        View mainView = findViewById(R.id.main);
        if (mainView != null) {
            ViewCompat.setOnApplyWindowInsetsListener(mainView, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        CircularScoreView scoreViewResult = findViewById(R.id.scoreViewResult);
        TextView tvWrongTitle = findViewById(R.id.tvWrongTitle);
        LinearLayout layoutWrongAnswers = findViewById(R.id.layoutWrongAnswers);
        Button btnHome = findViewById(R.id.btnHome);
        Button btnShare = findViewById(R.id.btnShare);

        final int correct = getIntent().getIntExtra("CORRECT", 0);
        final int total = getIntent().getIntExtra("TOTAL", 10);
        final int categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        ArrayList<Integer> wrongQuestions = getIntent().getIntegerArrayListExtra("WRONG_QUESTIONS");
        ArrayList<Integer> correctAnswers = getIntent().getIntegerArrayListExtra("CORRECT_ANSWERS");

        // Award Diamonds, Save Category Score & Update Recent Activity
        int earnedDiamonds = correct * 10 + (correct == total && total > 0 ? 20 : 0);
        UserPreferences userPreferences = new UserPreferences(this);
        userPreferences.addDiamonds(earnedDiamonds);
        userPreferences.setCategoryScore(categoryIndex, correct);
        userPreferences.addRecentCategory(categoryIndex);

        if (earnedDiamonds > 0) {
            Toast.makeText(this, "+" + earnedDiamonds + " 💎 Olmos ishlandingiz!", Toast.LENGTH_LONG).show();
        }

        if (scoreViewResult != null) {
            scoreViewResult.setScore(correct, total);
        }

        if (wrongQuestions != null && correctAnswers != null && !wrongQuestions.isEmpty() && layoutWrongAnswers != null) {
            if (tvWrongTitle != null) {
                tvWrongTitle.setVisibility(View.VISIBLE);
            }

            for (int i = 0; i < wrongQuestions.size(); i++) {
                LinearLayout itemCard = new LinearLayout(this);
                itemCard.setOrientation(LinearLayout.VERTICAL);
                itemCard.setBackgroundResource(R.drawable.bg_option_unselected);
                itemCard.setPadding(24, 24, 24, 24);

                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LinearLayout.LayoutParams.MATCH_PARENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
                );
                params.setMargins(0, 0, 0, 16);
                itemCard.setLayoutParams(params);

                TextView tvQuestion = new TextView(this);
                String questionText = getString(wrongQuestions.get(i));
                tvQuestion.setText((i + 1) + ". " + questionText);
                tvQuestion.setTextSize(14f);
                tvQuestion.setTextColor(Color.parseColor("#1E293B"));
                tvQuestion.setTypeface(null, Typeface.BOLD);

                TextView tvAnswer = new TextView(this);
                String answerText = getString(correctAnswers.get(i));
                tvAnswer.setText("To'g'ri javob: " + answerText);
                tvAnswer.setTextSize(13f);
                tvAnswer.setTextColor(Color.parseColor("#10B981"));
                tvAnswer.setPadding(0, 8, 0, 0);

                itemCard.addView(tvQuestion);
                itemCard.addView(tvAnswer);
                layoutWrongAnswers.addView(itemCard);
            }
        }

        if (btnHome != null) {
            btnHome.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ResultActivity.this, StartActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    finish();
                }
            });
        }

        if (btnShare != null) {
            btnShare.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent shareIntent = new Intent(Intent.ACTION_SEND);
                    shareIntent.setType("text/plain");
                    String shareText = "Men Mantiqiy Testlar ilovasida " + total + " tadan " + correct + " ta to'g'ri topdim va +" + earnedDiamonds + " 💎 olmos ishlandim!";
                    shareIntent.putExtra(Intent.EXTRA_TEXT, shareText);
                    startActivity(Intent.createChooser(shareIntent, "Natijani ulashish"));
                }
            });
        }
    }
}