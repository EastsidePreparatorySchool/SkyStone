package org.eastsideprep.eps8103;

import android.graphics.Color;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
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

import java.util.Arrays;

@Autonomous(name = "Encoder Test", group = "Concept")
public class EncoderTest extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    //these have to be found experimentally aka a lot of testing
    int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
    int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);//last constants need tweaking

    int slow_strafe_c = 1;
    int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
    int ext_c = 1;

    int angle_c = 360 / robot.TICKS_PER_REV;

    double[] drivetrainEncoders = new double[4];

    ColorSensor color_sensor;

    public void forwards(int l, double speed) {
        for (DcMotor m : robot.allMotors) {
            m.setTargetPosition(m.getCurrentPosition() + (int) fast_c * l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(speed);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(speed);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
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

    }

    public void turnleft(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - (int) turn_c * angle);

        //for (DcMotor m : robot.allMotors) {
        //  m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //m.setPower(0.25);
        //}
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

    public void strafeleft(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + strafe_c * l);
        for (DcMotor m : robot.allMotors) {
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
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void straferight(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - strafe_c * l);
        for (DcMotor m : robot.allMotors) {
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
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void print_encoders() {
        telemetry.addData("wheel encoders", Arrays.toString(drivetrainEncoders));
        telemetry.update();
    }


    @Override
    public void runOpMode() {

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;


        robot.init(hardwareMap); //load hardware from other program

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();

        //Color.RGBToHSV(color_sensor.red()*8, color_sensor.green() *8, color_sensor.blue() * 8, hsvValues);
        //telemetry.addData("red", color_sensor.red());
        //telemetry.addData("Green", color_sensor.green());
        //telemetry.addData("Blue", color_sensor.blue());

        //telemetry.addData("color sensor", new float[] {color_sensor.red(), color_sensor.green(), color_sensor.blue()});
        //color_sensor.enableLed(true);

        telemetry.addData("log", "starting");

        forwards(18, 0.6);
        straferight(18);
        strafeleft(24);
        forwards(6, 0.2);
        backwards(6);
        straferight(18);
        backwards(2);
        straferight(18);
        strafeleft(24);
        forwards(6, 0.2);
        backwards(6);

            /*

            forwards(30, 0.1);//check using camera!!
            if (true) {
                turnleft(90);
                extendarm(8);
                robot.updown.setPosition(1);
                robot.closer.setPosition(0.5);
                reelarm(8);
            }//pickup block

            turnleft(90);
            forwards(80, 0.6);
            turnleft(90);
            backwards(24);


             */
        print_encoders();

    }
}




