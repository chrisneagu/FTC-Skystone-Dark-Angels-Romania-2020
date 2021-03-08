package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="MainDrive", group="DriveTest")
public class MainControl extends OpMode {

    private Robot darky = new Robot();
    private MecanumControl wheels= new MecanumControl();


    @Override
    public void init() {
        darky.init(hardwareMap, telemetry);
        wheels.init(hardwareMap,telemetry,false);
        gamepad1.setJoystickDeadzone(.01F);
    }

    @Override
    public void init_loop() {}

    @Override
    public void start() {}

    @Override
    public void loop() {

        wheels.analog_control(gamepad1.left_stick_x, -gamepad1.left_stick_y, gamepad1.right_stick_x,gamepad1.b,gamepad1.right_bumper,gamepad1.left_bumper,gamepad1.dpad_left,gamepad1.dpad_right,gamepad1.dpad_down,gamepad1.dpad_up);
        darky.motor_control(gamepad2.a,gamepad2.b,gamepad2.left_trigger,gamepad2.right_trigger);
        darky.servo_control(gamepad2.x,gamepad2.y,gamepad2.right_bumper,gamepad2.dpad_up,gamepad2.dpad_down,gamepad2.left_bumper);
       // wheels.play_john(gamepad1.left_bumper,gamepad2.right_bumper);
        wheels.debug();
        darky.debug();
        telemetry.update();

    }

    @Override
    public void stop() {}
}
