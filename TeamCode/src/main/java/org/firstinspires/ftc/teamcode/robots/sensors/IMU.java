package org.firstinspires.ftc.teamcode.robots.sensors;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

public class IMU {

    /**
     * The IMU
     */
    private BNO055IMU imu;

    /**
     * the previously read angle
     */
    private Orientation lastOrientation;


    /**
     * The current angle
     */
    private double currAngle;

    BNO055IMU.CalibrationStatus imuCalib;

    /**
     * @param imu is the IMU
     */
    public IMU(BNO055IMU imu) {

        //this.imu = null;
        this.imu = imu;
    }

    public void initialize() {


        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        parameters.loggingEnabled = true;
        parameters.mode = BNO055IMU.SensorMode.IMU;

        imu.initialize(parameters);

        imuCalib = imu.getCalibrationStatus();
    }

    public BNO055IMU.CalibrationStatus getCalibStatus(){
            imuCalib = imu.getCalibrationStatus();
            return imuCalib;

    }

    // more about IMU's https://stemrobotics.cs.pdx.edu/node/7265
    //
    public Double getAngle() {

        Orientation imuAngularOrientation = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        Double changeInAngle;
        /*if (lastOrientation != null) {
            changeInAngle = (double) lastOrientation.firstAngle - imuAngularOrientation.firstAngle;
        } else {
            changeInAngle = currAngle - imuAngularOrientation.firstAngle;

        }
        if (changeInAngle < -Math.PI) {
            changeInAngle += Math.PI * 2;
        } else if (changeInAngle > Math.PI) {
            changeInAngle -= Math.PI * 2;
        }

        currAngle += changeInAngle;

        if(currAngle <0){
            currAngle = Math.abs(currAngle);

        }

        if(currAngle > Math.PI*2){
            currAngle -=Math.PI*2;

        }
        lastOrientation = imuAngularOrientation;

         */
        Double x = imuAngularOrientation.firstAngle - 0.01;
        x += 0.01;
        return x;
    }

    public Double getAngleDegrees(){
        return (getAngle()/6.2)*360;

    }

    @Override
    public String toString() {
        return imu.toString();
    }
}
