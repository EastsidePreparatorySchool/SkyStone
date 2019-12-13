package org.firstinspires.ftc.teamcode.auto.Points;

public class Pose extends Point implements Cloneable {


    // this is where the robot is, and what it's angular orientation is

    public double x;
    public double y;

    // angle is measured in rads from the x-axis (thats the audience side)
    double angle;

    public Pose(double x, double y, double angle){
        this.x=x;
        this.y=y;
        this.angle=angle;
    }

    public Pose(Pose po){
        this(po.x, po.y, po.angle);
    }

    public Pose(Point p, double angle){
        this.x=p.x;
        this.y=p.y;
        this.angle = angle;

    }

    public double heading(){

        return angle;
    }


    @Override
    public Pose scale(double val){
        return new Pose(x*val, y*val, angle*val);
    }

    @Override
    public Pose clone(){
        Pose newClone = new Pose(this.x, this.y, this.angle);
        return newClone;

    }

}
