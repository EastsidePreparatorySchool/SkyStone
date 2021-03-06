package org.eastsideprep.eps8103;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

import android.graphics.Color;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.RobotLog;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.RobotLog;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.ArrayList;
import java.util.List;

import com.qualcomm.robotcore.hardware.ColorSensor;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import java.util.*;

import java.util.Arrays;

import java.util.Arrays;

@Autonomous(name = "Vuforia Encoders", group = "Concept")
public class VuforiaEncoders extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();


    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    //these have to be found experimentally aka a lot of testing
    int fast_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.24); //2 because the motors are geared up 2:1 to the wheels
    int strafe_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.5);//last constants need tweaking

    int slow_strafe_c = 1;
    int turn_c = (int) (2 * robot.TICKS_PER_REV / robot.WHEEL_CIRC * 0.061);
    int ext_c = 1;

    int angle_c = 360 / robot.TICKS_PER_REV;

    double[] drivetrainEncoders = new double[4];

    ColorSensor color_sensor;

    public void forwards(int l, double speed) {
        for (DcMotor m : robot.allMotors) {
            m.setTargetPosition(m.getCurrentPosition() + (int) fast_c * l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(speed);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(speed);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void backwards(int l) {
        for (DcMotor m : robot.allMotors) {
            m.setTargetPosition(m.getCurrentPosition() - (int) fast_c * l);
            m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            m.setPower(0.4);
        }
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.4);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void turnright(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + (int) turn_c * angle);

    }

    public void turnleft(int angle) {
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - (int) turn_c * angle);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + (int) turn_c * angle);
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - (int) turn_c * angle);

        //for (DcMotor m : robot.allMotors) {
        //  m.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        //m.setPower(0.25);
        //}
        //once all motors are going i can start turning them off
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.25);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void stopmotors() {
        for (DcMotor m : robot.allMotors) {
            m.setPower(0);
        }
    }

    public void strafeleftslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + slow_strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.1);
        }
    }

    public void straferightslowly(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - slow_strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + slow_strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - slow_strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.1);
        }
    }

    public void strafeleft(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() + strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() - strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() - strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() + strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.25);
        }
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.25);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void straferight(int l) {
        robot.rightFrontMotor.setTargetPosition(robot.rightFrontMotor.getCurrentPosition() - strafe_c * l);
        robot.rightBackMotor.setTargetPosition(robot.rightBackMotor.getCurrentPosition() + strafe_c * l);
        robot.leftFrontMotor.setTargetPosition(robot.leftFrontMotor.getCurrentPosition() + strafe_c * l);
        robot.leftBackMotor.setTargetPosition(robot.leftBackMotor.getCurrentPosition() - strafe_c * l);
        for (DcMotor m : robot.allMotors) {
            m.setPower(0.25);
        }
        for (DcMotor m : robot.allMotors) {
            while (m.isBusy()) {
                m.setPower(0.25);
            }
            if (!m.isBusy()) {
                m.setPower(0);
            }
        }

        for (int i = 0; i < 4; i++) {
            drivetrainEncoders[i] = robot.allMotors[i].getCurrentPosition();
        }
    }

    public void print_encoders() {
        telemetry.addData("wheel encoders", Arrays.toString(drivetrainEncoders));
        telemetry.update();
    }


    @Override
    public void runOpMode() {

        float hsvValues[] = {0F, 0F, 0F};
        final float values[] = hsvValues;

        robot.init(hardwareMap); //load hardware from other program

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();


        RobotLog.w(TAG, "runopmode");
        robot.init(hardwareMap); //load hardware from other program


        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId); //load and show camera (doesn't have to show on set up but this makes it show)

        //allows me to use vuforia (personalized key)
        parameters.vuforiaLicenseKey = "AUCrvrn/////AAABmQoD3WABhU+vo48FduNAIPc5LL8I5mrwOnmGaxW3j5LvI0QwHYdUHkmQxDTBMj1Xa1vsENMj54bQzYvp8a9tgFQgSDddqRS1y7i+fztsPgjNQLPMt1SyCJu+yu9WifYIV248tUOsAUfhHnY7grLLU1pr7UCVdlVDMccSLKKXcp8bXJlmMfFy4mTIVC/1xfdrmqg8ypGX++WUHFkNaljXGHnuAK0UKZ4xfmtquYxz7nMjoiJzX+HAsoIuAia5sxez5mzqXQq4OCWm25xWaIbF5aEX3MTSDaWLZd/JuJh+QQyjT/0DgrytMHmSzm9s1pwJOYfhShBUTMy0Fmmf7PnCUb0wMsyJot/65BGlVEpMmV4r";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //say where camera is
        vuforia = ClassFactory.getInstance().createVuforia(parameters); //get instance of class


        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

        targetsSkyStone.activate();


        VuforiaTrackable stoneTarget = targetsSkyStone.get(0);
        stoneTarget.setName("Stone Target");
        VuforiaTrackable blueRearBridge = targetsSkyStone.get(1);
        blueRearBridge.setName("Blue Rear Bridge");
        VuforiaTrackable redRearBridge = targetsSkyStone.get(2);
        redRearBridge.setName("Red Rear Bridge");
        VuforiaTrackable redFrontBridge = targetsSkyStone.get(3);
        redFrontBridge.setName("Red Front Bridge");
        VuforiaTrackable blueFrontBridge = targetsSkyStone.get(4);
        blueFrontBridge.setName("Blue Front Bridge");
        VuforiaTrackable red1 = targetsSkyStone.get(5);
        red1.setName("Red Perimeter 1");
        VuforiaTrackable red2 = targetsSkyStone.get(6);
        red2.setName("Red Perimeter 2");
        VuforiaTrackable front1 = targetsSkyStone.get(7);
        front1.setName("Front Perimeter 1");
        VuforiaTrackable front2 = targetsSkyStone.get(8);
        front2.setName("Front Perimeter 2");
        VuforiaTrackable blue1 = targetsSkyStone.get(9);
        blue1.setName("Blue Perimeter 1");
        VuforiaTrackable blue2 = targetsSkyStone.get(10);
        blue2.setName("Blue Perimeter 2");
        VuforiaTrackable rear1 = targetsSkyStone.get(11);
        rear1.setName("Rear Perimeter 1");
        VuforiaTrackable rear2 = targetsSkyStone.get(12);
        rear2.setName("Rear Perimeter 2");


        List<VuforiaTrackable> allTrackables = new ArrayList<VuforiaTrackable>();
        allTrackables.addAll(targetsSkyStone);

        for (VuforiaTrackable trackable : allTrackables) { //for each VuMark in array
            telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible"); //if it is visible mark it visible in telemetry
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) { //change location as robot moves
                lastLocation = robotLocationTransform;
            }
        }

        float mmPerInch = 25.4f; //allow it to convert
        float mmBotWidth = 17 * mmPerInch;        //size of robot //change size of robot
        float mmFTCFieldWidth = (46 * 46 - 2) * mmPerInch;   // size of FTC field (glass panels)
        float mmTargetHeight = (6) * mmPerInch;

        float bridgeZ = 6.42f * mmPerInch;
        float bridgeY = 23 * mmPerInch;
        float bridgeX = 5.18f * mmPerInch;
        float bridgeRotY = 59;                                 // Units are degrees
        float bridgeRotZ = 180;
        float stoneZ = 2.00f * mmPerInch;
        float halfField = 72 * mmPerInch;
        float quadField = 36 * mmPerInch;

        boolean targetVisible = false;
        float phoneXRotate = 90;
        float phoneYRotate = 0;
        float phoneZRotate = 0;


        OpenGLMatrix redTargetLocationOnField = OpenGLMatrix
                .translation(-mmFTCFieldWidth / 2, 0, 0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XZX, AngleUnit.DEGREES, 90, 90, 0));

        OpenGLMatrix phoneLocationOnRobot = OpenGLMatrix.translation(mmBotWidth / 2, 0, 0).multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.YZY, AngleUnit.DEGREES, -90, 0, 0));
        RobotLog.ii(TAG, "phone=%s", (phoneLocationOnRobot));


        stoneTarget.setLocation(OpenGLMatrix
                .translation(0, 0, stoneZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, -90)));

        //Set the position of the bridge support targets with relation to origin (center of field)
        blueFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 0, bridgeRotY, bridgeRotZ)));

        blueRearBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 0, -bridgeRotY, bridgeRotZ)));

        redFrontBridge.setLocation(OpenGLMatrix
                .translation(-bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 0, -bridgeRotY, 0)));

        redRearBridge.setLocation(OpenGLMatrix
                .translation(bridgeX, -bridgeY, bridgeZ)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 0, bridgeRotY, 0)));

        //Set the position of the perimeter targets with relation to origin (center of field)
        red1.setLocation(OpenGLMatrix
                .translation(quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 180)));

        red2.setLocation(OpenGLMatrix
                .translation(-quadField, -halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 180)));

        front1.setLocation(OpenGLMatrix
                .translation(-halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 90)));

        front2.setLocation(OpenGLMatrix
                .translation(-halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 90)));

        blue1.setLocation(OpenGLMatrix
                .translation(-quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 0)));

        blue2.setLocation(OpenGLMatrix
                .translation(quadField, halfField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, 0)));

        rear1.setLocation(OpenGLMatrix
                .translation(halfField, quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, -90)));

        rear2.setLocation(OpenGLMatrix
                .translation(halfField, -quadField, mmTargetHeight)
                .multiplied(Orientation.getRotationMatrix(AxesReference.EXTRINSIC, AxesOrder.XYX, AngleUnit.DEGREES, 90, 0, -90)));


        ((VuforiaTrackableDefaultListener) stoneTarget.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //listen for each type //change
        ((VuforiaTrackableDefaultListener) blueFrontBridge.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) blueRearBridge.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) redFrontBridge.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) blueRearBridge.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) red1.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) red2.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) front1.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) front2.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) blue1.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) blue2.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) rear1.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change
        ((VuforiaTrackableDefaultListener) rear2.getListener()).setPhoneInformation(phoneLocationOnRobot, parameters.cameraDirection); //change


        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update(); //add stuff to telemetry
        waitForStart();

        targetsSkyStone.activate();

        for (VuforiaTrackable trackable : allTrackables) { //for each VuMark in array
            telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible"); //if it is visible mark it visible in telemetry
            OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
            if (robotLocationTransform != null) { //change location as robot moves
                lastLocation = robotLocationTransform;
            }
        }

        while (opModeIsActive()) {


            for (VuforiaTrackable trackable : allTrackables) {
                telemetry.addData(trackable.getName(), ((VuforiaTrackableDefaultListener) trackable.getListener()).isVisible() ? "Visible" : "Not Visible");
                OpenGLMatrix robotLocationTransform = ((VuforiaTrackableDefaultListener) trackable.getListener()).getUpdatedRobotLocation();
                if (robotLocationTransform != null) {
                    lastLocation = robotLocationTransform;
                }
            }
            if (((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()) {
                stopmotors();
                sleep(2000);
            }

            if (!((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()) {
                forwards(18, 0.6);
                straferight(18);
                strafeleft(24);
                forwards(6, 0.2);
                backwards(6);
                straferight(18);
                backwards(2);
                straferight(18);
                strafeleft(24);
                forwards(6, 0.2);
                backwards(6);

            }


            //Color.RGBToHSV(color_sensor.red()*8, color_sensor.green() *8, color_sensor.blue() * 8, hsvValues);
            //telemetry.addData("red", color_sensor.red());
            //telemetry.addData("Green", color_sensor.green());
            //telemetry.addData("Blue", color_sensor.blue());

            //telemetry.addData("color sensor", new float[] {color_sensor.red(), color_sensor.green(), color_sensor.blue()});
            //color_sensor.enableLed(true);

            telemetry.addData("log", "starting");
            //remember to start in the "legal" position


            /*

            forwards(30, 0.1);//check using camera!!
            if (true) {
                turnleft(90);
                extendarm(8);
                robot.updown.setPosition(1);
                robot.closer.setPosition(0.5);
                reelarm(8);
            }//pickup block

            turnleft(90);
            forwards(80, 0.6);
            turnleft(90);
            backwards(24);


             */
            print_encoders();

            telemetry.addData("Pos", "We see it!");
            if (lastLocation != null) {
                telemetry.addData("Pos", (lastLocation)); //if there was a last location, add it
            } else {
                telemetry.addData("Pos", "Unknown");
            }
            telemetry.update();

            if (lastLocation == null) {
            }

        }

        //String format(OpenGLMatrix transformationMatrix) {
        //return transformationMatrix.formatAsTransform();
    }

}






