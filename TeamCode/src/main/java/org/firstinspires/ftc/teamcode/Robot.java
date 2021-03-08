package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.robotcore.external.Telemetry;


public class Robot {
    private DcMotor motor_vex = null;
    private DcMotor motor_vex2 = null;
    private DcMotor motorslide_left = null;
    private DcMotor motorslide_right = null;

    private Servo servo_claw = null;
    private Servo servo_claw2 = null;
    private Servo servo_catch = null;
    private Servo servo_rotate = null;
    private Servo servo_cap=null;
    private CRServo vex=null;

    //private DcMotor extender = null;


    private HardwareMap hardMap = null;
    private Telemetry telemetry = null;
    private ElapsedTime runt = new ElapsedTime();
    private ElapsedTime runt2 = new ElapsedTime();
    private ElapsedTime runt3 = new ElapsedTime();
    private ElapsedTime runt4 = new ElapsedTime();
    private ElapsedTime runt5 = new ElapsedTime();

    public void init(HardwareMap oldHardMap, Telemetry oldTelemetry) {
        hardMap = oldHardMap;
        telemetry = oldTelemetry;

        servo_claw = hardMap.get(Servo.class, "clawl");
        servo_claw2 = hardMap.get(Servo.class, "clawr");

        servo_catch = hardMap.get(Servo.class, "catch");
        servo_rotate = hardMap.get(Servo.class, "rotate");

        servo_cap= hardMap.get(Servo.class, "capstone");

        motorslide_right = hardMap.get(DcMotor.class, "slider");
        motorslide_left = hardMap.get(DcMotor.class, "slidel");

        motor_vex = hardMap.get(DcMotor.class, "vex");
        motor_vex2 = hardMap.get(DcMotor.class, "vex2");
      //  extender = hardMap.get(DcMotor.class, "crs_extender");
        vex=hardMap.get(CRServo.class,"VEX");

        motorslide_left.setDirection(DcMotorSimple.Direction.FORWARD);
        motorslide_right.setDirection(DcMotorSimple.Direction.FORWARD);
        motorslide_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorslide_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        //extender.setDirection(DcMotor.Direction.FORWARD);

        motor_vex.setDirection(DcMotorSimple.Direction.FORWARD);
        motor_vex2.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_vex.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motor_vex2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);


        motorslide_left.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorslide_right.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motorslide_left.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        motorslide_right.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

      //  extender.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorslide_left.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motorslide_right.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        motor_vex.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_vex2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        servo_rotate.scaleRange(0, 1);
        //servo_cap.scaleRange(0,1);
        servo_claw.setPosition(.45);
        servo_claw2.setPosition(.8);
        servo_catch.setPosition(0.86);
        servo_rotate.setPosition(.04);
        servo_cap.setPosition(0.04);
        vex.setPower(0);
    }


    public void motor_control(boolean bt1, boolean bt2, double pw1, double pw2) {
        if (runt.milliseconds() > 500) {
            if (bt1) {
                if (motor_vex.getPower() == 1) {
                    motor_vex.setPower(0);
                    motor_vex2.setPower(0);
                } else {
                    motor_vex.setPower(1);
                    motor_vex2.setPower(1);
                }
                runt.reset();
            } else if (bt2) {
                if (motor_vex.getPower() == -1) {
                    motor_vex.setPower(0);
                    motor_vex2.setPower(0);
                } else {
                    motor_vex.setPower(-1);
                    motor_vex2.setPower(-1);
                }
                runt.reset();
            }
        }
        if (pw1 > 0) {
            motorslide_right.setPower(pw1);
            motorslide_left.setPower(pw1);
        } else if (pw2 > 0) {
            motorslide_right.setPower(-pw2);
            motorslide_left.setPower(-pw2);
        } else {
            motorslide_right.setPower(0);
            motorslide_left.setPower(0);
        }
    }


    public void servo_control(boolean bt1, boolean bt3, boolean bt4, boolean bt5, boolean bt6,boolean bt7) {

        if (bt5) {
            vex.setPower(1);
        } else if (bt6) {
            vex.setPower(-1);
        } else {
            vex.setPower(0);
        }

        if (bt1 && runt5.milliseconds() >= 500) {
            if (servo_claw.getPosition() == .45) {
                servo_claw.setPosition(.9);
                servo_claw2.setPosition(.25);
            } else {
                servo_claw.setPosition(.45);
                servo_claw2.setPosition(.8);
            }
            runt5.reset();
        }

        if (bt3 && runt2.milliseconds() >= 500) {
            if (servo_catch.getPosition() == .86) {
                servo_catch.setPosition(.27);
            } else {
                servo_catch.setPosition(.86);
            }
            runt2.reset();
        }

        if (bt4 && runt3.milliseconds() >= 500) {
            if (servo_rotate.getPosition() == 0.04) {
                servo_rotate.setPosition(0.93);
            } else {
                servo_rotate.setPosition(0.04);//poz initiala
            }
            runt3.reset();
        }

        if (bt7 && runt4.milliseconds() >= 500) {
            if (servo_cap.getPosition() == 0.41) {
                servo_cap.setPosition(0.04);
            } else {
                servo_cap.setPosition(0.41);//poz initiala
            }
            runt4.reset();
        }
    }


    public void debug() {
        telemetry.addData("ciuro left", motorslide_left.getCurrentPosition()); //8247
        telemetry.addData("ciuro right ", motorslide_right.getCurrentPosition());
        telemetry.addData("positie capstone", servo_cap.getPosition());
    }



    public void gh_down() {
        servo_claw.setPosition(.9);
        servo_claw2.setPosition(.25);
    }

    public void gh_up() {
        servo_claw.setPosition(.45);
        servo_claw2.setPosition(.8);
    }

}