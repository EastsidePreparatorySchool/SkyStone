package org.firstinspires.ftc.teamcode.tester.arm;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.teamcode.robots.Arm;

@TeleOp(name = "ArmMover", group = "Tests")

public class ArmMover extends OpMode {
    DcMotor pivotMotor;
    DcMotor linkageMotor;
    double threshold = 0.1;
    double speed = 0.5;
    double pivotPower;
    double linkagePower;
    double pivotPos = 0;
    double linkagePos = 0;
    Arm arm;

    @Override
    public void init() {

        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");
        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        arm = new Arm(hardwareMap, telemetry);
        arm.init();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {


    }

    @Override
    public void loop() {


        linkagePower = thresholdCheck(this.gamepad1.left_trigger - this.gamepad1.right_trigger);

        if (this.gamepad1.left_bumper) {
            pivotPower = 1;
        } else if (this.gamepad1.right_bumper) {
            pivotPower = -1;
        } else {
            pivotPower = 0;
        }

        arm.pivotArm(pivotPower);

        if(linkagePower>0){
            arm.extendLinkage(linkagePower);
        }else if(linkagePower <0){
            arm.retractLinkage(linkagePower);
        }

        linkageMotor.setPower(speed * linkagePower);
        pivotMotor.setPower(speed * pivotPower);
        arm.update();
        telemetry.addData("arm", arm);
        telemetry.update();
    }

    public double thresholdCheck(double motor) {
        if (-threshold < motor && motor < threshold) {

            motor = 0.0;

        }

        return motor;
    }

}
