package org.eastsideprep.robotools;

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


}
