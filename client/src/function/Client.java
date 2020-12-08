/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import static function.Function.alert;
import static function.Function.exit;
import static function.Function.getInt;
import static function.Function.openTabBrowser;
import java.io.IOException;
import main.Main;
import model.Account;
import model.InfoAndressIP;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.json.simple.parser.ParseException;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 *
 * @author Administrator
 */
public class Client {

    private static final String SERVER_ANDRESS = "https://poly.g88.us";
    private static final String SERVER_API = SERVER_ANDRESS + "/api/index.php";

     public static void check() {
        if (Main.ADMIN_DEBUG_APP) {
            return;
        }
        final String param = "t=application";
        try {
            HttpRequest httpRequest = new HttpRequest(SERVER_API, param);
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

    public static void sendAnalysis(Account cmsAccount) {
        if (Main.ADMIN_DEBUG_APP) {
            return;
        }
        InfoAndressIP infoAndressIP = getInfoAndressIP();
        final String param = "t=new-uses&user=%s&ip=%s&city=%s&region=%s&country=%s&timezone=%s";
        String parampost = String.format(param,
                Function.URLEncoder(cmsAccount.getUserName()),
                Function.URLEncoder(infoAndressIP.getIp()),
                Function.URLEncoder(infoAndressIP.getCity()),
                Function.URLEncoder(infoAndressIP.getRegion()),
                Function.URLEncoder(infoAndressIP.getCountry()),
                Function.URLEncoder(infoAndressIP.getTimezone())
        );
        HttpRequest httpRequest = new HttpRequest(SERVER_API, parampost);
        try {
            httpRequest.connect();
            System.out.println(httpRequest.getResponseHTML());
        } catch (IOException ex) {
        }
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
}
