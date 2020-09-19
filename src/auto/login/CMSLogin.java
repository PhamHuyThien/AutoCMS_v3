package auto.login;

import auto.login.exception.CookieNullException;
import auto.login.exception.CourseIsEmpty;
import auto.login.exception.JsonInfoCMSUserNotFound;
import auto.login.exception.ParseCRSFTokenError;
import function.Function;
import java.io.IOException;
import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import user.course.Course;

import object.cms.CMSAccount;
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

    private final String CMS_URL_DASBOARD = "https://cms.poly.edu.vn/dashboard/";

    private boolean isLogin;
    private boolean done;
    
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

    public boolean isDone() {
        return done;
    }

    public void login() throws CookieNullException, IOException, ParseException, JsonInfoCMSUserNotFound, ParseCRSFTokenError, CourseIsEmpty {
        if(isLogin){
            return;
        }
        isLogin = !isLogin;
        
        if (cookie == null) {
            throw new CookieNullException("Cookie null? CMSLogin(String cookie); setCookie(String cookie); !");
        }
        
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cookie);
        HttpRequest httpRequest = new HttpRequest(
                CMS_URL_DASBOARD,
                httpRequestHeader
        );
        httpRequest.connect();
        //get được user và course
        String htmlResp = httpRequest.getResponseHTML();
        this.cmsAccount = buildCMSAccount(this.cookie, htmlResp);
        Function.debug("cmsAccount => "+cmsAccount.toString());
        this.course = buildCourse(htmlResp);
        Function.debug("course[] => "+Arrays.toString(course));
        done = !done;
    }

    //get user từ htmlResp
    private static CMSAccount buildCMSAccount(String cookie, String htmlResp) throws ParseException, JsonInfoCMSUserNotFound, ParseCRSFTokenError {
        
        Pattern pattern = Pattern.compile("(\\{\"(.+?)\"\\})");
        Matcher matcher = pattern.matcher(htmlResp);

        if (matcher.find()) {
            
            String sJson = matcher.group().replaceAll("\\s", "");
            Object jObj = JSONValue.parseWithException(sJson);
            JSONObject jsonObj = (JSONObject) jObj;
            
            CMSAccount cmsAccount = new CMSAccount();
            cmsAccount.setCookie(cookie);
            cmsAccount.setCsrfToken(parseCRSFToken(cookie));
            cmsAccount.setUserName(jsonObj.get("username").toString());
            cmsAccount.setUserId(String.valueOf(jsonObj.get("user_id")));
            return cmsAccount;

        }
        throw new JsonInfoCMSUserNotFound("find JSON CMS User Error!");
    }
    //parse từ String htmlResp sang Array Course, 

    private static Course[] buildCourse(String htmlResp) throws CourseIsEmpty {
        Document document = Jsoup.parse(htmlResp);
        Elements aTag = document.select("a[rel='leanModal']");
        if (aTag.size() > 0) {
            Course[] course = new Course[aTag.size()];
            int i = 0;
            for (Element e : aTag) {
                course[i] = new Course();
                course[i].setName(e.attr("data-course-name"));
                course[i].setId(e.attr("data-course-id"));
                course[i].setIndex(e.attr("data-dashboard-index"));
                course[i].setNumber(e.attr("data-course-number"));
                course[i].setRefurnUrl(e.attr("data-course-refund-url"));
                i++;
            }
            return course;
        }
        throw new CourseIsEmpty("rel='learnModal' is Empty!");
    }

    //lấy crsf từ cookie
    private static String parseCRSFToken(String cookie) throws ParseCRSFTokenError {
        String regex = "csrftoken=(.+?);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            int indexStart = matcher.group().indexOf("=");
            int indexEnd = matcher.group().indexOf(";");
            return matcher.group().substring(indexStart + 1, indexEnd);
        }
        throw new ParseCRSFTokenError("CRSFToken from cookie is null!");
    }

}
