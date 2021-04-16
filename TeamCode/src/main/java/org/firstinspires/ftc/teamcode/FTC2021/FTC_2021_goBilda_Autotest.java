package org.firstinspires.ftc.teamcode.FTC2021;

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

@Autonomous(name="FTC_2021_goBilda_Autotest", group ="FTC 2021")

public class FTC_2021_goBilda_Autotest extends Nav {

    FTC_2021_goBilda_Init robot = new FTC_2021_goBilda_Init();

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
    void update1 () {
        Color.RGBToHSV((int) (robot.CS1.red() * SCALE_FACTOR),
                (int) (robot.CS1.green() * SCALE_FACTOR),
                (int) (robot.CS1.blue() * SCALE_FACTOR),
                hsvValues);
    }

    void update2 () {
        Color.RGBToHSV((int) (robot.CS2.red() * SCALE_FACTOR),
                (int) (robot.CS2.green() * SCALE_FACTOR),
                (int) (robot.CS2.blue() * SCALE_FACTOR),
                hsvValues);
    }

    //find how many
    String donut(int cs_1, int cs_2) {
        if ((cs_1>=5000000)&&(cs_2>=5000000)) return "ZERO";
        else if (cs_1>=5000000) return "ONE";
        else return "FOUR";
    }

    void Zero () {
        return;
    }

    void One () {
        return;
    }

    void Four () {
        return;
    }

    @Override
    public void runOpMode() {

        initial();
        Nav_Init();

        waitForStart();

        update1();
        int CS1 = -(Color.HSVToColor(0xff, values));

        update2();
        int CS2 = -(Color.HSVToColor(0xff, values));

        String ans=donut(CS1,CS2);

        telemetry.addData("CS1", CS1);
        telemetry.addData("CS2", CS2);
        telemetry.addData("There are ", ans," Donuts~");
        telemetry.update();

        switch (ans) {
            case "ZERO":
                Zero();
                break;
            case "ONE":
                One();
                break;
            case "FOUR":
                Four();
                break;
        }

        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        telemetry.addData("CS1", CS1);
        telemetry.addData("CS2", CS2);
        telemetry.addData("There are ", ans," Donuts~");
        telemetry.update();

        sleep(50000000);

    }

}
