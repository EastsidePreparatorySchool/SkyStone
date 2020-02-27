package org.eastsideprep.robotools;


import com.qualcomm.robotcore.hardware.DcMotor;

import java.util.ArrayList;

public class Intake {
    private ArrayList<MotorPower> motors;

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

    public void goFor(int milliseconds) throws InterruptedException {
        this.go();
        Thread.sleep(milliseconds);
        this.stop();
    }
}
