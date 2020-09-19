package auto.getquiz;

import function.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import object.cms.CMSAccount;
import request.HttpRequest;
import request.support.HttpRequestHeader;

import user.course.Course;
import user.course.quiz.Quiz;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSQuizGet {

    private CMSAccount cmsAccount;
    private Course course;

    private String[] allLinkQuizRaw;

    private Quiz[] quiz;

    public HashSet<Quiz> hashsetQuiz;

    private boolean useRaw;
    private boolean useStandard;

    public CMSQuizGet() {
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

    public void getRaw() throws IOException {
        if (useRaw) {
            return;
        }
        useRaw = !useRaw;
        allLinkQuizRaw = getAllLinkQuiz(cmsAccount, course);
    }

    public void getStandard() throws IOException {
        if (useStandard) {
            return;
        }
        useStandard = !useStandard;
        getRaw();
        hashsetQuiz = new HashSet<>();
        fillerAllLinkQuiz(allLinkQuizRaw);
        this.quiz = sortQuiz(hashsetQuiz);
    }

    // lấy tất cả link quiz 
    public static String[] getAllLinkQuiz(CMSAccount cmsAccount, Course course) throws IOException {

        final String CMS_ALL_COURSE = "https://cms.poly.edu.vn/courses/" + course.getId() + "/course";

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cmsAccount.getCookie());
        HttpRequest httpRequest = new HttpRequest(CMS_ALL_COURSE, httpRequestHeader);

        return parseURLQuiz(httpRequest.getResponseHTML());

    }

    //lấy danh sách tất cả các URLQuiz
    private static String[] parseURLQuiz(String htmlRespGetAllQuiz) {

        ArrayList<String> resAl = new ArrayList<>();

        String regex = "href=\"https://cms(.+?)\"";

        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(htmlRespGetAllQuiz);

        while (m.find()) {
            int indexStart = m.group().indexOf("\"");
            int indexEnd = m.group().lastIndexOf("\"");
            resAl.add(m.group().substring(indexStart + 1, indexEnd));
        }
        String[] res = new String[resAl.size()];
        int i = 0;
        for (String s : resAl) {
            res[i++] = s;
        }
        return res;
    }

    private void fillerAllLinkQuiz(String[] allLinkUrlQuiz) {

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

        for (String urlQuiz : allLinkUrlQuiz) {
            CheckUrlQuiz checkUrlQuiz = new CheckUrlQuiz();
            checkUrlQuiz.setCmsAccount(cmsAccount);
            checkUrlQuiz.setUrl(urlQuiz);
            checkUrlQuiz.setCmsQuizGet(this);
            threadPoolExecutor.execute(checkUrlQuiz);
            Function.sleep(100);
        }

        threadPoolExecutor.shutdown();
        while (threadPoolExecutor.isTerminating()) {
            Function.sleep(100);
        }
    }
    //sắp xếp các quiz theo thứ tự từ quiz 1 => quiz N

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
        for(int i=0; i<listQuiz.size(); i++){
            int k = -1;
            for(int j=0; j<listQuizTmp.size(); j++){
                if(listQuiz.get(i).getName().equals(listQuizTmp.get(j).getName())){
                    k = j;
                    break;
                }
            }
            if(k==-1){
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

    //so sánh tên của quiz với array, trùng thì true
    private static boolean compareQuiz(Quiz quiz, ArrayList<Quiz> alQuiz) {
        for (Quiz q : alQuiz) {
            if (q.getName().equals(quiz.getName())) {
                return true;
            }
        }
        return false;
    }

}
