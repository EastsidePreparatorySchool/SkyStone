package org.eastsideprep.whitmerbot;

public class AutoPath {
    static int[] programRightGold = new int[]
            {
                    cwRobot.SETHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.SETPOWER, 25,
                    cwRobot.DRIVE, cwRobot.inches(19.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(16.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.DRIVE, cwRobot.inches(-6.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.STRAFE, cwRobot.inches(-3.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(58.0)
            };

    static int[] programLeftGold = new int[]
            {
                    cwRobot.SETHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.SETPOWER, 25,
                    cwRobot.DRIVE, cwRobot.inches(18.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(16.0),
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.DRIVE, cwRobot.inches(-6.0),
                    cwRobot.TURNTOHEADING, 45,
                    cwRobot.DRIVE, cwRobot.inches(18.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.STRAFE, cwRobot.inches(-4.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(59.5),
            };

    static int[] programCenterGold = new int[]
            {
                    cwRobot.SETHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(57.0),
                    cwRobot.DRIVE, cwRobot.inches(-7.0),
                    cwRobot.TURNTOHEADING, 45,
                    cwRobot.DRIVE, cwRobot.inches(11.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(59.0),
            };

    static int[] programToAndFro = new int[]
            {
                    cwRobot.SETHEADING, 0,
                    cwRobot.APPROACHTO, 40,
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.APPROACHTO, 70,
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.APPROACHTO, 40,
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.APPROACHTO, 70,
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.APPROACHTO, 40,
                    cwRobot.TURNTOHEADING, 0,
            };

    static int[] programOrbit = new int[]
            {
                    cwRobot.SETHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.TURNTOHEADING, -135,
                    cwRobot.DRIVE, cwRobot.inches(34.5),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.APPROACHTO, 36,

                    cwRobot.TURNTOHEADING, 135,
                    cwRobot.DRIVE, cwRobot.inches(69.0),
                    cwRobot.TURNTOHEADING, 180,
                    cwRobot.APPROACHTO, 36,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(69.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.APPROACHTO, 36,

                    cwRobot.TURNTOHEADING, 135,
                    cwRobot.DRIVE, cwRobot.inches(69.0),
                    cwRobot.TURNTOHEADING, 180,
                    cwRobot.APPROACHTO, 36,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(69.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.APPROACHTO, 36,

                    cwRobot.TURNTOHEADING, -135,
                    cwRobot.DRIVE, cwRobot.inches(-34.5),
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.SETPOWER, 25,
                    cwRobot.DRIVE, cwRobot.inches(-13.0),
                    cwRobot.SETPOWER, 50,
//                    cwRobot.TURNTOHEADING, 45,
//                    cwRobot.DRIVE, cwRobot.inches(68.0),
//                    cwRobot.TURNTOHEADING, 0,
//                    cwRobot.DISPLAY,0,
//                    cwRobot.APPROACHTO, 36,
//                    cwRobot.TURNTOHEADING, -135,
//                    cwRobot.DRIVE, cwRobot.inches(68.0),

            };


    static int[] programCraterRightGold = new int[]
            {
                    cwRobot.SETHEADING, 45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.SETPOWER, 25,
                    cwRobot.STRAFE, cwRobot.inches(14.5),
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.DRIVE, cwRobot.inches(-13.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(45.5),
                    cwRobot.TURNTOHEADING, 0,
                    cwRobot.APPROACHTO, 36,
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.DRIVE, cwRobot.inches(18.0),
                    cwRobot.SETPOWER, 25,
                    cwRobot.DRIVE, cwRobot.inches(30.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(10.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.DRIVE, cwRobot.inches(9.0),
                    cwRobot.DRIVE, cwRobot.inches(-6.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.STRAFE, cwRobot.inches(-4.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(73.0)
            };

    static int[] programCraterCenterGold = new int[]
            {
                    cwRobot.SETHEADING, 45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.SETPOWER, 25,
                    cwRobot.DRIVE, cwRobot.inches(12.0),
                    cwRobot.DRIVE, cwRobot.inches(-12.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(45.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.DRIVE, cwRobot.inches(49.5),
                    cwRobot.WAIT, 500,
                    cwRobot.DRIVE, cwRobot.inches(-6.0),
                    cwRobot.STRAFE, cwRobot.inches(-3.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.STRAFE, cwRobot.inches(-3.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(68.0)
            };

    static int[] programCraterLeftGold = new int[]
            {
                    cwRobot.SETHEADING, 45,
                    cwRobot.DRIVE, cwRobot.inches(13.0),
                    cwRobot.SETPOWER, 25,
                    cwRobot.STRAFE, cwRobot.inches(-14.5),
                    cwRobot.DRIVE, cwRobot.inches(12.0),
                    cwRobot.DRIVE, cwRobot.inches(-12.0),
                    cwRobot.SETPOWER, 50,
                    cwRobot.TURNTOHEADING, -45,
                    cwRobot.DRIVE, cwRobot.inches(41.0),
                    cwRobot.TURNTOHEADING, -90,
                    cwRobot.DRIVE, cwRobot.inches(49.5),
                    cwRobot.WAIT, 500,
                    cwRobot.DRIVE, cwRobot.inches(-6.0),
                    cwRobot.STRAFE, cwRobot.inches(-3.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.STRAFE, cwRobot.inches(-3.0),
                    cwRobot.TURNTOHEADING, 90,
                    cwRobot.DRIVE, cwRobot.inches(68.0)
            };


}
