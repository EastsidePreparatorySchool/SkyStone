package org.firstinspires.ftc.teamcode.auto.Points;

import java.util.LinkedList;

/**
 * A line segment, with defined start and end points
 */
public class Segment {
    /**
     * The start point
     */
    private Point start;
    /**
     * The end point
     */
    private Point end;
    /**
     * The smallest values that are on the line segment
     */
    private Point smallest;
    /**
     * The largest values that are on the line segment
     */
    private Point largest;
    /**
     * The slope of the line
     */
    private Double slope;
    /**
     * True if the line's end point has an angle
     */
    private Boolean hasAngle;
    /**
     * The end point's angle
     */
    private Double angle;
    /**
     * the line's C
     */
    private Double lineC;
    /**
     * the length of the line
     */
    private Double length;

    /**
     *
     * @param start the start point for the line segment
     * @param end the end point for the line segment; if Angled, the robot will angle to that heading
     */
    Segment(Point start, AngledPoint end){
        hasAngle = true;
        angle = end.angle;
        this.start = start;
        this.end = end;
        length = Math.sqrt(Math.pow(start.x-end.x,2)+Math.pow(start.y-end.y,2));
        slope = (start.x-end.x)/(start.y-end.y);
        lineC = (slope*end.x)+end.y;
        // find the lowest possible x and y
        smallest = new Point((start.x<=end.x)?start.x:end.x, (start.y<=end.y)?start.y:end.y);

        // highest possible x and y
        largest = new Point((start.x>=end.x)?start.x:end.x, (start.y>=end.y)?start.y: end.y);
    }

    /**
     *
     * @param start the start point for the line segment
     * @param end the end point for the line segment; if Angled, the robot will angle to that heading
     */
    Segment(Point start, AngledWaypoint end){
        hasAngle = true;
        angle = end.angle;
        this.start = start;
        this.end = end;
        length = Math.sqrt(Math.pow(start.x-end.x,2)+Math.pow(start.y-end.y,2));
        slope = (start.x-end.x)/(start.y-end.y);
        lineC = (slope*end.x)+end.y;
        // find the lowest possible x and y
        smallest = new Point((start.x<=end.x)?start.x:end.x, (start.y<=end.y)?start.y:end.y);

        // highest possible x and y
        largest = new Point((start.x>=end.x)?start.x:end.x, (start.y>=end.y)?start.y: end.y);
    }

    /**
     *
     * @param start the start point for the line segment
     * @param end the end point for the line segment; if Angled, the robot will angle to that heading
     */
    Segment(Point start, Point end){
        hasAngle = false;
        this.start = start;
        this.end = end;

        slope = (start.x-end.x)/(start.y-end.y);
        lineC = (slope*end.x)+end.y;
        smallest = new Point((start.x<=end.x)?start.x:end.x, (start.y<=end.y)?start.y:end.y);

        // highest possible x and y
        largest = new Point((start.x>=end.x)?start.x:end.x, (start.y>=end.y)?start.y: end.y);
    }

    /**
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     */
    Segment(double x1, double y1, double x2, double y2){
        this(new Point(x1, y1), new Point(x2, y2));

    }

    /**
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param angle1 the angle of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     * @param angle2 the angle of the end point; the robot will angle its heading in this direction
     */
    Segment(double x1, double y1, double angle1, double x2, double y2, double angle2){
        this(new AngledPoint(x1, y1, angle1), new AngledPoint(x2, y2, angle2));
    }

    /**
     *
     * @param x1 the x coordinate of the start point
     * @param y1 the y coordinate of the start point
     * @param x2 the x coordinate of the end point
     * @param y2 the y coordinate of the end point
     * @param angle2 the angle of the end point; the robot will angle its heading in this direction
     */
    Segment(double x1, double y1, double x2, double y2, double angle2){
        this(new Point(x1, y1), new AngledPoint(x2, y2, angle2));
    }

    // draws a circle, checks if it intersects the line
    // if it doesn't, return 0
    // if it does, see where it does.

    /**
     *
     * @return true if the end point has an angle
     */
    public Boolean getHasAngle(){
        return  getHasAngle();
    }

    /**
     *
     * @return the angle, if this has one
     */
    public Double getAngle(){
        return angle;
    }

    /**
     *
     * @return start point
     */
    public Point getStart() {
        return start;
    }

    /**
     *
     * @return end point
     */
    public Point getEnd(){
        return end;
    }

    /**
     *
     * @param p the origin of the circle
     * @param radius the radius of the circle
     * @return the point closest to this line's end point that intersects with the circle (null if none)
     */

    public Point furthestIntersection(Point p, double radius){

        // this segment equation is y = slope(x) +lineC;
        // circle equation is (x-p.x)^2 +(y-p.y)^2=radius
        // so first, substitute their the circle y for ours

        // normals are the a b and c in "ax^2 + bx +c"
        // A= (slope(x))^2 + x^2
        // they have 1 x because it is a circle
        double normalA = Math.pow(slope,2)+1;
        // we want to add up the b from (x-p.x)^2
        // and the b from (myY-p.y)^2
        // Xb = -p.x*2
        // Yb = (lineC-p.y)*2
        // B = Xb + Yb
        double normalB = (-p.x*2)+(lineC-p.y)*2;
        // this is the same process as normal A
        // but we also subtract the radius at the end
        // C = (lineC -p.y)^2 +(p.x)^2 - radius
        double normalC = Math.pow(lineC-p.y,2)+Math.pow(p.x, 2)-radius;

        double plusMinus = Math.pow(normalB,2)-(4*normalA*normalB);
        if(plusMinus <0){
            return null;
        }
        double minusedX = (-normalB - plusMinus) / (2 * normalA);
        double plusedX = (-normalB + plusMinus) / (2*normalA);
        // only bother plugging it back into the equation if it's in our segment
        LinkedList<Point> points = new LinkedList<Point>();
        if(inxRange(minusedX)){
            double minusedY = (normalA*Math.pow(minusedX,2))+(normalB*minusedX)+normalC;
            if(inyRange(minusedY)) {
                points.add(new Point(minusedX, minusedY));
            }
        }

        if(inxRange(plusedX)){
            double plusedY = (normalA*Math.pow(plusedX,2))+(normalB+plusedX)+normalC;
            if(inyRange(plusedY)){
                points.add(new Point(plusedX, plusedY));
            }
        }
        double dist = length;
        double tempDist = 100000;
        if(points.size()==0){
            return null;
        }
        // comparing the points to see which is closer to the Point that the robot wants to go to
        Point chosenPoint = null;
        for(Point point: points){
            tempDist = Math.sqrt(Math.pow(end.x-point.x,2)+Math.pow(end.y-point.y,2));
            if(tempDist<dist){
                chosenPoint = point;
            }
        }
        return chosenPoint;
    }

    /**
     *
     * @param x the x coordinate
     * @return true if the x coordinate is on the line segment
     */
    public Boolean inxRange(double x){
        if(smallest.x<x && x<largest.x){
            return true;
        }
        return false;
    }

    /**
     *
     * @param y the y coordinate
     * @return true if the y coordinate is on the line segment
     */
    public Boolean inyRange(double y){
        if(smallest.y<y && y<largest.y){
            return true;
        }
        return  false;
    }




}
