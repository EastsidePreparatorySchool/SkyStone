package org.eastsideprep.eps9884;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
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
public class Test8103 extends LinearOpMode{


        /* Declare OpMode members. */
        Hardware8103 robot = new Hardware8103();

        @Override
        public void runOpMode() {
            robot.init(hardwareMap);
            waitForStart();

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

            robot.leftBackMotor.setPower(1);
            robot.rightBackMotor.setPower(1);
            robot.leftFrontMotor.setPower(1);
            robot.rightFrontMotor.setPower(1);
            float startlb = robot.leftBackMotor.getCurrentPosition();
            float startrb = robot.rightBackMotor.getCurrentPosition();
            float startrf = robot.rightFrontMotor.getCurrentPosition();
            float startlf = robot.leftFrontMotor.getCurrentPosition();
            for(int i=0; i<10; i++) {
                telemetry.addData("left back", (robot.leftBackMotor.getCurrentPosition()-startlb)/i);
                telemetry.addData("right back", (robot.rightBackMotor.getCurrentPosition()-startrb)/i);
                telemetry.addData("left front", (robot.leftFrontMotor.getCurrentPosition()-startlf)/i);
                telemetry.addData("right front", (robot.rightFrontMotor.getCurrentPosition()-startrf)/i);
                telemetry.update();
                sleep(1000);
            }

            robot.leftBackMotor.setPower(0.25);
            robot.rightBackMotor.setPower(0.25);
            robot.leftFrontMotor.setPower(0.25);
            robot.rightFrontMotor.setPower(0.25);
            startlb = robot.leftBackMotor.getCurrentPosition();
            startrb = robot.rightBackMotor.getCurrentPosition();
            startrf = robot.rightFrontMotor.getCurrentPosition();
            startlf = robot.leftFrontMotor.getCurrentPosition();
            for(int i=0; i<10; i++) {
                telemetry.addData("left back", (robot.leftBackMotor.getCurrentPosition()-startlb)/i);
                telemetry.addData("right back", (robot.rightBackMotor.getCurrentPosition()-startrb)/i);
                telemetry.addData("left front", (robot.leftFrontMotor.getCurrentPosition()-startlf)/i);
                telemetry.addData("right front", (robot.rightFrontMotor.getCurrentPosition()-startrf)/i);
                telemetry.update();
                sleep(1000);

            }

            robot.leftBackMotor.setPower(0.1);
            robot.rightBackMotor.setPower(0.1);
            robot.leftFrontMotor.setPower(0.1);
            robot.rightFrontMotor.setPower(0.1);
            startlb = robot.leftBackMotor.getCurrentPosition();
            startrb = robot.rightBackMotor.getCurrentPosition();
            startrf = robot.rightFrontMotor.getCurrentPosition();
            startlf = robot.leftFrontMotor.getCurrentPosition();
            for(int i=0; i<10; i++) {
                telemetry.addData("left back", (robot.leftBackMotor.getCurrentPosition()-startlb)/i);
                telemetry.addData("right back", (robot.rightBackMotor.getCurrentPosition()-startrb)/i);
                telemetry.addData("left front", (robot.leftFrontMotor.getCurrentPosition()-startlf)/i);
                telemetry.addData("right front", (robot.rightFrontMotor.getCurrentPosition()-startrf)/i);
                telemetry.update();
                sleep(1000);
            }


            // Send telemetry message to signify robot waiting;
            telemetry.addData("Status", "started");    //
            telemetry.update();
        }
    }



