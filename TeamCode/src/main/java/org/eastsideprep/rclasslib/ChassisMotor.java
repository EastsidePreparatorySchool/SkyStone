package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class ChassisMotor {

    private DcMotor motor;
    private double modifier;
//    private Telemetry telemetry;
    private String name;

    public void setPower(double power){
        this.motor.setPower(power * this.modifier);
//        this.telemetry.addData("Power " + this.name, power);
    }

    public ChassisMotor(DcMotor dc, double multiplier){
        this.motor = dc;
        this.modifier = multiplier;
    }

    public ChassisMotor(DcMotor dc){
        this.motor = dc;
        this.modifier = 1.0;
    }

//    public ChassisMotor(DcMotor dc, String name, Telemetry telemetry){
//        this(dc);
//        this.name = name;
//        this.telemetry = telemetry;
//    }

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
