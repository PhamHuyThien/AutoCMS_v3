/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import function.Function;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import object.cms.CMSAccount;
import object.course.quiz.Quiz;
import object.course.quiz.QuizQuestion;
import org.json.simple.JSONArray;
import org.json.simple.JSONValue;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import request.HttpRequest;
import request.support.HttpRequestHeader;

/**
 *
 * @author Administrator
 */
public class BuildQuiz {

    private CMSAccount cmsAccount;
    private String url;
    private String htmlResponse;
    private boolean getStatus;

    private Quiz quiz;

    private boolean isBuild;

    public BuildQuiz() {
    }

    public BuildQuiz(CMSAccount cmsAccount, String url, boolean getStatus) {
        this.cmsAccount = cmsAccount;
        this.url = url;
        this.getStatus = getStatus;
    }

    public BuildQuiz(String htmlResponse, boolean getStatus) {
        this.htmlResponse = htmlResponse;
        this.getStatus = getStatus;
    }

    public CMSAccount getCmsAccount() {
        return cmsAccount;
    }

    public void setCmsAccount(CMSAccount cmsAccount) {
        this.cmsAccount = cmsAccount;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getHtmlResponse() {
        return htmlResponse;
    }

    public void setHtmlResponse(String htmlResponse) {
        this.htmlResponse = htmlResponse;
    }

    public boolean isGetStatus() {
        return getStatus;
    }

    public void setGetStatus(boolean getStatus) {
        this.getStatus = getStatus;
    }

    public boolean isIsBuild() {
        return isBuild;
    }

    public void setIsBuild(boolean isBuild) {
        this.isBuild = isBuild;
    }

    public Quiz getQuiz() throws IOException, BuildQuizException {
        return quiz;
    }

    public void build() throws IOException, BuildQuizException {
        if (isBuild) {
            return;
        }
        isBuild = !isBuild;
        if (this.cmsAccount != null && this.url != null) {
            HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
            httpRequestHeader.add("cookie", this.cmsAccount.getCookie());
            HttpRequest httpRequest = new HttpRequest(url, httpRequestHeader);
            htmlResponse = httpRequest.getResponseHTML();
        }
        //parse document toàn trang
        Document document = Jsoup.parse(Jsoup.parse(htmlResponse).html());
        //
        this.quiz = buildQuiz(document, getStatus);
        this.quiz.setUrl(url);
    }

    public void buildQuizQuestion() throws IOException, BuildQuizException {
        if (isBuild) {
            return;
        }
        isBuild = !isBuild;
        if (this.cmsAccount != null && this.url != null) {
            HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
            httpRequestHeader.add("cookie", this.cmsAccount.getCookie());
            HttpRequest httpRequest = new HttpRequest(url, httpRequestHeader);
            htmlResponse = httpRequest.getResponseHTML();
        }
        //parse document toàn trang
        Document document = Jsoup.parse(Jsoup.parse(htmlResponse).html());
        //
        this.quiz = new Quiz();
        this.quiz.setQuizQuestion(buildQuizQuestions(document, getStatus));
        this.quiz.setUrl(url);
    }

    private static Quiz buildQuiz(Document document, boolean getStatus) throws BuildQuizException {
        //===========================
        Element elmData = document.selectFirst("div[class='seq_contents tex2jax_ignore asciimath2jax_ignore']");
        if (elmData == null) {
            throw new BuildQuizException("build div[class='seq_contents tex2jax_ignore asciimath2jax_ignore'] is NULL!");
        }
        //tạo lại document (giải mã đoạn mã hóa)
        document = Jsoup.parse(elmData.text());
        elmData = document.selectFirst("div[class='problems-wrapper']");
        if (elmData == null) {
            throw new BuildQuizException("build div[class='problems-wrapper'] is NULL!");
        }
        //===========================
        Quiz quiz = new Quiz();
        //setname
        Element elementNameQuiz = document.selectFirst("h2[class='hd hd-2 unit-title']");
        quiz.setName(buildNameQuiz(elementNameQuiz.html()));
        //set score
        double score = Double.parseDouble(elmData.attr("data-problem-score"));
        quiz.setScore(score);
        //set score posible
        double scorePosible = Double.parseDouble(elmData.attr("data-problem-total-possible"));
        quiz.setScorePossible(scorePosible);
        //set QuizQuestion
        String content = elmData.attr("data-content");
        document = Jsoup.parse(content);
        quiz.setQuizQuestion(buildQuizQuestions(document, getStatus));
        return quiz;
    }

    public static QuizQuestion[] buildQuizQuestions(Document document, boolean getSatus) throws BuildQuizException {
        //kiểu chọn
        Elements elmsPoly = document.select("div[class='poly']");
        //kiểu nhập
        Elements elmsPolyInput = document.select("div[class='poly poly-input']");
        if (elmsPoly.isEmpty() && elmsPolyInput.isEmpty()) {
            throw new BuildQuizException("buildQuizQuestions div[class='poly'] && div[class='poly poly-input'] is Empty!");
        }
        ArrayList<QuizQuestion> alQuizQuestions = new ArrayList<>();
        //xử lý kiểu chọn trước
        for (Element elmPoly : elmsPoly) {
            Element elmWraper = elmPoly.nextElementSibling();
            Element elmStatus = elmWraper.selectFirst("span[class='sr']");
            //
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setName(elmPoly.selectFirst("h3").text());
            quizQuestion.setType(elmWraper.selectFirst("input").attr("type").equals("radio") ? "radio" : "checkbox");
            quizQuestion.setQuestion(elmPoly.selectFirst("pre[class='poly-body']").text());
            quizQuestion.setKey(elmWraper.selectFirst("input").attr("name"));
            try {
                quizQuestion.setListValue(buildListValue(elmWraper));
            } catch (BuildQuizException e) {
                //không lấy được câu trả lời
                continue;
            }
            quizQuestion.setAmountInput(-1);
            quizQuestion.setMultiChoice(quizQuestion.getType().equals("checkbox"));
            quizQuestion.setCorrect(getSatus && elmStatus != null ? elmStatus.text().equals("correct") : false);
            alQuizQuestions.add(quizQuestion);
        }
        //xử lý kiểu text sau
        for (Element elmPolyInput : elmsPolyInput) {
            Element elmWraper = elmPolyInput.nextElementSibling();
            Element elmStatus = elmWraper.selectFirst("span[class='sr']");

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setName(elmPolyInput.selectFirst("h3").text());
            quizQuestion.setType("text");
            quizQuestion.setQuestion(elmPolyInput.selectFirst("pre").text());
            quizQuestion.setKey(elmWraper.selectFirst("input").attr("name"));
            try {
                quizQuestion.setListValue(buildListValueText(elmPolyInput));
            } catch (BuildQuizException e) {
                //có chứa đáp án tự luận
                //not continue;
            }
            quizQuestion.setAmountInput(elmPolyInput.select("input").size());
            quizQuestion.setMultiChoice(quizQuestion.getAmountInput() > 1);
            quizQuestion.setCorrect(getSatus && elmStatus != null ? elmStatus.text().equals("correct") : false);
            alQuizQuestions.add(quizQuestion);
        }
        QuizQuestion[] quizQuestions = new QuizQuestion[alQuizQuestions.size()];
        int i = 0;
        for (QuizQuestion q : alQuizQuestions) {
            quizQuestions[i++] = q;
        }
        return quizQuestions;
    }

    private static String[] buildListValueText(Element elmPolyInput) throws BuildQuizException {
        Element elmData = elmPolyInput.selectFirst("div[class='data']");
        if (elmData == null) {
            throw new BuildQuizException("buildListValueText div[class='data'] is NULL!");
        }
        Object obj = JSONValue.parse(elmData.text());
        JSONArray jsonArray = (JSONArray) obj;
        Iterator itrArray = jsonArray.iterator();
        String[] listValue = new String[jsonArray.size()];
        int i = 0;
        while (itrArray.hasNext()) {
            Iterator itrValue = ((Map) itrArray.next()).entrySet().iterator();
            while (itrValue.hasNext()) {
                Map.Entry pair = (Map.Entry) itrValue.next();
                listValue[i++] = Function.convertVIToEN(pair.getValue().toString());
            }
        }
        return listValue;
    }

    private static String[] buildListValue(Element elmWraper) throws BuildQuizException {
        Elements elmsInput = elmWraper.select("input");
        if (elmsInput.isEmpty()) {
            throw new BuildQuizException("buildListValue input is empty");
        }
        String[] listValue = new String[elmsInput.size()];
        int i = 0;
        for (Element elmInput : elmsInput) {
            listValue[i++] = elmInput.attr("value");
        }
        return listValue;
    }

    private static String buildNameQuiz(String name) {
        if (name.contains("_")) {
            return name.substring(0, name.indexOf("_")).trim();
        }
        if (name.contains("-")) {
            return name.substring(0, name.indexOf("-")).trim();
        }
        return name;
    }
}
