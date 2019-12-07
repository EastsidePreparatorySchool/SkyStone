package org.eastsideprep.eps8103;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;


@Autonomous(name = "Pull Foundation", group = "Concept")
public class PullFoundation extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();


    //these have to be found experimentally aka a lot of testing
    int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
    int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);  //last constants need tweaking

    int slow_strafe_c = 1;
    int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
    int ext_c = 1;

    int angle_c = 360 / robot.TICKS_PER_REV;

    double[] drivetrainEncoders = new double[4];

    double[] drivetrainEncodersPrevious = new double[4];

    //ColorSensor color_sensor;

    public void getEncoderValues() {
        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            telemetry.addData("motor" + i + " speed", (drivetrainEncoders[i] - drivetrainEncodersPrevious[i]) / 40);
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }
        telemetry.update();
    }

    public void forwards(double speed, double time) {
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setPower(speed);
        }

    }

    public void backwards(double speed, double time) {
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
        stopmotors();
        getEncoderValues();
    }

    public void turnright(double time) {
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
        stopmotors();

        getEncoderValues();
    }

    public void turnleft(double time) {
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
        robot.leftFrontMotor.setPower(0.15);
        robot.leftBackMotor.setPower(0.15);
        robot.rightFrontMotor.setPower(-0.15);
        robot.rightBackMotor.setPower(-0.15);

        sleep(20);
        stopmotors();

        getEncoderValues();
    }

    public void stopmotors() {
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }
    }

    public void lowerPullers() {
        double position = robot.rightpuller.getPosition();
        robot.leftpuller.setPosition(90);
        robot.rightpuller.setPosition(90);
        sleep(2500);
    }

    public void raisePullers() {
        robot.leftpuller.setPosition(0);
        robot.rightpuller.setPosition(0);
        sleep(2500);
    }

    public void straferight(double speed, double time) {
        double start = System.currentTimeMillis();
        double elapsed = 0.0;
        for (DcMotor m : robot.allMotors) {
            //m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
        robot.rightFrontMotor.setPower(speed);//for some reason setting positive speed makes it negative?
        robot.rightBackMotor.setPower(-speed);//speeds may be inversed (for some reason this makes it go forward
        robot.leftFrontMotor.setPower(speed);
        robot.leftBackMotor.setPower(-speed);


    }


    public void startVuStrafe(double speed, double time) {
        robot.rightFrontMotor.setPower(speed);//for some reason setting positive speed makes it negative?
        robot.rightBackMotor.setPower(-speed);//speeds may be inversed (for some reason this makes it go forward
        robot.leftFrontMotor.setPower(speed);
        robot.leftBackMotor.setPower(-speed);


    }


    //run peacefully until i have 20 ms left


    public void strafeleft(int l) {

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
        stopmotors();
        getEncoderValues();
    }



    @Override
    public void runOpMode() {


        robot.init(hardwareMap); //load hardware from other program

        robot.init(hardwareMap); //load hardware from other program

        for (int i = 0; i < drivetrainEncoders.length; i++) {
            drivetrainEncodersPrevious[i] = drivetrainEncoders[i];
        }


        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();

        lowerPullers();

    }
}







