package org.eastsideprep.rclasslib;

public class ChassisDirection {
    private int id;

    //Use objects to keep track of which direction is which. they just contain integers but this prevents people from just typing
    //  '2' into chassis.perform(), no weird integers, keeps track of all that. Also it prevents you from putting a number that doesn't
    //  have a direction that it corresponds to because the constructor is private.
    public static final ChassisDirection FORWARD = new ChassisDirection(1);
    public static final ChassisDirection REVERSE = new ChassisDirection(2);
    public static final ChassisDirection STRAFE_LEFT = new ChassisDirection(3);
    public static final ChassisDirection STRAFE_RIGHT = new ChassisDirection(4);
    public static final ChassisDirection TURN_LEFT = new ChassisDirection(5);
    public static final ChassisDirection TURN_RIGHT = new ChassisDirection(6);

    private ChassisDirection(int id){
        this.id = id;
    }

    //just in case
    public int getId() {
        return id;
    }
}
