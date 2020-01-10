package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.ServoController;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class Claw {
    Servo frontServo;
    Servo backServo;
    ServoController frontController;
    ServoController backController;
    HardwareMap hardwareMap;
    Telemetry telemetry;

    double max = 0.56;
    double min = 0.001;
    double frontMax;
    double frontMin;
    double backMax;
    double backMin;
    Boolean closing;
    public Claw(){}

    public Claw(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }

    public void init(){

        frontServo = hardwareMap.servo.get("frontServo");
        backServo = hardwareMap.servo.get("backServo");
        frontController = frontServo.getController();
        backController = backServo.getController();
        closing = false;
        //NOTE, YOU MAY NEED TO SET DIRECTION FOR THESE SERVOS TO BE OPPOSITE TO EACH OTHER
        telemetry.addData("Status", "Initialized");
        telemetry.update();

    }

    public Servo getFrontServo(){
        return frontServo;
    }

    public Servo getBackServo(){
        return backServo;
    }

    /**
     * Gets if the claw is closing
     * @return if the claw is closing
     */
    public Boolean getClosing(){
        return closing;
    }

    /**
     * Sets the claw servos to their minimum, or "closed" position
     */
    public void close(){
        frontServo.setPosition(frontMin);
        backServo.setPosition(backMin);
        closing = true;

    }

    /**
     * Sets the claw servos to be closer to their closed position
     * Must be called multiple times to have the desired effect
     */
    public void varyingClose(){
        if(frontServo.getPosition()>frontMin){
            frontServo.setPosition(frontServo.getPosition()-0.1);
        }
        if(backServo.getPosition()<backMax){
            backServo.setPosition(backServo.getPosition()+0.1);
        }
        closing = true;


    }

    /**
     * Sets the claw servo positions to be closer to 0 than before
     */
    public void noLimitClose(){
        frontServo.setPosition(frontServo.getPosition()-0.1);
        backServo.setPosition(backServo.getPosition()+0.1);
        closing = true;
    }

    /**
     * Setts the claw servos to their maximum, or "open" position
     */
    public void open(){
        frontServo.setPosition(frontMax);
        closing = false;
    }

    /**
     * Sets the claw servos to be closer to their open position
     * Must be called multiple times to have the desired effect
     */
    public void varyingOpen(){
        if(frontServo.getPosition()<frontMax){
            frontServo.setPosition(frontServo.getPosition()+0.1);
        }
        if(backServo.getPosition()>backMin){
            backServo.setPosition(backServo.getPosition()-0.1);
        }
        closing = false;
    }

    /**
     * Sets the claw servo positions to be closer to 1 than before
     */
    public void noLimitOpen(){
        frontServo.setPosition(frontServo.getPosition()+0.1);
        backServo.setPosition(backServo.getPosition()-0.1);
        closing = false;
    }

    /**
     * Returns the front servo position
     * @return the front servo position
     */
    public Double frontServoPosition(){
        return frontServo.getPosition();
    }

    /**
     * Returns the back servo position
     * @return the back servo position
     */
    public Double backServoPosition(){
        return backServo.getPosition();
    }

    /**
     * Sets the front claw servo direction
     * @param direction the front claw servo direction
     */
    public void setFrontDirection(Servo.Direction direction){
        frontServo.setDirection(direction);

    }

    /**
     * Gets the front claw servo direction
     * @return the front claw servo direction
     */
    public Servo.Direction getFrontDirection(){
        return frontServo.getDirection();
    }

    /**
     * Sets the back claw servo direction
     * @param direction the back claw servo direction
     */
    public void setBackDirection(Servo.Direction direction){
        backServo.setDirection(direction);
    }

    /**
     * Gets the back claw servo direction
     * @return the back claw servo direction
     */
    public Servo.Direction getBackDirection(){
        return backServo.getDirection();
    }

    /**
     * Sets the front claw servo maximum
     * @param frontMax the front claw servo maximum
     */
    public void setFrontMax(double frontMax){
        this.frontMax = frontMax;
    }

    /**
     * Gets the front claw servo maximum
     * @return the front claw servo maximum
     */
    public double getFrontMax(){
        return frontMax;
    }

    /**
     * Sets the front claw servo minimum
     * @param frontMin the front claw servo minimum
     */
    public void setFrontMin(double frontMin){
        this.frontMin = frontMin;
    }

    /**
     * Gets the front claw servo minimum
     * @return the front claw servo minimum
     */
    public double getFrontMin(){
        return frontMin;
    }

    /**
     * Sets the back claw servo maximum
     * @param backMax the back claw servo maximum
     */
    public void setBackMax(double backMax){
        this.backMax = backMax;
    }

    /**
     * Gets the back claw servo maximum
     * @return the back claw servo maximum
     */
    public double getBackMax(){
        return backMax;
    }

    /**
     * Sets the back claw servo minimum
     * @param backMin the back claw servo minimum
     */
    public void setBackMin(double backMin){
        this.backMin = backMin;
    }

    /**
     * Gets the back claw servo minimum
     * @return the back claw servo minimum
     */
    public double getBackMin(){
        return backMin;
    }

    public String toString(){
        String clawStats = "";
        clawStats += "Front Position: " + frontServo.getPosition();
        clawStats += "Front Direction: " + frontServo.getDirection();
        clawStats += "Back Position: " + backServo.getPosition();
        clawStats += "Back Direction: " +backServo.getDirection();
        return clawStats;

    }

}
