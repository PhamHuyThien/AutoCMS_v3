package main;

import auto.login.CMSLogin;
import frames.FormMain;
import function.Function;
import object.cms.CMSAccount;
import user.course.Course;
import user.course.quiz.Quiz;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Main {

    public static FormMain formMain;

    public static CMSLogin cmsLogin;
    
    public static CMSAccount cmsAccount;
    public static Course[] course;
    public static Quiz[] quiz;
    
    public static void main(String[] args){

        Function.fixHTTPS();
        
        formMain = new FormMain();
        formMain.setVisible(true);
    }
}
