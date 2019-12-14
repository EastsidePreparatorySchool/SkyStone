package org.firstinspires.ftc.teamcode.auto.everest_tests;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;

@Autonomous(name = "everest 1", group = "autos")

public class everest_template_1 extends LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        long time = 500;
        robot.strafeTime(true, 0.5, time);
        long time2 = 5000;
        robot.forwardTime(true, 0.5, time2);

        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.update();

    }


}
