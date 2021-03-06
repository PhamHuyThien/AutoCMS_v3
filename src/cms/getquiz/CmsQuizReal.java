/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cms.getquiz;

import exception.CmsException;
import java.util.ArrayList;
import java.util.Arrays;
import model.Account;
import model.Quiz;
import model.QuizQuestion;
import org.http.simple.JHttp;
import org.json.simple.Json2T;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import util.Console;
import util.Util;

public class CmsQuizReal implements Runnable {

    private final Account account;
    private Quiz quiz;

    public CmsQuizReal(Account account, Quiz quiz) {
        this.account = account;
        this.quiz = quiz;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    @Override
    public void run() {
        try {
            this.quiz = CmsQuizReal.filter(this.account, this.quiz);
        } catch (CmsException ex) {
            this.quiz = null;
            Console.debug("Get Quiz Real Error!", CmsQuizReal.class, ex);
        }
    }

    //
    public static Quiz filter(Account account, Quiz quiz) throws CmsException {
        String body = JHttp.get(quiz.getUrl()).cookie(account.getCookie()).body();
        Document document = Jsoup.parse(body);
        Quiz quizNew = buildQuiz(document);
        quizNew.setUrl(quiz.getUrl());
        return quizNew;
    }

    public static Quiz buildQuiz(Document document) throws CmsException {
        return buildQuiz(document, false);
    }

    public static Quiz buildQuiz(Document document, boolean getStatus) throws CmsException {
        //===========================
        Element elmData = document.selectFirst("div[class='seq_contents tex2jax_ignore asciimath2jax_ignore']");
        if (elmData == null) {
            throw new CmsException("build div[class='seq_contents tex2jax_ignore asciimath2jax_ignore'] is NULL!");
        }
        //tạo lại document (giải mã đoạn mã hóa)
        document = Jsoup.parse(elmData.text());
        elmData = document.selectFirst("div[class='problems-wrapper']");
        if (elmData == null) {
            throw new CmsException("build div[class='problems-wrapper'] is NULL!");
        }
        //===========================
        Quiz quiz = new Quiz();
        //setname
        Element elementNameQuiz = document.selectFirst("h2[class='hd hd-2 unit-title']");
        String name = elementNameQuiz.html();
        quiz.setName(name.contains("_") ? name.substring(0, name.indexOf("_")) : name);
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

    public static QuizQuestion[] buildQuizQuestions(Document document) throws CmsException {
        return buildQuizQuestions(document, false);
    }

    public static QuizQuestion[] buildQuizQuestions(Document document, boolean getSatus) throws CmsException {
        //kiểu chọn
        Elements elmsPoly = document.select("div[class='poly']");
        //kiểu nhập
        Elements elmsPolyInput = document.select("div[class='poly poly-input']");
        if (elmsPoly.isEmpty() && elmsPolyInput.isEmpty()) {
            throw new CmsException("buildQuizQuestions div[class='poly'] && div[class='poly poly-input'] is Empty!");
        }
        ArrayList<QuizQuestion> alQuizQuestions = new ArrayList<>();
        //xử lý kiểu chọn trước
        for (Element elmPoly : elmsPoly) {
            Element elmWraper = elmPoly.nextElementSibling();
            //
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setName(elmPoly.selectFirst("h3").text());
            quizQuestion.setType(elmWraper.selectFirst("input").attr("type").equals("radio") ? "radio" : "checkbox");
            quizQuestion.setQuestion(elmPoly.selectFirst("pre[class='poly-body']").text());
            quizQuestion.setKey(elmWraper.selectFirst("input").attr("name"));
            try {
                quizQuestion.setListValue(buildListValue(elmWraper));
            } catch (CmsException e) {
                continue;
            }
            quizQuestion.setAmountInput(-1);
            quizQuestion.setMultiChoice(quizQuestion.getType().equals("checkbox"));
            quizQuestion.setCorrect(getSatus && elmWraper.selectFirst("span[class='sr']").text().equals("correct"));
            alQuizQuestions.add(quizQuestion);
        }
        //xử lý kiểu text sau
        for (Element elmPolyInput : elmsPolyInput) {
            Element elmWraper = elmPolyInput.nextElementSibling();

            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setName(elmPolyInput.selectFirst("h3").text());
            quizQuestion.setType("text");
            quizQuestion.setQuestion(elmPolyInput.selectFirst("pre").text());
            quizQuestion.setKey(elmWraper.selectFirst("input").attr("name"));
            try {
                quizQuestion.setListValue(buildListValueText(elmPolyInput));
            } catch (CmsException e) {
                //not continue;
            }
            quizQuestion.setAmountInput(elmPolyInput.select("input").size());
            quizQuestion.setMultiChoice(quizQuestion.getAmountInput() > 1);
            quizQuestion.setCorrect(getSatus && elmWraper.selectFirst("span[class='sr']").text().equals("correct"));
            alQuizQuestions.add(quizQuestion);
        }
        QuizQuestion[] quizQuestions = new QuizQuestion[alQuizQuestions.size()];
        int i = 0;
        for (QuizQuestion q : alQuizQuestions) {
            quizQuestions[i++] = q;
        }
        return quizQuestions;
    }

    private static String[] buildListValueText(Element elmPolyInput) throws CmsException {
        Element elmData = elmPolyInput.selectFirst("div[class='data']");
        if (elmData == null) {
            throw new CmsException("buildListValueText div[class='data'] is NULL!");
        }
        Json2T[] jArrs = Json2T.parse(elmData.text()).toObjs();
        String[] values = new String[jArrs.length];
        for (int i = 0; i < jArrs.length; i++) {
            values[i] = Util.convertVIToEN(jArrs[i].k("text").toStr());
        }
        return values;
    }

    private static String[] buildListValue(Element elmWraper) throws CmsException {
        Elements elmsInput = elmWraper.select("input");
        if (elmsInput.isEmpty()) {
            throw new CmsException("buildListValue input is empty");
        }
        String[] listValue = new String[elmsInput.size()];
        int i = 0;
        for (Element elmInput : elmsInput) {
            listValue[i++] = elmInput.attr("value");
        }
        return listValue;
    }
}
