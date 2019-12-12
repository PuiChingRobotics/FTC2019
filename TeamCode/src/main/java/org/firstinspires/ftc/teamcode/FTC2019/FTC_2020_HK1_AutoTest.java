package org.firstinspires.ftc.teamcode.FTC2020;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

import android.app.Activity;
import android.graphics.Color;
import android.view.View;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import java.util.Timer;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import com.qualcomm.robotcore.hardware.DcMotor;
import java.util.List;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.rev.Rev2mDistanceSensor;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;

import java.util.List;

import java.util.Locale;

@Autonomous(name="FTC_2020_HK1_AutoTest", group ="FTC 2020")

public class FTC_2020_HK1_AutoTest extends Nav {

    FTC_2020_HK1_Init robot = new FTC_2020_HK1_Init();

    //For Color Sensor
    float hsvValues[] = {0F, 0F, 0F};
    final float values[] = hsvValues;
    final double SCALE_FACTOR = 255;

    //small distance
    double small = 1.05;

    String turn;
    public void initial(){

        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        robot.runModeSet("reset");
        robot.runModeSet("encoder");
    }

    //update value of color sensor
    void update () {
        Color.RGBToHSV((int) (robot.CS.red() * SCALE_FACTOR),
                (int) (robot.CS.green() * SCALE_FACTOR /1.8 ),
                (int) (robot.CS.blue() * SCALE_FACTOR /1.1 ),
                hsvValues);
    }

    void updateL () {
        Color.RGBToHSV((int) (robot.CSL.red() * SCALE_FACTOR),
                (int) (robot.CSL.green() * SCALE_FACTOR /1.8 ),
                (int) (robot.CSL.blue() * SCALE_FACTOR /1.1 ),
                hsvValues);
    }

    void updateR () {
        Color.RGBToHSV((int) (robot.CSR.red() * SCALE_FACTOR),
                (int) (robot.CSR.green() * SCALE_FACTOR /1.8 ),
                (int) (robot.CSR.blue() * SCALE_FACTOR /1.1 ),
                hsvValues);
    }

    //abs
    int abs (int x) {
        if (x<0) return -x;
        else return x;
    }

    int max (int x,int y) {
        if (x>y) return x;
        else return y;
    }

    //find where is the skystone
    String where_isit(int l,int m,int r) {
        int leftabsv=abs(m-l)+abs(r-l);
        int middleabsv=abs(l-m)+abs(r-m);
        int rightabsv=abs(l-r)+abs(m-r);
        int maxvalue=max(leftabsv,max(middleabsv,rightabsv));
        if (leftabsv==maxvalue) return "LEFT";
        else if (middleabsv==maxvalue) return "MIDDLE";
        else return "RIGHT";
    }

    //Clip the block (L/M/R)
    void Left () {
        robot.HammerL.setPosition(0.7);
        sleep(1000);
        return;
    }

    void Middle () {
        robot.Hammer.setPosition(0.7);
        sleep(1000);
        return;
    }

    void Right () {
        robot.HammerR.setPosition(0.7);
        sleep(1000);
        return;
    }


    @Override
    public void runOpMode() {

        initial();
        Nav_Init();
        robot.Hammer.setPosition(0.1);
        robot.HammerL.setPosition(0.1);
        robot.HammerR.setPosition(0.1);

        waitForStart();

        updateR();
        int right = -(Color.HSVToColor(0xff, values));

        update();
        int center = -(Color.HSVToColor(0xff, values));

        updateL();
        int left = -(Color.HSVToColor(0xff, values));

        String ans=where_isit(left, center, right);

        telemetry.addData("Left", left);
        telemetry.addData("Center", center);
        telemetry.addData("Right", right);
        telemetry.addData("The skystone", ans);
        telemetry.update();

        switch (ans) {
            case "LEFT":
                //Left();
                break;
            case "MIDDLE":
                //Middle();
                break;
            case "RIGHT":
                Right();
                break;
        }

        go_sideways(270,0,1,10);

        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        telemetry.addData("Left", left);
        telemetry.addData("Center", center);
        telemetry.addData("Right", right);
        telemetry.addData("The skystone", ans);
        telemetry.update();

        sleep(50000000);

    }

}
