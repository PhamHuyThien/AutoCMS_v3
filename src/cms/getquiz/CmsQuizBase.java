package cms.getquiz;

import exception.CmsException;
import model.Account;
import model.Course;
import model.Quiz;
import org.http.simple.JHttp;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


public class CmsQuizBase {

    private static final String CMS_QUIZ_BASE = "https://cms.poly.edu.vn/courses/%s/course";

    public static Quiz[] parse(Account account, Course course) throws CmsException {
        final String url = String.format(CMS_QUIZ_BASE, course.getId());
        String body = JHttp.get(url).cookie(account.getCookie()).body();
        //
        Document document = Jsoup.parse(body);
        Elements elmsUrlQuizRaw = document.select("a[class='outline-item focusable']");
        if (elmsUrlQuizRaw.isEmpty()) {
            throw new CmsException("getURLQuizRaw a[class='outline-item focusable'] is Empty!");
        }
        Quiz[] quizs = new Quiz[elmsUrlQuizRaw.size()];
        for(int i=0; i<elmsUrlQuizRaw.size(); i++){
            quizs[i] = new Quiz(elmsUrlQuizRaw.get(i).attr("href"));
        }
        return quizs;
    }

}
