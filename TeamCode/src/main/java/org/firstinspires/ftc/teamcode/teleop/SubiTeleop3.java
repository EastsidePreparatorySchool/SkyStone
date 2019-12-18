package org.firstinspires.ftc.teamcode.teleop;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.teamcode.robots.TeleopRobot3;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;
import org.firstinspires.ftc.teamcode.robots.TeleopRobot;

@TeleOp(name = "SubiTeleOp3", group = "TeleOps")

public class SubiTeleop3 extends OpMode {

    TeleopRobot3 robot;

    MotorPowers motorPowers;

    // a minimum joystick value before movement
    double threshold = 0.05;
    // a modifier to all movement
    int moves = 0;

    double speed = 0.5;
    double armAndPivotSpeed = 0.5;
    double sprint = 1;
    double normal = 0.5;
    double slow = 0.25;

    double g1LeftAnalogX;
    double g1LeftAnalogY;
    double g1RightAnalogX;
    double g1RightAnalogY;
    double fLM = 0;
    double bLM = 0;
    double fRM = 0;
    double bRM = 0;
    double turn;
    double pivotPower = 0;
    int i = 0;
    int mode = 3;
    boolean twoGamePads;


    double linkageMove;

    @Override
    public void init() {
        twoGamePads = false;
        robot = new TeleopRobot3(hardwareMap, this.telemetry, false);
        robot.init();
        motorPowers = new MotorPowers(0, 0, 0, 0);
        telemetry.addData("Status", "Initialized");


    }

    @Override
    public void start() {
        telemetry.addData("here", "here2");
        updateTelemetry(telemetry);
    }


    @Override
    public void loop() {
        updateTelemetry(telemetry);
        robot.armUpdate();
        telemetry.addData("angle", "%6.2f", (robot.getIMUAngle() / 6.2) * 360);
        telemetry.addData("mode", mode);
        getInput();
        telemetry.addData("speed", speed);
        telemetry.addData("armAndPivotSpeed", armAndPivotSpeed);
        telemetry.addData("pivotPower", pivotPower);
        telemetry.addData("clawPos", robot.clawServo.getPosition());
        //telemetry.addData("loops", i);
        goMotor();


        motorPowers.set(thresholdCheck2(fLM), thresholdCheck2(bLM), thresholdCheck2(fRM), thresholdCheck2(bRM));
        //motorPowers.set(0.25,0.25,0.25,0.25);
        telemetry.addData("", motorPowers);
        robot.setMotors(motorPowers);
        //telemetry.addData("robot",robot.driveTrain);
        robot.moveMotors();
        //telemetry.addData("robot", robot.driveMotors);
        telemetry.addData("robot", robot.driveTrain.negativePositive());


        if (linkageMove != 0.0) {
            robot.moveLinkage(armAndPivotSpeed * linkageMove);
        }
        if (pivotPower != 0.0) {
            robot.pivotArm(armAndPivotSpeed * pivotPower);
        }


        speed = 0.5;
        //armAndPivotSpeed = normal;
        i++;


    }

    public void getInput() {
        // checks threshold here as well
        g1RightAnalogX = thresholdCheck(this.gamepad1.right_stick_x);
        g1RightAnalogY = thresholdCheck(this.gamepad1.right_stick_y);
        g1LeftAnalogX = thresholdCheck(this.gamepad1.left_stick_x);
        g1LeftAnalogY = thresholdCheck(this.gamepad1.left_stick_y);
        // SWAP CONTROL SCHEMES!!
        if (this.gamepad1.back) {
            twoGamePads = !twoGamePads;
        }


        // pivot controlled by bumpers
        // arm uses triggers
        // claw uses a and b
        // x for changing arm speed

        if (this.gamepad1.left_bumper) {
            pivotPower = 1;
        } else if (this.gamepad1.right_bumper) {
            pivotPower = -1;
        } else {
            pivotPower = 0;
        }

        linkageMove = thresholdCheck(this.gamepad1.left_trigger - this.gamepad1.right_trigger);


        if (this.gamepad1.a) {
            robot.grab();

        } else if (this.gamepad1.b) {
            robot.ungrab();

        }

        if (this.gamepad1.x) {
            if (armAndPivotSpeed == slow) {
                armAndPivotSpeed = normal;

            } else if (armAndPivotSpeed == normal) {
                armAndPivotSpeed = sprint;

            } else if (armAndPivotSpeed == sprint) {
                armAndPivotSpeed = slow;
            } else {
                armAndPivotSpeed = normal;

            }

        }


        if (this.gamepad1.y) {
            switch (moves) {
                // normal speed --> sprint
                case 0: {
                    speed = sprint;
                    moves++;
                    break;
                }
                case 1: { // sprint --> slow
                    speed = slow;
                    moves++;
                    break;
                }
                case 2: {
                    //slow --> normal
                    speed = normal;
                    moves = 0;
                    break;
                }

            }

        }


        if (this.gamepad1.dpad_up) {
            // turn while moving
            mode = 0;

        } else if (this.gamepad1.dpad_right) {
            // turn in place
            mode = 1;

        } else if (this.gamepad1.dpad_down) {
            //strafe
            mode = 2;

        } else if (this.gamepad1.dpad_left) {
            //  total control
            mode = 3;

        }
        turn = getTurn();

    }

    public void goMotor() {


        switch (mode) {

            case 0: {
                turnWhileMoving();
                break;
            }
            case 1: {
                turnInPlace();
                telemetry.addData("turning in place", turn);
                break;
            }
            case 2: {
                strafe();
                break;
            }
            case 3: {
                totalControl();
                break;
            }

        }


    }

    // is the motor bigger than the threshold?

    public double thresholdCheck(double motor) {
        if (-threshold < motor && motor < threshold) {

            motor = 0.0;

        }

        return motor;
    }

    public double getTurn() {

        return g1RightAnalogX;

    }

    public double thresholdCheck2(double motor) {
        if (-0.05 < motor && motor < 0.05) {
            motor = 0.0;

        }
        return motor;
    }
    //motor actions below

    public void forwardBack() {
        fRM = -g1LeftAnalogY * speed;
        fLM = -g1LeftAnalogY * speed;
        bLM = -g1LeftAnalogY * speed;
        bRM = -g1LeftAnalogY * speed;

    }

    public void turnInPlace() {
        turn = getTurn();
        fLM = turn * speed;
        bLM = turn * speed;
        fRM = -turn * speed;
        bRM = -turn * speed;


    }

    public void turnWhileMoving() {

        fLM = (-g1LeftAnalogY + turn) * speed;
        bLM = (-g1LeftAnalogY + turn) * speed;
        fRM = (-g1LeftAnalogY + -turn) * speed;
        bRM = (-g1LeftAnalogY + -turn) * speed;


    }

    public void strafe() {

        fLM = g1LeftAnalogX * speed;
        bLM = -g1LeftAnalogX * speed;
        fRM = -g1LeftAnalogX * speed;
        bRM = g1LeftAnalogX * speed;

    }

    public void forwardStrafe() {

        fLM = (-g1LeftAnalogY + -g1LeftAnalogX) * speed;
        bLM = (-g1LeftAnalogY + g1LeftAnalogX) * speed;
        fRM = (-g1LeftAnalogY + g1LeftAnalogX) * speed;
        bRM = (-g1LeftAnalogY + -g1RightAnalogX) * speed;


    }

    public void totalControl() {

        fLM = (-g1LeftAnalogY + turn + g1LeftAnalogX) * speed;
        bLM = (-g1LeftAnalogY + turn + -g1LeftAnalogX) * speed;
        fRM = (-g1LeftAnalogY + -turn + -g1LeftAnalogX) * speed;
        bRM = (-g1LeftAnalogY + -turn + g1LeftAnalogX) * speed;

    }
}
