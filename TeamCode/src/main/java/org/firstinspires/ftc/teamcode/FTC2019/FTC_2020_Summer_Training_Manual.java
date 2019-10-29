package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.LightSensor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="FTC_2020_Summer_Training_Manual", group="FTC 2019")
public class FTC_2020_Summer_Training_Manual extends OpMode {

    FTC_2020_Summer_Training_Init robot = new FTC_2020_Summer_Training_Init();

    double speedmodifier = 1;

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
    }
    public double RoundDownDp(double value, double place){
        double result = value / place;
        result = Math.floor(result)*place;
        return result;
    }
    @Override
    public void loop() {
        /*
        //drive
        double leftstickx = 0;
        double leftsticky = 0;
        double rightstickx = 0;
        double wheelpower;
        double stickangleradians;
        double rightX;
        double leftfrontpower;
        double rightfrontpower;
        double leftrearpower;
        double rightrearpower;
        double dpadpower = .2;
        double dpadturningpower = .4;

        if (gamepad1.left_stick_button) {
            speedmodifier = 1;
        }
        if (gamepad1.right_stick_button) {
            speedmodifier = .5;
        }

        if (gamepad1.dpad_up) {
            leftsticky = dpadpower;
        } else if (gamepad1.dpad_right) {
            leftstickx = dpadturningpower;
        } else if (gamepad1.dpad_down) {
            leftsticky = -dpadpower;
        } else if (gamepad1.dpad_left) {
            leftstickx = -dpadturningpower;
        } else {
            leftstickx = gamepad1.left_stick_x;
            leftsticky = -gamepad1.left_stick_y;
            rightstickx = gamepad1.right_stick_x * speedmodifier;
        }


        if (Math.abs(leftsticky) <= .15) {
            leftsticky = 0;
        }
        wheelpower = Math.hypot(leftstickx, leftsticky);
        stickangleradians = Math.atan2(leftsticky, leftstickx);

        stickangleradians = stickangleradians - Math.PI / 4; //adjust by 45 degrees

        rightX = rightstickx * .5;
        leftfrontpower = wheelpower * Math.cos(stickangleradians) + rightX;
        rightfrontpower = wheelpower * Math.sin(stickangleradians) - rightX;
        leftrearpower = wheelpower * Math.sin(stickangleradians) + rightX;
        rightrearpower = wheelpower * Math.cos(stickangleradians) - rightX;

        robot.Lfront.setPower(leftfrontpower);
        robot.Rfront.setPower(rightfrontpower);
        robot.Lback.setPower(leftrearpower);
        robot.Rback.setPower(rightrearpower);
        */
        double leftStickX = gamepad1.left_stick_x;
        double leftStickY = -gamepad1.left_stick_y;
        double rightStickX = gamepad1.right_stick_x;
        double rightStickY = -gamepad1.right_stick_y;
        double g2leftStickX = gamepad2.left_stick_x;
        double g2leftStickY = -gamepad2.left_stick_y;
        double g2rightStickX = gamepad2.right_stick_x;
        double g2rightStickY = -gamepad2.right_stick_y;

        double speed_new = 1-0.7;
        double speed_old = 0.7;

        telemetry.addData("Running", "Robot 1");

        //player1

        //drive
        robot.Lfrontforward = RoundDownDp(robot.Lfrontforward*speed_old+leftStickY*speed_new, 0.001);
        robot.Rfrontforward = RoundDownDp(robot.Rfrontforward*speed_old+leftStickY*speed_new, 0.001);
        robot.Lbackforward = RoundDownDp(robot.Lbackforward*speed_old+leftStickY*speed_new, 0.001);
        robot.Rbackforward = RoundDownDp(robot.Rbackforward*speed_old+leftStickY*speed_new, 0.001);

        robot.Lfronttmp = RoundDownDp(leftStickY*1+rightStickX*0.7+leftStickX*0.7, 0.001);
        robot.Lbacktmp = RoundDownDp(leftStickY*1+rightStickX*0.7-leftStickX*0.7, 0.001);
        robot.Rfronttmp = RoundDownDp(leftStickY*1-rightStickX*0.7-leftStickX*0.7, 0.001);
        robot.Rbacktmp = RoundDownDp(leftStickY*1-rightStickX*0.7+leftStickX*0.7, 0.001);

        robot.Lfront.setPower(robot.Lfronttmp);
        robot.Lback.setPower(robot.Lbacktmp);
        robot.Rfront.setPower(robot.Rfronttmp);
        robot.Rback.setPower(robot.Rbacktmp);

        //servo

        if (gamepad1.dpad_up) {
            robot.S1.setPosition(0.1);
        } else if (gamepad1.dpad_down) {
            robot.S1.setPosition(0.45);
        }

    }
}
