package com.abdullojon.quizapp.start.presenter;

import android.content.Context;
import android.content.SharedPreferences;

import com.abdullojon.quizapp.start.StartContract;

public class StartPresenterImpl implements StartContract.Presenter {
    private StartContract.View view;
    private StartContract.Model model;
    private SharedPreferences preferences;

    public StartPresenterImpl(StartContract.View view) {
        this.view = view;
        this.preferences = ((Context) view).getSharedPreferences("QuizPrefs", Context.MODE_PRIVATE);
    }

    @Override
    public void onStartQuiz() {
        preferences.edit().clear().apply();
        view.startQuiz(false); // isResume = false
    }

    @Override
    public void onResumeQuiz() {
        view.startQuiz(true); // isResume = true
    }

    @Override
    public void checkSavedState() {
        int savedLevel = preferences.getInt("current_level", -1);
        view.showResumeButton(savedLevel != -1);
    }
}