/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.getquiz;

import function.Function;
import java.io.IOException;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import object.cms.CMSAccount;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import request.HttpRequest;
import request.support.HttpRequestHeader;
import user.course.quiz.Quiz;
import user.course.quiz.QuizQuestion;

/**
 *
 * @author Administrator
 */
public class CheckUrlQuiz implements Runnable {

    private CMSAccount cmsAccount;
    private String url;
    private CMSQuizGet cmsQuizGet;

    public CheckUrlQuiz() {
    }

    public CheckUrlQuiz(CMSAccount cmsAccount, String url, CMSQuizGet cmsQuizGet) {
        this.cmsAccount = cmsAccount;
        this.url = url;
        this.cmsQuizGet = cmsQuizGet;
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

    public CMSQuizGet getCmsQuizGet() {
        return cmsQuizGet;
    }

    public void setCmsQuizGet(CMSQuizGet cmsQuizGet) {
        this.cmsQuizGet = cmsQuizGet;
    }

    @Override
    public void run() {
        //read resp url
        HttpRequestHeader httpRequestHeader = new HttpRequestHeader();
        httpRequestHeader.add("cookie", cmsAccount.getCookie());
        HttpRequest httpRequest = new HttpRequest(url, httpRequestHeader);
        String htmlResp = null;
        try {
            httpRequest.connect();
            htmlResp = Jsoup.parse(httpRequest.getResponseHTML()).text();
        } catch (IOException ex) {
            Function.debug("CheckUrlQuiz => HttpRequestError => " + ex.toString());
            return;
        }
        //parse JSOUP
        Document document = null;
        try {
            document = Jsoup.parse(htmlResp);
        } catch (Exception ex) {
            Function.debug("CheckUrlQuiz => setNameError => " + ex.toString());
            return;
        }
        //create element full data 
        Element elementData = document.selectFirst("div[class='problems-wrapper']");
        if(elementData==null){
            return;
        }
        
        Quiz quiz = new Quiz();
        //set url
        quiz.setUrl(url);
        //set name
        Element elementNameQuiz = document.selectFirst("h2[class='hd hd-2 unit-title']");
        String name = elementNameQuiz.html();
        if (name.contains("_")) {
            quiz.setName(name.substring(0, name.indexOf("_")));
        } else {
            quiz.setName(name);
        }
        //set score
        double score = Double.parseDouble(elementData.attr("data-problem-score"));
        quiz.setScore(score);
        //set score posible
        double scorePosible = Double.parseDouble(elementData.attr("data-problem-total-possible"));
        quiz.setScorePossible(scorePosible);
        //read data content
        String content = elementData.attr("data-content");
        //setQuizQuestion
        quiz.setQuizQuestion(analysisRespHtml(Jsoup.parse(content).html()));

        cmsQuizGet.hashsetQuiz.add(quiz);
    }

    //phân tích htmlResp trang urlQuiz và parse ra danh sách các câu hỏi
    private static QuizQuestion[] analysisRespHtml(String htmlResp) {

        ArrayList<QuizQuestion> alDataQuiz = new ArrayList<>();

        Document document = Jsoup.parse(htmlResp);

        //lấy tất cả input type ===> CHECKBOX, RADIO, TEXT
        Elements elmData = document.select("input[type='checkbox'], input[type='radio'], input[type='text']");
        //data cho inp TEXT
        Elements divData = document.select("div[class='data']");
        //data cho inp TEXT, check xem có phải multi choice không
        Elements preData = isPreOfTypeText(document.select("pre[class='poly-body']"));

        int countTypeText = 0;

        for (Element e : elmData) {
            boolean isMulti = e.attr("type").equals("checkbox");
            //tạo mới 
            QuizQuestion quizQuestion = new QuizQuestion();
            quizQuestion.setName(e.attr("name"));
            quizQuestion.setType(e.attr("type"));
            quizQuestion.setMultiChoice(isMulti);
            quizQuestion.setChoice(new String[]{});
            quizQuestion.setTest(0);
            quizQuestion.setCorrect(false);
            quizQuestion.setAnswer("");
            //chỉ thêm các phần tử chưa tồn tại
            //vì sau còn dính cách choice checkbox
            if (!subCMSDataQuiz(quizQuestion, alDataQuiz)) {
                alDataQuiz.add(quizQuestion);
            }
            //lấy vị trí của quiz cũ
            int index = getIndexOf(quizQuestion, alDataQuiz);
            //nếu là kiểu radio hoặc checkbox
            if (e.attr("type").equals("radio") || e.attr("type").equals("checkbox")) {
                //tăng số lượng đáp án lên 1
                int count = alDataQuiz.get(index).getChoice().length;
                alDataQuiz.get(index).setChoice(getChoice(count + 1));
            } else {
                //set choice cho type text
                alDataQuiz.get(index).setChoice(getChoiceTypeText(countTypeText, divData));
                //kiểm tra xem type text này có phải multichoice
                alDataQuiz.get(index).setMultiChoice(isMultiChoiceTypeText(countTypeText, preData));
                countTypeText++;
            }
        }
        //parse sang quizQuestion
        QuizQuestion resQuizQuestion[] = new QuizQuestion[alDataQuiz.size()];
        int i = 0;
        for (QuizQuestion q : alDataQuiz) {
            resQuizQuestion[i++] = q;
        }
        return resQuizQuestion;
    }

    //lấy int index của QuizQuestion trùng tên trong ArrayList<QuizQuestion>
    private static int getIndexOf(QuizQuestion quizQuestion, ArrayList<QuizQuestion> alQuizQuestions) {
        for (int i = 0; i < alQuizQuestions.size(); i++) {
            if (quizQuestion.getName().equals(alQuizQuestions.get(i).getName())) {
                return i;
            }
        }
        return -1;
    }

    //parse choice cho type radio or checkbox
    private static String[] getChoice(int countChoice) {
        String res[] = new String[countChoice];
        for (int i = 0; i < countChoice; i++) {
            res[i] = "choice_" + i;
        }
        return res;
    }

    //hỗ trợ analysisRespHtml
    //loại bỏ các thẻ pre mà bên trong nó không có thẻ input
    private static Elements isPreOfTypeText(Elements e) {
        Elements res = new Elements();
        e.forEach((E) -> {
            String html = E.html();
            String regex = "(<input>)";
            Pattern p = Pattern.compile(regex);
            Matcher m = p.matcher(html);
            int j = 0;
            while (m.find()) {
                j++;
            }
            if (j > 0) {
                res.add(E);
            }
        });
        return res;
    }

    //kiểm tra xem đây có phải multichoice type text hay không?
    private static boolean isMultiChoiceTypeText(int location, Elements e) {
        int i = 0;
        for (Element E : e) {
            if (i++ == location) {
                String html = E.html();
                String regex = "(<input>)";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(html);
                int j = 0;
                while (m.find()) {
                    j++;
                }
                return j > 1;
            }
        }
        return false;
    }

    //lấy giá trị các đáp án của type text
    private static String[] getChoiceTypeText(int location, Elements e) {
        ArrayList<String> alString = new ArrayList<>();
        int i = 0;
        for (Element E : e) {
            if (i++ == location) {
                String html = E.text();

                String regex = "\":\"(.+?)\"\\}";
                Pattern p = Pattern.compile(regex);
                Matcher m = p.matcher(html);
                while (m.find()) {
                    String choice = m.group().substring(3, m.group().length() - 2);
                    alString.add(convertVIToEN(choice));
                }
                break;
//                String html = E.text();
//                Object o = JSONValue.parse(html);
//                JSONArray jsonArray = (JSONArray) o;
//                
//                Iterator itr2 = jsonArray.iterator();
//                while (itr2.hasNext()) {
//                    Iterator itr1 = ((Map) itr2.next()).entrySet().iterator();
//                    while (itr1.hasNext()) {
//                        Map.Entry pair = (Map.Entry) itr1.next();
//                        System.out.println(pair.getKey() + " : " + pair.getValue());
//                        alString.add(convertVIToEN(pair.getValue().toString()));
//                    }
//                }
//                break;
            }
        }
        String res[] = new String[alString.size()];
        i = 0;
        for (String s : alString) {
            res[i++] = s;
        }
        return res;
    }

    //parse value type text để send request
    public static String convertVIToEN(String str) {
        try {
            String temp = Normalizer.normalize(str, Normalizer.Form.NFD);
            Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
            return pattern.matcher(temp).replaceAll("").toLowerCase().replaceAll(" ", "-").replaceAll("đ", "d");
        } catch (Exception ex) {
        }
        return "";
    }

    //so sánh tên của quiz có trong array không, có trả về true
    private static boolean subCMSDataQuiz(QuizQuestion quizQuestion, ArrayList<QuizQuestion> alQuizQuestion) {
        for (QuizQuestion q : alQuizQuestion) {
            if (quizQuestion.getName().equals(q.getName())) {
                return true;
            }
        }
        return false;
    }
}
