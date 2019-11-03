package org.firstinspires.ftc.teamcode;

import android.graphics.Path;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraManager;
import org.firstinspires.ftc.robotcore.external.hardware.camera.CameraName;

import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackableDefaultListener;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

import javax.crypto.spec.OAEPParameterSpec;

@TeleOp(name = "VuforiaTest", group = "Tests")
public class VuforiaTest  extends OpMode{

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

    // height of the trackables
    float tHeight;

    // trackable distances along walls
    float len1;
    float len2;
    float len3;
    OpenGLMatrix webcamLocation;
    OpenGLMatrix lastLocation;
    @Override
    public void init() {

        setVuforia();

        telemetry.addData("Status", "Initialized");
    }


    @Override
    public void start() {
        //last known location of the robot
        lastLocation = newMatrix(50,40,20,90,0,0);
        // activate tracking of all of them
        skystoneTrackables.activate();

    }


    @Override
    public void loop(){


        float[] b = lastLocation.getData();
        String l = "[";
        int count= 0;
        for(float m: b){
            if(count == 4){
                count = 0;
                l+="]  [";
            }
            l+=m+", ";
            count++;

        }
        telemetry.addData("Location1:", l);
        // important things:
        // test this by getting the columns 4 columns of last location
        // then get hte four rows
        // see what the shape is, and what is where in actuallity
        // then use that to understand what the pose is from the numbers given in
        // in the mean time though, this is on hold.
        telemetry.addData("Location2 ", lastLocation);
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
