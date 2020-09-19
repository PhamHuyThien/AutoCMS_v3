package object.course.quiz;

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
    private String question;
    private String key;
    private String[] listValue;
    private boolean multiChoice;
    private String selectValue;
    private int testCount;
    private boolean correct;

    public QuizQuestion() {
    }

    public QuizQuestion(String name, String type, String question, String key, String[] listValue, boolean multiChoice, String selectValue, int testCount, boolean correct) {
        this.name = name;
        this.type = type;
        this.question = question;
        this.key = key;
        this.listValue = listValue;
        this.multiChoice = multiChoice;
        this.selectValue = selectValue;
        this.testCount = testCount;
        this.correct = correct;
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

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String[] getListValue() {
        return listValue;
    }

    public void setListValue(String[] listValue) {
        this.listValue = listValue;
    }

    public boolean isMultiChoice() {
        return multiChoice;
    }

    public void setMultiChoice(boolean multiChoice) {
        this.multiChoice = multiChoice;
    }

    public String getSelectValue() {
        return selectValue;
    }

    public void setSelectValue(String selectValue) {
        this.selectValue = selectValue;
    }

    public int getTestCount() {
        return testCount;
    }

    public void setTestCount(int testCount) {
        this.testCount = testCount;
    }

    public boolean isCorrect() {
        return correct;
    }

    public void setCorrect(boolean correct) {
        this.correct = correct;
    }

    @Override
    public String toString() {
        return "QuizQuestion{" + "name=" + name + ", type=" + type + ", question=" + question + ", key=" + key + ", listValue=" + Arrays.toString(listValue) + ", multiChoice=" + multiChoice + ", selectValue=" + selectValue + ", testCount=" + testCount + ", correct=" + correct + '}';
    }


 
}
