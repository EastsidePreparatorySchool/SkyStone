package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;

import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;

public class IMU {

    /**
     * The IMU
     */
    private BNO055IMU imu;
    /**
     * the previously read angle
     */
    private Double lastAngle;
    /**
     * The current angle
     */
    private Double currAngle;

    /**
     *
     * @param imu is the IMU
     */


    IMU(BNO055IMU imu){
        this.imu = imu;


    }

    double getAngle(){
        double litAngle = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.XYZ, Angl);


    }

}
