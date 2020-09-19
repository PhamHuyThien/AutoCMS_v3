package function;

import java.io.File;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Function {

    //parse value type text để send request
    public static String convertVIToEN(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception ex) {
        }
        return "";
    }

    public static void debug(String content) {
        System.out.println(content);
    }

    public static void writeLog(String log) {

    }

    public static void fixHTTPS() {
        TrustManager[] trustAllCerts = new TrustManager[]{
            new X509TrustManager() {
                @Override
                public java.security.cert.X509Certificate[] getAcceptedIssuers() {
                    return new X509Certificate[0];
                }

                @Override
                public void checkClientTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }

                @Override
                public void checkServerTrusted(
                        java.security.cert.X509Certificate[] certs, String authType) {
                }
            }
        };
        try {
            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
        } catch (GeneralSecurityException e) {
            System.out.println("FixHTTPSError => " + e.toString());
        }
    }

    public static void contactMe() {
        String path[] = new String[]{
            "C:\\Users\\" + Function.getUserName() + "\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files\\Internet Explorer\\iexplore.exe"
        };
        String contact = "https://facebook.com/thiendz.systemerror";
        for (String s : path) {
            try {
                Function.shell(s, contact, "--new-tab", "--full-screen");
                break;
            } catch (IOException e) {
            }
        }
    }

    public static void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

    public static void shell(String... shell) throws IOException {
        if (getOSName().toLowerCase().startsWith("windows")) {
            ProcessBuilder builder = new ProcessBuilder();
            builder.command(shell);
            builder.directory(new File(System.getProperty("user.home")));
            builder.start();
        }
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
