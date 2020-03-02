package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.eastsideprep.rclasslib.ChassisDirection;
import org.eastsideprep.rclasslib.ChassisInstruction;


@Autonomous(name = "Everest Auto 6 - Real Actual", group = "15203")

public class AutoE6 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();   // Use a Pushbot's hardware


    @Override
    public void runOpMode() throws InterruptedException {
        robot.init(hardwareMap);

        //open the claw to init. Note, robot will need 'Moves upon initialization' sticker.
        robot.claw.open();

        //Create array with all the commands I want to run
        ChassisInstruction[] commands_to_block = new ChassisInstruction[]{
                new ChassisInstruction(ChassisDirection.FORWARD, 0.5, 750),
                new ChassisInstruction(ChassisDirection.TURN_RIGHT, 0.25, 1520),
                new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 1000),
                new ChassisInstruction(ChassisDirection.STRAFE_LEFT, 0.25, 1600),
                new ChassisInstruction(ChassisDirection.FORWARD, 0.2, 1500)
        }; //you can predefine the array or not, works either way

        waitForStart();

        robot.chassis.performAll(commands_to_block); //perform my commands

        robot.claw.close(); //pick up block

        robot.chassis.perform(new ChassisInstruction(ChassisDirection.REVERSE, 0.5, 2000)); //back up away from block
        sleep(2000);

        telemetry.addData("Status", "ended");
        telemetry.update();

    }
}

