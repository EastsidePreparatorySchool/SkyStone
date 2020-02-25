package org.eastsideprep.robotools;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Grabber {
    private Servo leftServo;
    private Servo rightServo;
    private int min1;
    private int max1;
    private int min2;
    private int max2;
    private boolean servo1set = false;
    private boolean servo2set = false;

    public Servo getLeftServo(){
        return leftServo;
    }

    public Servo getRightServo(){
        return rightServo;
    }

    public Grabber(HardwareMap hwmap, String leftServo, int min1, int max1, String rightServo, int min2, int max2){
        this.setLeftServo(hwmap.servo.get(leftServo), min1, max1);
        this.setRightServo(hwmap.servo.get(rightServo), min2, max2);
    }

    private void setLeftServo(Servo servo1, int min, int max) {
        if(!servo1set) {
            this.leftServo = servo1;
            this.min1 = min;
            this.max1 = max;
            this.servo1set = true;
        }
    }

    private void setRightServo(Servo servo2, int min, int max) {
        if(!servo1set) {
            this.rightServo = servo2;
            this.min2 = min;
            this.max2 = max;
            this.servo2set = true;
        }
    }

    public void open(){
        leftServo.setPosition(max1);
        rightServo.setPosition(max2);
    }

    public void close(){
        leftServo.setPosition(min1);
        rightServo.setPosition(min2);
    }

}
