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

    public void backwards(int l) {
        for (DcMotor m : robot.allMotors) {
            m.setTargetPosition(m.getCurrentPosition() - (int) fast_c * l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.4);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.4);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void turnright(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + (int) turn_c * angle);


        robot.leftBackMotor.setPower(-0.25); //have some wheels turn different directions so it goes left
        robot.leftFrontMotor.setPower(0.25);
        robot.rightBackMotor.setPower(0.25);
        robot.rightFrontMotor.setPower(-0.25);
        // sleep(turn_c * angle);
    }

    public void turnrightslowly(int angle) {
        robot.leftBackMotor.setPower(-0.05); //have low power so it goes very slow
        robot.leftFrontMotor.setPower(0.05);
        robot.rightFrontMotor.setPower(0.05);
        robot.rightBackMotor.setPower(-0.05);
        //sleep(turn_c*angle*5)
    }

    public void turnleft(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - (int) turn_c * angle);

        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.25);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.25);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void stopmotors() {
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }
    }

    public void strafeleftslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + slow_strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.1);
        }
    }

    public void straferightslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - slow_strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.1);
        }
    }

    public void straferight(double speed, double time) {
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.rightFrontMotor.setPower(-speed);
        robot.rightBackMotor.setPower(speed);
        robot.leftFrontMotor.setPower(speed);
        robot.leftBackMotor.setPower(-speed);

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

    public void strafeleft(int l) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - strafe_c * l);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + strafe_c * l);

        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.25);
        }
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.25);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            robot.allMotors[i].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void lowerarm(int angle) {
        robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() - pivot_c * angle);
        robot.armPivot.setPower(1);
        //dont set the power to 0, make sure to hold position!
    }

    public void raisearm(int angle) {
        robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() + pivot_c * angle);
        robot.armPivot.setPower(1);
    }

    public void extendarm(int l) {
        robot.armExtender.setTargetPosition(1581);
        robot.armExtender.setPower(1);
    }

    public void reelarm(int l) {
        robot.armExtender.setTargetPosition(141);//found this value using teleop
        robot.armExtender.setPower(1);
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
        robot.armPivot.setTargetPosition(-400);
        robot.armPivot.setPower(0.5);
        robot.armExtender.setTargetPosition(141);
        robot.armExtender.setPower(0.5);
        while (robot.armPivot.isBusy()) {
            robot.armPivot.setPower(0.5);
        }
        if (!robot.armPivot.isBusy()) {
            robot.armPivot.setPower(0);
        }
        while (robot.armExtender.isBusy()) {
            robot.armExtender.setPower(0.5);
        }
        if (!robot.armExtender.isBusy()) {
            robot.armExtender.setPower(0);
        }

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
        forwards(0.5, 2000);
        straferight(0.5, 2000);


        telemetry.update();

//        robot.color_range_sensor.enableLed(false);
//        sleep(1000);
//        robot.color_range_sensor.enableLed(true);
//
//
//        while (robot.color_range_sensor.red() > 30) {
//
//            telemetry.update();
//            straferightslowly(40);
//            ///extendarm(8);
//            robot.updown.setPosition(1);
//            robot.closer.setPosition(0.5);
//            //reelarm(8);
//        }//pickup block
//        telemetry.addData("log", "found skytone");
//        turnleft(90);
//        forwards(80, 0.6);
//        turnleft(90);
//        backwards(24);
//
    }
}



