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

package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.eastsideprep.eps15203.Hardware15203;


@TeleOp(name = "Everest Teleop 1 - Grabber Test", group = "15203")

public class TeleopE1 extends LinearOpMode {

    /* Declare OpMode members. */
    HardwareE robot = new HardwareE();

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "ready");

        double grabberPos1 = 0.5;
        double grabberPos2 = 0.5;


        // Wait for the game to start (driver presses PLAY)
        //robot.armMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        waitForStart();


        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {


            //grabber controls
            double grabberPosMultiplier = 0.25;

            if(gamepad1.y){
                grabberPos1 = 0.48;
                grabberPos2 = 0.52;
            } else if (gamepad1.a) {
                grabberPos1 = 0.55;
                grabberPos2 = 0.45;
            }
//            } else {
//                grabberPos1 = robot.grabberServo.getPosition();
//                grabberPos2
//            }

            telemetry.update();

            //Update the grabber & finger position
            robot.gs1.setPosition(grabberPos1);
            robot.gs2.setPosition(grabberPos2);
//            robot.fingerServo1.setPosition(fingerPos1);
//            robot.fingerServo2.setPosition(fingerPos2);

            // Pause for 40 mS each cycle = update 25 times a second.
            sleep(40);

        }
    }
}
