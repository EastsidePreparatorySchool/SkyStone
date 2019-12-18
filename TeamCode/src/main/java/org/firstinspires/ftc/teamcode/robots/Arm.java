package org.firstinspires.ftc.teamcode.robots;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BasicUtility;

public class Arm {
    HardwareMap hardwareMap;
    Telemetry telemetry;
    public DcMotor pivotMotor;
    public DcMotor linkageMotor;
    public double linkageError = 20;
    public double pivotError = 20;
    public double pivotNormalStall = 0.3;
    public double pivotStall = 0.3;
    public double pivotBigStall = 0.5;
    public double pivotPosition;
    public double pivotStartPosition;
    public double linkageStartPosition;
    public double linkagePosition;
    public double linkageStall = 0.3;
    public Boolean pivotMoving;
    public Boolean pivotUp;
    public Boolean extending;
    public Boolean linkageMoving;
    BasicUtility basicUtility = new BasicUtility();

    // TODO: check the Arm Update for more info
    Arm(){}


    Arm(HardwareMap hardwareMap, Telemetry telemetry){
        this.hardwareMap = hardwareMap;
        this.telemetry = telemetry;

    }



    /**
     * Initializes the Arm
     */
    public void init(){
        pivotMotor = hardwareMap.dcMotor.get("pivotMotor");
        linkageMotor = hardwareMap.dcMotor.get("linkageMotor");
        pivotPosition = pivotMotor.getCurrentPosition();
        linkagePosition = linkageMotor.getCurrentPosition();
        pivotStartPosition = pivotPosition;
        linkageStartPosition = linkagePosition;
        pivotMoving = false;
        linkageMoving = false;
        telemetry.addData("Arm", "Initialized");
    }

    public DcMotor getPivotMotor(){
        return pivotMotor;
    }

    public DcMotor getLinkageMotor(){
        return linkageMotor;
    }
    /**
     * Updates the arm
     * must be constantly ran if the arm is not in the down position
     */
    public void update(){
        // Is the arm low enough to be considered "down?"
        if(Math.abs(pivotMotor.getCurrentPosition()-pivotStartPosition)<= pivotError){

            // check if the pivot is moving
            if(pivotMoving){
                if(!pivotUp) {

                    //if the pivot is moving down, stop all linkage movement
                    linkageMoving = false;
                    extending = false;
                    linkageMotor.setPower(0);
                }
            }else{
                 // if the pivot isn't moving, stop all linkage movement
                // and tell the pivot to drop to 0
                pivotMotor.setPower(0);
                linkageMoving = false;
                extending = false;
                linkageMotor.setPower(0);
            }

        }else{
            if(linkageMotor.getPower() == 0.0){
                linkageMoving = false;
            }
            // if the arm is higher than the "down" position
            // then check if the linkage is moving
            if(linkageMoving){

                if (extending){
                    // TODO AREA 1:
                    // FIND THE MAXIMUM OF THE LINKAGE
                    // ALSO FIND THE POINT WHEN THE LINKAGE IS PUTTING THE MOST STRAIN ON THE MOTORS
                    // use that knowledge to set up a time when the pivot is at the big stall
                    // versus at the normal stall
                    // for now, the pivot will use the big stall power at all times the linkage is up
                    pivotMotor.setPower(pivotBigStall);
                }else{
                    // if the linkage motor is in the start position
                    if(Math.abs(linkageStartPosition - linkageMotor.getCurrentPosition())< linkageError){
                        linkageMotor.setPower(linkageStall);
                        linkageMoving = false;
                    }else{
                        // if the linkage is outside of the start position
                    }
                }
            }else {
                // if the pivot isn't moving:
                if(!pivotMoving){
                    pivotMotor.setPower(pivotStall);
                }

                // if the linkage isn't moving
                // check if it's in the down position
                if(Math.abs(linkageStartPosition - linkageMotor.getCurrentPosition())< linkageError){
                    // if the linkage isn't moving and it's in the down position, set it's power to 0
                    linkageMotor.setPower(0);
                    linkageMoving = false;
                }else{
                    // if the linkage isn't in the down position
                    // just do corrections,
                    // meaning if the linkage is to far forward, move back, and vice-versa
                    if(linkagePosition - linkageMotor.getCurrentPosition()> linkageError){
                        linkageMotor.setPower(0.1);

                    }else if(linkagePosition - linkageMotor.getCurrentPosition() < linkageError){
                        linkageMotor.setPower(-0.1);
                    }
                }

            }

        }

    }

    /**
     * Pivots the arm given a certain power. After calling a movement function
     * the update function must be continuously called
     * @param power the power to put into the pivot motor
     */
    public void pivotArm(Double power){

        pivotMotor.setPower(basicUtility.constraintCheck(power, 1.0, 0.0));
        pivotPosition = pivotMotor.getCurrentPosition();

        if(pivotMotor.getPower() == 0.0){
            pivotMoving = false;

        }else{
            pivotMoving = true;
        }
        if(power < 0.0){
            pivotUp = false;

        }else if (power > 0.0){
            pivotUp = true;
        }
    }

    /**
     * Extends the linkage given a certain power. After calling a movement function
     * the update function must be continuously called
     * @param power the power to put into the linkage motor
     */
    public void extendLinkage(Double power){
        linkageMotor.setPower(basicUtility.constraintCheck(power, 1.0, 0.0));
        linkagePosition = linkageMotor.getCurrentPosition();
        // the motor can only extend with power above 0.0
        if(linkageMotor.getPower() > 0.0) {
            extending = true;
            linkageMoving = true;
        }else{
            linkageMoving = false;
            extending = false;
        }
    }

    /**
     * Retracts the linkage given a certain power
     * @param power
     */
    public void retractLinkage(Double power){
        linkageMotor.setPower(basicUtility.constraintCheck(power, 0.0, -1.0));
        linkagePosition = linkageMotor.getCurrentPosition();
        if(linkageMotor.getPower() == 0.0){
            linkageMoving = false;
            extending = false;
        }else {
            linkageMoving = true;
            extending = false;
        }

    }

    @Override
    public String toString(){
        String armString = "";
        armString += "Pivot Motor Pos: "+pivotMotor.getCurrentPosition();
        armString += "\nPivot Desired Pos: " + pivotPosition;
        armString += "\nPivot Motor Power: " + pivotMotor.getPower();
        armString += "\nPivot Moving: " + pivotMoving;
        armString += "\nLinkage Motor Pos: "+ linkageMotor.getCurrentPosition();
        armString += "\nLinkage Desired Pos: " +linkagePosition;
        armString += "\nLinkage Motor Power: " + linkageMotor.getPower();
        armString += "\nLinkage Moving: " + linkageMoving;
        return armString;

    }






}
