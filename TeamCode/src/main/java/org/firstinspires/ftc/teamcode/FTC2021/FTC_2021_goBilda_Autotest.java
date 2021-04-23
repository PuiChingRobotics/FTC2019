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

    //find how many
    String donut(double cs_1, double cs_2) {
        if ((cs_1>=100)&&(cs_2>=100)) return "ZERO";
        else if (cs_1>=100) return "ONE";
        else return "FOUR";
    }

    void Zero () {
        go_forward(9,0,spd,false);
        go_sideways(90,0,0.5*spd,8);
        sleep(200);
        return;
    }

    void One () {
        go_forward(24,0,0.3,false);
        sleep(200);
        go_sideways(270,0,0.2*spd,10);
        sleep(200);
        return;
    }

    void Four () {
        go_forward(50,0,0.3,false);
        go_sideways(90,0,0.2*spd,12);
        sleep(200);
        return;
    }

    void place () {
        //robot.Grabber.setTargetPosition(-400);
        //sleep(200);
        //robot.Grabber.setPower(-0.5);
        //sleep(200);
        robot.Kai.setPosition(0.2);
        sleep(200);
    }

    void shoot (int n) {
        for (int i=0;i<n;i++) {
            robot.Shoot.setPosition(0.1);

            sleep(750);

            robot.Shoot.setPosition(0.2);

            sleep(200);

            robot.Stack.setPosition(0.45);

            sleep(500);

            robot.Stack.setPosition(0.15);

            sleep(200);
        }
    }

    double spd = .75; //speed multiplier

    @Override
    public void runOpMode() {

        initial();
        Nav_Init();

        robot.Grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Grabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);

        //kai
        robot.Kai.setPosition(1);
        //grabber
        robot.Grabber.setTargetPosition(0);
        robot.Grabber.setPower(1);

        robot.Shoot.setPosition(0.2);

        robot.Stack.setPosition(0.15);

        waitForStart();

        //go to scan the donuts
        go_forward(34,0,spd,false);

        sleep(200);

        //go_sideways(270,0,spd,1);

        //sleep(200);

        //scan the donuts
        double rs1 = robot.CS1.getDistance(DistanceUnit.MM);
        double rs2 = robot.CS2.getDistance(DistanceUnit.MM);
        String ans=donut(rs1,rs2);
        // output
        telemetry.addData("CS1", rs1);
        telemetry.addData("CS2", rs2);
        telemetry.addData("There are ", ans," Donuts~");
        telemetry.update();

        sleep(300);

        // go shoot shit
        //go_sideways(90,0,spd,3);
        //sleep(200);
        go_forward(7,0,spd,false);
        sleep(200);
        go_sideways(270,0,spd*0.5,10);
        sleep(200);
        robot.Fly.setPower(-1);
        robot.Flyhigh.setPower(-1);

        sleep(1000);
        //shoot

        robot.Shoot.setPosition(0.2);

        sleep(200);

        shoot(3);

        robot.Fly.setPower(0);
        robot.Flyhigh.setPower(0);


        //different result different path
        switch (ans) {
            case "ZERO":
                Zero();
                break;
            case "ONE":
                Zero();
                break;
            case "FOUR":
                Zero();
                break;
        }

        place();

        sleep(500);

        go_forward(21,0,-spd,false);

        sleep(200);

        turn_to_heading(180);

        sleep(200);

        go_sideways(90,180,spd*0.5,18);

        sleep(200);

        go_forward(9.5,180,spd*0.15,false);

        sleep(200);

        robot.Kai.setPosition(1);
        sleep(200);

        turn_to_heading(30);

        sleep(200);

        go_forward(34,30,spd,false);

        sleep(200);

        robot.Kai.setPosition(0.2);
        sleep(200);

        //robot.Grabber.setTargetPosition(100);
        //robot.Grabber.setPower(0.5);


        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        telemetry.addData("CS1", rs1);
        telemetry.addData("CS2", rs2);
        telemetry.addData("There are ", ans," Donuts~");
        telemetry.update();

        //sleep(50000000);

    }

}
