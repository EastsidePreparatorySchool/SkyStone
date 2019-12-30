package org.eastsideprep.eps8103;

/*
 * Copyright (c) 2019 OpenFTC Team
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */


import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Point;
import org.opencv.core.Scalar;
import org.opencv.imgproc.Imgproc;
import org.openftc.easyopencv.OpenCvCamera;
import org.openftc.easyopencv.OpenCvCameraRotation;
import org.openftc.easyopencv.OpenCvInternalCamera;
import org.openftc.easyopencv.OpenCvPipeline;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * In this sample, we demonstrate how to use the {@link OpenCvPipeline#onViewportTapped()}
 * callback to switch which stage of a pipeline is rendered to the viewport for debugging
 * purposes. We also show how to get data from the pipeline to your OpMode.
 */
@Autonomous
public class SkystoneDetector extends LinearOpMode {
    OpenCvCamera phoneCam;
    SampleSkystoneDetector detector;
    Hardware8103 robot = new Hardware8103();

    double targetX = 240;
    double error = 30;

    boolean found = false;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        phoneCam = new OpenCvInternalCamera(OpenCvInternalCamera.CameraDirection.BACK, cameraMonitorViewId);
        phoneCam.openCameraDevice();
        detector = new SampleSkystoneDetector();
        detector.useDefaults();
        phoneCam.setPipeline(detector);
        phoneCam.startStreaming(640, 480, OpenCvCameraRotation.UPSIDE_DOWN);


        waitForStart();

        while (opModeIsActive()) {
            while (!found) {
                telemetry.addData("FPS", String.format("%.2f", phoneCam.getFps()));
                telemetry.addData("Top left corner", detector.getScreenPosition());
                telemetry.addData("Found rectangle", detector.foundRectangle());

                Point corner = detector.getScreenPosition();
                double currentX = corner.x;

                //turn left and right until its in the center ish

                if (currentX < targetX - error) {
                    turnright(0.01);//seconds not millis
                    telemetry.addData("log", "turning right");
                } else if (currentX > targetX + error) {
                    turnleft(0.01);
                    telemetry.addData("log", "turning left");
                } else if (currentX > targetX - error && currentX < targetX + error) {
                    found = true;
                }

                telemetry.update();
                sleep(100);
            }
            intakeIn(2);
        }
    }

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
        robot.leftFrontMotor.setPower(-0.15);
        robot.leftBackMotor.setPower(-0.15);
        robot.rightFrontMotor.setPower(0.15);
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

    public void intakeIn(double time) {
        robot.intakeRight.setPower(1);
        robot.intakeLeft.setPower(1);
        sleep((long) time);
        robot.intakeRight.setPower(0);
        robot.intakeLeft.setPower(0);
    }

    public void intakeOut(double time) {
        robot.intakeRight.setPower(-1);
        robot.intakeLeft.setPower(-1);
        sleep((long) time);
        robot.intakeRight.setPower(0);
        robot.intakeLeft.setPower(0);
    }

    double[] drivetrainEncoders = new double[4];
    double[] drivetrainEncodersPrevious = new double[4];

}

