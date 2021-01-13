/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Version;
import java.io.IOException;
import java.util.Arrays;
import main.Main;
import model.Account;
import model.Course;
import model.InfoAndressIP;
import model.Quiz;
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

    public static int[] getTotalQuiz(Course course) {
        String courseId = Utilities.URLEncoder(course.getId());
        final String param = "t=get-course&id_course="+courseId;
        HttpRequest httpRequest = new HttpRequest(SERVER_API, param);
        try {
            String body = httpRequest.getResponseHTML();
            Object o = JSONValue.parseWithException(body);
            JSONObject jSONObject = (JSONObject) o;
            int status = Utilities.getInt(jSONObject.get("status").toString());
            if(status==1){
                jSONObject = (JSONObject) jSONObject.get("data");
                return new int[]{
                    Utilities.getInt(jSONObject.get("total_quiz").toString()),
                    Utilities.getInt(jSONObject.get("safety").toString())
                };
            }
        } catch (IOException | ParseException ex) {
            Console.debug(Client.class, ex);
        }
        return null;
    }

    public static boolean sendTotalQuiz(Course course) {
        String courseId = Utilities.URLEncoder(course.getId());
        int totalQuiz = course.getQuizs().length;
        final String param = "t=add-course&id_course="+courseId+"&total_quiz="+totalQuiz;
        HttpRequest httpRequest = new HttpRequest(SERVER_API, param);
        try {
            String body = httpRequest.getResponseHTML();
            Object o = JSONValue.parseWithException(body);
            JSONObject jSONObject = (JSONObject) o;
            return Utilities.getInt(jSONObject.get("status").toString())==1;
        } catch (IOException | ParseException e) {
            Console.debug(Client.class, e);
        }
        return false;
    }

    public static boolean checkApp() {
        if (Main.ADMIN_DEBUG_APP) {
            return true;
        }
        final String param = "t=application";
        try {
            HttpRequest httpRequest = new HttpRequest(SERVER_API, param);
            if (httpRequest.getResponseStatus() == 200) {
                String htmlResp = httpRequest.getResponseHTML();
                Object obj = JSONValue.parseWithException(htmlResp);
                JSONObject json = (JSONObject) obj;
                int status = Utilities.getInt(json.get("status").toString());
                String msg = json.get("msg").toString();
                JSONObject data = (JSONObject) json.get("data");
                //check open
                int open = Utilities.getInt(((JSONObject) json.get("data")).get("open").toString());
                if (open == 0) {
                    MsgBox.alert("FPLAutoCMS v" + Main.APP_VER + " đang được bảo trì!");
                    throw new Exception("close open!");
                }
                //check version
                String strVerNew = ((JSONObject) json.get("data")).get("ver_new").toString();
                if (new Version(strVerNew).compareTo(new Version(Main.APP_VER)) > 0) {
                    MsgBox.alert("FPLAutoCMS v" + Main.APP_VER + " đã lỗi thời!\nPhiên bản mới nhất FPLAutoCMS v" + strVerNew + "!\nTruy cập " + SERVER_ANDRESS + " để tải bản mới nhất!");
                    OS.openTabBrowser(SERVER_ANDRESS);
                    throw new Exception("outdated version!");
                }
            } else {
                MsgBox.alert("Không thể kết nối đến máy chủ!\nKiểm tra lại mạng hoặc liên hệ với Admin để giải quyết!");
                throw new IOException("connect ERROR!");
            }
        } catch (Exception ex) {
            Console.debug(Client.class, ex);
            return false;
        }
        return true;
    }

    public static boolean sendAnalysis(Account cmsAccount) {
        if (Main.ADMIN_DEBUG_APP) {
            return true;
        }
        InfoAndressIP infoAndressIP = getInfoAndressIP();
        final String param = "t=new-uses&user=%s&ip=%s&city=%s&region=%s&country=%s&timezone=%s";
        String parampost = String.format(param,
                Utilities.URLEncoder(cmsAccount.getUserName()),
                Utilities.URLEncoder(infoAndressIP.getIp()),
                Utilities.URLEncoder(infoAndressIP.getCity()),
                Utilities.URLEncoder(infoAndressIP.getRegion()),
                Utilities.URLEncoder(infoAndressIP.getCountry()),
                Utilities.URLEncoder(infoAndressIP.getTimezone())
        );
        HttpRequest httpRequest = new HttpRequest(SERVER_API, parampost);
        try {
            httpRequest.connect();
        } catch (IOException ex) {
            Console.debug(Client.class, ex);
            return false;
        }
        return true;
    }

    private static InfoAndressIP getInfoAndressIP() {
        final String url = "https://ipinfo.io/json";
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("Content-Type", "application/json; charset=utf-8");
        HttpRequest httpRequest = new HttpRequest(url, httpRequestHeader);
        try {
            String respHTML = httpRequest.getResponseHTML();
            Object o = JSONValue.parseWithException(respHTML);
            JSONObject json = (JSONObject) o;
            InfoAndressIP infoAndressIP = new InfoAndressIP();
            infoAndressIP.setIp(json.get("ip").toString());
            infoAndressIP.setCity(json.get("city").toString());
            infoAndressIP.setCountry(json.get("country").toString());
            infoAndressIP.setRegion(json.get("region").toString());
            infoAndressIP.setTimezone(json.get("timezone").toString());
            return infoAndressIP;
        } catch (IOException | ParseException ex) {
            Console.debug(Client.class, ex);
            return null;
        }
    }
}
