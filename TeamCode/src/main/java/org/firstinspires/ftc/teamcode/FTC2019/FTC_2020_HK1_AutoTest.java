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

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

@Autonomous(name="FTC_2020_HK1_AutoTest", group ="FTC 2020")

public class FTC_2020_HK1_AutoTest extends Nav {

    FTC_2020_HK1_Init robot = new FTC_2020_HK1_Init();

    //For Color Sensor
    // hsvValues is an array that will hold the hue, saturation, and value information.
    float hsvValues[] = {0F, 0F, 0F};

    // values is a reference to the hsvValues array.
    final float values[] = hsvValues;

    // sometimes it helps to multiply the raw RGB values with a scale factor
    // to amplify/attentuate the measured values.
    final double SCALE_FACTOR = 255;

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

    @Override
    public void runOpMode() {

        initial();

        waitForStart();



        if (opModeIsActive()) {
            while (Color.HSVToColor(0xff, values)<-9000||DS.getDistance(DistanceUnit.MM)<=30) {
                robot.Lfront.setPower(-0.1);)
                robot.Rfront.setPower(-0.1);
                robot.Lback.setPower(-0.1);
                robot.Rback.setPower(-0.1);
                // convert the RGB values to HSV values.
                // multiply by the SCALE_FACTOR.
                // then cast it back to int (SCALE_FACTOR is a double)
                Color.RGBToHSV((int) (robot.CS.red() * SCALE_FACTOR),
                        (int) (robot.CS.green() * SCALE_FACTOR /1.8 ),
                        (int) (robot.CS.blue() * SCALE_FACTOR /1.1 ),
                        hsvValues);

                // send the info back to driver station using telemetry function.
                telemetry.addData("Alpha", robot.CS.alpha());
                telemetry.addData("Red  ", robot.CS.red());
                telemetry.addData("Green", robot.CS.green());
                telemetry.addData("Blue ", robot.CS.blue());
                telemetry.addData("Hue", hsvValues[0]);
                telemetry.addData("Test", Color.HSVToColor(0xff, values));

                telemetry.update();

            }
            robot.Lfront.setPower(0);
            robot.Rfront.setPower(0);
            robot.Lback.setPower(0);
            robot.Rback.setPower(0);
        }


    }

}
