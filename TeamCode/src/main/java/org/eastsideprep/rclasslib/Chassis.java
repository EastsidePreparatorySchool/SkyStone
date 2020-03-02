package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Chassis{

    //Keep track of motors and modifiers using ChassisMotor class
    private ChassisMotor fl;
    private ChassisMotor fr;
    private ChassisMotor bl;
    private ChassisMotor br;

    //I need to pass in a hardware map so that it can convert the strings into objects
    public Chassis(HardwareMap hwMap, String fL, String fR, String bL, String bR){
        this.fl = new ChassisMotor(hwMap.dcMotor.get(fL));
        this.fr = new ChassisMotor(hwMap.dcMotor.get(fR));
        this.bl = new ChassisMotor(hwMap.dcMotor.get(bL));
        this.br = new ChassisMotor(hwMap.dcMotor.get(bR));
    }

    //DcMotorSimple.FORWARD, DcMotorSimple.REVERSE - important so the motor knows which way to go. to be called in init
    public void setDirections(DcMotorSimple.Direction fL, DcMotorSimple.Direction fR, DcMotorSimple.Direction bL, DcMotorSimple.Direction bR){
        this.fl.getMotor().setDirection(fL);
        this.fr.getMotor().setDirection(fR);
        this.bl.getMotor().setDirection(bL);
        this.br.getMotor().setDirection(bR);
    }

    //Brake or coast? set in init
    public void setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior behavior){
        this.fl.getMotor().setZeroPowerBehavior(behavior);
        this.fr.getMotor().setZeroPowerBehavior(behavior);
        this.bl.getMotor().setZeroPowerBehavior(behavior);
        this.br.getMotor().setZeroPowerBehavior(behavior);
    }

    public void setModes(DcMotor.RunMode mode){
        this.fl.getMotor().setMode(mode);
        this.fr.getMotor().setMode(mode);
        this.bl.getMotor().setMode(mode);
        this.br.getMotor().setMode(mode);
    }

    //Performs an instruction passed in with ChassisInstruction object. The robot will move in one direction for a
    //certain number of milliseconds. Uses ChassisDirection object to tell which direction to go in
    public void perform(ChassisInstruction instruction) throws InterruptedException {
        //I tried this with a switch but it didn't like switching on objects
        ChassisDirection d = instruction.getDirection();
        if(d == ChassisDirection.FORWARD){
            this.fl.setPower(instruction.getPower());
            this.fr.setPower(instruction.getPower());
            this.bl.setPower(instruction.getPower());
            this.br.setPower(instruction.getPower());
        } else if (d == ChassisDirection.REVERSE){
            this.fl.setPower(instruction.getReversePower());
            this.fr.setPower(instruction.getReversePower());
            this.bl.setPower(instruction.getReversePower());
            this.br.setPower(instruction.getReversePower());
        } else if (d == ChassisDirection.TURN_RIGHT){
            this.fl.setPower(instruction.getPower());
            this.bl.setPower(instruction.getPower());
            this.fr.setPower(instruction.getReversePower());
            this.br.setPower(instruction.getReversePower());
        } else if (d == ChassisDirection.TURN_LEFT) {
            this.fl.setPower(instruction.getReversePower());
            this.bl.setPower(instruction.getReversePower());
            this.fr.setPower(instruction.getPower());
            this.br.setPower(instruction.getPower());
        } else if (d == ChassisDirection.STRAFE_LEFT){
            this.fl.setPower(instruction.getReversePower());
            this.bl.setPower(instruction.getPower());
            this.fr.setPower(instruction.getPower());
            this.br.setPower(instruction.getReversePower());
        } else if (d == ChassisDirection.STRAFE_RIGHT){
            this.fl.setPower(instruction.getPower());
            this.bl.setPower(instruction.getReversePower());
            this.fr.setPower(instruction.getReversePower());
            this.br.setPower(instruction.getPower());
        }

        if(instruction.getMilliseconds() != ChassisInstruction.FOREVER){
            Thread.sleep(instruction.getMilliseconds());
        }

        this.stop();
    }

    //Perform an array of instructions.
    public void performAll(ChassisInstruction[] instructions) throws InterruptedException {
        for (ChassisInstruction i : instructions) {
            this.perform(i);
        }
    }


    private void setAllPowers(double power){
        this.fr.setPower(power);
        this.fl.setPower(power);
        this.bl.setPower(power);
        this.br.setPower(power);
    }

    //stop the robot in case of emergency.
    public void stop(){
        this.setAllPowers(0.0);
    }

    public void stopAndWait(int millis) throws InterruptedException {
        this.stop();
        Thread.sleep(millis);
    }

    //get() commands for each of the motors so that people can modify properties, etc, that I don't already
    //have it set up for (e.g. modifier)
    public ChassisMotor getFrontLeftMotor(){
        return fl;
    }

    public ChassisMotor getFrontRightMotor(){
        return fr;
    }

    public ChassisMotor getBackLeftMotor(){
        return bl;
    }

    public ChassisMotor getBackRightMotor(){
        return br;
    }

}
