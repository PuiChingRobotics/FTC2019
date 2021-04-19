package org.firstinspires.ftc.teamcode.FTC2021;

import com.qualcomm.hardware.bosch.BNO055IMU;
import java.util.Stack;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DigitalChannel;
import android.app.Activity;
import android.graphics.Color;
import android.view.View;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import java.util.Locale;
import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;


public class FTC_2021_goBilda_Init {

    //Drive Motors
    public DcMotor Lfront;
    public DcMotor Rfront;
    public DcMotor Lback;
    public DcMotor Rback;

    //Drive
    public double Lfronttmp = 0;
    public double Lbacktmp = 0;
    public double Rfronttmp = 0;
    public double Rbacktmp = 0;

    public double Lfrontforward = 0;
    public double Lbackforward = 0;
    public double Rfrontforward = 0;
    public double Rbackforward = 0;

    //imu
    BNO055IMU imu;

    //color sensor V3
    public ColorSensor CS1;
    public ColorSensor CS2;

    //Grabber
    public DcMotor Grabber;

    //Fly
    public DcMotor Fly;
    public DcMotor Flyhigh;

    //Intake
    public DcMotor Intake;

    //
    public CRServo Lift;
    public Servo Shoot;
    public Servo Stack;
    public Servo Kai;

    public HardwareMap _hw;

    public void init(HardwareMap hw) {
        _hw = hw;

        Lfront = _hw.dcMotor.get("Lfront");
        Rfront = _hw.dcMotor.get("Rfront");
        Lback = _hw.dcMotor.get("Lback");
        Rback = _hw.dcMotor.get("Rback");

        Grabber = _hw.dcMotor.get("Grabber");

        Intake = _hw.dcMotor.get("Intake");

        Fly = _hw.dcMotor.get("Fly");

        Flyhigh = _hw.dcMotor.get("Flyhigh");

        imu = _hw.get(BNO055IMU.class, "imu");

        CS1 = _hw.get(ColorSensor.class, "CS1");
        CS2 = _hw.get(ColorSensor.class, "CS2");

        Lift = _hw.crservo.get("Lift");
        Shoot = hw.servo.get("Shoot");
        Stack = hw.servo.get("Stack");
        Kai = hw.servo.get("Kai");


        Rfront.setDirection(DcMotorSimple.Direction.REVERSE);

        Rback.setDirection(DcMotorSimple.Direction.REVERSE);

        Rfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Rback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        Lback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

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