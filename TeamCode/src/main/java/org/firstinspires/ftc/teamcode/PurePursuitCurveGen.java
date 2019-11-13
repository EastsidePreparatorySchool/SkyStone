package org.firstinspires.ftc.teamcode;

import java.util.LinkedList;

public class PurePursuitCurveGen {

    PurePursuitCurveGen(){

    }

    public Path genCurve(double lookAheadDist, AutoRobot robot, Waypoint endPoint){

        LinkedList<Waypoint> curve = new LinkedList<Waypoint>();
        Pose robotPose = robot.getPose();
        double totalDist = Math.sqrt(Math.pow(robotPose.x-endPoint.x,2) + Math.pow(robotPose.y - endPoint.y,2));

        if(lookAheadDist > totalDist){
            lookAheadDist = totalDist;
            double localX = (endPoint.x-robotPose.x)*Math.cos(robotPose.heading())+(endPoint.y-robotPose.y)*Math.sin(robotPose.heading());

            double curvature = localX*2/Math.pow(lookAheadDist,2);


        }



        // this is the D or l in the equation
        // M = 2x/D^2
        //  trying with 3, 5 and 7, 8
        // 3.6

        // 36.86989-90 = -53.13
        // -4, -3
        // 4, 3
        //36.86989
        // 126.86989

        // cos(90)= 0
        // sin(90)=1
        //
        //0*(4) + 1*(3) = 3
        // 0*(3) + 1*(4) = 4
        // curvature = 6/(9+16)=0.24
        //




        return null;
    }



    public Path genCurve(double lookAheadDist, Robot robot, AngledWaypoint endPoint){



        return null;
    }

}
