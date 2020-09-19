
package object.course.quiz;

import java.util.Arrays;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */

public class Quiz {
    private String url;
    private String name;
    private double score;
    private double scorePossible;
    
    private QuizQuestion[] quizQuestion;

    public Quiz() {
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

    


    
    
    
}
