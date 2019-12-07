package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcontroller.external.samples.SensorBNO055IMU;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;

public class SkystoneRobot implements AutoRobot {

    Pose pose;
    MotorPowers driveMotors;
    HardwareMap hardwareMap;
    Telemetry telemetry;
    IMU imu;
    SensorBNO055IMU sensorBNO055IMU;
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    DriveTrain driveTrain;
    Servo clawServo;

    Vuforia vuforia;
    VuforiaTrackable[] skystoneTrackables;
    VuforiaTrackableDefaultListener[] listeners;

    // this may need to be changed, depending on what we want the default for this servo to be
    double clawInit = 0.0;
    double clawGrab = 0.5;

    public SkystoneRobot(HardwareMap hardwareMap, Pose pose, Telemetry telemetry, Vuforia vuforia, VuforiaTrackable[] skystoneTrackables, VuforiaTrackableDefaultListener[] listeners){
        this.hardwareMap =hardwareMap;
        this.pose = pose;
        this.telemetry = telemetry;

        this.vuforia = vuforia;
        this.skystoneTrackables = skystoneTrackables;
        this.listeners = listeners;
    }

    public void init(){
        imu = new IMU(hardwareMap.get(BNO055IMU.class, "imu"));
        frontLeftMotor =hardwareMap.dcMotor.get("frontLeftMotor");
        frontRightMotor =hardwareMap.dcMotor.get("frontRightMotor");
        backLeftMotor =hardwareMap.dcMotor.get("backLeftMotor");
        backRightMotor =hardwareMap.dcMotor.get("backRightMotor");
        driveTrain = new DriveTrain(frontLeftMotor, backLeftMotor, frontRightMotor, backRightMotor);
        driveMotors = new MotorPowers(0,0,0,0);


        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        armMotor = hardwareMap.dcMotor.get("linkageMotor");
        clawServo = hardwareMap.servo.get("clawServo");

        clawServo.setPosition(clawInit);
        imu.initialize();

    }




    public Pose getPose(){

        return pose;
    }



    public void setMotors(MotorPowers robotMotors){

        driveMotors.fL = robotMotors.fL;
        driveMotors.bL = robotMotors.bL;
        driveMotors.fR = robotMotors.fR;
        driveMotors.bR = robotMotors.bR;



    }



    public void moveMotors(){
        driveMotors.scale();
        driveTrain.runMotors(driveMotors);


    }


    public MotorPowers getDriveMotors() {
        return driveMotors;
    }

    public void grab(){
        clawServo.setPosition(clawGrab);

    }

    public void ungrab()
    {

        clawServo.setPosition(clawInit);
    }

    public void moveArm(double m){
        armMotor.setPower(m);


    }

    /**
     * This method locks the robot into turning
     * @param angle is the desired heading angle
     * @param speed how fast the robot should be turning (recommended 0.5 or less)
     *
     */
    public void turnToHeading(Double angle, Double speed){
        speed = (speed>1)? 1:speed;

        //this.pose.heading().getAngle();

    }


    public void trackRobot(){

        OpenGLMatrix trackedLocation = null;

        int li = 0;
        while(trackedLocation == null && li <listeners.length){
            trackedLocation = listeners[li].getUpdatedRobotLocation();
            if(trackedLocation != null){
                telemetry.addData("This target is: ", skystoneTrackables[li+5].getName());

            }
            li++;
        }

        //trackedLocation = listener.getUpdatedRobotLocation();
        if(trackedLocation != null){
            //pose = (trackedLocation.getData[14], trackedLocation.getData[15], trackedLocation.getData[16]);
            telemetry.addData("Location:", "changed");

        }
    }

    public Double getIMUAngle(){
        return imu.getAngle();

    }

    public BNO055IMU.CalibrationStatus getIMUCalibStatus(){

        return imu.getCalibStatus();
    }

}
