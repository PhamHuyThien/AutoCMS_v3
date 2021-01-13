/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package auto.solution;

import auto.solution.exception.SolutionException;
import util.Console;
import util.Utilities;
import model.Account;
import model.Course;
import model.Quiz;

/**
 *
 * @author Administrator
 */
public class SolutionRunnable extends CMSSolution implements Runnable {

    public SolutionRunnable(Account cmsAccount, Course course, Quiz quiz) {
        super(cmsAccount, course, quiz);
    }

    

    @Override
    public void run() {
        try {
            super.solution();
        } catch (SolutionException ex) {
            Console.debug(SolutionRunnable.class, ex);
        }
    }

}
