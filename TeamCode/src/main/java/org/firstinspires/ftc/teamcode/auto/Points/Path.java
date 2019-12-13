package org.firstinspires.ftc.teamcode.auto.Points;

import org.firstinspires.ftc.teamcode.robots.Robot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Path {

    ArrayList<Waypoint> path;
    Robot robot;


    Path(Robot r){
        this.robot = r;

    }

    /**
     *
     * @param robot the robot
     * @param path the list of Waypoints the robot should approach
     */
    Path(Robot robot, ArrayList<Waypoint> path){
        this.robot = robot;
        this.path = path;

    }
    /**
     *
     * @param robot the robot
     * @param points the Waypoints the robot should approach
     */
    Path(Robot robot, Waypoint... points){
            this(robot, Arrays.asList(points));


    }

    /**
     *
     * @param robot the robot
     * @param waypointList the list of Waypoints the robot should approach
     */
    Path(Robot robot, List<Waypoint> waypointList){
        Stack<Waypoint> waypointStack = new Stack<Waypoint>();
        // don't want it flipped
        for(int i = 0; i< waypointList.size(); i++){

            waypointStack.push(waypointList.get(i));
        }
        while(!waypointStack.isEmpty()){
            path.add(waypointStack.pop().clone());
        }
    }


    /**
     *
     * @param waypointList the LinkedList of Waypoints the robot should visit
     * @return the line as an ArrayList of segments
     */
    public ArrayList<Segment> genLine(List<Waypoint> waypointList){

        ArrayList<Segment> line = new ArrayList<>();
        int index = 0;
        for(int i = 1; i<waypointList.size(); i++){
            Segment seg = new Segment(waypointList.get(i-1),waypointList.get(i));
            line.add(seg);
        }

        return line;

    }

    public Waypoint furthestPoint(Pose robotPose){

        return null;
    }

    public void update(){




    }



}
