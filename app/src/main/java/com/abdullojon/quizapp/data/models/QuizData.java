package com.abdullojon.quizapp.data.models;

public class QuizData {
    private int question;
    private int variantA;
    private int variantB;
    private int variantC;
    private int variantD;
    private int answer;
    private int selectedAnswer;
    public QuizData(int question,int variantA,int variantB,int variantC,int variantD,int answer){
        this.question=question;
        this.variantA=variantA;
        this.variantB=variantB;
        this.variantC=variantC;
        this.variantD=variantD;
        this.answer=answer;
    }
    public int getSelectedAnswer(){
        return selectedAnswer;
    }

    public void setSelectedAnswer(int selectedAnswer) {
        this.selectedAnswer = selectedAnswer;
    }

    public int getQuestion() {
        return question;
    }

    public int getVariantA() {
        return variantA;
    }

    public int getVariantB() {
        return variantB;
    }

    public int getVariantC() {
        return variantC;
    }

    public int getVariantD() {
        return variantD;
    }

    public int getAnswer() {
        return answer;
    }
}
