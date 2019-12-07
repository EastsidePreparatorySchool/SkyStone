package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

@TeleOp(name = "ServoMover2", group = "Tests")

public class ServoMover2 extends OpMode {
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
        testController.setServoPosition(0, 0.001);

       //testController.setServoPosition(0, 1);

    }

    @Override
    public void loop() {
        telemetry.addData("Servo Position", testServo.getPosition());
        telemetry.addData("ControlPos", testController.getServoPosition(0));
        // press a go to 1
        if(this.gamepad1.a){
            testController.setServoPosition(0, max);
        }else if(this.gamepad1.b){
            //press b go to 0
            testController.setServoPosition(0,0.001);
        }
        telemetry.update();
    }
}
