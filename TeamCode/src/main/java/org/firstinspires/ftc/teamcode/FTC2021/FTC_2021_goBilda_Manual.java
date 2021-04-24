package org.firstinspires.ftc.teamcode.FTC2021;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="FTC2021_gobilda_Manual", group="FTC 2021")
public class FTC_2021_goBilda_Manual extends OpMode {

    FTC_2021_goBilda_Init robot = new FTC_2021_goBilda_Init();

    double speedmodifier = 1;
    boolean kaimove=false;
    boolean gamepad_b_prev = false;
    boolean gamepad_b_curr = false;

    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
        robot.runModeSet("encoder");
        robot.Grabber.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.Grabber.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Grabber.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start() {

    }

    @Override
    public void loop() {

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
        int liftdirection = 1;

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

        //Grabber
        if (gamepad2.dpad_down) {
            robot.Grabber.setTargetPosition(0);
            robot.Grabber.setPower(0.5);
        }
        else if (gamepad2.dpad_up) {
            robot.Grabber.setTargetPosition(450);
            robot.Grabber.setPower(0.5);
        }
        //Kai
        gamepad_b_prev = gamepad_b_curr;
        gamepad_b_curr = gamepad2.b;
        if (!gamepad_b_prev&&gamepad_b_curr) {
            kaimove=(!kaimove);
        }
        if (kaimove){
            robot.Kai.setPosition(1);
        }
        else {
            robot.Kai.setPosition(0.2);
        }
        //Intake
        if (gamepad2.left_bumper) {
            robot.Lift.setPower(-1);
            robot.Intake.setPower(1);
        }
        //reverse intake
        else if (gamepad2.left_trigger>.5) {
            robot.Intake.setPower(-1);

        }
        else {
            robot.Lift.setPower(0);
            robot.Intake.setPower(0);
        }


        //fliping
        if (gamepad2.right_bumper) {
            robot.Stack.setPosition(0.45);
        }
        else {
            robot.Stack.setPosition(0.15);
        }




        //pushStack
        //if (gamepad1.B) {
        //    robot.Stack.setPosition(0.15);
        //}

        //Fly
        if (gamepad1.left_bumper) {
            robot.Fly.setPower(-1);
            robot.Flyhigh.setPower(-1);
        }
        else {
            robot.Fly.setPower(0);
            robot.Flyhigh.setPower(0);
        }

        //Shoot
        if (gamepad1.right_bumper){
            robot.Shoot.setPosition(0.1);
        }
        else {
            robot.Shoot.setPosition(0.2);
        }

        //


    }


    @Override
    public void stop () {

    }
}
