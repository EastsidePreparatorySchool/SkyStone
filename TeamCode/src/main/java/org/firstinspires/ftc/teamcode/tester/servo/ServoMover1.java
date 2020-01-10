package org.firstinspires.ftc.teamcode.tester.servo;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover1", group = "Tests")

public class ServoMover1 extends OpMode {
    Servo frontServo;
    ServoController frontController;

    double max = 0.56;

    @Override
    public void init() {
        frontServo = hardwareMap.servo.get("frontServo");
        frontController = frontServo.getController();
        frontServo.setDirection(Servo.Direction.FORWARD);
        telemetry.addData("Status", "Initialized");
        telemetry.update();
    }

    @Override
    public void start() {
        frontServo.setPosition(0.001);
        //frontController.setServoPosition(0, 0);
        //frontServo.setPosition(max);
        frontController.setServoPosition(0, 0.001);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", frontServo.getPosition());
        telemetry.addData("ControlPos", frontController.getServoPosition(0));
        if(this.gamepad1.a){
            frontServo.setPosition(0.001);
        }else if(this.gamepad1.b){
            frontServo.setPosition(max);
        }
        telemetry.update();
    }
}
