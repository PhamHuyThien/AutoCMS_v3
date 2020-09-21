/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import java.io.IOException;
import object.cms.CMSAccount;


/**
 *
 * @author Administrator
 */
public class BuildQuizThreadPool implements Runnable {

    private CMSAccount cmsAccount;
    private String url;
    private CMSGetQuiz cmsQuizGet;

    public BuildQuizThreadPool() {
    }

    public BuildQuizThreadPool(CMSAccount cmsAccount, String url, CMSGetQuiz cmsQuizGet) {
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

    public CMSGetQuiz getCmsQuizGet() {
        return cmsQuizGet;
    }

    public void setCmsQuizGet(CMSGetQuiz cmsQuizGet) {
        this.cmsQuizGet = cmsQuizGet;
    }

    @Override
    public void run() {
        BuildQuiz buildQuiz = new BuildQuiz();
        buildQuiz.setCmsAccount(cmsAccount);
        buildQuiz.setUrl(url);
        buildQuiz.setGetStatus(true);
        try {
            buildQuiz.build();
            cmsQuizGet.hashsetQuiz.add(buildQuiz.getQuiz());
        } catch (IOException | BuildQuizException ex) {
        }
    }

}
