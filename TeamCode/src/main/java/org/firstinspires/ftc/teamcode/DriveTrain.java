package org.firstinspires.ftc.teamcode;


import com.qualcomm.robotcore.hardware.DcMotor;

public class DriveTrain {

    DcMotor frontLeftMotor;
    DcMotor backLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backRightMotor;

    DriveTrain(DcMotor fL, DcMotor bL, DcMotor fR, DcMotor bR){
        frontLeftMotor = fL;
        backLeftMotor = bL;
        frontRightMotor = fR;
        backRightMotor = bR;

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

    public DcMotor[] asArray(){
        DcMotor[] motors = new DcMotor[4];

        motors[0] = frontLeftMotor;
        motors[1] = backLeftMotor;
        motors[2] = frontRightMotor;
        motors[3] = backRightMotor;
        return motors;
    }
}
