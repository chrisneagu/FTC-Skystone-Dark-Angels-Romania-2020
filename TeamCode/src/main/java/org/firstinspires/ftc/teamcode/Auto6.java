package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

@Autonomous(name="Mecanum Stanga Parcare")
public class Auto6 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    private ElapsedTime timp=new ElapsedTime();
    @Override

    public void runOpMode()
    {
        wheels.init(hardwareMap,telemetry,true);
        darky.init(hardwareMap,telemetry);
        waitForStart();
        timp.reset();
        while(timp.seconds()<=20);
        wheels.TargetDrive(2,-2,-2,2,0.2,1);//mecanum stanga
        wheels.TargetDrive(24,24,24,24,0.4,3);
        wheels.TargetDrive(-2,2,2,-2,0.2,1);//mecanum dreapta


    }


}
