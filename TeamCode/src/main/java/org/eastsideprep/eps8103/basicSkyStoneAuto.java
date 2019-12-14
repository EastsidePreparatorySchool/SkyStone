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

@Autonomous(name = "Trajan's auto", group = "Concept")
public class basicSkyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    //these have to be found experimentally aka a lot of testing
    int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
    int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);//last constants need tweaking

    int slow_strafe_c = 1;
    int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
    int pivot_c = 1;

    int angle_c = 360 / robot.TICKS_PER_REV;

    double[] drivetrainEncoders = new double[4];
    double[] drivetrainEncodersPrevious = new double[4];

//    ColorSensor color_sensor;

    public void forwards(double speed, double time) {
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

//    public void lowerarm(int angle) {
//        robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() - pivot_c * angle);
//        robot.armPivot.setPower(1);
//        //dont set the power to 0, make sure to hold position!
//    }
//
//    public void raisearm(int angle) {
//        robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() + pivot_c * angle);
//        robot.armPivot.setPower(1);
//    }
//
//    public void extendarm(int l) {
//        robot.armExtender.setTargetPosition(1581);
//        robot.armExtender.setPower(1);
//    }
//
//    public void reelarm(int l) {
//        robot.armExtender.setTargetPosition(141);//found this value using teleop
//        robot.armExtender.setPower(1);
//    }

    public void bayintake(double time) {
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
        double position = robot.rightpuller.getPosition();
        robot.leftpuller.setPosition(1);
        robot.rightpuller.setPosition(1);
        sleep(2500);
    }

    public void raisePullers() {
        robot.leftpuller.setPosition(0);
        robot.rightpuller.setPosition(0);
        sleep(2500);
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
        //remember to start in the "legal" position
//        robot.armPivot.setTargetPosition(-400);
//        robot.armPivot.setPower(0.5);
//        robot.armExtender.setTargetPosition(141);
//        robot.armExtender.setPower(0.5);
//        while (robot.armPivot.isBusy()) {
//            robot.armPivot.setPower(0.5);
//        }
//        if (!robot.armPivot.isBusy()) {
//            robot.armPivot.setPower(0);
//        }
//        while (robot.armExtender.isBusy()) {
//            robot.armExtender.setPower(0.5);
//        }
//        if (!robot.armExtender.isBusy()) {
//            robot.armExtender.setPower(0);
//        }

//        forwards(18, 0.6);
//        straferight(20);
//        backwards(20);
//        strafeleft(20);
//        forwards(20, 0.6);

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
        strafeleft(1.5);
        forwards(0.4, 1);
        turnleft(2);
        backwards(0.1, 0.5);

        robot.rightpuller.setPosition(1);
        robot.leftpuller.setPosition(1);

        robot.rightpuller.setPosition(0);
        robot.leftpuller.setPosition(0);

        robot.lift.setPower(0.25);//power that keeps it steady

        telemetry.update();

    }
}



