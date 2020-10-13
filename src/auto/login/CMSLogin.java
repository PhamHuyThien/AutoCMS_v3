package auto.login;

import auto.login.exception.LoginException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import object.cms.CMSAccount;
import object.course.Course;
import org.json.simple.parser.ParseException;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSLogin {

    private String cookie;

    private CMSAccount cmsAccount;
    private Course[] course;

    private final String CMS_URL_DASHBOARD = "https://cms.poly.edu.vn/dashboard/";

    private boolean isLogin;

    public CMSLogin() {
    }

    public CMSLogin(String cookie) {
        this.cookie = cookie;
    }

    public String getCookie() {
        return cookie;
    }

    public void setCookie(String cookie) {
        this.cookie = cookie;
    }

    //getter
    public CMSAccount getCmsAccount() {
        return cmsAccount;
    }

    public Course[] getCourse() {
        return course;
    }

    public void login() throws IOException, LoginException {
        if (isLogin) {
            return;
        }
        isLogin = !isLogin;
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cookie);
        HttpRequest httpRequest = new HttpRequest(CMS_URL_DASHBOARD, httpRequestHeader);
        String htmlResp = httpRequest.getResponseHTML();
        //
        Document document = Jsoup.parse(htmlResp);
        //
        this.cmsAccount = buildCMSAccount(document);
        this.cmsAccount.setCookie(cookie);
        this.cmsAccount.setCsrfToken(parseCRSFToken(cookie));
        //
        this.course = buildCourse(document);
    }

    //get user từ htmlResp
    private static CMSAccount buildCMSAccount(Document document) throws LoginException {
        //get element
        Element elmUserMetaData = document.selectFirst("script[id='user-metadata']");
        if (elmUserMetaData == null) {
            throw new LoginException("buildCMSAccount script[id='user-metadata'] is NULL!");
        }
        try {
            //parse JSON
            Object jObj = JSONValue.parseWithException(elmUserMetaData.html());
            JSONObject jsonObj = (JSONObject) jObj;
            //build CMSAccount
            CMSAccount cmsAccount = new CMSAccount();
            cmsAccount.setUserName(jsonObj.get("username").toString());
            cmsAccount.setUserId(jsonObj.get("user_id").toString());
            return cmsAccount;
        } catch (Exception e) {
            throw new LoginException("buildCMSAccount parse Error!");
        }
    }
    //parse từ String htmlResp sang Array Course, 

    private static Course[] buildCourse(Document document) throws LoginException {
        Elements elmsLeanModal = document.select("a[rel='leanModal']");
        if (elmsLeanModal.isEmpty()) {
            throw new LoginException("buildCourse a[rel='leanModal'] is empty!");
        }
        Course[] course = new Course[elmsLeanModal.size()];
        int i = 0;
        for (Element elmLean : elmsLeanModal) {
            course[i] = new Course();
            course[i].setName(elmLean.attr("data-course-name"));
            course[i].setId(elmLean.attr("data-course-id"));
            course[i].setIndex(elmLean.attr("data-dashboard-index"));
            course[i].setNumber(elmLean.attr("data-course-number"));
            course[i++].setRefurnUrl(elmLean.attr("data-course-refund-url"));
        }
        return course;
    }

    //lấy crsf từ cookie
    private static String parseCRSFToken(String cookie) throws LoginException {
        String regex = "csrftoken=(.+?);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            int indexStart = matcher.group().indexOf("=");
            int indexEnd = matcher.group().indexOf(";");
            return matcher.group().substring(indexStart + 1, indexEnd);
        }
        throw new LoginException("Regex 'csrftoken=(.+?);' is null!");
    }

}
