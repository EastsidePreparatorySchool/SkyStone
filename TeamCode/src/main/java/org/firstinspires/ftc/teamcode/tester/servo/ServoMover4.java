package org.firstinspires.ftc.teamcode.tester.servo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover4", group = "Tests")

public class ServoMover4 extends OpMode {
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
        //frontServo.setPosition(1);
        frontController.setServoPosition(0, 0.56);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", frontServo.getPosition());
        telemetry.addData("ControlPos", frontController.getServoPosition(0));
        double controllerPos = frontController.getServoPosition(0);
        if(this.gamepad1.a && controllerPos >0.1){
            telemetry.addData("turning", "closer");
            frontController.setServoPosition(0, frontController.getServoPosition(0)-0.1);
        }else if(this.gamepad1.b && controllerPos<max){
            telemetry.addData("turning", "farther");
            frontController.setServoPosition(0, frontController.getServoPosition(0)+0.1);
        }
        telemetry.update();
    }
}
