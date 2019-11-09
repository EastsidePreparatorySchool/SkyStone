package org.firstinspires.ftc.teamcode;

public class Point implements Cloneable{

    double x;
    double y;

    Point(){}

    Point(double x, double y){
        this.x = x;
        this.y = y;

    }

    Point(Point p){
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
