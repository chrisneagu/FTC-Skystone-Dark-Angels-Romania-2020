package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="DriveOnly", group="DriveTest")
public class DriveOnly extends OpMode {

    private MecanumControl wheels= new MecanumControl();


    @Override
    public void init() {
        wheels.init(hardwareMap,telemetry,false);
        gamepad1.setJoystickDeadzone(.01F);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {

        wheels.analog_control(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,gamepad1.b,gamepad1.right_bumper,gamepad2.left_bumper,gamepad1.dpad_left,gamepad1.dpad_right,gamepad1.dpad_down,gamepad1.dpad_up);
        wheels.debug();
        telemetry.update();
    }

    @Override
    public void stop() {}
}
