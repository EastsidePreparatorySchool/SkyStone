package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.robots.motors.DriveTrain;
import org.firstinspires.ftc.teamcode.robots.sensors.IMU;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

public class TeleopRobot implements Robot {

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
    Servo frontServo;
    Servo backServo;
    public Boolean armForUpdate = false;
    public Arm arm;
    public DriveTrain driveTrain;
    public Claw claw;
    public IMU imu;
    // this may need to be changed, depending on what we want the default for this servo to be
    double clawInit = 0.56;
    double clawGrab = 0;

    boolean grabbing;

    public TeleopRobot() {


    }

    public TeleopRobot(HardwareMap hardwareMap, Telemetry telemetry, boolean runwithEncoders) {
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

        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor, true);
        driveTrain.stopAndResetEncoders();
        if (runningEncoders) {
            driveTrain.runWithEncoders();
        } else {
            driveTrain.runWithoutEncoders();
        }
        driveMotors = new MotorPowers(0, 0, 0, 0);
        arm = new Arm(hardwareMap, telemetry);
        arm.init();
        telemetry.update();
        pivotMotor = arm.getPivotMotor();
        linkageMotor = arm.getLinkageMotor();
        claw = new Claw(hardwareMap, telemetry);
        claw.init();
        telemetry.update();
        frontServo = claw.getFrontServo();
        backServo = claw.getBackServo();
        grabbing = false;
        imu.initialize();
        telemetry.addData("imu", "inited");
        telemetry.update();
    }


    public void setMotors(MotorPowers robotMotors) {

        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = robotMotors.fR;
        driveMotors.bR = robotMotors.bR;


    }

    public Claw getClaw(){
        return claw;
    }

    public Arm getArm(){
        return arm;
    }

    public Servo getFrontServo() {
        return frontServo;
    }

    public Servo getBackServo(){
        return backServo;
    }

    /**
     * Set the Drive Train's motors to the Drive Motor Powers
     */
    public void moveMotors() {
        driveMotors.scale();
        telemetry.addData("dmotor", driveMotors);
        driveTrain.runMotors(driveMotors);


    }



    public MotorPowers getDriveMotors() {
        return driveMotors;
    }

    /**
     * Set the linkage's power. After calling an arm movement function,
     * the arm must be continually updated
     * @param power Is the power the linkage motor will be set to
     */
    public void moveLinkage(double power) {
        armForUpdate = true;
        if(power <= 0.0){
            arm.retractLinkage(power);
        }else{
            arm.extendLinkage(power);
        }

    }

    /**
     * Set the pivot's power. After calling an arm movement function,
     * the arm must be continually updated
     * @param power Is the power the pivot motor will be set to.
     */
    public void pivotArm(double power) {
        armForUpdate = true;
        arm.pivotArm(power);

    }

    public Double getIMUAngle() {
        return imu.getAngle();

    }

    public BNO055IMU.CalibrationStatus getIMUCalibStatus() {

        return imu.getCalibStatus();
    }


    /**
     * Closes the claw
     */
    public void grab() {

        claw.close();
        grabbing = true;

    }

    /**
     * Opens the claw
     */
    public void ungrab() {
        claw.open();
        grabbing = false;
    }

    /**
     * Sets the claw servo positions closer to the 0 position
     */
    public void limitlessGrab(){
        claw.noLimitClose();
        grabbing = true;
    }
    /**
     * Moves the claw closer to the furthest open extreme
     * This means that the servos will move towards whichever direction is their 1
     */
    public void limitlessUngrab(){
        claw.noLimitOpen();
        grabbing = false;
    }

    /**
     * Sets the claw servos to be closer to their closed position
     * Must be called multiple times to have the desired effect
     */
    public void variedGrab(){
        claw.varyingClose();
        grabbing = true;
    }

    /**
     * Sets the claw servos to be closer to their open position
     * Must be called multiple times to have the desired effect
     */
    public void variedUngrab(){
        claw.varyingOpen();
        grabbing = false;
    }

    /**
     * Updates the arm
     */
    public void armUpdate() {
        if(armForUpdate){
            arm.update();
        }

    }


}
