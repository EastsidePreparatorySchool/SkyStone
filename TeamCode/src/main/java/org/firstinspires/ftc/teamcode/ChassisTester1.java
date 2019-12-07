package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

@TeleOp(name = "ChassisTester1", group = "Tests")

public class ChassisTester1 extends OpMode {

    ChassisRobot robot;
    MotorPowers motorPowers;
    double speed = 0.5;
    final double sprintf = 1;
    final double normalf = 0.5;
    final double slowf = 0.25;
    double power;
    double sprint = sprintf;
    double normal = normalf;
    double slow = slowf;
    double threshold = 1;
    double fLM;
    double bLM;
    double fRM;
    double bRM;
    int precision = 0;
    int moves = 0;
    int pickMotor = 0;
    String whichMotor;

    @Override
    public void init() {
        robot = new ChassisRobot(hardwareMap, this.telemetry, false);
        robot.init();
        motorPowers = new MotorPowers(0, 0, 0, 0);
        telemetry.addData("Status", "Initialized");


    }

    @Override
    public void loop() {
        updateTelemetry(telemetry);


        getInput();
        telemetry.addData("speed", speed);
        goMotor();
        telemetry.addData("num", pickMotor);
        telemetry.addData("motor", whichMotor);
        telemetry.addData("Encoders", (robot.runningWithEncoders)?"yes":"no");
        telemetry.addData("press", "d-pad to change motors");
        telemetry.addData("press", "a,x,b,y to change speeds");
        motorPowers.set(thresholdCheck2(fLM), thresholdCheck2(bLM), thresholdCheck2(-fRM), thresholdCheck2(-bRM));


        telemetry.addData("", motorPowers);
        robot.setMotors(motorPowers);
        telemetry.addData("robot",robot.driveTrain);
        robot.moveMotors();
        telemetry.addData("robot", robot.driveMotors);
        telemetry.addData("robot", robot.driveTrain.negativePositive());


    }

    public void goMotor() {
        power *= speed;
        fLM = 0;
        bLM = 0;
        fRM = 0;
        bRM = 0;
        switch (pickMotor) {
            case 0: {
                fLM = power;
                bLM = power;
                fRM = power;
                bRM = power;
                whichMotor = "All";
            }
            case 1: {
                fRM = power;
                whichMotor = "Front Right";
            }
            case 2: {
                bRM = power;
                whichMotor = "Back Right";
            }
            case 3: {
                bLM = power;
                whichMotor = "Back Left";
            }
            case 4: {
                fLM = power;
                whichMotor = "Front Left";
            }

        }
    }


    public void getInput() {

        // checks threshold here as well
        power = -thresholdCheck(this.gamepad1.left_stick_y);

        // reset speed to default
        if (this.gamepad1.a) {
            sprint = sprintf;
            slow = slowf;
            normal = normalf;

        }
        // slow it down
        if (this.gamepad1.x) {
            sprint /= 2;
            slow /= 2;
            normal /= 2;
        }
        //speed it up
        if (this.gamepad1.b) {
            sprint *= 2;
            slow *= 2;
            normal *= 2;
        }


        if (this.gamepad1.dpad_right) {
            switch (pickMotor) {
                case 1: {
                    pickMotor = 2;
                    break;

                }
                case 2: {
                    pickMotor = 3;
                    break;

                }
                case 3: {
                    pickMotor = 4;
                     break;

                }
                case 4: {
                    pickMotor = 1;
                    break;

                }
                case 0: {
                    pickMotor = 1;
                    break;

                }

            }


        }else if (gamepad1.dpad_left) {
            switch (pickMotor) {
                case 1: {
                    pickMotor = 4;
                    break;
                }
                case 2: {
                    pickMotor = 1;
                    break;
                }
                case 3: {
                    pickMotor = 2;
                    break;
                }
                case 4: {
                    pickMotor = 3;
                    break;
                }
                case 0: {
                    pickMotor = 1;
                    break;
                }

            }


        }else if (gamepad1.dpad_up) {
            pickMotor = 0;
        }
        telemetry.addData("pickmotor", pickMotor);


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

        } else {
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

        speed += this.gamepad1.right_trigger;
        speed -= this.gamepad1.left_trigger;
        if (speed > 1) {
            speed = 1;
        } else if (speed < 0) {
            speed = 0;
        }
        if(this.gamepad1.left_bumper){
            robot.runWithEncoders();
        }else if(this.gamepad1.right_bumper){
            robot.runWithoutEncoders();
        }



    }

    public double thresholdCheck(double motor) {
        if (-threshold < motor && motor < threshold) {

            motor = 0.0;

        }

        return motor;
    }

    public double thresholdCheck2(double motor) {
        if (-0.05 < motor && motor < 0.05) {
            motor = 0.0;

        }
        return motor;

    }
}