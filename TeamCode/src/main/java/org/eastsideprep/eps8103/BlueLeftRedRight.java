package org.eastsideprep.eps8103;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
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


@Autonomous(name = "BlueLeftRedLeft", group = "Concept")
public class BlueLeftRedRight extends LinearOpMode{

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
            waitForStart();
            robot.init(hardwareMap);
            straferight();
            sleep(500);

            // Send telemetry message to signify robot waiting;
            telemetry.addData("Status", "started");    //
            telemetry.update();


/*
            telemetry.addData("Pos", "We see it!");
            if (lastLocation != null) {
                telemetry.addData("Pos", format(lastLocation)); //if there was a last location, add it
            } else {
                telemetry.addData("Pos", "Unknown");
            }
            telemetry.update();

            if (lastLocation == null) {
            }

        }

 */


        }
    }



