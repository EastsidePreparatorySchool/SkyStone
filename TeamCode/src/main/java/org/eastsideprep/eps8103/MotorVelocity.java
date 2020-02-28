package org.eastsideprep.eps8103;


/**
 * @author tespelien
 */
public class MotorVelocity {

    double leftfront;
    double rightfront;
    double rightback;
    double leftback;

    MotorVelocity(double lf, double rf, double rb, double lb) {
        this.leftfront =(double) Math.round(lf*1000) / 1000;
        this.rightfront = (double) Math.round(rf*1000) / 1000;
        this.rightback = (double) Math.round(rb*1000) / 1000;
        this.leftback = (double) Math.round(lb*1000) / 1000;
    }

    public String toString() {
        return "lf: " + leftfront + "; rf: " + rightfront + "; rb: " + rightback + "; lb: " + leftback;
    }

}
