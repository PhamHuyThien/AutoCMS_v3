package auto.solution;

import auto.solution.exception.BuildAnswerException;
import auto.solution.exception.EssayQuestionException;
import function.Function;
import function.combination.Combination;
import function.combination.exception.InputException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import object.cms.CMSAccount;
import org.json.simple.parser.ParseException;
import request.HttpRequest;
import request.support.HttpRequestHeader;
import user.course.Course;
import user.course.quiz.Quiz;
import user.course.quiz.QuizQuestion;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSSolution implements Runnable {

    private CMSAccount cmsAccount;
    private Course course;
    private Quiz quiz;

    private String[] sToHop;

    private ArrayList<ArrayList<Integer>> alInt;

    private boolean done;

    public CMSSolution() {
    }

    public CMSSolution(CMSAccount cmsAccount, Course course, Quiz quiz) {
        this.cmsAccount = cmsAccount;
        this.course = course;
        this.quiz = quiz;
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

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public boolean isDone() {
        return done;
    }

    public void solution() throws IOException, EssayQuestionException, ParseException, BuildAnswerException {

        String paramPost = null, jsonResp = null, solutionContent = null;
        int solutionTest = 0;
        double solutionScore = 0.0, solutionScoreMax = 0.0;
        boolean solutionChanged = false;

        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cmsAccount.getCookie());
        httpRequestHeader.add("X-CSRFToken", cmsAccount.getCsrfToken());
        httpRequestHeader.add("Referer", quiz.getUrl());
        HttpRequest httpRequest = null;

        //đã đủ điểm
        if (quiz.getScore() == quiz.getScorePossible()) {
            done = true;
            return;
        }
        
        String urlPost = parseUrlPost();
        do {
            paramPost = parseParamPost();
            System.out.println(paramPost);
            if (paramPost == null) {
                throw new EssayQuestionException("The question contains the essay answer!");
            }
            httpRequest = new HttpRequest(urlPost, paramPost, httpRequestHeader);
            jsonResp = httpRequest.getResponseHTML();
            Object o = JSONValue.parseWithException(jsonResp);
            JSONObject jsonObj = (JSONObject) o;
            solutionTest = Integer.parseInt(jsonObj.get("attempts_used").toString());
            solutionContent = String.valueOf(jsonObj.get("contents").toString());
            solutionScore = Double.parseDouble(jsonObj.get("current_score").toString());
            solutionChanged = Boolean.parseBoolean(jsonObj.get("success").toString());
            solutionScoreMax = Double.parseDouble(jsonObj.get("total_possible").toString());

            if (solutionScore == solutionScoreMax) {
                done = true;
                break;
            }
            quiz = checkResult(solutionContent, quiz);

            Function.sleep(60000);

        } while (true);
    }

    //tạo parampost cho request
    private String parseParamPost() throws BuildAnswerException {
        QuizQuestion quizQuestion[] = quiz.getQuizQuestion();
        StringBuilder sb = new StringBuilder();
        //ghép các parampost từ quizQuestion, thành paramPost Full
        for (int i = 0; i < quizQuestion.length; i++) {
            String ans = setChoice(quizQuestion[i]);
            if (ans == null) {
                return null;
            }
            sb.append(ans).append("&");
            if (!quizQuestion[i].isCorrect()) { //hoàn thành rồi thì bỏ qua tăng test
                quiz.getQuizQuestion()[i].setTest(quiz.getQuizQuestion()[i].getTest() + 1);
            }
            quiz.getQuizQuestion()[i].setAnswer(ans); // set đáp án
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    //convert quizQuestion sang paramPost, tự động ++ giá trị tiếp theo cho quizQUestion
    private String setChoice(QuizQuestion quizQuestion) throws BuildAnswerException {

        //đã hoàn thành thì chỉ lấy getAnswer()
        if (quizQuestion.isCorrect()) {
            return quizQuestion.getAnswer();
        } else if (quizQuestion.isMultiChoice()) { // đây là kiểu multichoice
            //tạo mới biến global alInt chứa danh sách tổ hợp chập k của n phần tử
            try {
                alInt = new Combination(2, quizQuestion.getChoice().length, true).getResult();
            } catch (InputException e) {
                throw new BuildAnswerException("setChoice => Input Combination Error!");
            }
            //nếu là input=text và multichoice => nhân đôi mảng (vì đáp án có thể ngược lại nữa (tổ hợp 1, đảo tổ hợp 1, tổ hợp 2, đảo tổ hợp 2....)
            if (quizQuestion.getType().equals("text")) {
                this.alInt = reverseAlInt(this.alInt);
            }
            StringBuilder sb = new StringBuilder();
            int index = quizQuestion.getTest();
            if (index < alInt.size()) {  //nếu có getTest có trong tổ hợp
                //nếu là text: định dạng key=value1,value2...
                if (quizQuestion.getType().equals("text")) {
                    sb.append(quizQuestion.getName()).append("=");
                }
                for (ArrayList<Integer> alInteger : alInt) {
                    for (Integer integer : alInteger) {
                        try {
                            if (quizQuestion.getType().equals("text")) { // kiểu text chỉ việc append value1,value2...
                                sb.append(URLEncoder.encode(quizQuestion.getChoice()[integer], "utf-8")).append("%2C");
                            } else { // kiểu checkbox => key[]=value1&key[]=value2.....
                                sb.append(URLEncoder.encode(quizQuestion.getName(), "utf-8")).append("=").append(URLEncoder.encode(quizQuestion.getChoice()[integer], "utf-8")).append("&");
                            }
                        } catch (ArrayIndexOutOfBoundsException | UnsupportedEncodingException e) {
                            throw new BuildAnswerException("MultiChoice URL Encode Error!");
                        }
                    }
                }
            }
            //xóa kí tự nối cuối và return quizQuestion
            try {
                String sbStr = sb.toString();
                int sbLen = sb.length();
                sbStr = sbStr.substring(0, sbLen - (quizQuestion.getType().equals("text") ? 3 : 1));
                return sbStr;
            } catch (Exception e) {
                throw new BuildAnswerException("delete the end hyphen Error!");
            }
        } else { // đây là kiểu chọn 1 đáp án
            String choice[] = quizQuestion.getChoice();
            try {
                //định dạng: key=value
                String res = URLEncoder.encode(quizQuestion.getName(), "utf-8") + "=" + URLEncoder.encode(choice[quizQuestion.getTest()], "utf-8") + "&";
                return res.substring(0, res.length() - 1);
            } catch (ArrayIndexOutOfBoundsException | UnsupportedEncodingException e) {
                throw new BuildAnswerException("Choice URL Encode Error!");
            }
        }
    }

    //thêm phần tử vào alInt, vị trí lẻ = đảo ngược vị trí giá trị của vị trí chẵn
    public static ArrayList<ArrayList<Integer>> reverseAlInt(ArrayList<ArrayList<Integer>> alInt) {
        ArrayList<ArrayList<Integer>> alIntTmp = new ArrayList<>();
        for (ArrayList<Integer> a : alInt) {
            alIntTmp.add(a);
            alIntTmp.add(reverse(a));
        }
        return alIntTmp;
    }

    //hỗ trợ reverseAlInt, đảo 1 ArrayList<Integer>
    public static ArrayList<Integer> reverse(ArrayList<Integer> alInt) {
        ArrayList<Integer> alTmpInt = new ArrayList<>();
        for (int i = alInt.size() - 1; i >= 0; i--) {
            alTmpInt.add(alInt.get(i));
        }
        return alTmpInt;
    }

    //kiểm tra giá trị đầu vào và setCorrect lại cho mỗi quizQuestion
    private static Quiz checkResult(String htmlResp, Quiz quiz) {
        htmlResp = Jsoup.parse(htmlResp).html();
        Document document = Jsoup.parse(htmlResp);
        Elements span = document.select("span[class='sr']");
        int i = 0;
        for (Element e : span) {
            String resHtml = e.html().trim().toLowerCase();
            if (resHtml.equals("correct") || resHtml.equals("incorrect")) {
                quiz.getQuizQuestion()[i++].setCorrect(resHtml.equals("correct"));
            }
        }
//        htmlResp = Jsoup.parse(htmlResp).html();
//        Pattern p = Pattern.compile("\"sr\">([correct]|[incorrect]){1}</");
//        Matcher m = p.matcher(htmlResp);
//        int i=0; 
//        while(m.find()){
//            String res = m.group().substring(5, m.group().length()-2).trim();
//            if(res.equals("correct") || res.equals("incorrect")){
//                quiz.getQuizQuestion()[i++].setCorrect(res.equals("correct"));
//            }
//        }
        return quiz;
    }

    //
    private String parseUrlPost() {
        return "https://cms.poly.edu.vn/courses/" + course.getId() + "/xblock/" + course.getId().replace("course", "block") + "+type@problem+block@" + parseHashKey(quiz.getQuizQuestion()[0].getName()) + "/handler/xmodule_handler/problem_check";
    }

    //
    private String parseHashKey(String hashKey) {
        return hashKey.split("_")[1];
    }

    @Override
    public void run() {
        try {
            solution();
        } catch (ParseException ex) {
            Function.debug("name=" + quiz.getName() + " => " + ex.toString());
        } catch (IOException | EssayQuestionException | BuildAnswerException ex) {
            Function.debug("name=" + quiz.getName() + " => " + ex.toString());
        }
    }
}
