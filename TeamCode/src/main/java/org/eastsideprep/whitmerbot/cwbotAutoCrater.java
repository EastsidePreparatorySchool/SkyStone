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

package org.eastsideprep.whitmerbot;

//import com.disnodeteam.dogecv.CameraViewDisplay;
//import com.disnodeteam.dogecv.DogeCV;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

//import static java.lang.Boolean.FALSE;
@Autonomous(name="Crater Side Auto", group="cwbot")
public class cwbotAutoCrater extends LinearOpMode
{
    /* Declare OpMode members. */
    cwRobot robot = new cwRobot();

    // Detector object
    //private MyGoldDetector detector;

    @Override
    public void runOpMode() {
        /* Initialize the hardware variables.
         * The init() method of the hardware class does all the work here
         */
        robot.init(hardwareMap);

        // Send telemetry message to signify robot waiting;
        telemetry.addData("Say", "Hello Otto");    //
        telemetry.update();

        robot.phoneServo.setPosition(robot.PHONE_AT_45);

        // Set up detector
//        detector = new MyGoldDetector(); // Create detector
//        detector.init(hardwareMap.appContext, CameraViewDisplay.getInstance()); // Initialize it with the app context and camera
//        detector.useDefaults(); // Set detector to use default settings
//
//        // Optional tuning
//        detector.downscale = 0.4; // How much to downscale the input frames
//
//        detector.areaScoringMethod = DogeCV.AreaScoringMethod.MAX_AREA; // Can also be PERFECT_AREA
//        //detector.perfectAreaScorer.perfectArea = 10000; // if using PERFECT_AREA scoring
//        detector.maxAreaScorer.weight = 0.005; //
//
//        detector.ratioScorer.weight = 5; //
//        detector.ratioScorer.perfectRatio = 1.0; // Ratio adjustment
//
//        detector.enable(); // Start the detector!

        while (!isStarted())
        {
//            if (detector.isFound())
//            {
//                String location = String.format("%5d , %5d / %5d x %5d / %5d x %5d",
//                        detector.X, detector.Y, detector.Width, detector.Height, detector.ScreenWidth, detector.ScreenHeight );
//                telemetry.addData("Gold", location);
//            }
//            else
//            {
//                telemetry.addData("Gold", "not found");
//            }
//            telemetry.update();

            robot.waitForTick(50);
        }

        // Wait for the game to start (drive r presses PLAY)
        //waitForStart();

        int[] programToRun = AutoPath.programCraterRightGold;

//        if (detector.isFound())
//        {
//            if (detector.X < detector.ScreenWidth/2)
//            {
//                programToRun = AutoPath.programCraterLeftGold;
//            }
//            else
//            {
//                programToRun = AutoPath.programCraterCenterGold;
//            }
//        }

        robot.phoneServo.setPosition(robot.PHONE_HORIZONTAL);
        robot.resetTickPeriod();
        robot.RunProgram(programToRun, this);

//        detector.disable();
    }
}
