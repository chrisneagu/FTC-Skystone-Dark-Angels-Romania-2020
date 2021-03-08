package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

@Autonomous(name="Placa Blue")
public class Auto3 extends LinearOpMode {

    private Robot darky= new Robot();
    private MecanumControl wheels=new MecanumControl();
    @Override

    public void runOpMode()
    {
        darky.init(hardwareMap,telemetry);
        wheels.init(hardwareMap,telemetry,true);
        waitForStart();
        wheels.TargetDrive(-24,0,0,-24,.4,3);
        wheels.TargetDrive(-21,-21,-21,-21,.4,3);
        wheels.TargetDrive(-4,4,4,-4,0.2,1);
        darky.gh_down();
        sleep(500);
        wheels.TargetDrive(24,0,0,24,.4,3);
        wheels.TargetDrive(-24,24,-24,24,.4,3);
        wheels.TargetDrive(-24,-24,-24,-24,0.4,3);
        darky.gh_up();
        sleep(500);
        wheels.TargetDrive(-24,24,24,-24,0.4,3);
        wheels.TargetDrive(40,40,40,40,0.4,3);
        wheels.TargetDrive(-4,4,4,-4,0.2,1);
        //wheels.TargetDrive2(36,36,36,36,0.5,3);
    }


}
