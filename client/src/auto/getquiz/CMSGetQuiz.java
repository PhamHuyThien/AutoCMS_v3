package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import util.Utilities;
import util.SimpleThreadPoolExecutor;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import model.Account;
import model.Course;
import model.Quiz;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSGetQuiz {

    private Account account;
    private Course course;

    private String[] allLinkQuizRaw;

    private Quiz[] quiz;

    private HashSet<Quiz> hashsetQuiz;

    private boolean useRaw;
    private boolean useStandard;

    public CMSGetQuiz() {
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }


    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String[] getAllLinkQuizRaw() {
        return allLinkQuizRaw;
    }

    public HashSet<Quiz> getHsQuizStandard() {
        return hashsetQuiz;
    }

    public Quiz[] getQuiz() {
        return quiz;
    }

    public void getRaw() throws IOException, BuildQuizException {
        if (useRaw) {
            return;
        }
        useRaw = !useRaw;
        allLinkQuizRaw = getURLQuizRaw(account, course);
    }

    public void getStandard() throws IOException, BuildQuizException {
        if (useStandard) {
            return;
        }
        useStandard = !useStandard;
        getRaw();
        hashsetQuiz = new HashSet<>();
//        allLinkQuizRaw = new String[]{"https://cms.poly.edu.vn/courses/course-v1:FPOLY+SOF2041+2020_T9/courseware/a6d69fdeaa50498a8ca5ae65c16898d4/436d5efb5e754641bd69da506fbce745/1?activate_block_id=block-v1%3AFPOLY%2BSOF2041%2B2020_T9%2Btype%40vertical%2Bblock%40a9e1b0b0c7df41878eff163c8cde7216"};
        getUrlQuizStandard(allLinkQuizRaw);
        this.quiz = sortQuiz(hashsetQuiz);
    }

    // lấy tất cả link quiz 
    public static String[] getURLQuizRaw(Account account, Course course) throws IOException, BuildQuizException {
        final String CMS_QUIZ_ALL_LINK = "https://cms.poly.edu.vn/courses/" + course.getId() + "/course";

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", account.getCookie());
        HttpRequest httpRequest = new HttpRequest(CMS_QUIZ_ALL_LINK, httpRequestHeader);
        String htmlResp = httpRequest.getResponseHTML();
        //
        Document document = Jsoup.parse(htmlResp);
        Elements elmsUrlQuizRaw = document.select("a[class='outline-item focusable']");
        if (elmsUrlQuizRaw.isEmpty()) {
            throw new BuildQuizException("getURLQuizRaw a[class='outline-item focusable'] is Empty!");
        }
        String[] res = new String[elmsUrlQuizRaw.size()];
        int i = 0;
        for (Element elmUrlQUizRaw : elmsUrlQuizRaw) {
            res[i++] = elmUrlQUizRaw.attr("href");
        }
        return res;
    }

    //lọc các quiz
    private void getUrlQuizStandard(String[] allLinkUrlQuiz) {
        BuildQuizRunnable[] buildQuizThreadPools = new BuildQuizRunnable[allLinkUrlQuiz.length];
        for (int i = 0; i < buildQuizThreadPools.length; i++) {
            buildQuizThreadPools[i] = new BuildQuizRunnable(account, allLinkUrlQuiz[i], true, this);
        }
        SimpleThreadPoolExecutor simpleThreadPool = new SimpleThreadPoolExecutor(buildQuizThreadPools);
        simpleThreadPool.execute();
        while (simpleThreadPool.isTerminating()) {
            Utilities.sleep(100);
        }
    }

    private static Quiz[] sortQuiz(HashSet<Quiz> hsQuiz) {
        //sắp xếp
        Comparator comparator = (Comparator<Quiz>) (Quiz quiz1, Quiz quiz2) -> {
            int vtQ1 = Utilities.getInt(quiz1.getName());
            int vtQ2 = Utilities.getInt(quiz2.getName());
            return vtQ1 == -1 || vtQ2 == -1 ? 1 : vtQ1 > vtQ2 ? 1 : -1;
        };
        List<Quiz> listQuiz = new ArrayList<>(hsQuiz);
        Collections.sort(listQuiz, comparator);
        //lọc trùng
        List<Quiz> listQuizTmp = new ArrayList<>();
        for (int i = 0; i < listQuiz.size(); i++) {
            int k = -1;
            for (int j = 0; j < listQuizTmp.size(); j++) {
                if (listQuiz.get(i).getName().equals(listQuizTmp.get(j).getName())) {
                    k = j;
                    break;
                }
            }
            if (k == -1) {
                listQuizTmp.add(listQuiz.get(i));
            }
        }
        listQuiz = listQuizTmp;
        //đẩy về array thuần
        Quiz quiz[] = new Quiz[listQuiz.size()];
        for (int i = 0; i < listQuiz.size(); i++) {
            quiz[i] = listQuiz.get(i);
        }
        return quiz;
    }

}
