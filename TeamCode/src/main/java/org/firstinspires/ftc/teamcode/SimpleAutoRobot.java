package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SimpleAutoRobot implements Robot {

    MotorPowers driveMotors;

    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Telemetry telemetry;
    LinearOpMode lop;
    //DcMotor pivotMotor;
    //DcMotor armMotor;
    DriveTrain driveTrain;
    //Servo clawServo;
    double encoderSpinDegrees;
    double encoderDist;

    // accuracy is how close an encoder value needs to be to what it's expected to be
    double accuracy;

    public SimpleAutoRobot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode lop){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.lop = lop;

    }

    @Override
    public void init() {
        frontLeftMotor =hardwareMap.dcMotor.get("FrontLeftMotor");
        frontRightMotor =hardwareMap.dcMotor.get("FrontRightMotor");
        backLeftMotor =hardwareMap.dcMotor.get("BackLeftMotor");
        backRightMotor =hardwareMap.dcMotor.get("BackRightMotor");


        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
        // running with encoders, you should probably sotp and resert their current position to 0
        driveTrain.stopAndResetEncoders();
        driveMotors = new MotorPowers(0,0,0,0);

        // pivotMotor = hardwareMap.dcMotor.get("PivotMotor");
        // armMotor = hardwareMap.dcMotor.get("ArmMotor");

        // clawServo = hardwareMap.servo.get("ClawServo");
        // grabbing = false;

    }

    public void stopAndResetEncoders(){
        driveTrain.stopAndResetEncoders();

    }

    public void runWithoutEncoders(){
        driveTrain.runWithoutEncoders();
    }


    // if negative, spins to the left, positive to the right
    public void spinDegrees(double degrees, double speed){




    }



    public void spinEncoderVal(MotorDistances mds, MotorPowers mps, double check, double slowPoint, boolean slowApproach){
        driveTrain.runWithoutEncoders();

        driveTrain.toEncoderVal(mds, mps,check, slowPoint, slowApproach, telemetry, lop);
        telemetry.addData("status", "complete");
    }

    @Override
    public void setMotors(MotorPowers robotMotors) {

    }



    @Override
    public void moveMotors() {

    }

    @Override
    public MotorPowers getDriveMotors() {
        return null;
    }
}
