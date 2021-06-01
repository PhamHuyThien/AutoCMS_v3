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

    public static int[] getCourse(Course course) {
        String courseId = Util.URLEncoder(course.getId());
        String body = JHttp.post(SERVER_API, true,
                "c", "get-course",
                "id-tool", Main.idTool
        ).userAgent().send(false,
                "id-course", courseId
        ).body();
        Console.debug(body);
        Json2T json2T = new Json2T(body);
        if (json2T.q(".status").toInt() == 1) {
            return new int[]{
                json2T.q(".data.total").toInt(),
                json2T.q(".data.safety").toInt()
            };
        }
        return null;
    }

    public static boolean pushCourse(Course course) {
        String courseId = Util.URLEncoder(course.getId());
        int totalQuiz = course.getQuizs().length;
        String body = JHttp.post(SERVER_API, true,
                "c", "push-course",
                "id-tool", Main.idTool
        ).userAgent().send(false,
                "id-course", courseId,
                "total-quiz", totalQuiz
        ).body();
        Console.debug(body);
        Json2T json2T = Json2T.parse(body);
        return json2T.q(".status").toInt() == 1;
    }

    public static int checkApp() {
        if (Main.ADMIN_DEBUG_APP) {
            return -1;
        }
        String body = JHttp.post(SERVER_API, true,
                "c", "get-tool-info"
        ).userAgent().send(true,
                "name", Main.APP_NAME.toLowerCase()
        ).body();
        Console.debug(body);
        Json2T json2T = Json2T.parse(body);
        json2T = json2T.q(".data");
        if (json2T.toObj() == null) {
            MsgBox.alert("Không thể kết nối đến máy chủ, xin chờ chút!");
            return -1;
        }
        if (json2T.toStr().toLowerCase().equals("false")) {
            MsgBox.alert("Tool này không hợp lệ, lên trang chủ tải lại nhé!");
            return -1;
        }
        //check open
        if (json2T.q(".status").toInt() == 0) {
            MsgBox.alert("Tool đang được bảo trì, quay lại sau nhé!");
            return -1;
        }
        //check version
        String strVerOld = Main.APP_VER;
        String strVerNew = json2T.q(".version").toStr();
        if (new Version(strVerNew).compareTo(new Version(strVerOld)) > 0) {
            MsgBox.alert("FPLAutoCMS v" + strVerOld + " đã lỗi thời!\nĐã có phiên bản v" + strVerNew + "!\nTruy cập " + SERVER_ANDRESS + " để tải bản mới nhất!");
            OS.openTabBrowser(SERVER_ANDRESS);
            return -1;
        }
        return json2T.q(".id").toInt();
    }

    public static boolean pushAnalysis(Account account) {
        if (Main.ADMIN_DEBUG_APP) {
            return true;
        }
        InfoAndressIP infoAndressIP = getInfoAndressIP();
        String body = JHttp.post(SERVER_API, true,
                "c", "push-analysis",
                "id-tool", Main.idTool
        ).userAgent().send(false,
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
