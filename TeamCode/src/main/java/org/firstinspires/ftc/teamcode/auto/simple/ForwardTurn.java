package org.firstinspires.ftc.teamcode.auto.simple;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;

@Autonomous(name = "ForwardTurn", group = "autos")

public class ForwardTurn extends  LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        robot.stopAndResetEncoders();
        robot.runWithoutEncoders();



        robot.forwardEncoder(true, 0.5, 500.0, 60.0);
        robot.stopAndResetEncoders();
        robot.runWithoutEncoders();
        robot.turnToAngle(0.5,165.0,20.0);
        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.update();

    }
}
