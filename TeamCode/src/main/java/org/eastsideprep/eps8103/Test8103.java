package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import org.eastsideprep.eps8103.Hardware8103;
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

@Autonomous(name="Test 8103", group="8103")
public class Test8103 extends LinearOpMode {


    /* Declare OpMode members. */
    Hardware8103 robot = new Hardware8103();

    public void driveForward() {
        robot.leftBackMotor.setPower(0.25);
        robot.leftFrontMotor.setPower(0.25);
        robot.rightFrontMotor.setPower(0.25);
        robot.rightBackMotor.setPower(0.25);
        //sleep(l * 48);
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

    public void backwards() {
        robot.leftBackMotor.setPower(-0.25);
        robot.leftFrontMotor.setPower(-0.25);
        robot.rightFrontMotor.setPower(-0.25);
        robot.rightBackMotor.setPower(-0.25);
        //sleep(l * 58);
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

    public void strafeleft() {
        robot.rightFrontMotor.setPower(0.5);
        robot.rightBackMotor.setPower(-0.5);
        robot.leftFrontMotor.setPower(-0.5);
        robot.leftBackMotor.setPower(0.5);
    }

    public void straferight() {
        robot.rightFrontMotor.setPower(-0.5);
        robot.rightBackMotor.setPower(0.5);
        robot.leftFrontMotor.setPower(0.5);
        robot.leftBackMotor.setPower(-0.5);
    }

    public void lowerarm(int angle) {
        sleep(1000 * angle);
    }

    public void raisearm(int angle) {
        sleep(1000 * angle);
    }

    @Override
    public void runOpMode() {
        robot.init(hardwareMap);
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // really? good for autonomous. But in driver control?
        }
        waitForStart();

        straferight();
        sleep(4000);

        driveForward();
        sleep(7000);

        straferightslowly();
        sleep(6000);

        strafeleft();
        sleep(2000);

        driveForward();
        sleep(5000);

        strafeleft();
        sleep(3000);

        backwards();
        sleep(7000);

        straferight();
        sleep(3000);
        //sleep(3500 );

        driveForward();
        sleep(4000);

        straferightslowly();
        sleep(1000);

        strafeleft();
        sleep(2000);

        driveForward();
        sleep(2000);

        strafeleft();
        sleep(4000);

        backwards();
        sleep(3000);
    }
}


        // robot.leftBackMotor.setPower(1.0);

        // sleep(5000);

        //robot.leftBackMotor.setPower(-1.0);

        //sleep(4000);

        //robot.rightBackMotor.setPower(1);

        //sleep(4000);

        ///robot.rightBackMotor.setPower(0.5);

        //sleep(4000);
        //robot.rightFrontMotor.setPower(0);

        //robot.leftFrontMotor.setPower(1);

        //sleep(2000);
        //robot.leftFrontMotor.setPower(0);

        //robot.leftFrontMotor.setPower(-1);

        //sleep(2000);
        //robot.leftFrontMotor.setPower(0);

        //robot.rightFrontMotor.setPower(1);

        //sleep(2000);
        //robot.rightFrontMotor.setPower(0);

        //robot.rightFrontMotor.setPower(-1);

        //sleep(2000);
        //robot.rightFrontMotor.setPower(0);

        //robot.leftBackMotor.setPower(1);
        //sleep(4000);
        //robot.leftBackMotor.setPower(0.5);
        //sleep(4000);
        //robot.leftBackMotor.setPower(0);

        //robot.leftBackMotor.setPower(1);
        //robot.rightBackMotor.setPower(1);
        //robot.leftFrontMotor.setPower(1);
        //robot.rightFrontMotor.setPower(1);

