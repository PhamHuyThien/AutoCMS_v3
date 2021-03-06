
package model;

import java.util.Arrays;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */

public class Course {
    private String name;
    private String id;
    private String number;
    private String index;
    private String refurnUrl;
    
    private Quiz[] quizs;
    
    public Course() {
    }

    public Course(String name, String id, String number, String index, String refurnUrl, Quiz[] quizs) {
        this.name = name;
        this.id = id;
        this.number = number;
        this.index = index;
        this.refurnUrl = refurnUrl;
        this.quizs = quizs;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getIndex() {
        return index;
    }

    public void setIndex(String index) {
        this.index = index;
    }

    public String getRefurnUrl() {
        return refurnUrl;
    }

    public void setRefurnUrl(String refurnUrl) {
        this.refurnUrl = refurnUrl;
    }

    public Quiz[] getQuizs() {
        return quizs;
    }

    public void setQuiz(Quiz[] quizs) {
        this.quizs = quizs;
    }

    @Override
    public String toString() {
        return "Course{" + "name=" + name + ", id=" + id + ", number=" + number + ", index=" + index + ", refurnUrl=" + refurnUrl + ", quiz=" + Arrays.toString(quizs) + '}';
    }
    
}
