/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import java.io.IOException;
import model.Account;


/**
 *
 * @author Administrator
 */
public class BuildQuizThreadPool implements Runnable {

    private Account cmsAccount;
    private String url;
    private CMSGetQuiz cmsQuizGet;
    private boolean status;
    
    public BuildQuizThreadPool() {
    }

    public BuildQuizThreadPool(Account cmsAccount, String url, boolean status, CMSGetQuiz cmsQuizGet) {
        this.cmsAccount = cmsAccount;
        this.url = url;
        this.cmsQuizGet = cmsQuizGet;
        this.status = status;
    }

    @Override
    public void run() {
        BuildQuiz buildQuiz = new BuildQuiz();
        buildQuiz.setCmsAccount(cmsAccount);
        buildQuiz.setUrl(url);
        buildQuiz.setGetStatus(status);
        try {
            buildQuiz.build();
            cmsQuizGet.hashsetQuiz.add(buildQuiz.getQuiz());
        } catch (IOException | BuildQuizException ex) {
        }
    }

}
