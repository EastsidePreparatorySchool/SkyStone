package org.firstinspires.ftc.teamcode.robots.motors;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

import org.firstinspires.ftc.robotcore.external.Telemetry;
import org.firstinspires.ftc.teamcode.BasicUtility;
import org.firstinspires.ftc.teamcode.robots.sensors.IMU;

//TODO: check around hte toEncoderVal Section
//TODO: split toencoderVal into: toencoderStrafe toEncoderDrive toEncoderStrafeSlow toEncoderDriveSlow
//TODO: Make the turn functions
public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    // 500 ticks 90 mm
    // 5.555 ticks per mm
    double TPM = 5.56;
    Boolean rightFlipped;
    BasicUtility bu = new BasicUtility();

    /**
     *
     * @param fL
     * @param bL
     * @param fR
     * @param bR
     * @param rightFlipped Is the right side flipped?
     */
    public DriveTrain(DcMotor fL, DcMotor bL, DcMotor fR, DcMotor bR, Boolean rightFlipped){
        frontLeftMotor = fL;
        backLeftMotor = bL;
        frontRightMotor = fR;
        backRightMotor = bR;
        this.rightFlipped = rightFlipped;
    }

    /**
     *
     * @param fL
     * @param bL
     * @param fR
     * @param bR
     * @param telemetry
     * @param TPM
     * @param rightFlipped
     */
    public DriveTrain(DcMotor fL, DcMotor bL, DcMotor fR, DcMotor bR, Telemetry telemetry, double TPM, Boolean rightFlipped){
        this(fL, bL, fR, bR, rightFlipped);
        this.TPM = TPM;
        this.rightFlipped = rightFlipped;

    }

    public DriveTrain(DriveTrain motors, Boolean rightFlipped){
        this(motors.asArray(), rightFlipped);
        this.rightFlipped = rightFlipped;
    }



    public DriveTrain(DcMotor[] motors, Boolean rightFlipped){
        DcMotor[] a = asArray();

        this.rightFlipped = rightFlipped;
        for (int i = 0; i < motors.length; i++) {
            a[i] = motors[i];
        }


    }



    public void runMotors(MotorPowers motorPowers){
        DcMotor[] dt = asArray();
        double[] mp = motorPowers.asArray();

        for (int i = 0; i < mp.length; i++) {
            if(rightFlipped) {
                if(i>1) {
                    dt[i].setPower(-mp[i]);
                }else{
                    dt[i].setPower((mp[i]));
                }
            }else{
                if(i<=1){
                    dt[i].setPower(-mp[i]);
                }else {
                    dt[i].setPower(mp[i]);
                }
            }

        }

    }

    public void runMotors(double[] motorPowers){
        DcMotor[] dt = asArray();
        for (int i = 0; i < motorPowers.length; i++) {
            if(rightFlipped) {
                if(i>1) {
                    dt[i].setPower(-motorPowers[i]);
                }else{
                    dt[i].setPower((motorPowers[i]));
                }
            }else{
                if(i<=1){
                    dt[i].setPower(-motorPowers[i]);
                }else {
                    dt[i].setPower(motorPowers[i]);
                }
            }

        }
    }

    public void runMotors(double fL, double bL, double fR, double bR){
        DcMotor[] dt = asArray();
        dt[0].setPower(fL);
        dt[1].setPower(bL);
        dt[2].setPower(fR);
        dt[3].setPower(bR);
        if(rightFlipped){
            dt[2].setPower(-fR);
            dt[3].setPower(-bR);
        }else{
            dt[0].setPower(-fL);
            dt[1].setPower(-bL);
        }



    }

    public void runAllOneVal(double power){
        runMotors(power,power,power,power);

    }

    // this zeroes the motor encoders to whatever psotion the motors are at.

    public void stopAndResetEncoders(){
        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[1].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[2].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        dt[3].setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

    }

    public void runWithEncoders(){
        //stopAndResetEncoders();

        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[1].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[2].setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        dt[3].setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void runWithoutEncoders(){
        DcMotor[] dt = asArray();
        dt[0].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[1].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[2].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        dt[3].setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    //in millimeters
    public void toEncoderDist(MotorDistances mDists, MotorPowers motorPowers, double check, boolean slowApproach, Telemetry telemetry, LinearOpMode lop){

        // slow approach slows the motors as they approach target position

        if(!isWithoutEncoders()){runWithoutEncoders();}
        boolean done = false;
        int dmotors = 0;
        double[] mEV = mDists.asArray();
        double[] mps = motorPowers.asArray();
        DcMotor[] motors = asArray();
        while(!done && dmotors<3 && !lop.isStopRequested()){
            if(lop.isStopRequested()){
                break;
            }
            done = true;
            dmotors= 0;
            for(int i = 0; i <mEV.length; i++){
                if(lop.isStopRequested()){
                    break;
                }
                double diff = Math.abs(mEV[i]-motors[i].getCurrentPosition()/TPM);

                if(diff>1){
                    motors[i].setPower(mps[i]);
                    done = false;

                }
                else if(diff>check){
                    if(slowApproach) {
                        motors[i].setPower(mps[i] * diff);
                    }
                    done = false;
                }else {
                    dmotors++;
                    motors[i].setPower(0.0);

                }

            }
        }


    }


    //TODO: split toencoderVal into: toencoderStrafe toEncoderDrive toEncoderStrafeSlow toEncoderDriveSlow
    //TODO: Make the turn functions

    /**
     * This turns the robot to an angle on the imu (-180, 180) it can't wrap or go over or under
     * @param speed the speed of the turn. If this is negative, the turn will be in reverse
     * @param angle the desired angle of the turn
     * @param margin the margin of error (in degrees)
     * @param imu IMU
     * @param telemetry telemetry
     * @param lop Linear Op Mode
     */
    public void turnToAngle(double speed, double angle, double margin, IMU imu, Telemetry telemetry,LinearOpMode lop){
        Double error = 404.0;

        while(bu.wrapConstraintCheck(imu.getAngleDegrees(), angle, margin, 180.0, -180.0, error)==error && !lop.isStopRequested()){
            MotorPowers mp = new MotorPowers(speed, speed, -speed, -speed);
            runMotors(mp);
            telemetry.addData("angle", imu.getAngleDegrees());
            telemetry.update();
        }

    }

    /**
     *This function turns the robot a given number of degrees (positive numbers only)
     * If the speed is negative, the robot turns left instead of right
     * @param speed the speed of the turn, if negative the robot turns left
     * @param degrees the number of degrees to turn (must be positive)
     * @param margin the margin of error (in degrees)
     * @param imu IMU
     * @param telemetry telemetry
     * @param lop Linear Op Mode
     */
    public void turnDegrees(double speed, double degrees, double margin, IMU imu, Telemetry telemetry, LinearOpMode lop){

    }

    // not bothering with encoder values?
    public void toEncoderVal(MotorDistances mEncoderVals, MotorPowers motorPowers, double check, double slowPoint, boolean slowApproach, Telemetry telemetry, LinearOpMode lop){

        // slow approach slows the motors as they approach target position
        if(!isWithoutEncoders()){runWithoutEncoders();}
        boolean done = false;
        double[] mEV = mEncoderVals.asArray();
        double[] mps = motorPowers.asArray();
        boolean[] motDone = new boolean[mEV.length];

        telemetry.addData("motor power 1", mps[0]);
        DcMotor[] motors = asArray();

        for(int i = 0; i<mEV.length && lop.opModeIsActive(); i++) {
            motDone[i] = false;

            //then add your target position to your current position
            mEV[i]+=motors[i].getCurrentPosition();

            if(lop.isStopRequested()){break;}
        }

        int donemotors = 0;
        runMotors(mps);
        while(!done && !lop.isStopRequested() &&donemotors<3){

            done = true;
            donemotors = 0;
            telemetry.update();
            if(lop.isStopRequested()){break;}

            for(int i = 0; i <mEV.length && !lop.isStopRequested(); i++){
                double diff = Math.abs(Math.abs(mEV[i])-Math.abs(motors[i].getCurrentPosition()));

                telemetry.addData("motor " +i, diff);
                telemetry.addData("motor "+i+" mEV",mEV[i]);
                if(!motDone[i]) {
                    if (diff >check && diff<slowPoint) {

                      /*  if (slowApproach) {
                            motors[i].setPower(mps[i] * (diff / (slowPoint)));
                        }else{
                            motors[i].setPower(mps[i]);

                        }
                        */

                        done = false;
                    } else if(diff<=check){
                        //motors[i].setPower(0.0);
                        motDone[i] = true;
                        donemotors++;

                    }else if (diff > slowPoint) {
                        //motors[i].setPower(mps[i]);
                        done = false;

                    }
                }else{
                    donemotors++;
                }
                if(lop.isStopRequested()){break;}

            }
            telemetry.addData("motor 2 pow", motors[2].getPower());


        }

    }



    public boolean isWithoutEncoders(){
        for (DcMotor dt:
                asArray()) {
            if(dt.getMode() != DcMotor.RunMode.RUN_WITHOUT_ENCODER){
                return false;


            }
        }
        return true;

    }

    public DcMotor[] asArray(){
        DcMotor[] motors = new DcMotor[4];

        motors[0] = frontLeftMotor;
        motors[1] = backLeftMotor;
        motors[2] = frontRightMotor;
        motors[3] = backRightMotor;
        return motors;
    }

    @Override
    public String toString(){
        String m = "";
        DcMotor[] motors = asArray();
        m+="fL Power: "+ motors[0].getPower();
        m+=" fL EncPos: "+motors[0].getCurrentPosition();
        m+=" bL Power: "+motors[1].getPower();
        m+=" bL EncPos: "+motors[1].getCurrentPosition();
        m+=" fR Power: "+motors[2].getPower();
        m+=" fR EncPos: "+motors[2].getCurrentPosition();
        m+=" bR Power: "+motors[3].getPower();
        m+=" bR EncPos: "+motors[3].getCurrentPosition();
        return m;

    }

    public String negativePositive(){
        String nP = (rightFlipped)?"The right side is flipped":"The left side is flipped";
        DcMotor[] motors = asArray();
        for (DcMotor m1: motors
             ) {
            if(m1.getPower() >0){
                nP+=" +,";

            }else if(m1.getPower() <0){
                nP+= " -,";
            }else{
                nP+=" 0.0";

            }

        }
        return nP;

    }

    public String encoders(){
        String enc = "";
        DcMotor[] motors = asArray();
        enc+=" fL EncPos: "+motors[0].getCurrentPosition();
        enc+=" bL EncPos: "+motors[1].getCurrentPosition();
        enc+=" fR EncPos: "+motors[2].getCurrentPosition();
        enc+=" bR EncPos: "+motors[3].getCurrentPosition();

        return enc;
    }

    public void stopAllMotors(){
        DcMotor[] motors = asArray();
        for(DcMotor m1: motors){
            m1.setPower(0);
        }


    }
}
