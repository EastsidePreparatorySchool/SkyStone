package org.firstinspires.ftc.teamcode.robots;


import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.motors.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.sensors.IMU;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

public class TeleopRobot2 implements Robot {

    MotorPowers driveMotors;
    Boolean runningEncoders;
    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Telemetry telemetry;
    DcMotor pivotMotor;
    DcMotor linkageMotor;
    DriveTrain driveTrain;
    Servo clawServo;
    IMU imu;
    // this may need to be changed, depending on what we want the default for this servo to be
    double clawInit = 0.56;
    double clawGrab = 0;
    double pivotPos;
    double pivotError = 20;
    double linkagePos;
    double linkageNormalError = 20;
    double linkageError = 20;
    double linkageBigError = 40;
    double pivotNormalStall = 0.3;
    double pivotStall = 0.3;
    double pivotBigStall = 0.5;
    boolean grabbing;

    public TeleopRobot2() {


    }

    public TeleopRobot2(HardwareMap hardwareMap, Telemetry telemetry, boolean runwithEncoders) {
        runningEncoders = runwithEncoders;
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }

    public void init() {
        telemetry.addData("hj", "afa");
        telemetry.update();
        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));

        telemetry.addData("imu", "working");
        telemetry.update();

        frontLeftMotor = hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor = hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor = hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor = hardwareMap.dcMotor.get("backRightMotor");
        telemetry.addData("driveTrain", "done");
        telemetry.update();

        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
        if (runningEncoders) {
            driveTrain.runWithEncoders();
        } else {
            driveTrain.runWithoutEncoders();
        }
        driveMotors = new MotorPowers(0, 0, 0, 0);

        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");

        telemetry.addData("arm", "workin");
        telemetry.update();
        clawServo = hardwareMap.servo.get("frontServo");
        pivotPos = pivotMotor.getCurrentPosition();
        grabbing = false;
        //imu.initialize();
        telemetry.addData("imu", "inited");
        telemetry.update();
    }

    public void setMotors(MotorPowers robotMotors) {

        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = robotMotors.fR;
        driveMotors.bR = robotMotors.bR;


    }


    public void moveMotors() {
        driveMotors.scale();
        telemetry.addData("dmotor", driveMotors);
        driveTrain.runMotors(driveMotors);


    }


    public MotorPowers getDriveMotors() {
        return driveMotors;
    }

    public void moveLinkage(double m) {
        linkageMotor.setPower(m);
        linkagePos = linkageMotor.getCurrentPosition();
        if (m > 0.5) {
            if (pivotMotor.getPower() <= pivotNormalStall) {
                pivotMotor.setPower(pivotBigStall);
            }
        }else if(m>0){
            if(pivotMotor.getPower() <= pivotNormalStall){
                pivotMotor.setPower(pivotNormalStall);
            }
        }

    }

    public void pivotArm(double m) {
        pivotMotor.setPower(m);
        pivotPos = pivotMotor.getCurrentPosition();

    }

    public Double getIMUAngle() {
        return imu.getAngle();

    }

    public BNO055IMU.CalibrationStatus getIMUCalibStatus() {

        return imu.getCalibStatus();
    }


    public void grab() {

        if (clawServo.getPosition() > clawGrab) {

            clawServo.setPosition(clawServo.getPosition() - 0.1);
        }
        grabbing = true;

    }

    public void ungrab() {
        if (clawServo.getPosition() < clawInit) {

            clawServo.setPosition(clawServo.getPosition() + 0.1);
        }

        grabbing = false;
    }

    public void armUpdate() {
        if (linkagePos - linkageMotor.getCurrentPosition() > linkageError) {
            linkageMotor.setPower(-0.1);
            pivotStall = pivotBigStall;
        } else if (linkagePos - linkageMotor.getCurrentPosition() < -linkageError / 5) {
            linkageMotor.setPower(0.1);

        }
        linkageError = linkageNormalError;
        if (pivotPos - pivotMotor.getCurrentPosition() > pivotError) {
            pivotMotor.setPower(-0.1);
        } else if (pivotPos - pivotMotor.getCurrentPosition() < -pivotError / 5) {
            pivotMotor.setPower(pivotStall);
            if (pivotStall == pivotBigStall) {
                linkageError = linkageBigError;
            }
        }
        pivotStall = pivotNormalStall;
        if(grabbing){
            clawServo.setPosition(clawGrab);
        }

    }

}
