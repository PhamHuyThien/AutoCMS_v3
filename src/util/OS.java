/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 *
 * @author Administrator
 */
public class OS {

    public static boolean fixHTTPS() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
            return true;
        } catch (GeneralSecurityException e) {
            Console.debug("fixHTTPS", OS.class, e);
            return false;
        }
    }

    public static void openTabBrowser(String url) {
        String path[] = new String[]{
            //coccoc
            "C:\\Users\\" + getUserName() + "\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe",
            //chrome x86
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
            //chrome x64
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            //ie
            "C:\\Program Files\\Internet Explorer\\iexplore.exe"
        };
        for (String s : path) {
            if (shell(s, url, "--new-tab", "--full-screen")) {
                return;
            }
        }
    }

    public static boolean shell(String... shell) {
        if (getOSName().toLowerCase().startsWith("windows")) {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(shell);
            builder.directory(new File(System.getProperty("user.home")));
            try {
                builder.start();
                return true;
            } catch (IOException ex) {
                Console.debug("shell", OS.class, ex);
            }
        }
        return false;
    }

    public static String getUserName() {
        return System.getProperty("user.name");
    }

    public static String getScriptDir() {
        return System.getProperty("user.dir");
    }

    public static String getOSName() {
        return System.getProperty("os.name");
    }
}
