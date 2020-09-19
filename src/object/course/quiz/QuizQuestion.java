
package user.course.quiz;

import java.util.Arrays;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */

public class QuizQuestion {
    private String name;
    private String type;
    private boolean multiChoice;
    private String[] choice;
    private int test;
    private boolean correct;
    private String answer;

    public QuizQuestion() {
    }

    public QuizQuestion(String name, String type, boolean multiChoice, String[] choice, int test, boolean correct, String answer) {
        this.name = name;
        this.type = type;
        this.multiChoice = multiChoice;
        this.choice = choice;
        this.test = test;
        this.correct = correct;
        this.answer = answer;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    public String[] getChoice() {
        return choice;
    }

    public void setChoice(String[] choice) {
        this.choice = choice;
    }

    public int getTest() {
        return test;
    }

    public void setTest(int test) {
        this.test = test;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" + "name=" + name + ", type=" + type + ", multiChoice=" + multiChoice + ", choice=" + Arrays.toString(choice) + ", test=" + test + ", correct=" + correct + ", answer=" + answer + '}';
    }

}
