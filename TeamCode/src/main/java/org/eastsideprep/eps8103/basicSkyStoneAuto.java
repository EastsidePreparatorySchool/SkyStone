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

import java.util.ArrayList;
import java.util.List;

@Autonomous(name = "leftpark", group = "Concept")
public class basicSkyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    public static final String TAG = "Vuforia Navigation Sample";

    public void forwards(int l) {
        for(DcMotor m: robot.allMotors) {
            m.setTargetPosition(l);
            m.setPower(0.1);
            //sleep(l * 48);
        }
    }

    public void backwards(int l) {
        for(DcMotor m: robot.allMotors) {
            m.setTargetPosition(-1*l);
            m.setPower(0.1);
            //sleep(l * 48);
        }
    }

    public void turnright() {
        robot.leftBackMotor.setPower(-0.25); //have some wheels turn different directions so it goes left
        robot.leftFrontMotor.setPower(0.25);
        robot.rightBackMotor.setPower(0.25);
        robot.rightFrontMotor.setPower(-0.25);
    }

    public void turnrightslowly() {
        robot.leftBackMotor.setPower(-0.05); //have low power so it goes very slow
        robot.leftFrontMotor.setPower(0.05);
        robot.rightFrontMotor.setPower(0.05);
        robot.rightBackMotor.setPower(-0.05);
    }

    public void turnleft() {
        robot.leftFrontMotor.setPower(0.25);
        robot.leftBackMotor.setPower(-0.25);
        robot.rightBackMotor.setPower(-0.25);
        robot.rightFrontMotor.setPower(0.25);
    }

    public void motorStop() {
        robot.rightFrontMotor.setPower(0);
        robot.rightBackMotor.setPower(0);
        robot.leftFrontMotor.setPower(0);
        robot.leftBackMotor.setPower(0);
    }

    public void stopmotors() {
        robot.rightBackMotor.setPower(0);
        robot.rightFrontMotor.setPower(0);
        robot.leftBackMotor.setPower(0);
        robot.leftFrontMotor.setPower(0);
    }

    public void strafeleftslowly() {
        robot.rightFrontMotor.setPower(0.05);
        robot.rightBackMotor.setPower(-0.05);
        robot.leftFrontMotor.setPower(-0.05);
        robot.leftBackMotor.setPower(0.05);
    }

    public void straferightslowly() {
        robot.rightFrontMotor.setPower(-0.05);
        robot.rightBackMotor.setPower(0.05);
        robot.leftFrontMotor.setPower(0.05);
        robot.leftBackMotor.setPower(-0.05);
        //sleep(l*12*2000);
    }

    public void strafeleft(int l) {
        robot.rightFrontMotor.setTargetPosition(l);
        robot.rightBackMotor.setTargetPosition(-1*l);
        robot.leftFrontMotor.setTargetPosition(-1*l);
        robot.leftBackMotor.setTargetPosition(l);
        for(DcMotor m: robot.allMotors){
            m.setPower(0.01);
        }
    }

    public void straferight() {
        robot.rightFrontMotor.setPower(-0.5);
        robot.rightBackMotor.setPower(0.5);
        robot.leftFrontMotor.setPower(0.5);
        robot.leftBackMotor.setPower(-0.5);
    }

    public void lowerarm(int angle) {
        robot.armPivot.setPower(0.6);
        sleep(1000 * angle);
    }

    public void raisearm(int angle) {
        robot.armPivot.setPower(-1);
        sleep(1000 * angle);
    }

    public void extendarm(int l) {
        robot.armExtender.setPower(-1);
        sleep(1000 * l);
    }

    public void reelarm(int l) {
        robot.armExtender.setPower(1);
        sleep(1000 * l);
    }


    @Override
    public void runOpMode() {

        RobotLog.w(TAG, "runopmode");
        robot.init(hardwareMap); //load hardware from other program

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();

        for(DcMotor m: robot.allMotors){
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }

        while (opModeIsActive()) {
            telemetry.addData("log", "starting");
//            forwards(24);
//            telemetry.addData("log", "went forwards");
//            backwards(24);
//            telemetry.addData("log", "went backwards");
            strafeleft(9);
            telemetry.addData("log", "strafed");
        }
    }
}

