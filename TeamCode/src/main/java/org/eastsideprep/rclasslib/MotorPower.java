package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.DcMotor;

public class MotorPower {
    private DcMotor motor;
    private double power;

    public MotorPower(DcMotor motor, double power) {
        this.motor = motor;
        this.power = power;
    }

    public DcMotor getMotor() {
        return motor;
    }

    public double getPower() {
        return power;
    }

    public double setPower(double power){
        this.power = power;
        return power;
    }

    public void run(){
        this.motor.setPower(this.power);
    }

    public void runAtOtherPower(double otherPower){
        this.motor.setPower(otherPower);
    }

    public void stop(){
        this.motor.setPower(0.0);
    }


}
