package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BasicUtility;

public class Arm {
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public DcMotor pivotMotor;
    public DcMotor linkageMotor;
    public double linkageNormalError = 20;
    public double linkageError = 20;
    public double linkageBigError = 40;
    public double pivotError = 20;
    public double pivotNormalStall = 0.3;
    public double pivotStall = 0.3;
    public double pivotBigStall = 0.5;
    public double pivotPosition;
    public double pivotStartPosition;
    public double linkageStartPosition;
    public double linkagePosition;
    public Boolean extending;
    public Boolean linkageMoving;
    BasicUtility basicUtility = new BasicUtility();
    Arm(){}

    Arm(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }



    /**
     * Initializes the Arm
     */
    public void init(){
        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");
        pivotPosition = pivotMotor.getCurrentPosition();
        linkagePosition = linkageMotor.getCurrentPosition();
        pivotStartPosition = pivotPosition;
        linkageStartPosition = linkagePosition;
        telemetry.addData("Arm", "Initialized");
    }

    /**
     * Updates the arm
     * must be constantly ran if the arm is not in the down position
     */
    public void update(){



    }

    /**
     * Pivots the arm. After calling a movement function
     * the update function must be continuously called
     * @param power the power to put into the pivot motor
     */
    public void pivotArm(Double power){
        pivotMotor.setPower(basicUtility.constraintCheck(power, 1.0, 0.0));
        pivotPosition = pivotMotor.getCurrentPosition();
    }

    /**
     * Extends the linkage given a certain power. After calling a movement function
     * the update function must be continuously called
     * @param power the power to put into the linkage motor
     */
    public void extendLinkage(Double power){
        linkageMotor.setPower(basicUtility.constraintCheck(power, 1.0, 0.0));
        linkagePosition = linkageMotor.getCurrentPosition();
        linkageMoving = true;
        extending = true;
    }

    /**
     * Retracts the linkage given a certain power
     * @param power
     */
    public void retractLinkage(Double power){
        linkageMotor.setPower(basicUtility.constraintCheck(power, 0.0, -1.0));
        linkagePosition = linkageMotor.getCurrentPosition();
        linkageMoving = true;
        extending = false;
    }






}
