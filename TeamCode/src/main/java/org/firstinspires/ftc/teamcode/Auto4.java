package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Placa Red")
public class Auto4 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    @Override

    public void runOpMode()
    {
        darky.init(hardwareMap,telemetry);
        wheels.init(hardwareMap,telemetry,true);
        waitForStart();
        wheels.TargetDrive(24,24,24,24,.9,3);
    }


}
