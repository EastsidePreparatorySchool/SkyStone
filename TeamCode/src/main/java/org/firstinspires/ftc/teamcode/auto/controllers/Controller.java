package org.firstinspires.ftc.teamcode.auto.controllers;

import org.firstinspires.ftc.teamcode.auto.Points.Point;
import org.firstinspires.ftc.teamcode.auto.Points.Pose;
import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

public interface Controller {

    public MotorPowers motorPowers(Point point, Pose robotPose, double speed);


}
