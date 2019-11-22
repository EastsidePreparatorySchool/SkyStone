package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;

public class StrafeTest extends OpMode {
    Hardware8103 robot;

    @Override
    public void init() {
        robot = new Hardware8103();
        robot.init(hardwareMap);

        for (DcMotor m : robot.allMotors) {
            m.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

    @Override
    public void loop() {
        robot.leftBackMotor.setPower(0.2);
        robot.rightFrontMotor.setPower(0.2);
        robot.leftFrontMotor.setPower(0.2);
        robot.rightBackMotor.setPower(0.2);
    }
}
