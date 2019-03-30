package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class FTC_2019_USA_Init {
    //Dirve Motors
    public DcMotor Lfront;
    public DcMotor Rfront;
    public DcMotor Lback;
    public DcMotor Rback;
    //LS no.1
    public DcMotor LS1;
    //Latching
    public DcMotor Latch;
    //MCRotate
    public DcMotor MCRotate;
    //LS no.2
    public DcMotor LS2;

    //MC
    public CRServo MC;
    public Servo MCL;
    public Servo MCR;
    //Drive
    public Servo Hammer;
    public double Lfronttmp = 0;
    public double Lbacktmp = 0;
    public double Rfronttmp = 0;
    public double Rbacktmp = 0;

    public double Lfrontforward = 0;
    public double Lbackforward = 0;
    public double Rfrontforward = 0;
    public double Rbackforward = 0;

    //cam
    public WebcamName Webcam;
    //imu
    BNO055IMU imu;

    public HardwareMap _hw;

    public void init(HardwareMap hw) {
        _hw = hw;

        Lfront = _hw.dcMotor.get("Lfront");
        Rfront = _hw.dcMotor.get("Rfront");
        Lback = _hw.dcMotor.get("Lback");
        Rback = _hw.dcMotor.get("Rback");

        MCRotate = _hw.dcMotor.get("MCRotate");

        LS2 = _hw.dcMotor.get("LS2");

        Hammer = _hw.servo.get("Hammer");

        MCL = _hw.servo.get("MCL");
        MCR = _hw.servo.get("MCR");

        MC = _hw.crservo.get("MC");


        Webcam = _hw.get(WebcamName.class, "Webcam");

        imu = _hw.get(BNO055IMU.class, "imu");

        Latch = _hw.dcMotor.get("Latch");

        LS1 = _hw.dcMotor.get("LS1");

        Lfront.setDirection(DcMotorSimple.Direction.REVERSE);
        Lback.setDirection(DcMotorSimple.Direction.REVERSE);

        Rfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Rback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Lfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Lback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

        LS1.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        LS2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        MCRotate.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void runModeSet(String mode) {
        if (mode == "position") {
            Lfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Lback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Rfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Rback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        } else if (mode == "reset") {
            Lfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Lback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Rfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Rback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        } else if (mode == "tele") {
            Lfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Lback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Rfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Rback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        } else if (mode == "encoder") {
            Lfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Lback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Rfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Rback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }
}

