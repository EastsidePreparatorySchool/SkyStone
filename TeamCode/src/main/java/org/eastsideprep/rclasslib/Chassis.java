package org.eastsideprep.rclasslib;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Chassis{

    private ChassisMotor fl;
    private ChassisMotor fr;
    private ChassisMotor bl;
    private ChassisMotor br;

    private Telemetry telemetry;

    public Chassis(HardwareMap hwMap, String fL, String fR, String bL, String bR){
        this.fl = new ChassisMotor(hwMap.dcMotor.get(fL));
        this.fr = new ChassisMotor(hwMap.dcMotor.get(fR));
        this.bl = new ChassisMotor(hwMap.dcMotor.get(bL));
        this.br = new ChassisMotor(hwMap.dcMotor.get(bR));
    }

    public void setDirections(DcMotorSimple.Direction fL, DcMotorSimple.Direction fR, DcMotorSimple.Direction bL, DcMotorSimple.Direction bR){
        this.fl.getMotor().setDirection(fL);
        this.fr.getMotor().setDirection(fR);
        this.bl.getMotor().setDirection(bL);
        this.br.getMotor().setDirection(bR);
    }

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
        } else {
            //ummm........... whaaaaaa??????
        }

        if(instruction.getMilliseconds() != ChassisInstruction.FOREVER){
            Thread.sleep(instruction.getMilliseconds());
        }

        this.stop();
    }

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

    public void stop(){
        this.setAllPowers(0.0);
    }

    public void stopAndWait(int millis) throws InterruptedException {
        this.stop();
        Thread.sleep(millis);
    }

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
