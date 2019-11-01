package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

public class SkystoneRobot implements Robot {

    Pose pose;
    MotorPowers driveMotors;

    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    Servo clawServo;

    public SkystoneRobot(HardwareMap hardwareMap, Pose pose){
        frontLeftMotor =hardwareMap.dcMotor.get("FrontLeftMotor");
        frontRightMotor =hardwareMap.dcMotor.get("FrontRightMotor");
        backLeftMotor =hardwareMap.dcMotor.get("BackLeftMotor");
        backRightMotor =hardwareMap.dcMotor.get("BackRightMotor");

        pivotMotor = hardwareMap.dcMotor.get("PivotMotor");
        armMotor = hardwareMap.dcMotor.get("ArmMotor");

        clawServo = hardwareMap.servo.get("ClawServo");

        clawServo.setPosition(clawInit);

        this.pose = pose;

    }

    public Pose getPose(){

        return pose;
    }



    public void setMotors(MotorPowers robotMotors){
        driveMotors = robotMotors;

    }

    public MotorPowers getDriveMotors() {
        return driveMotors;
    }
}
