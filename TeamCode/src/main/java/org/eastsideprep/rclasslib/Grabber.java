package org.eastsideprep.rclasslib;

public class Grabber {
    //I previously had this class take 2 servos and 4 integers but then I realized that just taking 2
    //  mechanisms was way easier.
    private Mechanism leftMechanism;
    private Mechanism rightMechanism;
    private boolean isOpen; //keep track of if the grabber is open or not, so toggle() knows what to do.

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
