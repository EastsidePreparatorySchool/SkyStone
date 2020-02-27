package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class Mechanism {
    private Servo servo;
    private int max;
    private int min;
    private boolean isOpen;

    public Mechanism(HardwareMap hwmap, String servo, int max, int min){
        this.servo = hwmap.servo.get(servo);
        this.max = max;
        this.min = min;
    }

    public Servo getServo() {
        return servo;
    }

    public void setConstraints(int max, int min){
        this.max = max;
        this.min = min;
    }

    public void open(){
        if(!isOpen) {
            servo.setPosition(this.max);
            isOpen = true;
        }
    }

    public void close(){
        if(isOpen) {
            servo.setPosition(this.min);
            isOpen = false;
        }
    }

    public void setPosition(int pos){
        servo.setPosition(pos);
    }

}
