package function;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.text.Normalizer;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.swing.JOptionPane;
import main.Main;
import model.Account;
import model.InfoAndressIP;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class Function {

    public static void getStatus() {
        if(Main.ADMIN_DEBUG_APP){
            return;
        }
        final String url = "https://poly.g88.us/api/index.php";
        final String param = "t=application";
        try {
            HttpRequest httpRequest = new HttpRequest(url, param);
            if (httpRequest.getResponseStatus() == 200) {
                String htmlResp = httpRequest.getResponseHTML();
                Object obj = JSONValue.parseWithException(htmlResp);
                JSONObject json = (JSONObject) obj;
                int status = getInt(json.get("status").toString());
                String msg = json.get("msg").toString();
                JSONObject data = (JSONObject) json.get("data");
                //check open
                int open = getInt(((JSONObject) json.get("data")).get("open").toString());
                if (open == 0) {
                    alert("FPLAutoCMS v" + Main.APP_VER + " đang được bảo trì!");
                    throw new Exception("close open!");
                }
                //check version
                String strVerNew = ((JSONObject) json.get("data")).get("ver_new").toString();
                if (new Version(strVerNew).compareTo(new Version(Main.APP_VER)) > 0) {
                    alert("FPLAutoCMS v" + Main.APP_VER + " đã lỗi thời!\nPhiên bản mới nhất FPLAutoCMS v" + strVerNew + "!\nTruy cập https://poly.g88.us để tải bản mới nhất!");
                    openTabBrowser("https://poly.g88.us");
                    throw new Exception("outdated version!");
                }
            } else {
                alert("Không thể kết nối đến máy chủ!\nKiểm tra lại mạng hoặc liên hệ với Admin để giải quyết!");
                throw new IOException("connect ERROR!");
            }
            return;
        } catch (Exception ex) {
            System.out.println(ex.toString());
        }
        exit();
    }

    private static InfoAndressIP getInfoAndressIP() {
        final String url = "https://ipinfo.io/json";
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("Content-Type", "application/json; charset=utf-8");
        HttpRequest httpRequest = new HttpRequest(url, httpRequestHeader);
        try {
            String respHTML = httpRequest.getResponseHTML();
            try {
                Object o = JSONValue.parseWithException(respHTML);
                JSONObject json = (JSONObject) o;
                InfoAndressIP infoAndressIP = new InfoAndressIP();
                infoAndressIP.setIp(json.get("ip").toString());
                infoAndressIP.setCity(json.get("city").toString());
                infoAndressIP.setCountry(json.get("country").toString());
                infoAndressIP.setRegion(json.get("region").toString());
                infoAndressIP.setTimezone(json.get("timezone").toString());
                return infoAndressIP;
            } catch (ParseException ex) {
            }

        } catch (IOException ex) {
        }
        return null;
    }

    public static void analysis(Account cmsAccount) {
        if(Main.ADMIN_DEBUG_APP){
            return;
        }
        InfoAndressIP infoAndressIP = Function.getInfoAndressIP();
        final String url = "https://poly.g88.us/api/index.php";
        final String param = "t=new-uses&user=%s&ip=%s&city=%s&region=%s&country=%s&timezone=%s";
        String parampost = String.format(param,
                Function.URLEncoder(cmsAccount.getUserName()),
                Function.URLEncoder(infoAndressIP.getIp()),
                Function.URLEncoder(infoAndressIP.getCity()),
                Function.URLEncoder(infoAndressIP.getRegion()),
                Function.URLEncoder(infoAndressIP.getCountry()),
                Function.URLEncoder(infoAndressIP.getTimezone())
        );
        HttpRequest httpRequest = new HttpRequest(url, parampost);
        try {
            httpRequest.connect();
            System.out.println(httpRequest.getResponseHTML());
        } catch (IOException ex) {
        }
    }

    public static void exit() {
        System.exit(0);
    }

    public static void alert(String content) {
        JOptionPane.showMessageDialog(null, content);
    }

    public static long getCurrentMilis() {
        return new Date().getTime();
    }

    public static String time(int second) {
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
            return -1;
        }
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

    public static void debug(String content) {
        System.out.println(content);
    }

    public static void writeLog(String log) {

    }

    public static boolean fixHTTPS() {
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
            return true;
        } catch (GeneralSecurityException e) {
            System.out.println("FixHTTPSError => " + e.toString());
            return false;
        }
    }

    public static void openTabBrowser(String url) {
        String path[] = new String[]{
            "C:\\Users\\" + Function.getUserName() + "\\AppData\\Local\\CocCoc\\Browser\\Application\\browser.exe",
            "C:\\Program Files (x86)\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe",
            "C:\\Program Files\\Internet Explorer\\iexplore.exe"
        };
        for (String s : path) {
            try {
                Function.shell(s, url, "--new-tab", "--full-screen");
                return;
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
