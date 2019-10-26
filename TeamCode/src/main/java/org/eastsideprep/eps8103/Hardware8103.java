package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;


/**
 * Hardware definitions for 8103
 */
public class Hardware8103 {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;
    public DcMotor armPivot = null;
    public DcMotor armExtender = null;
    public Servo wrist = null;
    public Servo updown = null;
    public Servo closer = null;
    public DcMotor [] allMotors;
    double [] rotationArray;


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Hardware8103() {

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

        armPivot = hwMap.dcMotor.get("pivot");
        armExtender = hwMap.dcMotor.get("extend");

        wrist = hwMap.servo.get("servo1");
        updown = hwMap.servo.get("servo2");
        closer = hwMap.servo.get("servo3");


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

