package model;

import java.util.Arrays;
import util.Util;

public class Quiz implements Comparable<Quiz> {

    private String url;
    private String name;
    private double score;
    private double scorePossible;

    private QuizQuestion[] quizQuestion;

    public Quiz() {
    }

    public Quiz(String url) {
        this.url = url;
    }

    public Quiz(String url, String name, double score, double scorePossible, QuizQuestion[] quizQuestion) {
        this.url = url;
        this.name = name;
        this.score = score;
        this.scorePossible = scorePossible;
        this.quizQuestion = quizQuestion;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }

    public double getScorePossible() {
        return scorePossible;
    }

    public void setScorePossible(double scorePossible) {
        this.scorePossible = scorePossible;
    }

    public QuizQuestion[] getQuizQuestion() {
        return quizQuestion;
    }

    public void setQuizQuestion(QuizQuestion[] quizQuestion) {
        this.quizQuestion = quizQuestion;
    }

    @Override
    public String toString() {
        return "Quiz{" + "url=" + url + ", name=" + name + ", score=" + score + ", scorePossible=" + scorePossible + ", quizQuestion=" + Arrays.toString(quizQuestion) + '}';
    }

    @Override
    public int compareTo(Quiz quiz) {
        if (quiz == null) {
            return 1;
        }
        int num = Util.getInt(this.getName());
        int num2 = Util.getInt(quiz.getName());
        if (num == -1) {
            return -1;
        }
        if (num2 == -1) {
            return 1;
        }
        return num - num2;
    }

}
