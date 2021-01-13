package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Utilities {

    public static void exit() {
        System.exit(0);
    }

    public static long getCurrentMilis() {
        return new Date().getTime();
    }

    public static String toStringDate(int second) {
        String result = "";
        int numberOfMinutes;
        int numberOfSeconds;
        numberOfMinutes = ((second % 86400) % 3600) / 60;
        numberOfSeconds = ((second % 86400) % 3600) % 60;
        if (numberOfMinutes > 0) {
            result += numberOfMinutes + " minute ";
        }
        if (numberOfSeconds > 0) {
            result += numberOfSeconds + " second";
        }
        return result;
    }

    public static int getInt(String content) {
        String regex = "([0-9]+?)";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        String res = "";
        while (m.find()) {
            res += m.group();
        }
        try {
            return Integer.parseInt(res);
        } catch (NumberFormatException e) {
        }
        return -1;
    }

    public static double roundReal(double d, int index) {
        int j = 1;
        for (int i = 0; i < index; i++) {
            j *= 10;
        }
        return (double) Math.floor(d * j) / j;
    }

    public static String URLEncoder(String url) {
        String encoder = null;
        try {
            encoder = URLEncoder.encode(url, "UTF-8");
        } catch (UnsupportedEncodingException e) {
        }
        return encoder;
    }

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

    public static void sleep(long sleep) {
        try {
            Thread.sleep(sleep);
        } catch (InterruptedException ex) {
        }
    }

}
