package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.robotools.ChassisDirection;
import org.eastsideprep.robotools.ChassisInstruction;

@Autonomous(name = "Everest Auto 2", group = "15203")

public class AutoE2 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        waitForStart();

        telemetry.addData("BR Multiplier", robot.chassis.getBackRightMotor().getModifier());
        telemetry.update();

        robot.chassis.performAll(
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 1000000000),
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000000000)
        );

    }
}

