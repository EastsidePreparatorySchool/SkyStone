package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name = "StrafeRightDelay", group = "autos")

public class StrafeRightDelay extends LinearOpMode {

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
        robot.strafeTime(false, 0.5, time);

        telemetry.addData("robotWheels", robot.getChassisWheels());
        telemetry.update();

    }
}
