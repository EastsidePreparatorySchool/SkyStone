package org.firstinspires.ftc.teamcode.auto.Points;

public class Point implements Cloneable{

    public double x;
    public double y;

    public Point(){}

    public Point(double x, double y){
        this.x = x;
        this.y = y;

    }

    public Point(Point p){
        this(p.x, p.y);
    }

    public Point subtract(Point p){
        return new Point(this.x-p.x, this.y-p.y);


    }

    public double invTan(){
        return Math.atan(this.x/this.y);

    }

    public Point scale(double val){
        return new Point(x*val, y*val);
    }


    @Override
    public Point clone(){
        return new Point(this.x, this.y);

    }

}
