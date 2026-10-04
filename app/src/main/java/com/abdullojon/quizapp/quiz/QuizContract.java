package com.abdullojon.quizapp.quiz;

import com.abdullojon.quizapp.data.models.QuizData;

import java.util.ArrayList;
import java.util.List;

public interface QuizContract {
    interface View {
        void loadQuiz(int quiz);
        void loadVariants(List<Integer> variants);
        void stateNextButton(boolean state);
        void clearVariants();
        void restartVariants();
        void showResult(int correct, int total, int categoryIndex, ArrayList<Integer> wrongQuestions, ArrayList<Integer> correctAnswers);

        void showProgress(int current, int total);
        void setCheckedVariant(int index);

        void showHintUsed(int correctVariantIndex, int remainingDiamonds);
        void showNotEnoughDiamonds(int currentDiamonds);
    }

    interface Presenter {
        void next();
        void previous();
        void onSkip();
        void selectedAnswer(int index);
        void restart();
        void onUseHint();
    }

    interface Model {
        void incCorrectAnswer();

        int getQuizCount();
        QuizData getCurrentQuiz(int index);

        void shuffle();

        void addIncorrectAnswer(QuizData quizData, int index);

        int getCorrectAnswerCount();
    }
}