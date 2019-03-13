package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.hardware.CRServo;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;


public class FTC_2019_USA_Init {
    //Dirve Motors
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

    //Both Kick Servo Posistion
    public final double kickopen = 0.5;
    //Claim Servo Position
    public final double ClaimLevel = 0.43;
    public final double ClaimThrow = 1;

    public HardwareMap _hw;

    public void init(HardwareMap hw){
        _hw = hw;

        Lfront = _hw.dcMotor.get("Lfront");
        Rfront = _hw.dcMotor.get("Rfront");
        Lback = _hw.dcMotor.get("Lback");
        Rback = _hw.dcMotor.get("Rback");
        //LSlength = _hw.dcMotor.get("LSlength");

        //Latching.setDirection(DcMotorSimple.Direction.REVERSE);

        Lfront.setDirection(DcMotorSimple.Direction.REVERSE);
        Lback.setDirection(DcMotorSimple.Direction.REVERSE);

        Rfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Rback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Lfront.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
        Lback.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
}

    public void runModeSet(String mode) {
        if (mode == "position") {
            Lfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Lback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Rfront.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Rback.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (mode == "reset") {
            Lfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Lback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Rfront.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Rback.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else if (mode == "tele") {
            Lfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Lback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Rfront.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            Rback.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }
        else if (mode == "encoder") {
            Lfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Lback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Rfront.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Rback.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }

 /*   public void runModeSetLatching (String mode) {
        if (mode == "position") {
            Latching.setMode(DcMotor.RunMode.RUN_TO_POSITION);
            Latching2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        }
        else if (mode == "reset") {
            Latching.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            Latching2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }
        else if (mode == "encoder") {
            Latching.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
            Latching2.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }
    }*/
}

