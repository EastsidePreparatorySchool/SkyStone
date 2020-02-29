package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;

@Autonomous(name = "Everest Auto 2X", group = "15203")

public class AutoE2X extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.chassis.getBackRightMotor().setModifier(0.1);

        waitForStart();

        telemetry.addData("BR Multiplier", robot.chassis.getBackRightMotor().getModifier());
        telemetry.update();

        robot.chassis.performAll(
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 1000000000),
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000000000)
        );

    }
}

