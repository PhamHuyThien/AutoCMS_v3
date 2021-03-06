package cms.login;

import exception.CmsException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import model.Account;
import model.Course;
import org.http.simple.JHttp;
import org.json.simple.Json2T;

public class CmsLogin {

    private static final String CMS_URL_DASHBOARD = "https://cms.poly.edu.vn/dashboard/";

    public static Object[] start(String cookie) throws CmsException {
        String body = JHttp.get(CMS_URL_DASHBOARD).userAgent().cookie(cookie).body();
        Document document = Jsoup.parse(body);
        //
        Account account = buildAccount(document);
        account.setCookie(cookie);
        account.setCsrfToken(parseCRSFToken(cookie));
        //
        Course[] courses = buildCourse(document);
        Object[] objects = {
            account, courses
        };
        return objects;
    }

    //get user từ htmlResp
    private static Account buildAccount(Document document) throws CmsException {
        //get element
        Element elmUserMetaData = document.selectFirst("script[id='user-metadata']");
        if (elmUserMetaData == null) {
            throw new CmsException("buildCMSAccount script[id='user-metadata'] is NULL!");
        }
        //parse JSON
        Json2T json2T = Json2T.parse(elmUserMetaData.html());
        if (json2T == null) {
            throw new CmsException("buildCMSAccount parse Error!");
        }
        //build CMSAccount
        Account cmsAccount = new Account();
        cmsAccount.setUserName(json2T.q(".username").toStr());
        cmsAccount.setUserId(json2T.q(".user_id").toStr());
        return cmsAccount;
    }
    //parse từ String htmlResp sang Array Course, 

    private static Course[] buildCourse(Document document) throws CmsException {
        Elements elmsLeanModal = document.select("a[rel='leanModal']");
        if (elmsLeanModal.isEmpty()) {
            throw new CmsException("buildCourse a[rel='leanModal'] is empty!");
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
    private static String parseCRSFToken(String cookie) throws CmsException {
        String regex = "csrftoken=(.+?);";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(cookie);
        if (matcher.find()) {
            int indexStart = matcher.group().indexOf("=");
            int indexEnd = matcher.group().indexOf(";");
            return matcher.group().substring(indexStart + 1, indexEnd);
        }
        throw new CmsException("Regex 'csrftoken=(.+?);' is null!");
    }

}
