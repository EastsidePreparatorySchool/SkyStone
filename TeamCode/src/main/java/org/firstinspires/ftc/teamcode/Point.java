package org.firstinspires.ftc.teamcode;

public class Point implements Cloneable{

    double x;
    double y;

    Point(){}

    Point(double x, double y){
        this.x = x;
        this.y = y;

    }

    public Point subtract(Point p){
        return new Point(this.x-p.x, this.y-p.y);


    }



    @Override
    public Point clone(){
        return new Point(this.x, this.y);

    }

}
