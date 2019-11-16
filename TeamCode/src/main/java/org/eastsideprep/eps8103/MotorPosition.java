/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.eastsideprep.eps8103;

/**
 * @author tespelien
 */
public class MotorPosition {

    int leftfront;
    int rightfront;
    int rightback;
    int leftback;

    MotorPosition(double lf, double rf, double rb, double lb) {
        this.leftfront = (int) lf;
        this.rightfront = (int) rf;
        this.rightback = (int) rb;
        this.leftback = (int) lb;
    }

    public String toString() {
        return "lf: " + leftfront + "; rf: " + rightfront + "; rb: " + rightback + "; lb: " + leftback;
    }

}
