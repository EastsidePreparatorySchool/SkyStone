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

@Autonomous(name = "dead reckoning", group = "Concept")
public class basicSkyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    //these have to be found experimentally aka a lot of testing
    int fast_c = 1;
    int strafe_c = 1;
    int slow_strafe_c = 1;
    int turn_c = 1;
    int ext_c = 1;
    int angle_c = 1;

    public void forwards(int l) {
        for(DcMotor m: robot.allMotors) {
            m.setTargetPosition(fast_c*l);
            m.setPower(0.5);
            //sleep(l * 48);
        }
    }

    public void backwards(int l) {
        for(DcMotor m: robot.allMotors) {
            m.setTargetPosition(fast_c*l);
            m.setPower(0.5);
            //sleep(l * 48);
        }
    }

    public void turnright(int angle, double power) {
        robot.leftBackMotor.setPower(-power); //have some wheels turn different directions so it goes left
        robot.leftFrontMotor.setPower(power);
        robot.rightBackMotor.setPower(power);
        robot.rightFrontMotor.setPower(-power);
        sleep(turn_c * angle);
    }

    public void turnleft(int angle, double power) {
        robot.leftFrontMotor.setPower(power);
        robot.leftBackMotor.setPower(-power);
        robot.rightBackMotor.setPower(-power);
        robot.rightFrontMotor.setPower(power);
        sleep(turn_c * angle);
    }

    public void stopmotors() {
        for (DcMotor m: robot.allMotors){
            m.setPower(0);
        }
    }

    public void strafeleftslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(-1*slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(-1*slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(slow_strafe_c * l);
        for(DcMotor m: robot.allMotors){
            m.setPower(0.1);
        }
    }

    public void straferightslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(-1 * slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(-1 * slow_strafe_c * l);
        for(DcMotor m: robot.allMotors){
            m.setPower(0.1);
        }
    }

    public void strafeleft(int l) {
        robot.rightFrontMotor.setTargetPosition(strafe_c * l);
        robot.rightBackMotor.setTargetPosition(-1*strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(-1*strafe_c * l);
        robot.leftBackMotor.setTargetPosition(strafe_c * l);
        for(DcMotor m: robot.allMotors){
            m.setPower(0.25);
        }
    }

    public void straferight(int l) {
        robot.rightFrontMotor.setTargetPosition(-1 * strafe_c * l);
        robot.rightBackMotor.setTargetPosition(strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(strafe_c * l);
        robot.leftBackMotor.setTargetPosition(-1 * strafe_c * l);
        for(DcMotor m: robot.allMotors){
            m.setPower(0.25);
        }
    }

    public void lowerarm(int angle) {
        robot.armPivot.setTargetPosition(angle_c *angle);
        robot.armPivot.setPower(1);
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


    @Override
    public void runOpMode() {

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
            forwards(24);
            backwards(24);
            telemetry.addData("for/bac", "24, 24");

            straferight(12);
            strafeleft(12);
            telemetry.addData("R/L", "12, 12");

            turnright(45, 0.25);
            turnleft(90, 0.05);
            telemetry.addData("turning", "45R, 45R, 90L");
            

            telemetry.update();
        }
    }
}

