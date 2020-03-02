package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;


@Autonomous(name = "Everest Auto 5 - Forwards & Back", group = "15203")

public class AutoE5 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.claw.open();

        waitForStart();

        robot.chassis.perform(
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 2000)
        );

        robot.claw.close();

        robot.chassis.perform(
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000)
        );
        telemetry.addData("Status", "ended");
        telemetry.update();

    }
}

