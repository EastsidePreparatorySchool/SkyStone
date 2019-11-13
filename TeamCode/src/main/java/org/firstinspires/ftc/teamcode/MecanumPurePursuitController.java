package org.firstinspires.ftc.teamcode;

public class MecanumPurePursuitController implements Controller {

    @Override
    public MotorPowers motorPowers(Waypoint waypoint, Pose robotPose, double speed) {

        // this is how far away the object is

        double totalDist = Math.sqrt(Math.pow(waypoint.x-robotPose.x,2)+Math.pow(waypoint.y-robotPose.y,2));
        // this is the angle from the direction the bot is heading to the way-point
        // or, more accurately, how much the bot needs to turn to point at the way-point
        // that's the reason why i subtract the heading (direction of the robot)
        //  trying with 3, 5 and 7, 8
        // 3.6

        // 36.86989-90 = -53.13
        // -4, -3
        //
        // 2.16
        //  -2.88
        //
        double botPointAngle = robotPose.subtract(waypoint).invTan()-robotPose.heading();
        // this is the
        double botPointX = totalDist*Math.cos(botPointAngle);
        double botPointY = totalDist*Math.sin(botPointAngle);


        // this is a vector pointing from origin to a point
        // the vector is how much the robot needs to move to get to the way-point
        // it doesn't actually point from the robot to the way-point
        // negating it because the vectors point in the opposite direction of the target
        double headingDesire;

        if(waypoint instanceof AngledWaypoint){
            headingDesire = ((AngledWaypoint) waypoint).angle;
        }else{



            // these are the angles that the robot should approach the waypoints
            // we don't do this for angled waypoints because they already have
            // an angle they want us to come from

            double frontWayAngle = waypoint.subtract(robotPose).invTan();
            double backWayAngle = frontWayAngle+Math.PI;
            // 306.87
            // 36.86989
            double frontAngleMove = angleNegativePositive(frontWayAngle - robotPose.heading());
            double backAngleMove = angleNegativePositive(backWayAngle - robotPose.heading());
            // this is an if else that sets something
            // in this case, if the needed movement to reach angle a is bigger, do angle b
            // other wise do angle a
            headingDesire = (frontAngleMove>backAngleMove)? backWayAngle:frontWayAngle;



            }
        // 2.16
        // -2.88
        // 36.898989
        Pose movePose = new Pose(botPointX, botPointY, headingDesire);

        //







        return null;
    }

    public double angleNegativePositive(double angle){
        if(angle < 0){
            angle = (2*Math.PI)+angle;

        }
        if(angle>=(2*Math.PI)){
            angle %= 2*Math.PI;
        }
        return angle;
    }
}
