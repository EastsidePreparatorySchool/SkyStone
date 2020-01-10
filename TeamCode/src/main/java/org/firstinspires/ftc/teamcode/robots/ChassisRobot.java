package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;
import org.firstinspires.ftc.teamcode.robots.motors.DriveTrain;


public class ChassisRobot implements Robot {
    Telemetry telemetry;
    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    public DriveTrain driveTrain;
    public MotorPowers driveMotors;
    public Boolean runningWithEncoders;
    public ChassisRobot(HardwareMap hardwareMap, Telemetry telemetry, boolean runWithEncoders) {
        this.runningWithEncoders = runWithEncoders;
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }

    public Boolean getRunningWithEncoders(){
        return runningWithEncoders;
    }

    public DriveTrain getDriveTrain(){
        return driveTrain;
    }

    public void init() {


        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");


        telemetry.addData("driveTrain", "done");
        telemetry.update();

        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, true);
        driveTrain.stopAndResetEncoders();
        if(runningWithEncoders) {
            driveTrain.runWithEncoders();
        }else{
            driveTrain.runWithoutEncoders();
        }
        runningWithEncoders = true;
        driveMotors = new MotorPowers(0, 0, 0, 0);

    }

    @Override
    public void setMotors(MotorPowers robotMotors) {
        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = robotMotors.fR;
        driveMotors.bR = robotMotors.bR;

    }

    public void moveMotors() {
        driveMotors.scale();
        telemetry.addData("dmotor", driveMotors);
        driveTrain.runMotors(driveMotors);


    }


    public MotorPowers getDriveMotors() {
        return driveMotors;
    }
    public void runWithEncoders(){
        driveTrain.runWithEncoders();
        runningWithEncoders = true;
    }

    public void runWithoutEncoders(){
        driveTrain.runWithoutEncoders();
        runningWithEncoders = false;
    }

}
