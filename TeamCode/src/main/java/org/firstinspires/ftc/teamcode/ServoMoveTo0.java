package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMoveTo0", group = "Tests")
public class ServoMoveTo0 extends OpMode {
    Servo testServo;
    ServoController testController;

    @Override
    public void init() {
        // change this name based on your configuration
        testServo = hardwareMap.servo.get("clawServo");
        testController = testServo.getController();
        telemetry.addData("Status: ", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        testServo.setPosition(0);

        testController.setServoPosition(0, 0);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", testServo.getPosition());
        telemetry.addData("ControlPos", testController.getServoPosition(0));

        telemetry.update();

    }

}
