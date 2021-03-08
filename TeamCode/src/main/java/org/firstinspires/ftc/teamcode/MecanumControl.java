package org.firstinspires.ftc.teamcode;



import android.media.MediaPlayer;

import com.qualcomm.hardware.modernrobotics.ModernRoboticsI2cGyro;
import com.qualcomm.robotcore.hardware.GyroSensor;



import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.util.Range;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;




import org.firstinspires.ftc.robotcore.external.Telemetry;




public class MecanumControl{


    private DcMotor motor_bl = null; private DcMotor motor_br = null;
    private DcMotor motor_fl = null; private DcMotor motor_fr = null;

    private ModernRoboticsI2cGyro myGyro= null;// Additional Gyro device
    private GyroSensor sensorGyro=null;


    private HardwareMap hardMap = null;
    private Telemetry telemetry = null;

    private ElapsedTime runt4 = new ElapsedTime(); //button to reverse the robot face


    private double speedmodifier=1;
    private boolean inv=false;


    private static final double DRIVE_GEAR_REDUCTION = 1;
    private static final double TICKS_PER_REV=537.6;
    private static final double WHEEL_DIAMETER_INCHES=4;
    private static final double COUNTS_PER_INCH = (TICKS_PER_REV * DRIVE_GEAR_REDUCTION) / (WHEEL_DIAMETER_INCHES * Math.PI);
    private static final double P_DRIVE_COEFF = 0.15;     // Larger is more responsive, but also less stable

    private MediaPlayer hatz= new MediaPlayer();

    //int zAcummulated,
    int heading,xVal,yVal,zVal;



    public void init(HardwareMap oldHardMap, Telemetry oldTelemetry, boolean isAutonomous) {
        hardMap = oldHardMap;
        telemetry = oldTelemetry;


        hatz=MediaPlayer.create(oldHardMap.appContext,R.raw.hatzz);


        motor_bl = hardMap.get(DcMotor.class, "wheel_bl");
        motor_br = hardMap.get(DcMotor.class, "wheel_br");
        motor_fl = hardMap.get(DcMotor.class, "wheel_fl");
        motor_fr = hardMap.get(DcMotor.class, "wheel_fr");
        sensorGyro = hardMap.gyroSensor.get("gyro");
        myGyro=(ModernRoboticsI2cGyro) sensorGyro;

        myGyro.calibrate();
        while (myGyro.isCalibrating())  {
            telemetry.addData(">", "Calibrating Gyro");    //
            telemetry.addData(">", "Robot Heading = %d", myGyro.getHeading());
            telemetry.update();
        }
        myGyro.resetZAxisIntegrator();

        motor_bl.setDirection(DcMotorSimple.Direction.FORWARD);
        motor_br.setDirection(DcMotorSimple.Direction.REVERSE);
        motor_fl.setDirection(DcMotorSimple.Direction.FORWARD);
        motor_fr.setDirection(DcMotorSimple.Direction.REVERSE);

        if (isAutonomous) {
            //sleep(250);
            motor_bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            motor_bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            //if autonomous
        } else {
            motor_bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor_br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor_fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            motor_fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        motor_bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        motor_fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);



        telemetry.addData(">", "Robot Ready.");    //
        telemetry.update();
        // Wait for the game to start (Display Gyro value), and reset gyro before we move..

    }


    public void analog_control(double x, double y, double rotation,boolean key_invert,boolean bmp1,boolean bmp2,boolean dpad1,boolean dpad2,boolean dpad3,boolean dpad4) {

        if(key_invert&&runt4.milliseconds()>=500) {
            inv=!inv;
            runt4.reset();
        }
        if(bmp1) {
            speedmodifier=.5;
        }else if(bmp2){
            speedmodifier=1;
        }
        double wheelSpeed[] = new double[4];

        wheelSpeed[0] = (-x + y + rotation); //bl
        wheelSpeed[1] = (x + y - rotation);//br
        wheelSpeed[2] = (x + y + rotation);//fl
        wheelSpeed[3] = (-x + y - rotation);//fr

        if(dpad1) { //left
            wheelSpeed[0] =  .5;//bl
            wheelSpeed[1] = -.5;//br
            wheelSpeed[2] = -.5;//fl
            wheelSpeed[3] = .5;//fr
        } else if(dpad2) { //right
            wheelSpeed[0] =  -.5;//bl
            wheelSpeed[1] = .5;//br
            wheelSpeed[2] = .5;//fl
            wheelSpeed[3] = -.5;//fr
        }else if(dpad3){ //down
            wheelSpeed[0] = -.5;
            wheelSpeed[1] = -.5;
            wheelSpeed[2] = -.5;
            wheelSpeed[3] = -.5;
        }else if(dpad4){ //up
            wheelSpeed[0] = .5;
            wheelSpeed[1] = .5;
            wheelSpeed[2] = .5;
            wheelSpeed[3] = .5;
        }
        wheelSpeed[0]*=speedmodifier;
        wheelSpeed[1]*=speedmodifier;
        wheelSpeed[2]*=speedmodifier;
        wheelSpeed[3]*=speedmodifier;
        if(!inv) {
            motor_bl.setPower(Range.clip(wheelSpeed[0],-1,1));
            motor_br.setPower(Range.clip(wheelSpeed[1],-1,1));
            motor_fl.setPower(Range.clip(wheelSpeed[2],-1,1));
            motor_fr.setPower(Range.clip(wheelSpeed[3],-1,1));
        } else {
            motor_bl.setPower(Range.clip(-wheelSpeed[3],-1,1));
            motor_br.setPower(Range.clip(-wheelSpeed[2],-1,1));
            motor_fl.setPower(Range.clip(-wheelSpeed[1],-1,1));
            motor_fr.setPower(Range.clip(-wheelSpeed[0],-1,1));
        }
    }


    public  void TargetDrive(double backLeftInches, double backRightInches, double frontLeftInches,
                              double frontRightInches, double maxSpeed, int timeoutInSeconds)  {


        int a, b, c, d;

        a = motor_fr.getCurrentPosition() + (int) (frontRightInches * COUNTS_PER_INCH);
        b = motor_fl.getCurrentPosition() + (int) (frontLeftInches * COUNTS_PER_INCH);
        c = motor_br.getCurrentPosition() + (int) (backRightInches * COUNTS_PER_INCH);
        d = motor_bl.getCurrentPosition() + (int) (backLeftInches * COUNTS_PER_INCH);


        motor_fr.setTargetPosition(a);
        motor_fl.setTargetPosition(b);
        motor_br.setTargetPosition(c);
        motor_bl.setTargetPosition(d);

        ElapsedTime runtime = new ElapsedTime();

        motor_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        motor_fl.setPower(maxSpeed);
        motor_bl.setPower(maxSpeed);
        motor_br.setPower(maxSpeed);
        motor_fr.setPower(maxSpeed);


        while ((runtime.seconds() < timeoutInSeconds) && (motor_bl.isBusy() || motor_fr.isBusy() || motor_br.isBusy() || motor_fl.isBusy())) {
            // Display it for the driver.

        }


        motor_fr.setPower(0);
        motor_fl.setPower(0);
        motor_br.setPower(0);
        motor_bl.setPower(0);


        motor_bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        motor_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor_bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public  void DriveStraight(double Inches, double maxSpeed, int timeoutInSeconds, double target)  {


            int a, b, c, d;
            a = motor_fr.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
            b = motor_fl.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
            c = motor_br.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
            d = motor_bl.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);


            motor_fr.setTargetPosition(a);
            motor_fl.setTargetPosition(b);
            motor_br.setTargetPosition(c);
            motor_bl.setTargetPosition(d);


            motor_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motor_bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

            ElapsedTime runtime = new ElapsedTime();
            while ((runtime.seconds() < timeoutInSeconds) && motor_bl.isBusy() && motor_fr.isBusy() && motor_br.isBusy() && motor_fl.isBusy()) {
                // Display it for the driver.

                if(Inches>0) {
                    motor_fl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_bl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_br.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_fr.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                } else {
                    motor_fl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_bl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_br.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                    motor_fr.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                }
            }


            motor_fr.setPower(0);
            motor_fl.setPower(0);
            motor_br.setPower(0);
            motor_bl.setPower(0);


          //  motor_bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
           // motor_br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //motor_fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            //motor_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            motor_bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            motor_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

        }

    public  void DriveStrafe(double Inches,double maxSpeed, int timeoutInSeconds, double target)  {//mecanum dreapta default

        int a, b, c, d;

        a = motor_fr.getCurrentPosition() - (int) (Inches * COUNTS_PER_INCH);
        b = motor_fl.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        c = motor_br.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        d = motor_bl.getCurrentPosition() - (int) (Inches * COUNTS_PER_INCH);


        motor_fr.setTargetPosition(a);
        motor_fl.setTargetPosition(b);
        motor_br.setTargetPosition(c);
        motor_bl.setTargetPosition(d);


        motor_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_br.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ElapsedTime runtime = new ElapsedTime();
        while ((runtime.seconds() < timeoutInSeconds) && motor_bl.isBusy() && motor_fr.isBusy() && motor_br.isBusy() && motor_fl.isBusy()) {
            // Display it for the driver.

            if(Inches<0) {
                motor_fl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_bl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_br.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_fr.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }else {
                motor_fl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_bl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_br.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_fr.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }
        }


        motor_fr.setPower(0);
        motor_fl.setPower(0);
        motor_br.setPower(0);
        motor_bl.setPower(0);


        //motor_bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motor_br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motor_fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        //motor_fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        motor_bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public  void FirstDiagonalDrive(double Inches,double maxSpeed, int timeoutInSeconds, double target)  {

        int b, c;

        b = motor_fl.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        c = motor_br.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);

        motor_fl.setTargetPosition(b);
        motor_br.setTargetPosition(c);

        motor_fl.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_br.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ElapsedTime runtime = new ElapsedTime();
        while ((runtime.seconds() < timeoutInSeconds) && motor_br.isBusy() && motor_fl.isBusy()) {
            // Display it for the driver.

            if(Inches>0) {
                motor_fl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_br.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }else {
                motor_fl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_br.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }
        }
        motor_fl.setPower(0);
        motor_br.setPower(0);

        motor_br.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_fl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public  void SecondDiagonalDrive(double Inches,double maxSpeed, int timeoutInSeconds, double target)  {

        int b, c;

        b = motor_fr.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);
        c = motor_bl.getCurrentPosition() + (int) (Inches * COUNTS_PER_INCH);

        motor_fr.setTargetPosition(b);
        motor_bl.setTargetPosition(c);

        motor_fr.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        motor_bl.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        ElapsedTime runtime = new ElapsedTime();
        while ((runtime.seconds() < timeoutInSeconds) && motor_bl.isBusy() && motor_fr.isBusy()) {
            // Display it for the driver.

            if(Inches>0) {
                motor_fr.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_bl.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }else {
                motor_fr.setPower(Range.clip(maxSpeed + (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
                motor_bl.setPower(Range.clip(maxSpeed - (myGyro.getIntegratedZValue() - target) / 100, -1, 1));
            }
        }
        motor_fr.setPower(0);
        motor_bl.setPower(0);

        motor_fr.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        motor_bl.setMode(DcMotor.RunMode.RUN_USING_ENCODER);

    }

    public void debug() {
        telemetry.addData("Wheels Encoders:"," ");
        telemetry.addData("motor_bl",motor_bl.getCurrentPosition());
        telemetry.addData("motor_br",motor_br.getCurrentPosition());
        telemetry.addData("motor_fl",motor_fl.getCurrentPosition());
        telemetry.addData("motor_fr",motor_fr.getCurrentPosition());
        telemetry.addData("motor_bl power",motor_bl.getPower());
        telemetry.addData("motor_br pw",motor_br.getPower());
        telemetry.addData("motor_fl pw",motor_fl.getPower());
        telemetry.addData("motor_fr pw ",motor_fr.getPower());
    }

    public void play_john(boolean bt1,boolean bt2) {
        if(bt1) hatz.start();
        else if(bt2)
        {
            hatz.pause();
            hatz.seekTo(0);
            hatz.start();
        }
    }
}


