package com.abdullojon.quizapp.start;

public interface StartContract {
    interface View {
        void startQuiz(boolean isResume);
        void showResumeButton(boolean visible);
    }
    interface Presenter {
        void onStartQuiz();
        void onResumeQuiz();
        void checkSavedState();
    }

    interface Model {

    }
}