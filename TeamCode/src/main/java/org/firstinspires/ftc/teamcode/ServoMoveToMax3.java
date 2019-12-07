package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMoveToMax3", group = "Tests")

public class ServoMoveToMax3  extends OpMode {
    Servo testServo;
    ServoController testController;

    double max = 0.56;

    @Override
    public void init() {
        testServo = hardwareMap.servo.get("clawServo");
        testController = testServo.getController();

        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        //testServo.setPosition(0);

        testController.setServoPosition(0, 0);
        testServo.setPosition(0.56);
        //testController.setServoPosition(0, 1);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", testServo.getPosition());
        telemetry.addData("ControlPos", testController.getServoPosition(0));
        telemetry.update();
    }
}
