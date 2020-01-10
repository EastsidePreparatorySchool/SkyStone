package org.firstinspires.ftc.teamcode.auto.simple;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;

@Autonomous(name = "ForwardLeft", group = "autos")

public class ForwardGrabLeft extends LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        robot.stopAndResetEncoders();
        robot.runWithoutEncoders();

        robot.forwardEncoder(true, 0.5, 1000.0, 60.0);
        robot.stopAndResetEncoders();
        robot.runWithoutEncoders();

        robot.grab();
        long timer = 500;
        long lastTime = System.currentTimeMillis();
        while (System.currentTimeMillis() - lastTime < timer) {
            if (isStopRequested()) {
                break;
            }
        }

        robot.forwardEncoder(false, 0.5, 300.0, 60.0);
        robot.stopAndResetEncoders();
        robot.runWithoutEncoders();

        robot.turnToAngle(0.5, -90.0, 10.0);

    }
}
