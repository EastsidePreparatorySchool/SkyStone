package org.firstinspires.ftc.teamcode;

import java.util.LinkedList;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class SubiPath {

    LinkedList<Waypoint> path;
    Robot robot;

    SubiPath(Robot r){
        this.robot = r;

    }

    SubiPath(Robot robot, LinkedList<Waypoint> path){
        this.robot = robot;
        this.path = path;

    }

    SubiPath(Robot robot, Waypoint... points){
            this(robot, Arrays.asList(points));


    }

    SubiPath(Robot robot, List<Waypoint> waypointList){
        Stack<Waypoint> waypointStack = new Stack<Waypoint>();
        // don't want it flipped
        for(int i = 0; i< waypointList.size(); i++){

            waypointStack.push(waypointList.get(i));
        }
        while(!waypointStack.isEmpty()){
            path.add(waypointStack.pop().clone());
        }
    }

    public void update(){



    }



}
