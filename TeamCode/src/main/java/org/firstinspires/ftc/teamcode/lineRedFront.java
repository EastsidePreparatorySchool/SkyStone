package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;



@Autonomous(name = "lineRedFront", group = "NoVFAutos")

// every "NoVFAuto" doesn't use vuforia and only gets the minimum done
// other autonomous's use a far more complicated system, that's far more work
public class lineRedFront extends OpMode {
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    MotorPowers motorPowers;
    Servo clawServo;
    TeleopRobot robot;


    @Override
    public void init() {
        robot = new TeleopRobot(hardwareMap, this.telemetry);
        robot.init();
        motorPowers = new MotorPowers(0,0,0,0);
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void start(){


    }

    @Override
    public void loop() {

    }
}
