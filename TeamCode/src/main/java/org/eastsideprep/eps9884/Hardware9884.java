package org.eastsideprep.eps9884;

import com.qualcomm.robotcore.hardware.AnalogInput;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Hardware definitions for 9884
 */
public class Hardware9884 {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor [] allMotors;
    double [] rotationArray;


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Hardware9884() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap ahwMap) {
        // Save reference to Hardware map
        hwMap = ahwMap;
        // hey bob

        // Define and Initialize Motors
        leftFrontMotor = hwMap.dcMotor.get("LF");
        rightFrontMotor = hwMap.dcMotor.get("RF");
        leftBackMotor = hwMap.dcMotor.get("LB");
        rightBackMotor = hwMap.dcMotor.get("RB");

        allMotors = new DcMotor[]{ leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor};
        rotationArray= new double[]{-1.0, 1.0, -1.0, 1.0};

        leftBackMotor.setDirection(DcMotor.Direction.FORWARD);
        rightBackMotor.setDirection(DcMotor.Direction.REVERSE);
        leftFrontMotor.setDirection(DcMotor.Direction.FORWARD);
        rightFrontMotor.setDirection(DcMotor.Direction.REVERSE);

         for (DcMotor m : allMotors) {
            m.setPower(0.0);
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

