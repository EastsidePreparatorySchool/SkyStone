package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import java.util .*;

public class PathFollowerAuto {




    @Autonomous(name = "Trajan's auto", group = "Concept")
    public class basicSkyStoneAuto extends LinearOpMode {

        Hardware8103 robot = new Hardware8103();

        //these have to be found experimentally aka a lot of testing
        int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
        int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);//last constants need tweaking

        int slow_strafe_c = 1;
        int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
        int pivot_c = 1;

        int angle_c = 360 / robot.TICKS_PER_REV;

        double[] drivetrainEncoders = new double[4];

        ColorSensor color_sensor;

        public void forwards(int l, double speed) {
            for (DcMotor m : robot.allMotors) {
                m.setTargetPosition(m.getCurrentPosition() + fast_c * l);
                m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                m.setPower(speed);
            }
            //once all motors are going i can start turning them off
            for (DcMotor m : robot.allMotors) {
                while (m.isBusy()) {
                    m.setPower(speed);
                }
                if (!m.isBusy()) {
                    m.setPower(0);
                }
            }

            for (int i = 0; i < 4; i++) {
                drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            }
        }

        public void backwards(int l) {
            for (DcMotor m : robot.allMotors) {
                m.setTargetPosition(m.getCurrentPosition() - (int) fast_c * l);
                m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                m.setPower(0.4);
            }
            //once all motors are going i can start turning them off
            for (DcMotor m : robot.allMotors) {
                while (m.isBusy()) {
                    m.setPower(0.4);
                }
                if (!m.isBusy()) {
                    m.setPower(0);
                }
            }

            for (int i = 0; i < 4; i++) {
                drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            }
        }

        public void turnright(int angle) {
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + (int) turn_c * angle);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - (int) turn_c * angle);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - (int) turn_c * angle);
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + (int) turn_c * angle);


            robot.leftBackMotor.setPower(-0.25); //have some wheels turn different directions so it goes left
            robot.leftFrontMotor.setPower(0.25);
            robot.rightBackMotor.setPower(0.25);
            robot.rightFrontMotor.setPower(-0.25);
            // sleep(turn_c * angle);
        }

        public void turnrightslowly(int angle) {
            robot.leftBackMotor.setPower(-0.05); //have low power so it goes very slow
            robot.leftFrontMotor.setPower(0.05);
            robot.rightFrontMotor.setPower(0.05);
            robot.rightBackMotor.setPower(-0.05);
            //sleep(turn_c*angle*5)
        }

        public void turnleft(int angle) {
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - (int) turn_c * angle);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + (int) turn_c * angle);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + (int) turn_c * angle);
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - (int) turn_c * angle);

            for (DcMotor m : robot.allMotors) {
                m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                m.setPower(0.25);
            }
            //once all motors are going i can start turning them off
            for (DcMotor m : robot.allMotors) {
                while (m.isBusy()) {
                    m.setPower(0.25);
                }
                if (!m.isBusy()) {
                    m.setPower(0);
                }
            }

            for (int i = 0; i < 4; i++) {
                drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            }
        }

        public void stopmotors() {
            for (DcMotor m : robot.allMotors) {
                m.setPower(0);
            }
        }

        public void strafeleftslowly(int l) {
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + slow_strafe_c * l);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - slow_strafe_c * l);
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - slow_strafe_c * l);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + slow_strafe_c * l);
            for (DcMotor m : robot.allMotors) {
                m.setPower(0.1);
            }
        }

        public void straferightslowly(int l) {
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - slow_strafe_c * l);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + slow_strafe_c * l);
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + slow_strafe_c * l);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - slow_strafe_c * l);
            for (DcMotor m : robot.allMotors) {
                m.setPower(0.1);
            }
        }

        public void straferight(int l) {
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - strafe_c * l);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + strafe_c * l);
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + strafe_c * l);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - strafe_c * l);
            for (DcMotor m : robot.allMotors) {
                m.setPower(0.25);
            }
            for (DcMotor m : robot.allMotors) {
                while (m.isBusy()) {
                    m.setPower(0.25);
                }
                if (!m.isBusy()) {
                    m.setPower(0);
                }
            }

            for (int i = 0; i < 4; i++) {
                drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            }
        }

        public void strafeleft(int l) {
            robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + strafe_c * l);
            robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - strafe_c * l);
            robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - strafe_c * l);
            robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + strafe_c * l);
            for (DcMotor m : robot.allMotors) {
                m.setPower(0.25);
            }
            for (DcMotor m : robot.allMotors) {
                while (m.isBusy()) {
                    m.setPower(0.25);
                }
                if (!m.isBusy()) {
                    m.setPower(0);
                }
            }

            for (int i = 0; i < 4; i++) {
                drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
            }
        }

        public void lowerarm(int angle) {
            robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() - pivot_c * angle);
            robot.armPivot.setPower(1);
            //dont set the power to 0, make sure to hold position!
        }

        public void raisearm(int angle) {
            robot.armPivot.setTargetPosition(robot.armPivot.getCurrentPosition() + pivot_c * angle);
            robot.armPivot.setPower(1);
        }

        public void extendarm(int l) {
            robot.armExtender.setTargetPosition(1581);
            robot.armExtender.setPower(1);
        }

        public void reelarm(int l) {
            robot.armExtender.setTargetPosition(141);//found this value using teleop
            robot.armExtender.setPower(1);
        }

        public void print_encoders() {
            telemetry.addData("wheel encoders", Arrays.toString(drivetrainEncoders));
            telemetry.update();
        }


        @Override
        public void runOpMode() {

            double[][] waypoints = new double[][]{
                    {2, 8, 90},
                    {7, 2, 135},
                    {12, 8, 180},
                    {17, 14, 225},
                    {22, 8, 270},
                    {17, 2, 315},
                    {12, 8, 360},
                    {7, 14, 405},
                    {2, 8, 450}
            };

            double totalTime = 8; //max seconds we want to drive the path
            double timeStep = 0.05; //period of control loop on Rio, seconds
            double robotTrackWidth = 1.4; //distance between left and right wheels, feet
            double robotTrackLength = 1.4; //distance between front and rear wheels, feet

            MecanumPathPlanner path = new MecanumPathPlanner(waypoints);
            path.calculate(totalTime, timeStep, robotTrackWidth, robotTrackLength);

            robot.init(hardwareMap); //load hardware from other program

            telemetry.addData(">", "Press Play to start tracking");
            telemetry.update(); //add stuff to telemetry
            waitForStart();

            telemetry.addData("log", "starting");

            telemetry.update();


        }
    }
}



