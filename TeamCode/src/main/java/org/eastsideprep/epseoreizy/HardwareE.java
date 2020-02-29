package org.eastsideprep.epseoreizy;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.eastsideprep.rclasslib.Chassis;
import org.eastsideprep.rclasslib.Mechanism;


/**
 * Hardware definitions for everest
 * hi
 * testing123
 */
public class HardwareE {
    /* Public OpMode members. */

    double [] rotationArray;
    Chassis chassis;
    Mechanism foundation1;
    Mechanism foundation2;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public HardwareE() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;


        //Set up the Chassis
        //chassis = new Chassis(hwMap, "frontLeftMotor", "frontRightMotor", "backLeftMotor", "backRightMotor");
//        chassis = new Chassis(hwMap, "frontLeftMotor", "frontRightMotor", "backLeftMotor", "backRightMotor");
        chassis = new Chassis(hwMap, "LF", "RF", "LB", "RB");

        chassis.setDirections(
                DcMotor.Direction.REVERSE,
                DcMotor.Direction.FORWARD,
                DcMotor.Direction.REVERSE,
                DcMotor.Direction.FORWARD
        );
        chassis.setModes(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chassis.setModes(DcMotor.RunMode.RUN_USING_ENCODER);
        chassis.setZeroPowerBehaviors(DcMotor.ZeroPowerBehavior.BRAKE);
        //chassis.getBackRightMotor().setModifier(0.75);

    }

    public double[] getDrivePowersFromAngle(double angle) {
        double[] unscaledPowers = new double[4];
        unscaledPowers[0] = Math.sin(angle + Math.PI / 4);
        unscaledPowers[1] = Math.cos(angle + Math.PI / 4);
        unscaledPowers[2] = unscaledPowers[1];
        unscaledPowers[3] = unscaledPowers[0];
        return unscaledPowers;
    }

}

