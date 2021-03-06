package util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Util {

    public static Map<String, String> headerInit() {
        Map<String, String> mHeader = new HashMap<>();
        mHeader.put("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/89.0.4389.72 Safari/537.36");
        return mHeader;
    }

    public static String[] regex(String regex, String input) {
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(input);
        ArrayList<String> alValue = new ArrayList<>();
        while (matcher.find()) {
            alValue.add(matcher.group());
        }
        if (alValue.isEmpty()) {
            return null;
        }
        String[] strValues = new String[alValue.size()];
        int i = 0;
        for (String s : alValue) {
            strValues[i] = alValue.get(i);
            i++;
        }
        return strValues;
    }

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
    
    public static int getInt(String text) {
        String[] numbers = regex("([0-9]+)", text);
        if (numbers != null) {
            return Integer.parseInt(numbers[0]);
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
