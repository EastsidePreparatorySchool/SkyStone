package org.firstinspires.ftc.teamcode.tester.arm;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.teamcode.robots.Arm;
import org.firstinspires.ftc.teamcode.robots.Claw;

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
    double max = 0.56;
    Arm arm;
    Claw claw;
    @Override
    public void init() {

        claw = new Claw(hardwareMap, telemetry);
        claw.init();
        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");
        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        arm = new Arm(hardwareMap, telemetry);
        arm.init();
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        //frontController.setServoPosition(0, 0);
        //frontServo.setPosition(1);


    }

    @Override
    public void loop() {


        if(this.gamepad1.a){
            telemetry.addData("turning", "closer");
            claw.varyingClose();
        }else if(this.gamepad1.b){
            telemetry.addData("turning", "farther");
            claw.varyingOpen();
        }


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
        telemetry.addData("claw", claw);
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
