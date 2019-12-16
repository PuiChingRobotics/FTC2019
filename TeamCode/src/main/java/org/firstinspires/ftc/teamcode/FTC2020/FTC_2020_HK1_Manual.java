package org.firstinspires.ftc.teamcode.FTC2020;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DigitalChannel;


@TeleOp(name="FTC_2020_HK1_Manual", group="FTC 2020")
public class FTC_2020_HK1_Manual extends OpMode {
    FTC_2020_HK1_Init robot = new FTC_2020_HK1_Init();

    private double speedmodifier = 1;

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
        //robot.TS1.setMode(DigitalChannel.Mode.INPUT);
        robot.runModeSet("encoder");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

        //Player 1

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

        //Player 1
        //LS1
        if (gamepad1.left_bumper) {
            robot.LS1.setPower(0.3);
        }
        else if (gamepad1.right_bumper) {
            robot.LS1.setPower(-0.3);
        }
        else {
            robot.LS1.setPower(0);
        }

        //Player 2
        //LS2
        if (gamepad2.left_trigger > 0.1) {
            robot.LS2.setPower(-gamepad2.left_trigger);
        }
        else if (gamepad2.right_trigger > 0.1) {
            robot.LS2.setPower(gamepad2.right_trigger*.1);
        }
        else {
            robot.LS2.setPower(-0.3);
        }

        //Clip
        if (gamepad2.b) {
            robot.Clip.setPower(-1);
        }
        else if (gamepad2.a) {
            robot.Clip.setPower(0);
        }
        else {
            robot.Clip.setPower(1);
        }

        //Hammer
        if (gamepad2.dpad_up) {
            robot.Hammer.setPosition(0.1);
            robot.HammerR.setPosition(0.1);
            robot.HammerL.setPosition(0.65);
        } else if (gamepad2.dpad_down) {
            robot.Hammer.setPosition(0.7);
            robot.HammerR.setPosition(0.7);
            robot.HammerL.setPosition(0.05);
        }

    }

    @Override
    public void stop () {

    }

}
