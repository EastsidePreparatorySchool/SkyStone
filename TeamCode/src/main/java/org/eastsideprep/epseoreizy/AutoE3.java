package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;


@Autonomous(name = "Everest Auto 3", group = "15203")

public class AutoE3 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        double s = 0.1;

        waitForStart();

        robot.chassis.getBackRightMotor().setPower(s);
        sleep(1000);
        robot.chassis.getBackRightMotor().setPower(0.0);
        robot.chassis.getBackLeftMotor().setPower(s);
        sleep(1000);
        robot.chassis.getBackLeftMotor().setPower(0.0);
        robot.chassis.getFrontRightMotor().setPower(s);
        sleep(1000);
        robot.chassis.getFrontRightMotor().setPower(0.0);
        robot.chassis.getFrontLeftMotor().setPower(s);
        sleep(1000);
        robot.chassis.getFrontLeftMotor().setPower(0.0);

        telemetry.addData("Status", "started");
        telemetry.update();


    }
}

