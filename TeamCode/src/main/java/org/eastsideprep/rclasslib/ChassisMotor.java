package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ChassisMotor {

    private DcMotor motor;
    private double modifier;

    //Set the DcMotor power so instead of m.getMotor().setPower() it's just m.setPower(). also includes modifier
    public void setPower(double power){
        this.motor.setPower(power * this.modifier);
    }

    //option to construct without multiplier, because most times the motor wouldn't have one. I only really made the multiplier
    //  because 9884's robot had a weird motor that I was trying to fix.
    public ChassisMotor(DcMotor dc, double multiplier){
        this.motor = dc;
        this.modifier = multiplier;
    }

    public ChassisMotor(DcMotor dc){
        this.motor = dc;
        this.modifier = 1.0;
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
