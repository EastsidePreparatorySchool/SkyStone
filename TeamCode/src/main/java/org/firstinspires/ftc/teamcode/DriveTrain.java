package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

import org.firstinspires.ftc.robotcore.external.Telemetry;

public class DriveTrain {
    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;
    double TPM;

    DriveTrain(DcMotor fL, DcMotor bL, DcMotor fR, DcMotor bR){
        frontLeftMotor = fL;
        backLeftMotor = bL;
        frontRightMotor = fR;
        backRightMotor = bR;

    }

    DriveTrain(DcMotor fL, DcMotor bL, DcMotor fR, DcMotor bR, Telemetry telemetry, double TPM){
        this(fL, bL, fR, bR);
        this.TPM = TPM;


    }

    DriveTrain(DriveTrain motors){
        this(motors.asArray());

    }



    DriveTrain(DcMotor[] motors){
        DcMotor[] a = asArray();

        for (int i = 0; i < motors.length; i++) {
            a[i] = motors[i];
        }

    }



    public void runMotors(MotorPowers motorPowers){
        DcMotor[] dt = asArray();
        double[] mp = motorPowers.asArray();

        for (int i = 0; i < mp.length; i++) {
            dt[i].setPower(mp[i]);

        }

    }

    public void runMotors(double[] motorPowers){
        DcMotor[] dt = asArray();
        for (int i = 0; i < motorPowers.length; i++) {
            dt[i].setPower(motorPowers[i]);

        }
    }

    public void runMotors(double fL, double bL, double fR, double bR){
        DcMotor[] dt = asArray();
        dt[0].setPower(fL);
        dt[1].setPower(bL);
        dt[2].setPower(fR);
        dt[3].setPower(bR);


    }

    public void runAllOneVal(double power){
        DcMotor[] dt = asArray();
        for(DcMotor d: dt){
            d.setPower(power);

        }

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
    public void toEncoderDist(MotorDistances mDists, MotorPowers motorPowers, double check, boolean slowApproach){

        // slow approach slows the motors as they approach target position

        if(!isWithoutEncoders()){runWithoutEncoders();}
        boolean done = false;
        double[] mEV = mDists.asArray();
        double[] mps = motorPowers.asArray();
        DcMotor[] motors = asArray();
        while(!done){
            done = true;
            for(int i = 0; i <mEV.length; i++){
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
                    motors[i].setPower(0.0);

                }

            }
        }


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
            //if power is negative, get positive version
            if(mps[i]<0){
                mps[i] = -mps[i];
            }
            // only want magnitude, not direction, we'll take care of that

            //then add your target position to your current position
            mEV[i]+=motors[i].getCurrentPosition();
            // if your target is still less than your current position
            // set the motor powers to be negative
            // in other words, whichever direction will get you to your target position
            // go there
            if(mEV[i]<motors[i].getCurrentPosition()) {
                mps[i] = -mps[i];
            }
            if(lop.isStopRequested()){break;}
        }


        while(!done && !lop.isStopRequested()){
            done = true;
            telemetry.update();
            if(lop.isStopRequested()){break;}

            for(int i = 0; i <mEV.length && !lop.isStopRequested(); i++){
                double diff = mEV[i]-motors[i].getCurrentPosition();
                if(diff<0){
                    diff = -diff;
                }
                telemetry.addData("motor " +i, diff);
                telemetry.addData("motor "+i+" mEV",mEV[i]);
                if(!motDone[i]) {
                    if (diff > slowPoint) {
                        motors[i].setPower(mps[i]);
                        done = false;

                    } else if (diff >check && diff<slowPoint) {

                        if (slowApproach) {
                            motors[i].setPower(mps[i] * (diff / (slowPoint)));
                        }else{
                            motors[i].setPower(mps[i]);

                        }
                        done = false;
                    } else if(diff<=check){
                        motors[i].setPower(0.0);
                        motDone[i] = true;

                    }
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
        String nP = "";
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

    public void stopAllMotors(){
        DcMotor[] motors = asArray();
        for(DcMotor m1: motors){
            m1.setPower(0);
        }


    }
}
