package main;

import auto.login.CMSLogin;
import frames.FormMain;
import function.Function;
import object.cms.CMSAccount;
import object.course.Course;
import object.course.quiz.Quiz;

/**
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
    
    
    public static final String APP_NAME = "FPL@utoCMS";
    public static final String APP_VER = "3.2.2";
    public static final String APP_SLOGAN = "10 Quiz 10Point Easy!";
    public static final String APP_AUTHOR = "ThienDepZaii";
    public static final String APP_NICKNAME = "SystemError";
    public static final String APP_CONTACT = "https://fb.com/ThienDz.SystemError";

    public static void main(String[] args){

        Function.fixHTTPS();
        
        formMain = new FormMain();
        formMain.setVisible(true);
    }
}
