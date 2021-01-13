package main;

import frames.FormMain;
import model.Account;
import util.Client;
import util.OS;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Main {

    public static final boolean ADMIN_DEBUG_APP = true;
    public static final int ADMIN_QUIZ_SAFETY = 3;
    
    public static FormMain formMain;
    public static Account account;

    public static void main(String[] args) {

        formMain = new FormMain();
        formMain.setVisible(true);

        Client.checkApp();//kiểm tra hệ thống
        OS.fixHTTPS(); // fix lỗi chứng chỉ
    }

    public static final String APP_NAME = "FPL@utoCMS";
    public static final String APP_VER = "3.2.9";
    public static final String APP_SLOGAN = "10 Quiz 10 Point Easy!";
    public static final String APP_AUTHOR = "ThienDZaii";
    public static final String APP_NICKNAME = "SystemError";
    public static final String APP_CONTACT = "https://fb.com/ThienDz.SystemError";
}
