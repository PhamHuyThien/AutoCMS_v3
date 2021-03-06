package main;

import model.Account;
import model.Course;
import util.Client;
import util.OS;
import util.Util;


public class Main {

    public static final boolean ADMIN_DEBUG_APP = true;
    public static final int ADMIN_QUIZ_SAFETY = 3;

    public static FormMain formMain;
    public static Account account;
    public static Course[] courses;

    public static void main(String[] args) {

        formMain = new FormMain();
        formMain.setVisible(true);

        // fix lỗi chứng chỉ
        OS.fixHTTPS();
        //kiểm tra hệ thống
        if (!Client.checkApp()) {
            Util.exit();
        }

    }

    public static final String APP_NAME = "FPL@utoCMS";
    public static final String APP_VER = "3.3.0";
    public static final String APP_SLOGAN = "10 Quiz 10 Point Easy!";
    public static final String APP_AUTHOR = "ThienDZaii";
    public static final String APP_NICKNAME = "SystemError";
    public static final String APP_CONTACT = "https://fb.com/ThienDz.SystemError";
}
