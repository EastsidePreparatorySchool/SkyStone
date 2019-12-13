package org.firstinspires.ftc.teamcode.auto.Points;

public class Waypoint extends Point implements Cloneable {

    public double x;
    public double y;
    public double followDist;
    // Waypoints specify how far the robot should follow

    Waypoint(){}
    Waypoint(double x, double y, double followDist){
        this.x = x;
        this.y = y;
        this.followDist = followDist;
    }

    Waypoint(Waypoint w){
        this(w.x,w.y,w.followDist);
    }

    Waypoint(Pose pose, double followDist){
        this(pose.x, pose.y, followDist);

    }

    Waypoint(Point p, double followDist){
        this(p.x, p.y, followDist);
    }
    @Override
    public Waypoint clone(){
        return new Waypoint(this.x, this.y, this.followDist);

    }

    @Override
    public Waypoint scale(double val){
        return new Waypoint(x*val, y*val, followDist*val);
    }

    @Override
    public String toString(){
        return ("x: "+x+" y: "+y);

    }
}
