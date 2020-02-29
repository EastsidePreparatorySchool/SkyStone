package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.Chassis;
import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;

@Autonomous(name = "Everest Auto 2C - BR .25", group = "15203")

public class AutoE2C extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        robot.chassis.getBackRightMotor().setModifier(0.25);

        waitForStart();

        telemetry.addData("BR Multiplier", robot.chassis.getBackRightMotor().getModifier());
        telemetry.update();

        robot.chassis.performAll(new ChassisInstruction[]{
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 1000000000),
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000000000)
        });

    }
}

