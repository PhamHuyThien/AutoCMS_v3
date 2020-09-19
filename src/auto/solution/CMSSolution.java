package auto.solution;

import auto.solution.exception.SolutionException;
import function.Function;
import function.combination.Combination;
import function.combination.Permutation;
import function.combination.exception.InputException;
import java.io.IOException;
import java.util.ArrayList;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import object.cms.CMSAccount;
import object.course.Course;
import object.course.quiz.Quiz;
import object.course.quiz.QuizQuestion;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 * @name AutoCMS v3.0.0 OB1
 * @created 03/06/2020
 * @author ThienDepZaii - SystemError
 * @Facebook /ThienDz.SystemError
 * @Gmail ThienDz.DEV@gmail.com
 */
public class CMSSolution {

    private static final int TIME_SLEEP_SOLUTION = 60000;

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

    public void solution() {
        //request init
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cmsAccount.getCookie());
        httpRequestHeader.add("X-CSRFToken", cmsAccount.getCsrfToken());
        httpRequestHeader.add("Referer", quiz.getUrl());
        //đã đủ điểm
        if (quiz.getScore() == totalMaxScore(quiz)) {
            done = true;
            return;
        }
        Function.debug(totalMaxScore(quiz) + " MAX");
        final String urlPost = "https://cms.poly.edu.vn/courses/" + course.getId() + "/xblock/" + course.getId().replace("course", "block") + "+type@problem+block@" + parseHashKey(quiz.getQuizQuestion()[0].getKey()) + "/handler/xmodule_handler/problem_check";
        for (;;) {
            String paramPost = null;
            try {
                paramPost = parseParamPost();
            } catch (SolutionException e) {
                //
                System.out.println("solution parseParamPost => "+e.toString());
                continue;
            }
            HttpRequest httpRequest = new HttpRequest(urlPost, paramPost, httpRequestHeader);
            String jsonResp = null;
            try {
                jsonResp = httpRequest.getResponseHTML();
            } catch (IOException ex) {
                //
                System.out.println("solution httpRequest => "+ex.toString());
                continue;
            }
            System.out.println(jsonResp);
            //
            Object o = JSONValue.parse(jsonResp);
            JSONObject jsonObj = (JSONObject) o;
            String solutionContent = String.valueOf(jsonObj.get("contents").toString());
            double solutionScore = Function.roundReal(Double.parseDouble(jsonObj.get("current_score").toString()), 3);
            boolean solutionSuccess = Boolean.parseBoolean(jsonObj.get("success").toString());
            //
            if (solutionScore == totalMaxScore(quiz)) {
                done = true;
                break;
            }
            try {
                quiz = checkResultSolution(solutionContent, quiz);
            } catch (SolutionException ex) {
                break;
            }
            Function.sleep(TIME_SLEEP_SOLUTION);
        }
    }

    //tạo parampost cho request
    private String parseParamPost() throws SolutionException {
        QuizQuestion quizQuestion[] = quiz.getQuizQuestion();
        StringBuilder sb = new StringBuilder();
        //ghép các parampost từ quizQuestion, thành paramPost Full
        for (int i = 0; i < quizQuestion.length; i++) {
            //câu hỏi này là câu hỏi tự luận thì bỏ qua
            if(quizQuestion[i].getListValue()==null) continue;
            String ans = setValue(quizQuestion[i]);
            sb.append(ans).append("&");
            if (!quizQuestion[i].isCorrect()) { //hoàn thành rồi thì bỏ qua tăng test
                quiz.getQuizQuestion()[i].setTestCount(quiz.getQuizQuestion()[i].getTestCount() + 1);
            }
            quiz.getQuizQuestion()[i].setSelectValue(ans); // set đáp án
        }
        return sb.toString().substring(0, sb.length() - 1);
    }

    //convert quizQuestion sang paramPost, tự động ++ giá trị tiếp theo cho quizQUestion
    private String setValue(QuizQuestion quizQuestion) throws SolutionException {
        //đã hoàn thành thì chỉ lấy getAnswer()
        if (quizQuestion.isCorrect()) {
            return quizQuestion.getSelectValue();
        }
        // đây là kiểu multichoice
        if (quizQuestion.isMultiChoice()) {
            //tạo mới biến global alInt chứa danh sách tổ hợp chập k của n phần tử
            try {
                if (quizQuestion.getType().equals("text")) {
                    alInt = new Permutation(quizQuestion.getAmountInput(), quizQuestion.getListValue().length, true).getResult();
                } else {
                    alInt = new Combination(2, quizQuestion.getListValue().length, true).getResult();
                }
            } catch (InputException e) {
                throw new SolutionException("setValue Input Permutation or Combination Error!");
            }
            StringBuilder value = new StringBuilder();
            int index = quizQuestion.getTestCount();
            //nếu vượt quá index tổ hợp
            if (index >= alInt.size()) {
                throw new SolutionException("setValue ArrayIndexOutOfBound alInt!");
            }
            //nếu là text: định dạng key=value1,value2...
            if (quizQuestion.getType().equals("text")) {
                value.append(quizQuestion.getKey()).append("=");
            }
            alInt.forEach((alInteger) -> {
                alInteger.forEach((integer) -> {
                    if (quizQuestion.getType().equals("text")) {
                        // kiểu text chỉ việc append value1,value2...
                        value.append(Function.URLEncoder(quizQuestion.getListValue()[integer])).append("%2C");
                    } else {
                        // kiểu checkbox => key[]=value1&key[]=value2.....
                        value.append(Function.URLEncoder(quizQuestion.getKey())).append("=").append(Function.URLEncoder(quizQuestion.getListValue()[integer])).append("&");
                    }
                });
            });
            //xóa kí tự nối cuối và return quizQuestion
            return makeUpValue(value.toString());
        } else { // đây là kiểu chọn 1 đáp án
            String choice[] = quizQuestion.getListValue();
            //định dạng: key=value
            String res = Function.URLEncoder(quizQuestion.getKey()) + "=" + Function.URLEncoder(choice[quizQuestion.getTestCount()]) + "&";
            return makeUpValue(res);
        }
    }

    //kiểm tra giá trị đầu vào và setCorrect lại cho mỗi quizQuestion
    private static Quiz checkResultSolution(String htmlResp, Quiz quiz) throws SolutionException {
        htmlResp = Jsoup.parse(htmlResp).html();
        Document document = Jsoup.parse(htmlResp);
        Elements elmsResult = document.select("span[class='sr']");
        if (elmsResult.isEmpty()) {
            throw new SolutionException("checkResultSolution span[class='sr'] is empty!");
        }
        int i = 0;
        for (Element elmResult : elmsResult) {
            String resHtml = elmResult.text().trim().toLowerCase();
            if (!resHtml.equals("correct") && !resHtml.equals("incorrect")) {
                throw new SolutionException("checkResultSolution can't know result!");
            }
            quiz.getQuizQuestion()[i++].setCorrect(resHtml.equals("correct"));
        }
        return quiz;
    }

    private static String makeUpValue(String value) {
        int len = value.length();
        if (value.toCharArray()[len - 1] == '&') {
            return value.substring(0, len - 1);
        }
        if (value.indexOf("%2c") == len - 3) {
            return value.substring(0, len - 3);
        }
        return value;
    }

    //
    private String parseHashKey(String hashKey) {
        return hashKey.split("_")[1];
    }

    private static double totalMaxScore(Quiz quiz) {
        //tổng số câu hỏi
        int totalQuizQuestion = quiz.getQuizQuestion().length;
        //tổng số câu hỏi bỏ qua
        int quizQuestionEssay = 0;
        for (QuizQuestion quizQuestion : quiz.getQuizQuestion()) {
            if (quizQuestion.getListValue() == null) {
                quizQuestionEssay++;
            }
        }
        //điểm 1 bài
        double scoreOneQuestion = Function.roundReal(quiz.getScorePossible() / totalQuizQuestion, 3);
        return Function.roundReal((totalQuizQuestion - quizQuestionEssay) * scoreOneQuestion, 3);
    }
}
