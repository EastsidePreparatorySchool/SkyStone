package org.eastsideprep.rclasslib;


import java.util.ArrayList;

public class Intake {
    private ArrayList<MotorPower> motors;

    //use variable arguments to that people can add as many motors as they want - intakes don't have to have
    //  a certain number of motors
    public Intake(MotorPower... tuples){
        for (MotorPower t : tuples){
            this.motors.add(t);
        }
    }

    public void addMotors(MotorPower... tuples){
        for (MotorPower t : tuples){
            this.motors.add(t);
        }
    }

    //Commands go for infinity because it will be useful to do other things (eg driving) while your intake is going
    public void go(){
        for (MotorPower t : this.motors){
            t.run();
        }
    }

    public void stop(){
        for (MotorPower t : this.motors){
            t.stop();
        }
    }

    public void reverse(){
        for (MotorPower t : this.motors){
            t.runAtOtherPower(t.getPower() * -1);
        }
    }

    public void goFor(int milliseconds) throws InterruptedException {
        this.go();
        Thread.sleep(milliseconds);
        this.stop();
    }
}
