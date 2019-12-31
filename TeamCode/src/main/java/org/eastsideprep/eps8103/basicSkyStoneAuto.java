package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.*;

@Autonomous(name = "BLUE Just puller", group = "BLUE")
public class basicSkyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    //these have to be found experimentally aka a lot of testing
//    int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
//    int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);//last constants need tweaking
//
//    int slow_strafe_c = 1;
//    int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
//    int pivot_c = 1;
//
//    int angle_c = 360 / robot.TICKS_PER_REV;

    double[] drivetrainEncoders = new double[4];
    double[] drivetrainEncodersPrevious = new double[4];

//    ColorSensor color_sensor;

    public void forwards(double speed, double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setPower(speed);
        }
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        for (DcMotor m : robot.allMotors) {
            m.setPower(speed / 2);
        }
        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void backwards(double speed, double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setPower(-speed);
        }
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        for (DcMotor m : robot.allMotors) {
            m.setPower(-speed / 2);
        }
        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void turnright(double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.leftFrontMotor.setPower(0.3);
        robot.leftBackMotor.setPower(0.3);
        robot.rightFrontMotor.setPower(-0.3);
        robot.rightBackMotor.setPower(-0.3);
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        robot.leftFrontMotor.setPower(0.15);
        robot.leftBackMotor.setPower(0.15);
        robot.rightFrontMotor.setPower(-0.15);
        robot.rightBackMotor.setPower(-0.15);

        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void turnleft(double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.leftFrontMotor.setPower(-0.3);
        robot.leftBackMotor.setPower(-0.3);
        robot.rightFrontMotor.setPower(0.3);
        robot.rightBackMotor.setPower(0.3);
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        robot.leftFrontMotor.setPower(0.15);
        robot.leftBackMotor.setPower(0.15);
        robot.rightFrontMotor.setPower(-0.15);
        robot.rightBackMotor.setPower(-0.15);

        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void stopmotors() {
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }
    }

    public void straferight(double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.leftFrontMotor.setPower(0.3);
        robot.leftBackMotor.setPower(-0.3);
        robot.rightFrontMotor.setPower(-0.3);
        robot.rightBackMotor.setPower(0.3);
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        robot.leftFrontMotor.setPower(0.15);
        robot.leftBackMotor.setPower(-0.15);
        robot.rightFrontMotor.setPower(-0.15);
        robot.rightBackMotor.setPower(0.15);

        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void strafeleft(double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.leftFrontMotor.setPower(-0.3);
        robot.leftBackMotor.setPower(0.3);
        robot.rightFrontMotor.setPower(0.3);
        robot.rightBackMotor.setPower(-0.3);
        //run peacefully until i have 20 ms left
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        robot.leftFrontMotor.setPower(0.15);
        robot.leftBackMotor.setPower(0.15);
        robot.rightFrontMotor.setPower(-0.15);
        robot.rightBackMotor.setPower(-0.15);

        sleep(20);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void bayintake(double time) {
        time = time * 1000;
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        robot.bay1.setPower(1);
        robot.bay2.setPower(1);
        while (time - elapsed > 20) {
            elapsed = System.currentTimeMillis() - start;
            sleep(10);
        }
        robot.bay1.setPower(0.7);
        robot.bay2.setPower(0.7);
        sleep(20);
        robot.bay1.setPower(0);
        robot.bay2.setPower(0);
    }

    public void lowerPullers() {
        robot.rightpuller.setPosition(0.6);
        sleep(250);
        robot.leftpuller.setPosition(0);
    }

    public void raisePullers() {
        robot.rightpuller.setPosition(1);
        robot.leftpuller.setPosition(1);
    }

    public void print_encoders() {
        telemetry.addData("wheel encoders", Arrays.toString(drivetrainEncoders));
        telemetry.update();
    }

    @Override
    public void runOpMode() {

        robot.init(hardwareMap); //load hardware from other program

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();

        telemetry.addData("log", "starting");
        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }

        telemetry.addData("drivetrain encoders", Arrays.toString(drivetrainEncoders));
        telemetry.update();

//        robot.lift.setTargetPosition(robot.LIFT_LEVEL_PICKUP);
//        robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//        robot.lift.setPower(0.6);
//        sleep(2000);
//
//        for (int i = 1; i < 8; i++) {
//            robot.lift.setTargetPosition(robot.LIFT_LEVEL_PICKUP + i * robot.LIFT_DELTA);
//            robot.lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);
//            robot.lift.setPower(0.7);
//            sleep(2000);
//        }

        forwards(0.2, 0.7);
        turnleft(1);
        forwards(0.4, 1.3);
        turnleft(1.75);
        backwards(0.4, 0.9);
        straferight(0.5);

        lowerPullers();
        sleep(500);
        forwards(0.25, 2);
        sleep(3000);

        raisePullers();
        telemetry.addData("log", "done");
        telemetry.update();
//

        //robot.lift.setPower(0.15);//power that keeps it steady

        telemetry.update();
        sleep(2000);
    }
}



