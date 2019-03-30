package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="FTC_2019_USA_Manual", group="FTC 2019")
public class FTC_2019_USA_Manual extends OpMode{

    FTC_2019_USA_Init robot = new FTC_2019_USA_Init();

    double speedmodifier = 1;

    @Override
    public void init(){
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
        robot.runModeSet("encoder");
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start(){

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

        /*if (gamepad1.right_bumper) {
            speedmodifier = .5;
        }
        if (gamepad1.left_bumper) {
            speedmodifier = 1;
        }

        if (gamepad1.dpad_up) {
            leftsticky = dpadpower;
        } else if (gamepad1.dpad_right) {
            leftstickx = dpadturningpower;
        } else if (gamepad1.dpad_down) {
            leftsticky = -dpadpower;
        } else if (gamepad1.dpad_left) {
            leftstickx = -dpadturningpower;
        } else {*/
        leftstickx = gamepad1.left_stick_x;
        leftsticky = -gamepad1.left_stick_y;
        rightstickx = gamepad1.right_stick_x * speedmodifier;
        //{
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


        //Control Latching
        if (gamepad1.x){
            robot.Latch.setPower(1);
        }
        else if (gamepad1.y){
            robot.Latch.setPower(-1);
        }else{
            robot.Latch.setPower(0);
        }

        //Control LS(s)
        if (gamepad1.right_bumper){ //Down
            robot.LS2.setPower(-1);
        }
        else if (gamepad1.left_bumper){
            robot.LS2.setPower(1);
        }else{
            robot.LS2.setPower(0);
        }

        if (gamepad1.dpad_up){
            robot.Hammer.setPosition(0.5);
        }else if (gamepad1.dpad_left){
            robot.Hammer.setPosition(0.2);
        }else if (gamepad1.dpad_down){
            robot.Hammer.setPosition(0.03);
        }

        /*if (gamepad1.x){ //back
            robot.MCR.setPosition(0.7);
            robot.MCL.setPosition(0.3);
        }
        else if (gamepad1.y){
            robot.MCR.setPosition(0.2);
            robot.MCL.setPosition(0.8);
        }*/

        if (gamepad2.b){
            robot.MC.setPower(1);
        }
        else if (gamepad2.a){
            robot.MC.setPower(0);
        }

        if (gamepad2.x){
            robot.MCRotate.setPower(1);
        }
        else if (gamepad2.y){
            robot.MCRotate.setPower(-1);
        }
        else {
            robot.MCRotate.setPower(0);
        }

        if (gamepad2.left_bumper){
            robot.LS1.setPower(1);
        }
        else if (gamepad2.right_bumper){
            robot.LS1.setPower(-0.5);
        }
        else {
            robot.LS1.setPower(0);
        }

        //Player2
        telemetry.update();
    }
    @Override
    public void stop(){

    }
}
