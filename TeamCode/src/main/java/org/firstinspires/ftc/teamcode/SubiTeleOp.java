package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

@TeleOp(name = "SubiTeleOp", group = "TeleOps")

public class SubiTeleOp extends OpMode {

    TeleopRobot robot;

    MotorPowers motorPowers;

    // a minimum joystick value before movement
    double threshold = 0.1;
    // a modifier to all movement
    int moves = 0;

    double speed;
    double sprint = 1;
    double normal = 0.5;
    double slow = 0.25;

    double g1LeftAnalogX;
    double g1LeftAnalogY;
    double g1RightAnalogX;
    double g1RightAnalogY;
    double fLM;
    double bLM;
    double fRM;
    double bRM;
    double turn;

    int mode;
    boolean clawOpen;
    boolean clawClose;



    double linkageMove;

    @Override
    public void init() {
        robot = new TeleopRobot(hardwareMap);
        robot.init();
        motorPowers = new MotorPowers(0,0,0,0);
        telemetry.addData("Status", "Initialized");


    }



    @Override
    public void loop() {
        getInput();
        goMotor();
        robot.setMotors(new MotorPowers(fLM, bLM, fRM, bRM));
        if(clawOpen){
            robot.ungrab();
        }else{
            robot.grab();
        }
        if(linkageMove != 0.0){
            robot.moveArm(linkageMove);
        }

    }

    public void getInput(){
        // checks threshold here as well
        g1RightAnalogX = thresholdCheck(this.gamepad1.right_stick_x);
        g1RightAnalogY = thresholdCheck(this.gamepad1.right_stick_y);
        g1LeftAnalogX = thresholdCheck(this.gamepad1.left_stick_x);
        g1LeftAnalogY = thresholdCheck(this.gamepad1.left_stick_y);


        if(this.gamepad1.left_bumper){
            if(clawOpen){
                clawOpen = false;
            }else{
                clawOpen = true;

            }
        }

        // switch between normal, sprint, and precise slow speeds
        if(this.gamepad1.right_bumper){
            switch (moves){
                // normal speed --> sprint
                case 0:{
                    speed = sprint;
                    moves++;
                }case 1:{ // sprint --> slow
                    speed = slow;
                    moves++;

                }case 2:{
                    //slow --> normal
                    speed = normal;
                    moves=0;
                }

            }

        }

        linkageMove = thresholdCheck(this.gamepad1.left_trigger - this.gamepad1.right_trigger);



        if(this.gamepad1.dpad_up){
            // turn while moving
            mode = 0;

        } else if(this.gamepad1.dpad_right){
            // turn in place
            mode = 1;

        }else if(this.gamepad1.dpad_down){
            //strafe
            mode = 2;

        }else if(this.gamepad1.dpad_left){
            //  total control
            mode = 3;

        }

    }

    public void goMotor(){

        linkageMove *=speed;


        switch (mode){

            case 0:{
                turnWhileMoving();
            }case 1:{
                turnInPlace();
            }case 2:{
                strafe();
            }case 3:{
                totalControl();
            }

        }




    }

    // is the motor bigger than the threshold?

    public double thresholdCheck(double motor){
        if( -threshold<motor && motor<threshold){

            motor = 0.0;

        }

        return motor;
    }

    public double getTurn(){

        return g1RightAnalogX;

    }
    //motor actions below

    public void forwardBack(){
        fRM = -g1LeftAnalogY*speed;
        fLM = -g1LeftAnalogY*speed;
        bLM = -g1LeftAnalogY*speed;
        bRM = -g1LeftAnalogY*speed;

    }

    public void turnInPlace(){
        turn = getTurn();
        fLM = turn*speed;
        bLM = turn*speed;
        fRM = -turn*speed;
        bRM = -turn*speed;



    }

    public void turnWhileMoving() {

        fLM = (-g1LeftAnalogY + turn)*speed;
        bLM = (-g1LeftAnalogY + turn)*speed;
        fRM = (-g1LeftAnalogY + -turn)*speed;
        bRM = (-g1LeftAnalogY + -turn)*speed;



    }

    public void strafe(){

        fLM = g1LeftAnalogX*speed;
        bLM = -g1LeftAnalogX*speed;
        fRM = -g1LeftAnalogY*speed;
        bRM = g1LeftAnalogX*speed;

    }

    public void forwardStrafe(){

        fLM = (-g1LeftAnalogY + g1LeftAnalogX)*speed;
        bLM = (-g1LeftAnalogY + -g1LeftAnalogX)*speed;
        fRM = (-g1LeftAnalogY + -g1LeftAnalogX)*speed;
        bRM = (-g1LeftAnalogY + g1RightAnalogX)*speed;




    }

    public void totalControl(){

        fLM = (-g1LeftAnalogY + turn + g1LeftAnalogX)*speed;
        bLM = (-g1LeftAnalogY + turn + -g1LeftAnalogX)*speed;
        fRM = (-g1LeftAnalogY + -turn + -g1LeftAnalogX)*speed;
        bRM = (-g1LeftAnalogY + -turn + g1LeftAnalogX)*speed;

    }
}
