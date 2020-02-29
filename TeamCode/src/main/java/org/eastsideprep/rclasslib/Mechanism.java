package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Mechanism {
    private Servo servo;
    private double max;
    private double min;
    private boolean isOpen;

    public Mechanism(HardwareMap hwmap, String servo, double max, double min){
        this.servo = hwmap.servo.get(servo);
        this.max = max;
        this.min = min;
    }

    public Servo getServo() {
        return servo;
    }

    public void setConstraints(double max, double min){
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

//    public void openPercent(int p){
//        int calc = (this.max - this.min) * p;
//        servo.setPosition(this.min + calc);
//    }

}
