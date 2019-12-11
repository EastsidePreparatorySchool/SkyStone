package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "StrafeRight", group = "autos")

public class StrafeRight extends LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        long time = 500;
        robot.strafeTime(false, 0.5, time);

        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.update();

    }
}
