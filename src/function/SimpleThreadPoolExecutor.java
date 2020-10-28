/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package function;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 *
 * @author Administrator
 */
public class SimpleThreadPoolExecutor extends ThreadPoolExecutor{

    private Runnable runnable;
    private Runnable[] runnables;
    
    private boolean execute;

    public SimpleThreadPoolExecutor(Runnable runnable) {
        super(1, 1, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        this.runnable = runnable;
    }

    public SimpleThreadPoolExecutor(Runnable[] runnables) {
        super(runnables.length, runnables.length, 10, TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
        this.runnables = runnables;
    }

    public void execute() {
        if (execute) {
            return;
        }
        execute = !execute;
        if (runnable != null) {
            super.execute(runnable);
        } else {
            for (Runnable runnable : runnables) {
                super.execute(runnable);
                Function.sleep(10);
            }
            super.shutdown();
        }
    }
}
