package org.eastsideprep.rclasslib;

public class Grabber {

    private Mechanism leftMechanism;
    private Mechanism rightMechanism;
    private boolean isOpen;

    public Grabber(Mechanism leftMechanism, Mechanism rightMechanism){
        this.leftMechanism = leftMechanism;
        this.rightMechanism = rightMechanism;
    }

    public void open(){
        this.leftMechanism.open();
        this.rightMechanism.open();
        this.isOpen = true;
    }

    public void close(){
        this.leftMechanism.close();
        this.rightMechanism.close();
        this.isOpen = false;
    }

//    public void openPercent(int p){
//        leftMechanism.openPercent(p);
//        rightMechanism.openPercent(p);
//    }

    public void toggle(){
        if(this.isOpen){
            this.close();
        } else {
            this.open();
        }
    }

    public Mechanism getLeftMechanism() {
        return leftMechanism;
    }

    public Mechanism getRightMechanism() {
        return rightMechanism;
    }

}
