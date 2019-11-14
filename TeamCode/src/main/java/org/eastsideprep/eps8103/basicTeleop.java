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
        double[] servoPositions = new double[3];

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
                    //robot.allMotors[i].setTargetPosition((int)powers[i] / scale * biggestWithRotation * speedControl);
                    //robot.allMotors[i].setPower(0.8);
                    drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
                }
            } else {
                for (int i = 0; i < robot.allMotors.length; i++) {
                    robot.allMotors[i].setPower(0.0);
                    drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
                }
            }

            robot.armExtender.setTargetPosition(robot.armExtender.getCurrentPosition() + 2 * Math.round(gamepad2.right_trigger));
            robot.armExtender.setPower(0.6);

            robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() + 2 * Math.round(gamepad2.left_trigger));
            robot.armPivot.setPower(0.6);

            telemetry.addData("pivot encoder", robot.armPivot.getCurrentPosition());
            //pivot encoder results:
            //90 deg - 1535
            //all down is -750
            //all back is 3200

            if (robot.armPivot.getCurrentPosition() < -650 || robot.armPivot.getCurrentPosition() > 3100) {
                robot.armPivot.setPower(0);//dont go too low or high!!!
            }
            if (robot.armExtender.getCurrentPosition() < 141 || robot.armExtender.getCurrentPosition() > 1581) {
                robot.armExtender.setPower(0);
            }
            //extend encoder results
            //extend:141 all extended:1581

            telemetry.addData("drivetrain encoders", Arrays.toString(drivetrainEncoders));


            if (gamepad2.dpad_up) {
                robot.closer.setPosition(-0.4);
            } else if (gamepad2.dpad_down) {
                robot.closer.setPosition(-0.2);
                robot.wrist.setPosition(0.2);
            } else if (gamepad2.y) {
                robot.closer.setPosition(1);
            } else if (gamepad2.x) {
                robot.closer.setPosition(0.2);
            }

            if (gamepad2.b) {
                robot.updown.setPosition(1);
            } else if (gamepad2.a) {
                robot.updown.setPosition(0);
            }

            for (int i = 0; i < robot.allServos.length; i++) {
                servoPositions[i] = robot.allServos[i].getPosition();
            }
            telemetry.addData("servo positions", Arrays.toString(servoPositions));

            telemetry.addLine();
            telemetry.update();

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);
        }
    }
}
