package org.firstinspires.ftc.teamcode.robots;

import org.firstinspires.ftc.teamcode.robots.motors.MotorPowers;

public interface Robot {

    public void init();
    public void setMotors(MotorPowers robotMotors);
    public void moveMotors();
    public MotorPowers getDriveMotors();
}
