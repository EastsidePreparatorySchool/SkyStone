package org.firstinspires.ftc.teamcode;

public class Pose extends Point implements Cloneable {


    // this is where the robot is, and what it's angular orientation is

    double x;
    double y;

    // angle is measured in rads from the x-axis (thats the audience side)
    double angle;

    Pose(double x, double y, double angle){
        this.x=x;
        this.y=y;
        this.angle=angle;
    }

    Pose(Point p, double angle){
        this.x=p.x;
        this.y=p.y;
        this.angle = angle;

    }
    @Override
    public Pose clone(){
        Pose newClone = new Pose(this.x, this.y, this.angle);
        return newClone;

    }

}
