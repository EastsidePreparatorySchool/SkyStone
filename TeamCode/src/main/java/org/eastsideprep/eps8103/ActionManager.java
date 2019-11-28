package org.eastsideprep.eps8103;
import java.util.ArrayList;
import java.util.List;

public class ActionManager {
    public List <Action> actions = new ArrayList<Action>();

    public void checkQueue(){ //run through list and check to see if anything is there, if so remove and run it
        for (int i = 0; i < actions.size(); i++) {
            Action getAct = actions.get(i);
            if (getAct != null && System.currentTimeMillis() >= getAct.timesent){
                getAct.runnable.run();
                actions.remove(i);

            }

        }
    }

    public void addAction(Runnable newrun, long newtime){ //create new action and add to list
        Action newAct = new Action(newrun, newtime);
        actions.add(newAct);

    }

}
