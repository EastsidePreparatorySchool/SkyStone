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

@TeleOp(name = "Trajan Teleop", group = "8103")

public class basicTeleop extends LinearOpMode {

    /* Declare OpMode members. */
    Hardware8103 robot = new Hardware8103();

    @Override
    public void runOpMode() {

        robot.init(hardwareMap);

        double[] drivetrainEncoders = new double[4];
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        telemetry.addData("", "ready");
        waitForStart();

        int liftPos = 0;

        boolean intakeRun = false;


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
                robot.lift.setPower(0.6);

            } else if (gamepad2.left_trigger > 0.8) {
                robot.lift.setPower(-0.6);
            } else if (gamepad2.b) {
                robot.lift.setPower(0);
            }
            liftPos = robot.lift.getCurrentPosition();

//lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);


            if (gamepad2.a) {//toggle intake
                if (!intakeRun) {
                    robot.intakeRight.setPower(1);
                    robot.intakeLeft.setPower(1);
                    robot.bay1.setPower(-1);
                    robot.bay2.setPower(-1);
                    intakeRun = true;
                    sleep(500);
                } else if (intakeRun) {
                    robot.intakeRight.setPower(0);
                    robot.intakeLeft.setPower(0);
                    robot.bay1.setPower(0);
                    robot.bay2.setPower(0);
                    intakeRun = false;
                    sleep(500);
                }
            }

            telemetry.addData("lift height", liftPos);
            telemetry.addLine();
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}

