/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package util;

import model.Version;
import main.Main;
import model.Account;
import model.Course;
import model.InfoAndressIP;
import org.http.simple.JHttp;
import org.json.simple.Json2T;

public class Client {

    private static final String SERVER_ANDRESS = "https://poly.11x7.xyz";
    private static final String SERVER_API = SERVER_ANDRESS + "/api/index.php";

    public static int[] getTotalQuiz(Course course) {
        String courseId = Util.URLEncoder(course.getId());
        String body = JHttp.post(SERVER_API).userAgent().send(false, "t", "get-course", "id_course", courseId).body();
        Console.debug(body);
        Json2T json2T = new Json2T(body);
        if (json2T.q(".status").toInt() == 1) {
            return new int[]{
                json2T.q(".data.total_quiz").toInt(),
                json2T.q(".data.safety").toInt()
            };
        }
        return null;
    }

    public static boolean sendTotalQuiz(Course course) {
        String courseId = Util.URLEncoder(course.getId());
        int totalQuiz = course.getQuizs().length;
        String body = JHttp.post(SERVER_API).userAgent().send(false, "t", "add-course", "id_course", courseId, "total_quiz", totalQuiz).body();
        Console.debug(body);
        Json2T json2T = Json2T.parse(body);
        return json2T.q(".status").toInt() == 1;
    }

    public static boolean checkApp() {
        if (Main.ADMIN_DEBUG_APP) {
            return true;
        }
        String body = JHttp.post(SERVER_API).userAgent().send("t=application").body();
        Console.debug(body);
        if (!body.trim().equals("")) {
            Json2T json2T = Json2T.parse(body);
            int status = json2T.q(".status").toInt();
            String msg = json2T.q(".msg").toStr();
            if (status == 0) {
                MsgBox.alert("Không thể kết nối đến máy chủ!");
                return false;
            }
            //check open
            if (json2T.q(".data.open").toInt() == 0) {
                MsgBox.alert("FPLAutoCMS v" + Main.APP_VER + " đang được bảo trì!");
                return false;
            }
            //check version
            String strVerNew = json2T.q(".data.ver_new").toStr();
            if (new Version(strVerNew).compareTo(new Version(Main.APP_VER)) > 0) {
                MsgBox.alert("FPLAutoCMS v" + Main.APP_VER + " đã lỗi thời!\nPhiên bản mới nhất FPLAutoCMS v" + strVerNew + "!\nTruy cập " + SERVER_ANDRESS + " để tải bản mới nhất!");
                OS.openTabBrowser(SERVER_ANDRESS);
                return false;
            }
        } else {
            MsgBox.alert("Không thể kết nối đến máy chủ!\nKiểm tra lại mạng hoặc liên hệ với Admin để giải quyết!");
            return false;
        }
        return true;
    }

    public static boolean sendAnalysis(Account account) {
        if (Main.ADMIN_DEBUG_APP) {
            return true;
        }
        InfoAndressIP infoAndressIP = getInfoAndressIP();
        String body = JHttp.post(SERVER_API).userAgent().send(false,
                "t", "new-uses",
                "user", account.getUserName(),
                "ip", infoAndressIP.getIp(),
                "city", infoAndressIP.getCity(),
                "region", infoAndressIP.getRegion(),
                "country", infoAndressIP.getCountry(),
                "timezone", infoAndressIP.getTimezone()
        ).body();
        Console.debug(body);
        return Json2T.parse(body).q(".status").toInt() == 1;
    }

    private static InfoAndressIP getInfoAndressIP() {
        final String url = "https://ipinfo.io/json";
        String body = JHttp.get(url).header("Content-Type", "application/json; charset=utf-8").body();
        Json2T json2T = Json2T.parse(body);
        InfoAndressIP infoAndressIP = new InfoAndressIP();
        infoAndressIP.setIp(json2T.q(".ip").toStr());
        infoAndressIP.setCity(json2T.q(".city").toStr());
        infoAndressIP.setCountry(json2T.q(".country").toStr());
        infoAndressIP.setRegion(json2T.q(".region").toStr());
        infoAndressIP.setTimezone(json2T.q(".timezone").toStr());
        return infoAndressIP;
    }
}
