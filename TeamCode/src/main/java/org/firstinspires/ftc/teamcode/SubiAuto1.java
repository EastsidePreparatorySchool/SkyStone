package org.firstinspires.ftc.teamcode;
package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;

import org.firstinspires.ftc.robotcore.external.matrices.MatrixF;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.matrices.VectorF;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.MotionDetection;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import javax.crypto.spec.OAEPParameterSpec;

@Autonomous(name = "SubiAuto1", group = "Tests")
public class SubiAuto1  extends OpMode{

    VuforiaLocalizer vuforia;
    VuforiaTrackables skystoneTrackables;
    VuforiaTrackable skystoneTrack;
    VuforiaTrackable FrontPerimeterTgt1;
    VuforiaTrackable FrontPerimeterTgt2;
    VuforiaTrackable RearPerimeterTgt1;
    VuforiaTrackable RearPerimeterTgt2;
    VuforiaTrackable BluePerimeterTgt1;
    VuforiaTrackable BluePerimeterTgt2;
    VuforiaTrackable RedPerimeterTgt1;
    VuforiaTrackable RedPerimeterTgt2;
    VuforiaTrackableDefaultListener listener;
    VuforiaTrackableDefaultListener[] listeners;
    DcMotor frontLeftMotor;
    DcMotor frontRightMotor;
    DcMotor backLeftMotor;
    DcMotor backRightMotor;
    DcMotor pivotMotor;
    DcMotor armMotor;
    MotorPowers robotMotors;
    Servo clawServo;
    CameraManager cm;

    boolean clawOpen;
    boolean clawClose;

    // the index for the mode
    int mode = 0;

    float linkageMove;


    double clawPower = 1.0;

    double fLM = 0.0;
    double bLM = 0.0;
    double fRM = 0.0;
    double bRM = 0.0;
    double turn = 0.0;

    // this may need to be changed, depending on what we want the default for this servo to be
    double clawInit = 0.0;
    double clawGrab = 0.5;

    // a minimum joystick value before movement
    double threshold = 0.1;
    // a modifier to all movement
    double speed = 1;

    double g1LeftAnalogX;
    double desiredY;
    double g1RightAnalogX;
    double g1RightAnalogY;

    float tolerance = 1;

    // height of the trackables
    float tHeight;

    // trackable distances along walls
    float len1;
    float len2;
    float len3;

    MatrixF moveVector;

    OpenGLMatrix webcamLocation;
    OpenGLMatrix lastLocation;
    OpenGLMatrix targetLocation;
    @Override
    public void init() {




        // now for motor and other grabbing
        frontLeftMotor =hardwareMap.dcMotor.get("FrontLeftMotor");
        frontRightMotor =hardwareMap.dcMotor.get("FrontRightMotor");
        backLeftMotor =hardwareMap.dcMotor.get("BackLeftMotor");
        backRightMotor =hardwareMap.dcMotor.get("BackRightMotor");

        pivotMotor = hardwareMap.dcMotor.get("PivotMotor");
        armMotor = hardwareMap.dcMotor.get("ArmMotor");

        clawServo = hardwareMap.servo.get("ClawServo");

        clawServo.setPosition(clawInit);
        // NOTE: we may need a sleep() here if the servo takes too much time to move.


        setVuforia();

        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void start() {
        //last known location of the robot
        lastLocation = newMatrix(len1,0,0,0,0,0);
        // activate tracking of all of them
        skystoneTrackables.activate();
        // based on our own self-estimations, we may be able to estimate our positions
        // by the positions of the skystones
        // because they are always in one of three places.


        while(!inRange()){

        }
        lineUp()


    }

    public void targetToWheels(OpenGLMatrix targetLocation, mode){

        // to find angle between the
        VectorF est = lastLocation.toVector();


        VectorF tgl = targetLocation.toVector();
        tgl.put(2, 0);
        VectorF tglDire = tgl.normalized3D();
        VectorF heading = est.subtracted(tgl);
        //gets rid of the z axis (up and down)
        heading.put(2,0);
        float[] headingF = heading.getData();
        float distance = heading.magnitude();
        VectorF direction = new VectorF(headingF[0]/distance,headingF[1]/distance,headingF[2]/distance);
        // angle need to be turned to face the object
        double angleA = Math.cos((est.dotProduct(heading)));



        switch (mode){

            case 0:

                totalControl(();
            case 1:
                if(moveVector.)
                turnInPlace((float)angleA);
            case 2:
                strafe();
            case 3:
                turnWhileMoving();
        }



    }


    public void findRobot(){

        //if Vuforia can't see any targets, this will be null
        OpenGLMatrix trackedLocation = null;

        int li = 0;
        while(trackedLocation == null && li <listeners.length){
            trackedLocation = listeners[li].getUpdatedRobotLocation();
            if(trackedLocation != null){
                telemetry.addData("This target is: ", skystoneTrackables.get(li+5).getName());

            }
            li++;
        }

        //trackedLocation = listener.getUpdatedRobotLocation();
        if(trackedLocation != null){
            lastLocation = trackedLocation;
            telemetry.addData("Location: ", lastLocation);

        }

    }

    public boolean inRange(){
        findRobot();





        // this part of the code will have to change to account for variance
        // ie, if the robot moves faster or slower than expected
        // or the camera's off, the code will need to assume the robot is close enough.
        // so the constrictions of hte location of hte robot will change

        for(int i = 12; 11<i && i<15; i++){
            // this may need to be changed, as a difference in angles and a difference in spacing
            // result in two different things
            if(Math.abs(estPlacing[i]-wishPlacing[i]) > tolerance){
                VectorF a  = new VectorF(0, 3, 0, 5);
                a
                return false;

            }

        }




        return true;

    }



    @Override
    public void (){





        //if Vuforia can't see any targets, this will be null
        OpenGLMatrix trackedLocation = null;

        int li = 0;
        while(trackedLocation == null && li <listeners.length){
            trackedLocation = listeners[li].getUpdatedRobotLocation();
            if(trackedLocation != null){
                telemetry.addData("This target is: ", skystoneTrackables.get(li+5).getName());

            }
            li++;
        }

        //trackedLocation = listener.getUpdatedRobotLocation();
        if(trackedLocation != null){
            lastLocation = trackedLocation;
            telemetry.addData("Location: ", lastLocation);

        }



        moveRobot();

        fLM = defaultMotor(fLM);
        bLM = defaultMotor(bLM);
        fRM = defaultMotor(fRM);
        bRM = defaultMotor(bRM);

        setMotors();
    }

    public void setMotors(){
        backRightMotor.setPower(robotMotors.bR);
        frontRightMotor.setPower(robotMotors.fR);
        backLeftMotor.setPower(robotMotors.bL);
        frontLeftMotor.setPower(robotMotors.fL);

    }

    public void moveRobot(){

        switch (mode){

            case 0:
                totalControl();
            case 1:
                turnInPlace();
            case 2:
                strafe();
            case 3:
                turnWhileMoving();
        }
        if(clawOpen){
            ungrab();
        }else if(clawClose){
            grab();
        }

    }



    // normalizes motor
    // if motor is lower than threshold itll also set it to 0
    // if motor magnitude greater than 1, set to 1
    public double defaultMotor(double motor){

        if(motor < -1.0){
            motor = -1.0;

        }else if( motor> 1.0){

            motor = 1.0;
        } else{

            motor = thresholdCheck(motor);
        }

        return motor;

    }

    public double thresholdCheck(double motor){
        if( -threshold<motor && motor<threshold){

            motor = 0.0;

        }

        return motor;
    }
    //motor actions below

    public void forwardBack(){

        fRM = -desiredY*speed;
        fLM = -desiredY*speed;
        bLM = -desiredY*speed;
        bRM = -desiredY*speed;

    }

    public void turnInPlace(float LR){
        turn = LR;
        fLM = turn*speed;
        bLM = turn*speed;
        fRM = -turn*speed;
        bRM = -turn*speed;



    }

    public void turnWhileMoving(float LR, float dY) {
        turn = LR;
        desiredY = dY;
        fLM = (-desiredY + turn)*speed;
        bLM = (-desiredY + turn)*speed;
        fRM = (-desiredY + -turn)*speed;
        bRM = (-desiredY + -turn)*speed;



    }

    public void strafe(float FB){

        fLM = FB*speed;
        bLM = -FB*speed;
        fRM = -FB*speed;
        bRM = FB*speed;

    }

    public void parallelStrafe(float LR, B){


        fLM = FB*speed


    }

    public void totalControl(){

        fLM = (-desiredY + turn + g1LeftAnalogX)*speed;
        bLM = (-desiredY + turn + -g1LeftAnalogX)*speed;
        fRM = (-desiredY + -turn + -g1LeftAnalogX)*speed;
        bRM = (-desiredY + -turn + g1RightAnalogX)*speed;

    }

    public void grab(){
        clawServo.setPosition(clawGrab);

    }

    public void ungrab()
    {

        clawServo.setPosition(clawInit);
    }

    public void setVuforia(){
        // which camera view
        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        //cameraMonitorViewId = cm.getAllWebcams().get;
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        //insert license key
        parameters.vuforiaLicenseKey = "ARZMVCT/////AAABmVrU7HXaB0Ttr7F4BZ/Zhvpsgc91a/fNIUVoSeONo+WWNwiks0D/Abi/xiVe9cb9k/PefMs00HaGNPwxM8Wy/rN4r3iYgDngp5zMBB3hNtNkME/sRcsYFe4L48mk9qOG5hqif8kIniuGpzK8U6JYAWPz1aQ7vuDG2/MPs1i4UiaIGMd+przcqqQU9dIkYwJL7mgA7OJdetsD2QXbRRG37jwNmzxDkTjEovRkBQg3p/jovMlEgJJUAFr0laRbTTK3G3mNEJt4G89ZwyM/sgQtdSwUClcS5O9Y9VdVsqTFP4msTvY6G5pIYgM+vgIULpd+BgDN47d//H7J16vOWxOEdlYxgpQlHZhJmFX8RQf8gA5s";

        // the direction the camera is facing
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        // make an instance of vuforia
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // get all of the view targets for this year into an object
        skystoneTrackables = this.vuforia.loadTrackablesFromAsset("Skystone");
        // grab the view target for the skystone
        skystoneTrack = skystoneTrackables.get(0);
        //skystoneTrack.setLocation(newMatrix(0, 0 , 0, 0, 0 , 0));
        webcamLocation = newMatrix(0,0,0,0,0,0);
        telemetry.addLine("tadah");
        tHeight = (float)146.05;
        len1 = (float)908.05;
        len2 = (float)2673.35;
        len3 = (float)3881.4;
        // this names and sets all the listeners into a big array for updating later
        skystoneTrackables.get(5).setName("RedPerimeterTgt1");
        RedPerimeterTgt1 = skystoneTrackables.get(5);
        skystoneTrackables.get(6).setName("RedPerimeterTgt2");
        RedPerimeterTgt2 = skystoneTrackables.get(6);

        skystoneTrackables.get(7).setName("FrontPerimeterTgt1");
        FrontPerimeterTgt1 = skystoneTrackables.get(7);

        skystoneTrackables.get(8).setName("FrontPerimeterTgt2");
        FrontPerimeterTgt2 = skystoneTrackables.get(8);

        skystoneTrackables.get(9).setName("BluePerimeterTgt1");
        BluePerimeterTgt1 = skystoneTrackables.get(9);

        skystoneTrackables.get(10).setName("BluePerimeterTgt2");
        BluePerimeterTgt2 = skystoneTrackables.get(10);

        skystoneTrackables.get(11).setName("RearPerimeterTgt1");
        RearPerimeterTgt1 = skystoneTrackables.get(11);

        skystoneTrackables.get(12).setName("RearPerimeterTgt2");
        RearPerimeterTgt2 = skystoneTrackables.get(12);

        FrontPerimeterTgt1.setLocation(newMatrix(len2, 0,tHeight,-90,0,0));
        FrontPerimeterTgt2.setLocation(newMatrix(len1, 0, tHeight, -90, 0, 0));
        BluePerimeterTgt1.setLocation(newMatrix(0, len1, tHeight,90,0,90 ));
        BluePerimeterTgt2.setLocation(newMatrix(0, len2, tHeight, 90, 0, 90));
        RedPerimeterTgt1.setLocation(newMatrix(len3, len2,tHeight,90,0,-90));
        RedPerimeterTgt2.setLocation(newMatrix(len3,len1, tHeight,90,0,-90));
        RearPerimeterTgt1.setLocation(newMatrix(len1, len3, tHeight,90,0,0));
        RearPerimeterTgt2.setLocation(newMatrix(len2,len3,tHeight,90,0,0));

        listeners = new VuforiaTrackableDefaultListener[8];
        listeners[0] = (VuforiaTrackableDefaultListener)RedPerimeterTgt1.getListener();
        listeners[1] = (VuforiaTrackableDefaultListener)RedPerimeterTgt2.getListener();
        listeners[2] = (VuforiaTrackableDefaultListener)FrontPerimeterTgt1.getListener();
        listeners[3] = (VuforiaTrackableDefaultListener)FrontPerimeterTgt2.getListener();
        listeners[4] = (VuforiaTrackableDefaultListener)BluePerimeterTgt1.getListener();
        listeners[5] = (VuforiaTrackableDefaultListener)BluePerimeterTgt2.getListener();
        listeners[6] = (VuforiaTrackableDefaultListener)RearPerimeterTgt1.getListener();
        listeners[7] = (VuforiaTrackableDefaultListener)RearPerimeterTgt2.getListener();
        for (VuforiaTrackableDefaultListener li: listeners) {
            li.setCameraLocationOnRobot(parameters.cameraName, webcamLocation);
        }



        listener = (VuforiaTrackableDefaultListener) RearPerimeterTgt1.getListener();
        listener.setCameraLocationOnRobot(parameters.cameraName, webcamLocation);

    }

    public OpenGLMatrix newMatrix(float x, float y, float z, float u, float v, float w){
        // this takes x, y, z and u v w, and then moves and
        // rotates the target to where it needs to go
        return OpenGLMatrix.translation(x, y, z).
                multiplied(Orientation.getRotationMatrix(
                        AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES, u, v, w));
    }


}
