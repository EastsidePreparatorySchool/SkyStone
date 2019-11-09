package org.firstinspires.ftc.teamcode;

public class MecanumPurePursuitController implements Controller {

    @Override
    public MotorPowers motorPowers(Waypoint waypoint, Pose robotPose, double speed) {


        double totalDist = Math.sqrt(Math.pow(waypoint.x-robotPose.x,2)+Math.pow(waypoint.y-robotPose.y,2));
        if(waypoint instanceof AngledWaypoint){

        }else{
            // this is the angle from the direction the bot is heading to the waypoint
            //NOT the angle from the bot using the x-axis
            double botPointAngle = robotPose.subtract(waypoint).invTan()-robotPose.heading();
            // imagine a triangle with teh bPA as the reference angle
            double botPointX = totalDist*Math.cos(botPointAngle);
            double botPointY = totalDist*Math.sin(botPointAngle);

            // this is a vector pointing from the robot to the target
            Pose movePose = new Pose(botPointX, botPointY, botPointAngle);


        }



        return null;
    }
}
