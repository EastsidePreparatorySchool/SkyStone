package org.eastsideprep.eps15203;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Move Foundation 15203", group="15203")

public class MoveFoundation15203 extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware15203 robot = new Hardware15203();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() {
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Status", "started");    //
        telemetry.update();

        waitForStart();
        robot.allDrive(0.5, 750);
        robot.turn(0.5, 780);
        robot.allDrive(0.5, 2075);
        robot.turn(-0.5, 780);
        robot.allDrive(0.5, 2700);
        robot.turn(0.5, 785);
        robot.allDrive(0.5, 250);
        robot.turn(0.5, 785);
        //FINGER
        telemetry.addData("Info", "PUT THE FINGER DOWN HERE");
        sleep(3000);
        robot.allDrive(-0.5, 1250);
    }
}
