package org.eastsideprep.rclasslib;

public class ChassisInstruction {
    private ChassisDirection direction;
    private int milliseconds;
    private double power;

    private static double DEFAULT_POWER = 0.6;
    public  static final double FOREVER = 101010101;

    //Choice to construct using direction, power, milliseconds , or just direction and power (and will use default power)
    public ChassisInstruction(ChassisDirection d, double power, int milliseconds){
        this.direction = d;
        this.milliseconds = milliseconds;
        this.power = power;
    }

    public ChassisInstruction(ChassisDirection d, int milliseconds){
        this(d, DEFAULT_POWER, milliseconds);
    }


//  get() commands so that chassis.perform() can get the various attributes and use them to carry out instructions
    public ChassisDirection getDirection() {
        return direction;
    }

    public int getMilliseconds() {
        return milliseconds;
    }

    public double getPower() {
        return power;
    }

    public double getReversePower() {

        return this.power * -1;
    }

    //set the default power. call in hardware.java or init
    public static void setDefaultPower(double p){
        DEFAULT_POWER = p;
    }

}
