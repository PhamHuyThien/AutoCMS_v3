package cms.solution;

import cms.getquiz.CmsQuizReal;
import exception.CmsException;
import util.Util;
import util.Combination;
import util.Console;
import util.Permutation;
import java.util.ArrayList;
import model.Account;
import model.Course;
import model.Quiz;
import model.QuizQuestion;
import org.http.simple.JHttp;
import org.json.simple.Json2T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

public class CmsSolution implements Runnable {

    private static final String URL_POST_BASE = "https://cms.poly.edu.vn/courses/%s/xblock/%s+type@problem+block@%s/handler/xmodule_handler/problem_check";
    private static final int TIME_SLEEP_SOLUTION = 60000;
    //
    private final Account account;
    private final Course course;
    private Quiz quiz;
    //
    private ArrayList<ArrayList<Integer>> alInt;
    private double scorePresent;
    private int status = 0;

    public CmsSolution(Account account, Course course, Quiz quiz) {
        this.account = account;
        this.course = course;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public double getScorePresent() {
        return scorePresent;
    }

    public int getStatus() {
        return status;
    }

    @Override
    public void run() {
        try {
            start();
        } catch (CmsException ex) {
            this.status = -1;
            Console.debug("Solution Error!", CmsSolution.class, ex);
        }
    }

    public void start() throws CmsException {
        //đã đủ điểm
        if (isQuizFinished(quiz)) {
            this.status = 1;
            return;
        }
        quiz = resetQuizQuestion(quiz);
        final String url = String.format(
                URL_POST_BASE,
                course.getId(),
                course.getId().replace("course", "block"),
                quiz.getQuizQuestion()[0].getKey().split("_")[1]
        );
        scorePresent = quiz.getScore();
        long timeTick = 0;
        do {
            if (Util.getCurrentMilis() - timeTick > 60000) {
                String body = JHttp.post(url)
                        .header("X-CSRFToken", this.account.getCsrfToken())
                        .header("Referer", this.quiz.getUrl())
                        .header("Accept", "application/json, text/javascript, */*; q=0.01")
                        .cookie(this.account.getCookie())
                        .userAgent()
                        .send(buildParam())
                        .body();
                Json2T json2T = Json2T.parse(body);
                scorePresent = json2T.q(".current_score").toDouble();
                quiz = updateStatusQuizQuestion(json2T.q(".contents").toStr(), quiz);
                //
                Console.debug(body);
                Console.debug(quiz);
                //
                timeTick = Util.getCurrentMilis();
            }
            Util.sleep(100);
        } while (!isQuizFinished(quiz));
        this.status = 1;
    }

    private String buildParam() throws CmsException {
        QuizQuestion quizQuestion[] = quiz.getQuizQuestion();
        StringBuilder sb = new StringBuilder();
        //ghép các parampost từ quizQuestion, thành paramPost Full
        for (int i = 0; i < quizQuestion.length; i++) {
            //câu hỏi này là câu hỏi tự luận thì bỏ qua
            if (quizQuestion[i].getListValue() == null) {
                continue;
            }
            String ans = setValue(quizQuestion[i]);
            sb.append(ans).append("&");
            if (!quizQuestion[i].isCorrect()) { //hoàn thành rồi thì bỏ qua tăng test
                quiz.getQuizQuestion()[i].setTestCount(quiz.getQuizQuestion()[i].getTestCount() + 1);
            }
            quiz.getQuizQuestion()[i].setSelectValue(ans); // set đáp án
        }
        return makeUpValue(sb.toString());
    }

    //convert quizQuestion sang paramPost, tự động ++ giá trị tiếp theo cho quizQUestion
    private String setValue(QuizQuestion quizQuestion) throws CmsException {
        //đã hoàn thành thì chỉ lấy getAnswer()
        if (quizQuestion.isCorrect()) {
            return quizQuestion.getSelectValue();
        }
        // đây là kiểu multichoice
        if (quizQuestion.isMultiChoice()) {
            //tạo mới biến global alInt chứa danh sách tổ hợp chập k của n phần tử
            if (quizQuestion.getType().equals("text")) {
                alInt = new Permutation(quizQuestion.getAmountInput(), quizQuestion.getListValue().length).getResult();
            } else {
                alInt = new Combination(2, quizQuestion.getListValue().length, true).getResult();
            }

            StringBuilder value = new StringBuilder();
            int index = quizQuestion.getTestCount();
            //nếu vượt quá index tổ hợp
            if (index >= alInt.size()) {
                throw new CmsException("setValue ArrayIndexOutOfBound alInt!");
            }
            //nếu là text: định dạng key=value1,value2...
            if (quizQuestion.getType().equals("text")) {
                value.append(quizQuestion.getKey()).append("=");
            }
            alInt.get(index).forEach((i) -> {
                if (quizQuestion.getType().equals("text")) {
                    // kiểu text chỉ việc append value1,value2...
                    value.append(Util.URLEncoder(quizQuestion.getListValue()[i])).append("%2C");
                } else {
                    // kiểu checkbox => key[]=value1&key[]=value2.....
                    value.append(Util.URLEncoder(quizQuestion.getKey())).append("=").append(Util.URLEncoder(quizQuestion.getListValue()[i])).append("&");
                }
            });
            //xóa kí tự nối cuối và return quizQuestion
            return makeUpValue(value.toString());
        } else { // đây là kiểu chọn 1 đáp án
            String choice[] = quizQuestion.getListValue();
            //định dạng: key=value
            String res = Util.URLEncoder(quizQuestion.getKey()) + "=" + Util.URLEncoder(choice[quizQuestion.getTestCount()]) + "&";
            return makeUpValue(res);
        }
    }

    //kiểm tra giá trị đầu vào và setCorrect lại cho mỗi quizQuestion
    private static Quiz updateStatusQuizQuestion(String body, Quiz quiz) throws CmsException {
        Document document = Jsoup.parse(body);
        QuizQuestion[] quizResults = CmsQuizReal.buildQuizQuestions(document, true);
        if (!compareKeyQuizQuestion(quiz.getQuizQuestion(), quizResults)) {
            throw new CmsException("updateStatusQuizQuestion QuizResult != QuizStandard!");
        }
        for (int i = 0; i < quizResults.length; i++) {
            quiz.getQuizQuestion()[i].setCorrect(quizResults[i].isCorrect());
        }
        return quiz;
    }

    private static boolean compareKeyQuizQuestion(QuizQuestion[] quizQuestionsOne, QuizQuestion[] quizQuestionsTwo) {
        if (quizQuestionsOne.length == quizQuestionsTwo.length) {
            for (int i = 0; i < quizQuestionsOne.length; i++) {
                if (!quizQuestionsOne[i].getKey().equals(quizQuestionsTwo[i].getKey())) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    private static String makeUpValue(String value) {
        int len = value.length();
        if (value.endsWith("&")) {
            return value.substring(0, len - 1);
        }
        if (value.endsWith("%2C")) {
            return value.substring(0, len - 3);
        }
        return value;
    }

    private static boolean isQuizFinished(Quiz quiz) {
        QuizQuestion quizQuestions[] = quiz.getQuizQuestion();
        int done = 0;
        for (QuizQuestion quizQuestion : quizQuestions) {
            if (quizQuestion.isCorrect() || quizQuestion.getListValue() == null) {
                done++;
            }
        }
        return done == quizQuestions.length;
    }

    private static Quiz resetQuizQuestion(Quiz quiz) {
        for (QuizQuestion quizQuestion : quiz.getQuizQuestion()) {
            quizQuestion.setCorrect(false);
        }
        return quiz;
    }

}
