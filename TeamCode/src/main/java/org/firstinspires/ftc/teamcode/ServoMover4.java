package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover4", group = "Tests")

public class ServoMover4 extends OpMode {
    Servo testServo;
    ServoController testController;
    double max = 0.56;
    @Override
    public void init() {
        testServo = hardwareMap.servo.get("clawServo");
        testController = testServo.getController();
        testServo.setDirection(Servo.Direction.FORWARD);
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
        double controllerPos = testController.getServoPosition(0);
        if(this.gamepad1.a && controllerPos >0.1){
            telemetry.addData("turning", "closer");
            testController.setServoPosition(0,testController.getServoPosition(0)-0.1);
        }else if(this.gamepad1.b && controllerPos<max){
            telemetry.addData("turning", "farther");
            testController.setServoPosition(0,testController.getServoPosition(0)+0.1);
        }
        telemetry.update();
    }
}
