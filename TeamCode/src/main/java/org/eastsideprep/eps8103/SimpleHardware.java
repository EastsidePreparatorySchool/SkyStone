package org.eastsideprep.eps8103;

import com.qualcomm.hardware.motors.RevRoboticsCoreHexMotor;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.util.ElapsedTime;


import com.qualcomm.robotcore.hardware.ColorSensor;

import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Hardware definitions for 8103
 */
public class SimpleHardware {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

    public DcMotor[] allMotors;
    public Servo[] allServos;
    double[] rotationArray;

    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public SimpleHardware() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
//        hwMap = ahwMap;
        // hey bob

        // Define and Initialize Motors
        leftFrontMotor = hwMap.dcMotor.get("lf");
        rightFrontMotor = hwMap.dcMotor.get("rf");
        leftBackMotor = hwMap.dcMotor.get("lb");
        rightBackMotor = hwMap.dcMotor.get("rb");


        allMotors = new DcMotor[]{leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor};
        rotationArray = new double[]{-1.0, 1.0, -1.0, 1.0};

        leftBackMotor.setDirection(DcMotor.Direction.REVERSE);
        rightBackMotor.setDirection(DcMotor.Direction.FORWARD);
        leftFrontMotor.setDirection(DcMotor.Direction.REVERSE);
        rightFrontMotor.setDirection(DcMotor.Direction.FORWARD);

        for (DcMotor m : allMotors) {
            m.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            m.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE); // really? good for autonomous. But in driver control?
        }

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

