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
public class Hardware8103 {
    /* Public OpMode members. */
    public DcMotor leftFrontMotor = null;
    public DcMotor rightFrontMotor = null;
    public DcMotor leftBackMotor = null;
    public DcMotor rightBackMotor = null;

    public DcMotor intakeRight = null;
    public DcMotor intakeLeft = null;

    public DcMotor lift = null;

    public CRServo bay1 = null;
    public CRServo bay2 = null;

    public Servo leftpuller = null;
    public Servo rightpuller = null;

    public Servo left4Bar = null;
    public Servo right4Bar = null;
    public Servo grabber = null;

    public DcMotor[] allMotors;
    public Servo[] allServos;
    double[] rotationArray;
    ColorSensor color_range_sensor = null;

    double xpos;
    double ypos;

    //constants
    int TICKS_PER_REV = 1120;
    double WHEEL_RADIUS = 2;
    double WHEEL_CIRC = WHEEL_RADIUS * 2 * Math.PI;

    //using initial and change i can figure out any height
    int LIFT_DELTA = -345;//range from -340 to -350
    int LIFT_LEVEL_PICKUP = 200;

    //these are tested values and they work well enough
    int LIFT_LEVEL_1 = -150;
    int LIFT_LEVEL_2 = -500;
    int LIFT_LEVEL_3 = -850;
    int LIFT_LEVEL_4 = -1200;
    int LIFT_LEVEL_5 = -1550;
    int LIFT_LEVEL_6 = -1900;
    int LIFT_LEVEL_7 = -2250;
    int LIFT_LEVEL_8 = -2600;
    ArrayList<Integer> liftHeights = new ArrayList<>();


    /* local OpMode members. */
    HardwareMap hwMap = null;
    private ElapsedTime period = new ElapsedTime();

    /* Constructor */
    public Hardware8103() {

    }

    /* Initialize standard Hardware interfaces */
    public void init(HardwareMap hwMap) {
        // Save reference to Hardware map
//        hwMap = ahwMap;
        // hey bob

        // Define and Initialize Motors
        leftFrontMotor = hwMap.dcMotor.get("LF");
        rightFrontMotor = hwMap.dcMotor.get("RF");
        leftBackMotor = hwMap.dcMotor.get("LB");
        rightBackMotor = hwMap.dcMotor.get("RB");

        intakeLeft = hwMap.dcMotor.get("intakeLeft");
        intakeRight = hwMap.dcMotor.get("intakeRight");

        lift = hwMap.dcMotor.get("lift");
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        lift.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        bay1 = hwMap.crservo.get("bay1");
        bay2 = hwMap.crservo.get("bay2");

        leftpuller = hwMap.servo.get("leftpuller");
        rightpuller = hwMap.servo.get("rightpuller");

        left4Bar = hwMap.servo.get("left4bar");
        right4Bar = hwMap.servo.get("right4bar");
        grabber = hwMap.servo.get("grabbercloser");

        allMotors = new DcMotor[]{leftFrontMotor, rightFrontMotor, leftBackMotor, rightBackMotor};
        allServos = new Servo[]{leftpuller, rightpuller, left4Bar, right4Bar};
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
        for (int i = 0; i < 9; i++) {
            liftHeights.add(LIFT_LEVEL_PICKUP + i * LIFT_DELTA);
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

