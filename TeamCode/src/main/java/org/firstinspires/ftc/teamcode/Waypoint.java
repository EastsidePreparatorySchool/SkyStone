package org.firstinspires.ftc.teamcode;

public class Waypoint extends Point implements Cloneable {

    double x;
    double y;
    double followDist;
    // Waypoints specify how far the robot should follow

    Waypoint(){}

    Waypoint(double x, double y, double followDist){
        this.x = x;
        this.y = y;
        this.followDist = followDist;

    }

    Waypoint(Point p, double followDist){
        this.x = p.x;
        this.y = p.y;
        this.followDist = followDist;

    }

    public Waypoint clone(){
        return new Waypoint(this.x, this.y, this.followDist);

    }
}
