package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;


@Autonomous(name="Test fata 24")
public class Auto8 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    @Override

    public void runOpMode()
    {
        wheels.init(hardwareMap,telemetry,true);
        darky.init(hardwareMap,telemetry);
        waitForStart();
        wheels.DriveStraight(72,0.7,4,0); //69

    }


}
