package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;


@Autonomous(name = "Everest Auto 4 - Grabber Test", group = "15203")

public class AutoE4 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        robot.claw.open();
        sleep(1000);
        robot.claw.close();
        sleep(1000);

        telemetry.addData("Status", "started");
        telemetry.update();


    }
}

