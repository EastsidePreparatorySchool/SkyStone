package org.eastsideprep.rclasslib;

public class ChassisDirection {
    private int id;
    public static final ChassisDirection FORWARD = new ChassisDirection(1);
    public static final ChassisDirection REVERSE = new ChassisDirection(2);
    public static final ChassisDirection STRAFE_LEFT = new ChassisDirection(3);
    public static final ChassisDirection STRAFE_RIGHT = new ChassisDirection(4);
    public static final ChassisDirection TURN_LEFT = new ChassisDirection(5);
    public static final ChassisDirection TURN_RIGHT = new ChassisDirection(6);

    //Are these CHASSIS things? I think they're more of ROBOT things so idk if to include them here.............??
    //probs not . it's not that hard to just close the function and make a new one.
    //however, it would be nice...
//    public static final ChassisDirection OPEN_GRABBER = new ChassisDirection(11);
//    public static final ChassisDirection CLOSE_GRABBER = new ChassisDirection(12);
//    public static final ChassisDirection RUN_INTAKE = new ChassisDirection(13);
//    public static final ChassisDirection RUN_MECHANISM_A = new ChassisDirection(14);
//    public static final ChassisDirection RUN_MECHANISM_B = new ChassisDirection(15);

    private ChassisDirection(int id){
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
