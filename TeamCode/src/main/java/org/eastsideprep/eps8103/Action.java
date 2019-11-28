package org.eastsideprep.eps8103;

public class Action {
    Runnable runnable;
    long timesent;
    Action(Runnable runnable, long time){
        this.runnable = runnable;
        this.timesent = time + System.currentTimeMillis();
    }

}
