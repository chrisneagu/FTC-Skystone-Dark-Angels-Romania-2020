package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Testare 24 strafe left")
public class Auto11 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    @Override

    public void runOpMode()
    {
        wheels.init(hardwareMap,telemetry,true);
        darky.init(hardwareMap,telemetry);
        waitForStart();
        wheels.DriveStrafe(-72,0.7,4,0);
    }
}
