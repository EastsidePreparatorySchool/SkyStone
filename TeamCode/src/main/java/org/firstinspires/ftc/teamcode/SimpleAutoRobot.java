package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class SimpleAutoRobot implements Robot {

    MotorPowers driveMotors;

    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Telemetry telemetry;
    LinearOpMode lop;
    //DcMotor pivotMotor;
    //DcMotor linkageMotor;
    DriveTrain driveTrain;
    //Servo clawServo;
    double encoderSpinDegrees;
    double encoderDist;

    Boolean strafeMode;
    Boolean turnMode;
    Boolean forwardMode;
    Boolean nothingMode;
    Long lastTime;
    Long timer;

    // accuracy is how close an encoder value needs to be to what it's expected to be
    double accuracy;


    public SimpleAutoRobot(HardwareMap hardwareMap, Telemetry telemetry, LinearOpMode lop) {
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;
        this.lop = lop;

    }

    @Override
    public void init() {
        strafeMode = false;
        forwardMode = false;
        turnMode = false;
        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");


        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
        // running with encoders, you should probably sotp and resert their current position to 0
        driveTrain.runWithoutEncoders();

        //driveTrain.stopAndResetEncoders();
        driveMotors = new MotorPowers(0, 0, 0, 0);

        // pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        // linkageMotor = hardwareMap.dcMotor.get("linkageMotor");

        // clawServo = hardwareMap.servo.get("clawServo");
        // grabbing = false;

    }


    public void update() {


    }

    public String getChassisWheels() {
        return driveTrain.toString();
    }

    public void stopAndResetEncoders() {
        driveTrain.stopAndResetEncoders();

    }

    public void runWithoutEncoders() {
        driveTrain.runWithoutEncoders();
    }


    // if negative, spins to the left, positive to the right
    public void spinDegrees(double degrees, double speed) {


    }


    public void strafe(Boolean left, Double speed) {

        MotorPowers strafing = (left) ? new MotorPowers(-speed, speed, speed, -speed) : new MotorPowers(speed, -speed, -speed, speed);
        strafing.scale();
        setMotors(strafing);
        moveMotors();
    }


    public void strafeTime(Boolean left, Double speed, Long time) {
        strafeMode = true;
        forwardMode = false;
        timer = time;
        lastTime = System.currentTimeMillis();

        MotorPowers strafing = (left) ? new MotorPowers(-speed, speed, speed, -speed) : new MotorPowers(speed, -speed, -speed, speed);
        strafing.scale();
        setMotors(strafing);
        moveMotors();
        while (System.currentTimeMillis() - lastTime < timer) {
            moveMotors();
            if (lop.isStopRequested()) {
                break;
            }
        }
        driveMotors.setAll(0);
        moveMotors();
    }

    public void forward(Boolean forwardDirection, Double speed) {
        MotorPowers forwarding = (forwardDirection) ? new MotorPowers(speed, speed, speed, speed) : new MotorPowers(-speed, -speed, -speed, -speed);
        forwarding.scale();
        setMotors(forwarding);
        driveTrain.runMotors(driveMotors);
    }


    public void forwardTime(Boolean forwardDirection, Double speed, Long time) {
        forwardMode = true;
        strafeMode = false;
        timer = time;
        lastTime = System.currentTimeMillis();
        MotorPowers forwarding = (forwardDirection) ? new MotorPowers(speed, speed, speed, speed) : new MotorPowers(-speed, -speed, -speed, -speed);
        forwarding.scale();
        setMotors(forwarding);
        moveMotors();
        while (System.currentTimeMillis() - lastTime < timer) {
            moveMotors();
            if (lop.isStopRequested()) {
                break;
            }
        }
        driveMotors.setAll(0);
        moveMotors();
    }

    public void forwardEncoder(Boolean forwardDirection, Double speed, Double encoderVal, Double checkPoint) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, checkPoint, 0, false, telemetry, lop);


    }

    public void forwardEncoderSlowDown(Boolean forwardDirection, Double speed, Double encoderVal, Double checkPoint, Double slowPoint) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, checkPoint, slowPoint, true, telemetry, lop);


    }

    public void spinEncoderVal(MotorDistances mds, MotorPowers mps, double check, double slowPoint, boolean slowApproach) {
        driveTrain.runWithoutEncoders();

        driveTrain.toEncoderVal(mds, mps, check, slowPoint, slowApproach, telemetry, lop);
        telemetry.addData("status", "complete");
    }

    @Override
    public void setMotors(MotorPowers robotMotors) {
        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = -robotMotors.fR;
        driveMotors.bR = -robotMotors.bR;

    }

    public void setAllMotors(Double power){
        driveMotors.fL = power;
        driveMotors.bL = power;
        driveMotors.fR = power;
        driveMotors.bR = power;
    }

    @Override
    public void moveMotors() {
        driveTrain.runMotors(driveMotors);
    }

    @Override
    public MotorPowers getDriveMotors() {
        return null;
    }
}
