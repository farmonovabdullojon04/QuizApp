package com.abdullojon.quizapp.quiz.view;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import com.abdullojon.quizapp.R;
import com.abdullojon.quizapp.ResultActivity;
import com.abdullojon.quizapp.data.UserPreferences;
import com.abdullojon.quizapp.quiz.QuizContract;
import com.abdullojon.quizapp.quiz.presenter.QuizPresenterImpl;

import java.util.ArrayList;
import java.util.List;

public class QuizActivity extends AppCompatActivity implements QuizContract.View {
    private TextView question;
    private RadioGroup radioGroup;
    private QuizContract.Presenter presenter;
    private Button nextButton;
    private Button skipButton;
    private Button prevButton;
    private View btnBack;
    private View btnQuit;
    private View btnHint;
    private TextView tvCategoryTitle;
    private TextView tvUserDiamonds;

    private TextView tvProgressText;
    private ProgressBar progressBar;
    private UserPreferences userPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_quiz);

        userPreferences = new UserPreferences(this);

        boolean isResume = getIntent().getBooleanExtra("IS_RESUME", false);
        int categoryIndex = getIntent().getIntExtra("CATEGORY_INDEX", 0);
        String categoryName = getIntent().getStringExtra("CATEGORY_NAME");

        init();

        if (categoryName != null && tvCategoryTitle != null) {
            tvCategoryTitle.setText(categoryName);
        }

        presenter = new QuizPresenterImpl(this, isResume, categoryIndex);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });
    }

    private void init() {
        radioGroup = findViewById(R.id.groups);
        question = findViewById(R.id.txtQuestion);
        nextButton = findViewById(R.id.btnNext);
        skipButton = findViewById(R.id.btnSkip);
        prevButton = findViewById(R.id.btnPrev);
        btnBack = findViewById(R.id.btnBack);
        btnQuit = findViewById(R.id.btnQuit);
        btnHint = findViewById(R.id.btnHint);
        tvCategoryTitle = findViewById(R.id.tvCategoryTitle);
        tvUserDiamonds = findViewById(R.id.tvUserDiamonds);

        tvProgressText = findViewById(R.id.tvProgressText);
        progressBar = findViewById(R.id.progressBar);

        updateDiamondsDisplay();

        if (nextButton != null) {
            nextButton.setOnClickListener(v -> presenter.next());
        }
        if (skipButton != null) {
            skipButton.setOnClickListener(v -> presenter.onSkip());
        }
        if (prevButton != null) {
            prevButton.setOnClickListener(v -> presenter.previous());
        }
        if (btnBack != null) {
            btnBack.setOnClickListener(v -> finish());
        }
        if (btnQuit != null) {
            btnQuit.setOnClickListener(v -> finish());
        }
        if (btnHint != null) {
            btnHint.setOnClickListener(v -> presenter.onUseHint());
        }

        if (radioGroup != null) {
            radioGroup.setOnCheckedChangeListener((group, checkedId) -> {
                for (int i = 0; i < group.getChildCount(); i++) {
                    View child = group.getChildAt(i);
                    if (child instanceof RadioButton) {
                        RadioButton rb = (RadioButton) child;
                        if (rb.getId() == checkedId) {
                            rb.setTextColor(Color.WHITE);
                        } else {
                            rb.setTextColor(Color.parseColor("#1E293B"));
                        }
                    }
                }
            });
        }
    }

    private void updateDiamondsDisplay() {
        if (tvUserDiamonds != null && userPreferences != null) {
            tvUserDiamonds.setText(String.valueOf(userPreferences.getDiamonds()));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        updateDiamondsDisplay();
    }

    @Override
    public void loadQuiz(int quiz) {
        if (question != null) {
            question.setText(quiz);
        }
    }

    @Override
    public void loadVariants(List<Integer> variants) {
        if (radioGroup == null) return;
        for (int i = 0; i < variants.size() && i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                radioButton.setText(variants.get(i));
                radioButton.setTag(i);
                radioButton.setOnClickListener(v -> presenter.selectedAnswer((int) v.getTag()));
            }
        }
    }

    @Override
    public void stateNextButton(boolean state) {
        if (nextButton != null) {
            nextButton.setEnabled(state);
            nextButton.setAlpha(state ? 1.0f : 0.5f);
        }
    }

    @Override
    public void clearVariants() {
        if (radioGroup == null) return;
        radioGroup.clearCheck();
        for (int i = 0; i < radioGroup.getChildCount(); i++) {
            View child = radioGroup.getChildAt(i);
            if (child instanceof RadioButton) {
                ((RadioButton) child).setTextColor(Color.parseColor("#1E293B"));
            }
        }
    }

    @Override
    public void restartVariants() {
        clearVariants();
    }

    @Override
    public void showResult(int correct, int total, int categoryIndex, ArrayList<Integer> wrongQuestions, ArrayList<Integer> correctAnswers) {
        Intent intent = new Intent(this, ResultActivity.class);
        intent.putExtra("CORRECT", correct);
        intent.putExtra("TOTAL", total);
        intent.putExtra("CATEGORY_INDEX", categoryIndex);
        intent.putIntegerArrayListExtra("WRONG_QUESTIONS", wrongQuestions);
        intent.putIntegerArrayListExtra("CORRECT_ANSWERS", correctAnswers);

        startActivity(intent);
        finish();
    }

    @Override
    public void showProgress(int current, int total) {
        if (tvProgressText != null) {
            String progress = "Savol: " + current + "/" + total;
            tvProgressText.setText(progress);
        }

        if (progressBar != null) {
            progressBar.setMax(total);
            progressBar.setProgress(current);
        }
    }

    @Override
    public void setCheckedVariant(int index) {
        if (radioGroup != null && index >= 0 && index < radioGroup.getChildCount()) {
            View child = radioGroup.getChildAt(index);
            if (child instanceof RadioButton) {
                RadioButton radioButton = (RadioButton) child;
                radioButton.setChecked(true);
                radioButton.setTextColor(Color.WHITE);
            }
        }
    }

    @Override
    public void showHintUsed(int correctVariantIndex, int remainingDiamonds) {
        setCheckedVariant(correctVariantIndex);
        updateDiamondsDisplay();
        Toast.makeText(this, "To'g'ri javob belgilandi! (-50 💎)\nQolgan olmoslar: " + remainingDiamonds + " 💎", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showNotEnoughDiamonds(int currentDiamonds) {
        Toast.makeText(this, "Olmoslar yetarli emas! Sizda " + currentDiamonds + " 💎 bor. (To'g'ri javob uchun 50 💎 kerak)", Toast.LENGTH_LONG).show();
    }
}