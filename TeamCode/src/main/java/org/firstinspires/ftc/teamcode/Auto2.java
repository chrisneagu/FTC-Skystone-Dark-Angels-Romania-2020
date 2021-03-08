package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Placa Red Test")
public class Auto2 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    @Override

    public void runOpMode()
    {
        darky.init(hardwareMap,telemetry);
        wheels.init(hardwareMap,telemetry,true);
        waitForStart();
       // wheels.DriveStraight(-5,.3,3);
        //wheels.DriveStrafe(-24,0.5,3);
       // wheels.TargetDrive(24,-24,-24,24,0.5,3);
        //idle();
        //wheels.DriveStraight(-24,0.5,4);
        //idle();
        //darky.gh_down();
        //idle();
        //wheels.DriveStraight(30,0.4,4);
        //idle();
        //darky.gh_up();
        //idle();
        //wheels.DriveStrafe(62,0.5,4);
        //wheels.TargetDrive(-62,62,62,-62,0.5,5);

    }


}
