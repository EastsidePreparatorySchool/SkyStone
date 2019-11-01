package org.firstinspires.ftc.teamcode;

public interface Robot {

    public void init();
    public void setMotors(MotorPowers robotMotors);
    public void moveMotors();
    public MotorPowers getDriveMotors();
}
