package com.abdullojon.quizapp.quiz.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.abdullojon.quizapp.data.UserPreferences;
import com.abdullojon.quizapp.data.models.QuizData;
import com.abdullojon.quizapp.data.repositories.QuizModelImpl;
import com.abdullojon.quizapp.quiz.QuizContract;

import java.util.ArrayList;
import java.util.List;

public class QuizPresenterImpl implements QuizContract.Presenter {
    private QuizContract.Model model;
    private QuizContract.View view;
    private SharedPreferences preferences;

    private int level = 0;
    private int[] userAnswers;
    private int categoryIndex = 0;

    public QuizPresenterImpl(QuizContract.View view, boolean isResume, int categoryIndex) {
        this.categoryIndex = categoryIndex;
        model = new QuizModelImpl(categoryIndex);
        this.view = view;

        this.preferences = ((Context) view).getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);

        initUserAnswers();

        if (isResume) {
            level = preferences.getInt("current_level", 0);
            this.categoryIndex = preferences.getInt("category_index", 0);
            model = new QuizModelImpl(this.categoryIndex);

            String savedAnswers = preferences.getString("answers", "");
            if (!savedAnswers.isEmpty()) {
                String[] split = savedAnswers.split(",");
                for (int i = 0; i < split.length && i < userAnswers.length; i++) {
                    if (!split[i].isEmpty()) {
                        userAnswers[i] = Integer.parseInt(split[i]);
                    }
                }
            }
        } else {
            preferences.edit().clear().apply();
        }

        loadQuiz();
    }

    public QuizPresenterImpl(QuizContract.View view, boolean isResume) {
        this(view, isResume, 0);
    }

    private void initUserAnswers() {
        userAnswers = new int[model.getQuizCount()];
        for (int i = 0; i < userAnswers.length; i++) {
            userAnswers[i] = -1;
        }
    }

    private void saveState() {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt("current_level", level);
        editor.putInt("category_index", categoryIndex);

        StringBuilder sb = new StringBuilder();
        for (int answer : userAnswers) {
            sb.append(answer).append(",");
        }
        editor.putString("answers", sb.toString());
        editor.apply();
    }

    @Override
    public void selectedAnswer(int variant) {
        userAnswers[level] = variant;
        view.stateNextButton(true);
        saveState();
    }

    private int getFirstUnansweredIndex() {
        for (int i = 0; i < userAnswers.length; i++) {
            if (userAnswers[i] == -1) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void next() {
        if (userAnswers[level] == -1) {
            view.stateNextButton(false);
            return;
        }

        if (level < model.getQuizCount() - 1) {
            level++;
            saveState();
            loadQuiz();
        } else {
            int unansweredIndex = getFirstUnansweredIndex();
            if (unansweredIndex != -1 && unansweredIndex != level) {
                level = unansweredIndex;
                saveState();
                loadQuiz();
            } else {
                calculateFinalResult();
            }
        }
    }

    @Override
    public void previous() {
        if (level > 0) {
            level--;
            saveState();
            loadQuiz();
        }
    }

    @Override
    public void restart() {
        level = 0;
        initUserAnswers();
        preferences.edit().clear().apply();
        view.restartVariants();
        loadQuiz();
    }

    @Override
    public void onSkip() {
        userAnswers[level] = -1;
        int unansweredIndex = -1;

        for (int i = level + 1; i < userAnswers.length; i++) {
            if (userAnswers[i] == -1) {
                unansweredIndex = i;
                break;
            }
        }

        if (unansweredIndex == -1) {
            unansweredIndex = getFirstUnansweredIndex();
        }

        if (unansweredIndex != -1 && unansweredIndex != level) {
            level = unansweredIndex;
            saveState();
            loadQuiz();
        } else {
            calculateFinalResult();
        }
    }

    @Override
    public void onUseHint() {
        if (!(view instanceof Context)) return;
        UserPreferences userPreferences = new UserPreferences((Context) view);
        if (userPreferences.spendDiamonds(50)) {
            QuizData quizData = model.getCurrentQuiz(level);
            int correctIndex = quizData.getAnswer();
            selectedAnswer(correctIndex);
            view.showHintUsed(correctIndex, userPreferences.getDiamonds());
        } else {
            view.showNotEnoughDiamonds(userPreferences.getDiamonds());
        }
    }

    private void loadQuiz() {
        QuizData quizData = model.getCurrentQuiz(level);
        view.loadQuiz(quizData.getQuestion());

        List<Integer> variants = new ArrayList<>();
        variants.add(quizData.getVariantA());
        variants.add(quizData.getVariantB());
        variants.add(quizData.getVariantC());
        variants.add(quizData.getVariantD());

        view.clearVariants();
        view.loadVariants(variants);

        if (userAnswers[level] != -1) {
            view.setCheckedVariant(userAnswers[level]);
            view.stateNextButton(true);
        } else {
            view.stateNextButton(false);
        }

        view.showProgress(level + 1, model.getQuizCount());
    }

    private void calculateFinalResult() {
        int correctCount = 0;
        ArrayList<Integer> wrongQuestions = new ArrayList<>();
        ArrayList<Integer> correctAnswersList = new ArrayList<>();

        for (int i = 0; i < model.getQuizCount(); i++) {
            QuizData currentQuiz = model.getCurrentQuiz(i);
            int selected = userAnswers[i];

            if (selected != -1 && selected == currentQuiz.getAnswer()) {
                correctCount++;
            } else {
                wrongQuestions.add(currentQuiz.getQuestion());
                int ansIndex = currentQuiz.getAnswer();
                int correctResId = -1;

                if (ansIndex == 0) correctResId = currentQuiz.getVariantA();
                else if (ansIndex == 1) correctResId = currentQuiz.getVariantB();
                else if (ansIndex == 2) correctResId = currentQuiz.getVariantC();
                else if (ansIndex == 3) correctResId = currentQuiz.getVariantD();

                correctAnswersList.add(correctResId);
            }
        }
        view.showResult(correctCount, model.getQuizCount(), categoryIndex, wrongQuestions, correctAnswersList);
        preferences.edit().clear().apply();
    }
}