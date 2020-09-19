package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import function.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import object.cms.CMSAccount;
import object.course.Course;
import object.course.quiz.Quiz;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSGetQuiz {

    private CMSAccount cmsAccount;
    private Course course;

    private String[] allLinkQuizRaw;

    private Quiz[] quiz;

    public HashSet<Quiz> hashsetQuiz;

    private boolean useRaw;
    private boolean useStandard;

    public CMSGetQuiz() {
    }

    public CMSAccount getCmsAccount() {
        return cmsAccount;
    }

    public void setCmsAccount(CMSAccount cmsAccount) {
        this.cmsAccount = cmsAccount;
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

    public Quiz[] getQuiz() {
        return quiz;
    }

    public void getRaw() throws IOException, BuildQuizException {
        if (useRaw) {
            return;
        }
        useRaw = !useRaw;
//        allLinkQuizRaw = getURLQuizRaw(cmsAccount, course);
    }

    public void getStandard() throws IOException, BuildQuizException {
        if (useStandard) {
            return;
        }
        useStandard = !useStandard;
        getRaw();
        hashsetQuiz = new HashSet<>();
        allLinkQuizRaw = new String[]{"https://cms.poly.edu.vn/courses/course-v1:FPOLY+WEB3023+26.02.2020/courseware/136cd348dfd74b1bb0be0e46521d5f5b/160e7778c58a4c9c8b50b48a4c5d3b1b/1?activate_block_id=block-v1%3AFPOLY%2BWEB3023%2B26.02.2020%2Btype%40vertical%2Bblock%40ba66964328fb46f78ae271cca845294b"};
        getUrlQuizStandard(allLinkQuizRaw);
        this.quiz = sortQuiz(hashsetQuiz);
    }

    // lấy tất cả link quiz 
    public static String[] getURLQuizRaw(CMSAccount cmsAccount, Course course) throws IOException, BuildQuizException {
        final String CMS_QUIZ_ALL_LINK = "https://cms.poly.edu.vn/courses/" + course.getId() + "/course";

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cmsAccount.getCookie());
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

    private void getUrlQuizStandard(String[] allLinkUrlQuiz) {

        int corePoolSize = allLinkUrlQuiz.length;
        int maximumPoolSize = allLinkUrlQuiz.length;
        int queueCapacity = 100;
        ArrayBlockingQueue arrayBlockingQueue = new ArrayBlockingQueue<>(queueCapacity);

        ThreadPoolExecutor threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, // Số corePoolSize
                maximumPoolSize, // số maximumPoolSize
                10, // thời gian một thread được sống nếu không làm gì
                TimeUnit.SECONDS,
                arrayBlockingQueue // Blocking queue để cho request đợi
        );
        //execute
        for (String urlQuiz : allLinkUrlQuiz) {
            BuildQuizThreadPool checkUrlQuiz = new BuildQuizThreadPool(cmsAccount, urlQuiz, this);
            threadPoolExecutor.execute(checkUrlQuiz);
            Function.sleep(100);
        }
        //shutdown
        threadPoolExecutor.shutdown();
        //waiting
        while (threadPoolExecutor.isTerminating()) {
            Function.sleep(100);
        }
    }

    private static Quiz[] sortQuiz(HashSet<Quiz> hsQuiz) {

//        Comparator comparator = (Comparator<Quiz>) (Quiz quiz1, Quiz quiz2) -> {
//            try {
//                int vtQ1 = Integer.parseInt(quiz1.getName().substring(5).trim());
//                int vtQ2 = Integer.parseInt(quiz2.getName().substring(5).trim());
//                return vtQ1 > vtQ2 ? 1 : -1;
//            } catch (NumberFormatException e) {
//                return 1;
//            }
//        };
        List<Quiz> listQuiz = new ArrayList<>(hsQuiz);
//        Collections.sort(listQuiz, comparator);

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

        Quiz quiz[] = new Quiz[listQuiz.size()];
        for (int i = 0; i < listQuiz.size(); i++) {
            quiz[i] = listQuiz.get(i);
        }
        return quiz;
    }

}
