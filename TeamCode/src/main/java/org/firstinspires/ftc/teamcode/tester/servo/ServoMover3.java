package org.firstinspires.ftc.teamcode.tester.servo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover3", group = "Tests")

public class ServoMover3 extends OpMode {

    Servo frontServo;
    ServoController frontController;

    @Override
    public void init() {
        frontServo = hardwareMap.servo.get("frontServo");
        telemetry.addData("port number", frontServo.getPortNumber());
        telemetry.addData("device name", frontServo.getDeviceName());
        telemetry.addData("manufacturer", frontServo.getManufacturer());
        frontController = frontServo.getController();
        frontServo.setDirection(Servo.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        frontServo.setPosition(0.001);
        //frontController.setServoPosition(0, 0);
        //frontServo.setPosition(1);
        frontController.setServoPosition(0, 0.56);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", frontServo.getPosition());
        telemetry.addData("ControlPos", frontController.getServoPosition(0));
        if(this.gamepad1.a){
            telemetry.addData("turning", "closer");
            frontServo.setPosition(frontServo.getPosition()-0.01);
        }else if(this.gamepad1.b){
            telemetry.addData("turning", "farther");
            frontServo.setPosition(frontServo.getPosition()+0.01);
        }
        telemetry.update();
    }
}
