package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMoveToMax", group = "Tests")
public class ServoMoveToMax extends OpMode {
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
        testServo.setPosition(0);

        testController.setServoPosition(0, 0);
        testServo.setPosition(max);
        testController.setServoPosition(0, max);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", testServo.getPosition());
        telemetry.addData("ControlPos", testController.getServoPosition(0));

        telemetry.update();
    }
}
