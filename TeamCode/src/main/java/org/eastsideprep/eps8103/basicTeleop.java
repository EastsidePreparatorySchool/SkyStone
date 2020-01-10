/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.eastsideprep.eps8103;

import java.util.Arrays;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.eastsideprep.eps8103.Hardware8103;

@TeleOp(name = "All control teleop", group = "8103")

public class basicTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware8103 robot = new Hardware8103();

    int liftPos = 0;
    int liftEncoder = 0;
    int blockLevel = -1;
    boolean v4BarIn = true;

    boolean intakeRun = false;
    boolean pullersDown = false;

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        double[] drivetrainEncoders = new double[4];
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        telemetry.addData("", "ready");
        waitForStart();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {

            float x = gamepad1.left_stick_x;
            float y = -gamepad1.left_stick_y; // Negate to get +y forward.
            float rotation = -gamepad1.right_stick_x;
            float speedControl = 0.75f * (1.2f + gamepad1.left_trigger);
            double biggestControl = Math.sqrt(x * x + y * y);
            double biggestWithRotation = Math.sqrt(x * x + y * y + rotation * rotation);

            double angle = Math.atan2(y, -x) - Math.PI / 2.0;

            double[] powers = robot.getDrivePowersFromAngle(angle);
            double pow2 = 0.0;

            for (int i = 0; i < robot.allMotors.length; i++) {
                double pow = powers[i] * biggestControl + rotation * robot.rotationArray[i];
                powers[i] = pow;
                pow2 += pow * pow;
            }

            if (biggestWithRotation != 0.0) {
                double scale = Math.sqrt(pow2);
                for (int i = 0; i < robot.allMotors.length; i++) {
                    robot.allMotors[i].setPower(
                            powers[i] / scale * biggestWithRotation * speedControl);
                    drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
                }
            } else {
                for (int i = 0; i < robot.allMotors.length; i++) {
                    robot.allMotors[i].setPower(0.0);
                    drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
                }
            }

            telemetry.addData("drivetrain encoders", Arrays.toString(drivetrainEncoders));

            if (gamepad2.right_trigger > 0.8) {
                raiseLift(liftPos);
                liftPos++;
            } else if (gamepad2.left_trigger > 0.8) {
                dropLift();
                liftPos = 0;
            } else if (gamepad2.b) {
                robot.lift.setPower(0);
            }
            liftEncoder = robot.lift.getCurrentPosition();
            telemetry.addData("lift pos", liftPos);
            telemetry.addData("lift encoder", liftEncoder);

            if (!intakeRun) {//toggle intake
                if (gamepad2.left_bumper) {
                    robot.intakeRight.setPower(1);
                    robot.intakeLeft.setPower(1);
                    robot.bay1.setPower(-1);
                    robot.bay2.setPower(-1);
                    intakeRun = true;
                    sleep(300);
                    telemetry.addData("bay", "out");
                } else if (gamepad2.right_bumper) {
                    robot.intakeRight.setPower(-1);
                    robot.intakeLeft.setPower(-1);
                    robot.bay1.setPower(1);
                    robot.bay2.setPower(1);
                    intakeRun = true;
                    sleep(300);
                    telemetry.addData("bay", "in");
                }
            } else if (intakeRun && (gamepad2.left_bumper || gamepad2.right_bumper)) {
                robot.intakeRight.setPower(0);
                robot.intakeLeft.setPower(0);
                robot.bay1.setPower(0);
                robot.bay2.setPower(0);
                intakeRun = false;
                sleep(300);
                telemetry.addData("bay", "stopped");
            }

            if (gamepad1.a && !pullersDown) {
                lowerPullers();
                pullersDown = true;
            } else if (gamepad1.a && pullersDown) {
                raisePullers();
                pullersDown = false;
            }


            if (gamepad2.x && v4BarIn) {
                //placeBlock(blockLevel + 1);
                //blockLevel++;
                placeBlock(0.2);
                v4BarIn = false;
            } else if (gamepad2.x && !v4BarIn) {
                getBlock();
                v4BarIn = true;
            }

            telemetry.addLine();
            telemetry.update();
            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }

    public void lowerPullers() {
        robot.rightpuller.setPosition(0.4);
        robot.leftpuller.setPosition(0.1);
        sleep(350);
    }

    public void raisePullers() {
        robot.rightpuller.setPosition(1);
        robot.leftpuller.setPosition(1);
        sleep(350);
    }

    public void getBlock() {
        robot.left4Bar.setPosition(1);
        robot.right4Bar.setPosition(-1);
        robot.grabber.setPosition(0);
        sleep(250);
        telemetry.addData("log", "getting block");
    }

    public void placeBlock(double height) {
        robot.left4Bar.setPosition(-1);
        robot.right4Bar.setPosition(1);
        sleep(250);
        telemetry.addData("log", "placing block");
    }

    public void raiseLift(int height) {
        robot.lift.setTargetPosition(robot.liftHeights.get(height));
        robot.lift.setPower(-0.45);//pretty fast, then hold. Negative because lower encoder values mean higher lift
        while (robot.lift.getCurrentPosition() - robot.lift.getTargetPosition() > 20) {//on the way up target will be smaller than current
            sleep(10);
        }
        robot.lift.setPower(-0.35);//slow down when current gets within 20 ticks of target
        sleep(20);
        //make sure to leave the motor alone to hold its position
    }

    public void dropLift() {
        robot.lift.setTargetPosition(robot.LIFT_LEVEL_PICKUP);
        robot.lift.setPower(0.35);//positive direction to go down
        while (robot.lift.getCurrentPosition() - robot.LIFT_LEVEL_PICKUP > 20) {//current position will be higher than lift level
            sleep(10);
        }
        robot.lift.setPower(0.25);//slow down when you get close, about 20 encoder ticks away
        sleep(20);
        robot.lift.setPower(0);//at the bottom its ok to let go
    }
}

