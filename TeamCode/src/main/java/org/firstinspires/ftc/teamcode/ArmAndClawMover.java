package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ArmAndClawMover", group = "Tests")

public class ArmAndClawMover extends OpMode {

    DcMotor pivotMotor;
    DcMotor linkageMotor;
    double threshold = 0.1;
    double speed = 0.5;
    double pivotPower;
    double linkagePower;
    double pivotPos = 0;
    double linkagePos = 0;
    Servo testServo;
    ServoController testController;
    double max = 0.56;

    @Override
    public void init() {
        testServo = hardwareMap.servo.get("clawServo");
        testController = testServo.getController();
        testServo.setDirection(Servo.Direction.FORWARD);

        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");
        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        testServo.setPosition(0.001);
        //testController.setServoPosition(0, 0);
        //testServo.setPosition(1);
        testController.setServoPosition(0, 0.56);

    }

    @Override
    public void loop() {

        telemetry.addData("Servo Position", testServo.getPosition());
        telemetry.addData("ControlPos", testController.getServoPosition(0));
        if(this.gamepad1.a){
            telemetry.addData("turning", "closer");
            testServo.setPosition(testServo.getPosition()-0.01);
        }else if(this.gamepad1.b){
            telemetry.addData("turning", "farther");
            testServo.setPosition(testServo.getPosition()+0.01);
        }

        linkagePower = thresholdCheck(this.gamepad1.left_trigger - this.gamepad1.right_trigger);
        if (this.gamepad1.left_bumper) {
            pivotPower = 1;
        } else if (this.gamepad1.right_bumper) {
            pivotPower = -1;
        } else {
            pivotPower = 0;
        }

        linkageMotor.setPower(speed * linkagePower);
        pivotMotor.setPower(speed * pivotPower);

        pivotPos = pivotMotor.getCurrentPosition();
        linkagePos = linkageMotor.getCurrentPosition();
        telemetry.addData("Pivot Position", pivotPos);
        telemetry.addData("Linkage Position", linkagePos);
        telemetry.update();
    }

    public double thresholdCheck(double motor) {
        if (-threshold < motor && motor < threshold) {

            motor = 0.0;

        }

        return motor;
    }
}
