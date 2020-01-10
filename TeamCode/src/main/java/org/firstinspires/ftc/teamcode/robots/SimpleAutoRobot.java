package org.firstinspires.ftc.teamcode.robots;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.motors.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.teamcode.robots.motors.MotorDistances;
import org.firstinspires.ftc.teamcode.robots.sensors.IMU;

public class SimpleAutoRobot implements Robot {

    MotorPowers driveMotors;

    HardwareMap hardwareMap;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    Telemetry telemetry;
    LinearOpMode lop;
    public Arm arm;
    public Claw claw;
    DcMotor pivotMotor;
    DcMotor linkageMotor;
    public DriveTrain driveTrain;
    Servo frontServo;
    double encoderSpinDegrees;
    double encoderDist;

    Boolean strafeMode;
    Boolean turnMode;
    Boolean forwardMode;
    Boolean nothingMode;
    Boolean grabbing;
    Long lastTime;
    Long timer;
    public IMU imu;
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

        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, true);
        driveTrain.stopAndResetEncoders();

        // running with encoders, you should probably stop and reset their current position to 0
        driveTrain.runWithoutEncoders();

        //driveTrain.stopAndResetEncoders();
        driveMotors = new MotorPowers(0, 0, 0, 0);
        arm = new Arm(hardwareMap, telemetry);
        arm.init();
        linkageMotor = arm.getLinkageMotor();
        pivotMotor = arm.getPivotMotor();

        claw = new Claw(hardwareMap, telemetry);
        claw.init();
        frontServo = claw.getFrontServo();

        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        imu.initialize();

        grabbing = false;

    }


    public void update() {


    }

    public void armUpdate(){
        arm.update();
    }

    public String getChassisWheels() {
        return driveTrain.toString();
    }

    public Claw getClaw() {
        return claw;
    }

    public Arm getArm(){
        return arm;
    }

    public Servo getFrontServo(){
        return frontServo;
    }

    public DcMotor getPivotMotor(){
        return pivotMotor;
    }

    public DcMotor getLinkageMotor(){
        return linkageMotor;
    }

    public void stopAndResetEncoders() {
        driveTrain.stopAndResetEncoders();

    }

    public void runWithoutEncoders() {
        driveTrain.runWithoutEncoders();
    }

    /**
     * This turns the robot to an angle on the imu (-180, 180) it can't wrap or go over or under
     * @param speed the speed of the turn. If this is negative, the turn will be in reverse
     * @param angle the desired angle of the turn
     * @param margin the margin of error (in degrees)
     */
    public void turnToAngle(Double speed, Double angle, Double margin){
        driveTrain.turnToAngle(speed, angle, margin, imu, telemetry, lop);

    }


    // if negative, spins to the left, positive to the right
    public void spinDegrees(double degrees, double speed) {


    }

    public DriveTrain getDriveTrain(){
        return driveTrain;
    }



    /**
     * Set the Robot to strafe
     * @param left is the direction of the robot, left if true, right if false
     * @param speed is the speed of the motors
     */
    public void strafe(Boolean left, Double speed) {

        MotorPowers strafing = (left) ? new MotorPowers(-speed, speed, speed, -speed) : new MotorPowers(speed, -speed, -speed, speed);
        strafing.scale();
        setMotors(strafing);
        moveMotors();
    }

    /**
     * Set the Robot to strafe for a certain period of time
     * @param left is the direction of the robot, left if true, right if false
     * @param speed is the speed of the motors
     * @param time is the time to be strafed in milliseconds
     */
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


    /**
     * Set the Robot to drive forward
     * @param forwardDirection is the direction of the robot, forward if true, backward if false
     * @param speed is the speed of the motors
     */
    public void forward(Boolean forwardDirection, Double speed) {
        MotorPowers forwarding = (forwardDirection) ? new MotorPowers(speed, speed, speed, speed) : new MotorPowers(-speed, -speed, -speed, -speed);
        forwarding.scale();
        setMotors(forwarding);
        driveTrain.runMotors(driveMotors);
    }


    public void grab(){
        claw.close();
        grabbing = true;
    }

    public void ungrab(){
        grabbing = false;
        claw.open();
    }

    /**
     * Set the Robot to drive forward for a certain period of time
     * @param forwardDirection is the direction of the robot, forward if true, backward if false
     * @param speed is the speed of the motors
     * @param time is the time to drive forward in milliseconds
     */
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

    public void forwardEncoder(Boolean forwardDirection, Double speed, Double encoderVal, Double margin) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, margin, 0, false, telemetry, lop);


    }

    public void forwardEncoderSlowDown(Boolean forwardDirection, Double speed, Double encoderVal, Double margin, Double slowPoint) {
        MotorDistances encoderDists = (forwardDirection) ? new MotorDistances(Math.abs(encoderVal)) : new MotorDistances(-Math.abs(encoderVal));

        MotorPowers forwardMotorPowers = new MotorPowers(speed, speed, speed, speed);
        driveTrain.toEncoderVal(encoderDists, forwardMotorPowers, margin, slowPoint, true, telemetry, lop);


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
