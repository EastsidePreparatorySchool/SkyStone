package org.eastsideprep.eps8103;

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

@Autonomous(name = "2UKSSA", group = "Concept")
public class skyStoneAuto extends LinearOpMode {

    Hardware8103 robot = new Hardware8103();

    public static final String TAG = "Vuforia Navigation Sample";

    OpenGLMatrix lastLocation = null;
    VuforiaLocalizer vuforia;

    public void driveForward() {
        robot.leftBackMotor.setPower(0.25);
        robot.leftFrontMotor.setPower(0.25);
        robot.rightFrontMotor.setPower(0.25);
        robot.rightBackMotor.setPower(0.25);
        //sleep(l * 48);
    }

    public void turnright() {
        robot.leftBackMotor.setPower(-0.25); //have some wheels turn different directions so it goes left
        robot.leftFrontMotor.setPower(0.25);
        robot.rightBackMotor.setPower(0.25);
        robot.rightFrontMotor.setPower(-0.25);
    }

    public void turnrightslowly() {
        robot.leftBackMotor.setPower(-0.05); //have low power so it goes very slow
        robot.leftFrontMotor.setPower(0.05);
        robot.rightFrontMotor.setPower(0.05);
        robot.rightBackMotor.setPower(-0.05);
    }

    public void turnleft() {
        robot.leftFrontMotor.setPower(0.25);
        robot.leftBackMotor.setPower(-0.25);
        robot.rightBackMotor.setPower(-0.25);
        robot.rightFrontMotor.setPower(0.25);
    }

    public void backwards() {
        robot.leftBackMotor.setPower(-0.25);
        robot.leftFrontMotor.setPower(-0.25);
        robot.rightFrontMotor.setPower(-0.25);
        robot.rightBackMotor.setPower(-0.25);
        //sleep(l * 58);
    }

    public void motorStop() {
        robot.rightFrontMotor.setPower(0);
        robot.rightBackMotor.setPower(0);
        robot.leftFrontMotor.setPower(0);
        robot.leftBackMotor.setPower(0);
    }

    public void stopmotors() {
        robot.rightBackMotor.setPower(0);
        robot.rightFrontMotor.setPower(0);
        robot.leftBackMotor.setPower(0);
        robot.leftFrontMotor.setPower(0);
    }

    public void strafeleftslowly() {
        robot.rightFrontMotor.setPower(0.05);
        robot.rightBackMotor.setPower(-0.05);
        robot.leftFrontMotor.setPower(-0.05);
        robot.leftBackMotor.setPower(0.05);
    }

    public void straferightslowly() {
        robot.rightFrontMotor.setPower(-0.05);
        robot.rightBackMotor.setPower(0.05);
        robot.leftFrontMotor.setPower(0.05);
        robot.leftBackMotor.setPower(-0.05);
        //sleep(l*12*2000);
    }

    public void strafeleft() {
        robot.rightFrontMotor.setPower(0.5);
        robot.rightBackMotor.setPower(-0.5);
        robot.leftFrontMotor.setPower(-0.5);
        robot.leftBackMotor.setPower(0.5);
    }

    public void straferight() {
        robot.rightFrontMotor.setPower(-0.5);
        robot.rightBackMotor.setPower(0.5);
        robot.leftFrontMotor.setPower(0.5);
        robot.leftBackMotor.setPower(-0.5);
    }

    public void lowerarm(int angle) {
        robot.armPivot.setPower(0.6);
        sleep(1000 * angle);
    }

    public void raisearm(int angle) {
        robot.armPivot.setPower(-1);
        sleep(1000 * angle);
    }

    public void extendarm(int l) {
        robot.armExtender.setPower(-1);
        sleep(1000 * l);
    }

    public void reelarm(int l) {
        robot.armExtender.setPower(1);
        sleep(1000 * l);
    }


    @Override
    public void runOpMode() {
        RobotLog.w(TAG, "runopmode");
        robot.init(hardwareMap); //load hardware from other program

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId); //load and show camera (doesn't have to show on set up but this makes it show)

        //allows me to use vuforia (personalized key)
        parameters.vuforiaLicenseKey = "AUCrvrn/////AAABmQoD3WABhU+vo48FduNAIPc5LL8I5mrwOnmGaxW3j5LvI0QwHYdUHkmQxDTBMj1Xa1vsENMj54bQzYvp8a9tgFQgSDddqRS1y7i+fztsPgjNQLPMt1SyCJu+yu9WifYIV248tUOsAUfhHnY7grLLU1pr7UCVdlVDMccSLKKXcp8bXJlmMfFy4mTIVC/1xfdrmqg8ypGX++WUHFkNaljXGHnuAK0UKZ4xfmtquYxz7nMjoiJzX+HAsoIuAia5sxez5mzqXQq4OCWm25xWaIbF5aEX3MTSDaWLZd/JuJh+QQyjT/0DgrytMHmSzm9s1pwJOYfhShBUTMy0Fmmf7PnCUb0wMsyJot/65BGlVEpMmV4r";
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK; //say where camera is
        vuforia = ClassFactory.getInstance().createVuforia(parameters); //get instance of class


        VuforiaTrackables targetsSkyStone = this.vuforia.loadTrackablesFromAsset("Skystone");

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
        RobotLog.ii(TAG, "phone=%s", format(phoneLocationOnRobot));


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
                driveForward();
                sleep(7000);

                straferightslowly();
                sleep(6000);

                strafeleft();
                sleep(2000);

                driveForward();
                sleep(5000);

                strafeleft();
                sleep(3000);

                backwards();
                sleep(7000);

                straferight();
                sleep(3000);
                //sleep(3500 );

                driveForward();
                sleep(4000);

                straferightslowly();
                sleep(1000);

                strafeleft();
                sleep(2000);

                driveForward();
                sleep(2000);

                strafeleft();
                sleep(4000);

                backwards();
                sleep(3000);

                //if(((VuforiaTrackableDefaultListener) stoneTarget.getListener()).isVisible()){
                  //  stopmotors();
                    //sleep(2000);
                //}

            }
        }


        telemetry.addData("Pos", "We see it!");
        if (lastLocation != null) {
            telemetry.addData("Pos", format(lastLocation)); //if there was a last location, add it
        } else {
            telemetry.addData("Pos", "Unknown");
        }
        telemetry.update();

        if (lastLocation == null) {
        }

    }

    String format(OpenGLMatrix transformationMatrix) {
        return transformationMatrix.formatAsTransform();
    }
}

