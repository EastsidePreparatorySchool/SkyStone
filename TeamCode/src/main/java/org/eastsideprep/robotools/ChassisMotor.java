package org.eastsideprep.robotools;

import com.qualcomm.robotcore.hardware.DcMotor;

public class ChassisMotor {

    private DcMotor motor;
    private double modifier;

    public void setPower(double power){
        this.motor.setPower(power * this.modifier);
    }

    public ChassisMotor(DcMotor dc, double multiplier){
        this.motor = dc;
        this.modifier = multiplier;
    }

    public ChassisMotor(DcMotor dc){
        this.motor = dc;
    }

    public double getModifier() {
        return modifier;
    }

    public void setModifier(double modifier) {
        this.modifier = modifier;
    }

    public DcMotor getMotor() {
        return motor;
    }
}
