/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.getquiz;

import auto.getquiz.Exception.BuildQuizException;
import util.Console;
import java.io.IOException;
import model.Account;


/**
 *
 * @author Administrator
 */
public class BuildQuizRunnable extends BuildQuiz implements Runnable {
    
    private final CMSGetQuiz cmsGetQuiz;
    
    public BuildQuizRunnable(Account cmsAccount, String url, boolean status, CMSGetQuiz cmsQuizGet) {
        super(cmsAccount, url, status);
        this.cmsGetQuiz = cmsQuizGet;
    }

    @Override
    public void run() {
        try {
            super.build();
            this.cmsGetQuiz.getHsQuizStandard().add(getQuiz());
        } catch (IOException | BuildQuizException ex) {
            Console.debug(BuildQuizRunnable.class, ex);
        }
    }

}
