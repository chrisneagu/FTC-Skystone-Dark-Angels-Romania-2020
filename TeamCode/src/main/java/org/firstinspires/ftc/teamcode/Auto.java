package org.firstinspires.ftc.teamcode;

import android.graphics.Camera;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.vuforia.CameraDevice;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.internal.vuforia.externalprovider.FocusMode;

import java.util.List;

@Autonomous(name="AUTO Cub")
public class Auto extends LinearOpMode {

    private Robot darky= new Robot();
    private ElapsedTime chrono=new ElapsedTime();
    private Detector cam=new Detector();


    @Override
    public void runOpMode()
    {
        //darky.init(hardwareMap,telemetry,true);
        //24 25 mecanum
        cam.initVuforia();
        cam.initTfod(hardwareMap);

        if (cam.tfod != null) {
            cam.tfod.activate();
        }
            //if (ClassFactory.getInstance().canCreateTFObjectDetector()) {

            //} else {
             //   telemetry.addData("Sorry!", "This device is not compatible with TFOD");
           // }
            /**
             * Activate TensorFlow Object Detection before we wait for the start command.
             * Do it here so that the Camera Stream window will have the TensorFlow annotations visible.
             **/
            //CameraDevice.getInstance().setField("opti-zoom", "opti-zoom-on");
            //CameraDevice.getInstance().setField("zoom", "69");//max 69 fields

            //CameraDevice.getInstance().setFocusMode(CameraDevice.FOCUS_MODE.FOCUS_MODE_MACRO);

            waitForStart();
            chrono.reset();
            while (chrono.seconds()<=15) {
                if (cam.tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = cam.tfod.getUpdatedRecognitions();

                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        // step through the list of recognitions and display boundary info.
                        int i = 0;
                        for (Recognition recognition : updatedRecognitions) {
                            telemetry.addData(String.format("label (%d)", i), recognition.getLabel());
                            telemetry.addData(String.format("  left,top (%d)", i), "%.03f , %.03f",
                                    recognition.getLeft(), recognition.getTop());
                            telemetry.addData(String.format("  right,bottom (%d)", i), "%.03f , %.03f",
                                    recognition.getRight(), recognition.getBottom());
                            telemetry.addData("camera fields" ,CameraDevice.getInstance().getNumFields()); //69
                           // telemetry.addData(String.format("  Angle (%d)", i), "%.03f , %.03f",



                                if(recognition.getLabel()=="Skystone")
                                {
                                    //TODO: Instructions for skystone
                                }
                                else if(recognition.getLabel()=="Stone") {
                                  // TODO: Other else if else if else
                                }
                        }
                        telemetry.update();
                    }
                }
            }


            if (cam.tfod != null) {
                cam.tfod.deactivate();
                cam.tfod.shutdown();
            }
    }
}
