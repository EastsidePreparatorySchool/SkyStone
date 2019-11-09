package org.firstinspires.ftc.teamcode;

public class MotorDistances {
    public double fL;
    public double bL;
    public double fR;
    public double bR;

    MotorDistances(double all){
        this.fL = all;
        this.bL = all;
        this.fR = all;
        this.bR = all;

    }

    MotorDistances(double frontLeft, double backLeft, double frontRight, double backRight){
        this.fL = frontLeft;
        this.bL = backLeft;
        this.fR = frontRight;
        this.bR = backRight;

    }

    MotorDistances(double[] motors){
        fL = motors[0];
        bL = motors[1];
        fR = motors[2];
        bR = motors[3];

    }

    public void set(double fL, double bL, double fR, double bR){
        this.fL = fL;
        this.bL = bL;
        this.fR = fR;
        this.bR = bR;

    }

    public void setAll(double all){
        this.fL = all;
        this.bL = all;
        this.fR = all;
        this.bR = all;

    }

    public double[] asArray(){
        double[] motors = new double[4];
        motors[0] = fL;
        motors[1] = bL;
        motors[2] = fR;
        motors[3] = bR;
        return motors;
    }
    @Override
    public String toString(){
        String m = "";
        for (double a:
                asArray()) {
            m+= " "+a;
        }
        return m;

    }
}
