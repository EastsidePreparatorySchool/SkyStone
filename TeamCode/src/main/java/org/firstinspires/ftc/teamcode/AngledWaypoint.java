package org.firstinspires.ftc.teamcode;

public class AngledWaypoint extends Waypoint implements Cloneable{
    double x;
    double y;
    double angle;
    double followDist;

    AngledWaypoint(double x, double y, double angle, double followDist){
        this.x = x;
        this.y = y;
        this.angle = angle;
        this.followDist = followDist;

    }

    AngledWaypoint(Pose pose, double followDist){
        this(pose.x, pose.y, pose.angle, followDist);
    }

    AngledWaypoint(Point point, double angle, double followDist){
        this(point.x, point.y, angle, followDist);
    }

    @Override
    public AngledWaypoint scale(double val){
        return new AngledWaypoint(x*val, y*val, angle*val, followDist*val);
    }

    @Override
    public AngledWaypoint clone(){
        return new AngledWaypoint(x, y, angle, followDist);

    }


}
