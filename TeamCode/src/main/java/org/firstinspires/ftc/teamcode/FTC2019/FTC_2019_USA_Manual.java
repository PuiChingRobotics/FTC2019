package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;


@TeleOp(name="FTC_2019_USA_Manual", group="FTC 2019")
public class FTC_2019_USA_Manual extends OpMode {

    FTC_2019_USA_Init robot = new FTC_2019_USA_Init();

    double speedmodifier = 1;

    public boolean Sweep = true;
    public boolean Check = true;
    @Override
    public void init() {
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
        robot.runModeSet("encoder");
        robot.Latch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.Latch.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        robot.MCRotate.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        robot.MCRotate.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
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
                //Latching
                if (gamepad1.left_bumper && robot.Latch.getCurrentPosition() > robot.Latch_Limit) {
                    robot.Latch.setPower(-1);
                } else if (gamepad1.right_bumper) {
                    robot.Latch.setPower(1);
                } else {
                    robot.Latch.setPower(0);
                }

                //Mineral LS
                if (gamepad1.left_trigger > 0.1) { //Down
                    robot.LS2.setPower(-gamepad1.left_trigger);
                } else if (gamepad1.right_trigger > 0.1) {
                    robot.LS2.setPower(gamepad1.right_trigger);
                } else {
                    robot.LS2.setPower(0);
                }

                //MC Servo(s)
                if (gamepad1.x){ //back
                    robot.MCR.setPosition(0.7);
                    robot.MCL.setPosition(0.3);
                }
                else if (gamepad1.y){
                    robot.MCR.setPosition(0.2);
                    robot.MCL.setPosition(0.8);
                }

                if (gamepad2.left_stick_button)
                {
                    robot.MCR.setPosition(0.45);
                    robot.MCL.setPosition(0.55);
                }
                //Player 2
                //Sorter Position
                if (gamepad2.dpad_up) {
                    robot.Hammer.setPosition(0.6);
                } else if (gamepad2.dpad_left) {
                    robot.Hammer.setPosition(0.2);
                } else if (gamepad2.dpad_down) {
                    robot.Hammer.setPosition(0.03);
                }

                //Sweeping
                if (gamepad2.b && Sweep == true) {
                    Sweep = false;
                    if (Check) {
                        robot.MC.setPower(1);
                        Check = false;
                    } else {
                        robot.MC.setPower(0);
                        Check = true;
                    }
                }

                if(gamepad2.a && Sweep == true){
                    Sweep = false;
                    if (Check) {
                        robot.MC.setPower(-1);
                        Check = false;
                    } else {
                        robot.MC.setPower(0);
                        Check = true;
                    }
                }

                 if(gamepad2.a==false && gamepad2.b == false && Sweep == false){
                 //let the sweep work
                  Sweep = true;
                 }
        telemetry.addData("servo ",Check);
        telemetry.addData("servo sweep ",Sweep);
        telemetry.update();
        //MC Motor Rotation
        if (gamepad2.left_trigger > 0.1) {
                    robot.MCRotate.setPower(gamepad2.left_trigger);
        } else if (gamepad2.right_trigger > 0.1) {
                    robot.MCRotate.setPower(-gamepad2.right_trigger);
        }
        else{
                    robot.MCRotate.setPower(0);
        }

                //Sorter LS
                if (gamepad2.left_bumper) {
                    robot.LS1.setPower(1);
                } else if (gamepad2.right_bumper) {
                    robot.LS1.setPower(-0.5);
                } else {
                    robot.LS1.setPower(0);
                }
                telemetry.addData("MCRotate",robot.MCRotate.getCurrentPosition());
                telemetry.update();
            }

        @Override
        public void stop () {

        }
}
