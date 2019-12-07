package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.HardwareMap;

@TeleOp(name = "IMUTest", group = "Tests")

public class IMUTest  extends OpMode {
    IMU imu;
    int check;
    @Override
    public void init() {
        imu = new IMU(hardwareMap.get(BNO055IMU.class,"imu"));
        telemetry.addData("imu is",imu);

        imu.initialize();
        check = 0;
        telemetry.addData("Status", "Initialized");

    }

    @Override
    public void loop() {
        telemetry.addData("calib status", imu.getCalibStatus().toString());
        telemetry.addData("angle","%6.2f", (imu.getAngle()/6.2)*360);
        check++;
        if(check >= 50){
            check= 0;

        }
        telemetry.addData("shouldChange", check);
        telemetry.update();

    }
}
