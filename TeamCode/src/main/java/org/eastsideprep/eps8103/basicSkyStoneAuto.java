package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

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

@Autonomous(name = "dead reckoning", group = "Concept")
public class basicSkyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    //these have to be found experimentally aka a lot of testing
    int fast_c = (int) (2*robot.TICKS_PER_REV / robot.WHEEL_CIRC *0.2); //2 because the motors are geared up 2:1 to the wheels
    int strafe_c = (int) (2*robot.TICKS_PER_REV / robot.WHEEL_CIRC *0.5);//last constants need tweaking

    int slow_strafe_c = 1;
    int turn_c = (int) (2*robot.TICKS_PER_REV / robot.WHEEL_CIRC *0.065);
    int ext_c = 1;
    int angle_c = 1;

    double[] drivetrainEncoders = new double[4];


    public void forwards(int l) {
        for (DcMotor m : robot.allMotors) {
            m.setTargetPosition(m.getCurrentPosition() + (int) fast_c*l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.5);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m:robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.5);
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
            m.setTargetPosition(m.getCurrentPosition() - (int) fast_c*l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.5);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m:robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.5);
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
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - (int) turn_c*angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + (int) turn_c*angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + (int) turn_c*angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - (int) turn_c*angle);
        
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.2);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m:robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.2);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void turnleft(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + (int) turn_c*angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - (int) turn_c*angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - (int) turn_c*angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + (int) turn_c*angle);
        
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.2);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m:robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.2);
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
        for (DcMotor m:robot.allMotors) {
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
        for (DcMotor m:robot.allMotors) {
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

    public void lowerarm(int angle) {
        robot.armPivot.setTargetPosition(angle_c * angle);
        robot.armPivot.setPower(1);
        //dont set the power to 0, make sure to hold position!
    }

    public void raisearm(int angle) {
        robot.armPivot.setTargetPosition(-1 * angle_c * angle);
        robot.armPivot.setPower(1);
    }

    public void extendarm(int l) {
        robot.armExtender.setTargetPosition(ext_c * l);
        robot.armExtender.setPower(1);
    }

    public void reelarm(int l) {
        robot.armExtender.setTargetPosition(-1 * ext_c * l);
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

        forwards(96);
        strafeleft(4);
        turnright(360);
        straferight(4);
        backwards(18);

        print_encoders();
    }
}

