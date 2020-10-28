package main;

import auto.login.CMSLogin;
import frames.FormMain;
import function.Function;
import model.Account;
import model.Course;
import model.Quiz;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Main {
    public static final boolean ADMIN_DEBUG_APP = true;

    public static FormMain formMain;
    public static Account cmsAccount;
    public static CMSLogin cmsLogin;
    public static Course[] course;
    public static Quiz[] quiz;
    
    public static void main(String[] args) {
        
        formMain = new FormMain();
        formMain.setVisible(true);

        Function.getStatus(); //kiểm tra hệ thống
        Function.fixHTTPS(); // fix lỗi chứng chỉ
    }
    public static final String APP_NAME = "FPL@utoCMS";
    public static final String APP_VER = "3.2.8";
    public static final String APP_SLOGAN = "10 Quiz 10 Point Easy!";
    public static final String APP_AUTHOR = "ThienDepZaii";
    public static final String APP_NICKNAME = "SystemError";
    public static final String APP_CONTACT = "https://fb.com/ThienDz.SystemError";
}
