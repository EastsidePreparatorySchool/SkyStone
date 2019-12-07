package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover3", group = "Tests")

public class ServoMover3 extends OpMode {

    Servo testServo;
    ServoController testController;

    @Override
    public void init() {
        testServo = hardwareMap.servo.get("clawServo");
        telemetry.addData("port number", testServo.getPortNumber());
        telemetry.addData("device name", testServo.getDeviceName());
        telemetry.addData("manufacturer", testServo.getManufacturer());
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
        if(this.gamepad1.a){
            telemetry.addData("turning", "closer");
            testServo.setPosition(testServo.getPosition()-0.01);
        }else if(this.gamepad1.b){
            telemetry.addData("turning", "farther");
            testServo.setPosition(testServo.getPosition()+0.01);
        }
        telemetry.update();
    }
}
