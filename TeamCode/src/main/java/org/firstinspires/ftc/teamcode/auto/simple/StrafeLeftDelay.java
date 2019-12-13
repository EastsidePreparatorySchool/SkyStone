package org.firstinspires.ftc.teamcode.auto.simple;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.robots.SimpleAutoRobot;

@Autonomous(name = "StrafeLeftDelay", group = "autos")

public class StrafeLeftDelay extends LinearOpMode {

    SimpleAutoRobot robot;

    public void runOpMode() throws InterruptedException {
        robot = new SimpleAutoRobot(hardwareMap, telemetry, this);
        robot.init();
        long t2 = 10000;
        long lastTime = System.currentTimeMillis();
        while(System.currentTimeMillis()-lastTime<t2){
            //do nothing
            if(this.isStopRequested()){break;}
        }

        long time = 500;
        robot.strafeTime(true, 0.5, time);

        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.update();

    }
}
