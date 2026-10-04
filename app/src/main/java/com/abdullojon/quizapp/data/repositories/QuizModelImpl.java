package com.abdullojon.quizapp.data.repositories;

import com.abdullojon.quizapp.R;
import com.abdullojon.quizapp.data.models.QuizData;
import com.abdullojon.quizapp.quiz.QuizContract;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class QuizModelImpl implements QuizContract.Model {
    private List<QuizData> quizes;
    private int correctAnswers;

    public QuizModelImpl(int categoryIndex) {
        quizes = new ArrayList<>();
        switch (categoryIndex) {
            case 1: // Boshqotirma
                quizes.add(new QuizData(R.string.c2_q1, R.string.c2_q1_a, R.string.c2_q1_b, R.string.c2_q1_c, R.string.c2_q1_d, 3));
                quizes.add(new QuizData(R.string.c2_q2, R.string.c2_q2_a, R.string.c2_q2_b, R.string.c2_q2_c, R.string.c2_q2_d, 1));
                quizes.add(new QuizData(R.string.c2_q3, R.string.c2_q3_a, R.string.c2_q3_b, R.string.c2_q3_c, R.string.c2_q3_d, 0));
                quizes.add(new QuizData(R.string.c2_q4, R.string.c2_q4_a, R.string.c2_q4_b, R.string.c2_q4_c, R.string.c2_q4_d, 1));
                quizes.add(new QuizData(R.string.c2_q5, R.string.c2_q5_a, R.string.c2_q5_b, R.string.c2_q5_c, R.string.c2_q5_d, 2));
                quizes.add(new QuizData(R.string.c2_q6, R.string.c2_q6_a, R.string.c2_q6_b, R.string.c2_q6_c, R.string.c2_q6_d, 0));
                quizes.add(new QuizData(R.string.c2_q7, R.string.c2_q7_a, R.string.c2_q7_b, R.string.c2_q7_c, R.string.c2_q7_d, 2));
                quizes.add(new QuizData(R.string.c2_q8, R.string.c2_q8_a, R.string.c2_q8_b, R.string.c2_q8_c, R.string.c2_q8_d, 3));
                quizes.add(new QuizData(R.string.c2_q9, R.string.c2_q9_a, R.string.c2_q9_b, R.string.c2_q9_c, R.string.c2_q9_d, 1));
                quizes.add(new QuizData(R.string.c2_q10, R.string.c2_q10_a, R.string.c2_q10_b, R.string.c2_q10_c, R.string.c2_q10_d, 2));
                break;
            case 2: // Zukkolik
                quizes.add(new QuizData(R.string.c3_q1, R.string.c3_q1_a, R.string.c3_q1_b, R.string.c3_q1_c, R.string.c3_q1_d, 1));
                quizes.add(new QuizData(R.string.c3_q2, R.string.c3_q2_a, R.string.c3_q2_b, R.string.c3_q2_c, R.string.c3_q2_d, 1));
                quizes.add(new QuizData(R.string.c3_q3, R.string.c3_q3_a, R.string.c3_q3_b, R.string.c3_q3_c, R.string.c3_q3_d, 2));
                quizes.add(new QuizData(R.string.c3_q4, R.string.c3_q4_a, R.string.c3_q4_b, R.string.c3_q4_c, R.string.c3_q4_d, 1));
                quizes.add(new QuizData(R.string.c3_q5, R.string.c3_q5_a, R.string.c3_q5_b, R.string.c3_q5_c, R.string.c3_q5_d, 2));
                quizes.add(new QuizData(R.string.c3_q6, R.string.c3_q6_a, R.string.c3_q6_b, R.string.c3_q6_c, R.string.c3_q6_d, 1));
                quizes.add(new QuizData(R.string.c3_q7, R.string.c3_q7_a, R.string.c3_q7_b, R.string.c3_q7_c, R.string.c3_q7_d, 2));
                quizes.add(new QuizData(R.string.c3_q8, R.string.c3_q8_a, R.string.c3_q8_b, R.string.c3_q8_c, R.string.c3_q8_d, 0));
                quizes.add(new QuizData(R.string.c3_q9, R.string.c3_q9_a, R.string.c3_q9_b, R.string.c3_q9_c, R.string.c3_q9_d, 1));
                quizes.add(new QuizData(R.string.c3_q10, R.string.c3_q10_a, R.string.c3_q10_b, R.string.c3_q10_c, R.string.c3_q10_d, 1));
                break;
            case 3: // Topqirlik
                quizes.add(new QuizData(R.string.c4_q1, R.string.c4_q1_a, R.string.c4_q1_b, R.string.c4_q1_c, R.string.c4_q1_d, 2));
                quizes.add(new QuizData(R.string.c4_q2, R.string.c4_q2_a, R.string.c4_q2_b, R.string.c4_q2_c, R.string.c4_q2_d, 0));
                quizes.add(new QuizData(R.string.c4_q3, R.string.c4_q3_a, R.string.c4_q3_b, R.string.c4_q3_c, R.string.c4_q3_d, 0));
                quizes.add(new QuizData(R.string.c4_q4, R.string.c4_q4_a, R.string.c4_q4_b, R.string.c4_q4_c, R.string.c4_q4_d, 0));
                quizes.add(new QuizData(R.string.c4_q5, R.string.c4_q5_a, R.string.c4_q5_b, R.string.c4_q5_c, R.string.c4_q5_d, 1));
                quizes.add(new QuizData(R.string.c4_q6, R.string.c4_q6_a, R.string.c4_q6_b, R.string.c4_q6_c, R.string.c4_q6_d, 0));
                quizes.add(new QuizData(R.string.c4_q7, R.string.c4_q7_a, R.string.c4_q7_b, R.string.c4_q7_c, R.string.c4_q7_d, 1));
                quizes.add(new QuizData(R.string.c4_q8, R.string.c4_q8_a, R.string.c4_q8_b, R.string.c4_q8_c, R.string.c4_q8_d, 1));
                quizes.add(new QuizData(R.string.c4_q9, R.string.c4_q9_a, R.string.c4_q9_b, R.string.c4_q9_c, R.string.c4_q9_d, 2));
                quizes.add(new QuizData(R.string.c4_q10, R.string.c4_q10_a, R.string.c4_q10_b, R.string.c4_q10_c, R.string.c4_q10_d, 0));
                break;
            case 4: // Topishmoqlar
                quizes.add(new QuizData(R.string.c5_q1, R.string.c5_q1_a, R.string.c5_q1_b, R.string.c5_q1_c, R.string.c5_q1_d, 0));
                quizes.add(new QuizData(R.string.c5_q2, R.string.c5_q2_a, R.string.c5_q2_b, R.string.c5_q2_c, R.string.c5_q2_d, 0));
                quizes.add(new QuizData(R.string.c5_q3, R.string.c5_q3_a, R.string.c5_q3_b, R.string.c5_q3_c, R.string.c5_q3_d, 0));
                quizes.add(new QuizData(R.string.c5_q4, R.string.c5_q4_a, R.string.c5_q4_b, R.string.c5_q4_c, R.string.c5_q4_d, 1));
                quizes.add(new QuizData(R.string.c5_q5, R.string.c5_q5_a, R.string.c5_q5_b, R.string.c5_q5_c, R.string.c5_q5_d, 0));
                quizes.add(new QuizData(R.string.c5_q6, R.string.c5_q6_a, R.string.c5_q6_b, R.string.c5_q6_c, R.string.c5_q6_d, 0));
                quizes.add(new QuizData(R.string.c5_q7, R.string.c5_q7_a, R.string.c5_q7_b, R.string.c5_q7_c, R.string.c5_q7_d, 1));
                quizes.add(new QuizData(R.string.c5_q8, R.string.c5_q8_a, R.string.c5_q8_b, R.string.c5_q8_c, R.string.c5_q8_d, 0));
                quizes.add(new QuizData(R.string.c5_q9, R.string.c5_q9_a, R.string.c5_q9_b, R.string.c5_q9_c, R.string.c5_q9_d, 1));
                quizes.add(new QuizData(R.string.c5_q10, R.string.c5_q10_a, R.string.c5_q10_b, R.string.c5_q10_c, R.string.c5_q10_d, 0));
                break;
            default: // Mantiq (0)
                quizes.add(new QuizData(R.string.c1_q1, R.string.c1_q1_a, R.string.c1_q1_b, R.string.c1_q1_c, R.string.c1_q1_d, 2));
                quizes.add(new QuizData(R.string.c1_q2, R.string.c1_q2_a, R.string.c1_q2_b, R.string.c1_q2_c, R.string.c1_q2_d, 3));
                quizes.add(new QuizData(R.string.c1_q3, R.string.c1_q3_a, R.string.c1_q3_b, R.string.c1_q3_c, R.string.c1_q3_d, 3));
                quizes.add(new QuizData(R.string.c1_q4, R.string.c1_q4_a, R.string.c1_q4_b, R.string.c1_q4_c, R.string.c1_q4_d, 2));
                quizes.add(new QuizData(R.string.c1_q5, R.string.c1_q5_a, R.string.c1_q5_b, R.string.c1_q5_c, R.string.c1_q5_d, 1));
                quizes.add(new QuizData(R.string.c1_q6, R.string.c1_q6_a, R.string.c1_q6_b, R.string.c1_q6_c, R.string.c1_q6_d, 3));
                quizes.add(new QuizData(R.string.c1_q7, R.string.c1_q7_a, R.string.c1_q7_b, R.string.c1_q7_c, R.string.c1_q7_d, 1));
                quizes.add(new QuizData(R.string.c1_q8, R.string.c1_q8_a, R.string.c1_q8_b, R.string.c1_q8_c, R.string.c1_q8_d, 2));
                quizes.add(new QuizData(R.string.c1_q9, R.string.c1_q9_a, R.string.c1_q9_b, R.string.c1_q9_c, R.string.c1_q9_d, 1));
                quizes.add(new QuizData(R.string.c1_q10, R.string.c1_q10_a, R.string.c1_q10_b, R.string.c1_q10_c, R.string.c1_q10_d, 3));
                break;
        }
    }

    public QuizModelImpl() {
        this(0);
    }

    @Override
    public QuizData getCurrentQuiz(int level) {
        if (level >= 0 && level < quizes.size()) {
            return quizes.get(level);
        }
        return quizes.get(0);
    }

    @Override
    public void incCorrectAnswer() {
        correctAnswers++;
    }

    @Override
    public int getQuizCount() {
        return quizes.size();
    }

    @Override
    public void shuffle() {
        Collections.shuffle(quizes);
    }

    @Override
    public void addIncorrectAnswer(QuizData quizData, int index) {
    }

    @Override
    public int getCorrectAnswerCount() {
        return correctAnswers;
    }
}