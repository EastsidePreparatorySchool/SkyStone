package org.eastsideprep.robotools;

public class ChassisInstruction {
    private ChassisDirection direction;
    private int milliseconds;
    private double power;

    private static final double DEFAULT_POWER = 0.6;
    public  static final double FOREVER = 101010101;

    public ChassisInstruction(ChassisDirection d, double power, int milliseconds){
        this.direction = d;
        this.milliseconds = milliseconds;
        this.power = power;
    }

    public ChassisInstruction(ChassisDirection d, int milliseconds){
        this(d, DEFAULT_POWER, milliseconds);
    }


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

}
