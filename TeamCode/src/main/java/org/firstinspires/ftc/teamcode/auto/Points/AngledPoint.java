package org.firstinspires.ftc.teamcode.auto.Points;

public class AngledPoint extends Point implements Cloneable{
    double x;
    double y;
    double angle;

    AngledPoint(){}

    AngledPoint(double x, double y, double angle){
        this.x = x;
        this.y = y;
        this.angle = angle;

    }

    AngledPoint(Point p, double angle){
        this(p.x, p.y, angle);

    }

    public double getAngle() {
        return angle;
    }

    public void setAngle(double angle) {
        this.angle = angle;
    }

    @Override
    public AngledPoint subtract(Point p){
        return new AngledPoint(this.x-p.x, this.y-p.y, angle);

    }



    @Override
    public AngledPoint scale(double val){
        return new AngledPoint(x*val, y*val, angle);
    }


    @Override
    public AngledPoint clone(){
        return new AngledPoint(this.x, this.y, angle);

    }


}
