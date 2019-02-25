package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="FTC_2019_TestBot_Manual", group="FTC 2018")
public class FTC_2019_TestBot_Manual_Test extends OpMode{

    FTC_2019_TestBot_Init robot = new FTC_2019_TestBot_Init();

    double speedmodifier = 1;

    @Override
    public void init(){
        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
    }

    @Override
    public void init_loop() {

    }

    @Override
    public void start(){

    }

    public double RoundDownDp(double value, double place){
        double result = value / place;
        result = Math.floor(result)*place;
        return result;
    }

    @Override
    public void loop() {

        double leftStickX = gamepad1.left_stick_x;
        double leftStickY = -gamepad1.left_stick_y;
        double rightStickX = gamepad1.right_stick_x;
        double rightStickY = -gamepad1.right_stick_y;

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

        if (gamepad1.right_bumper) {
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

        telemetry.addData("gamepad1", !gamepad1.atRest());
        telemetry.addData("leftStickX", leftStickX);
        telemetry.addData("leftStickY", leftStickY);
        telemetry.addData("rightStickX", rightStickX);
        telemetry.addData("rightStickY", rightStickY);

        telemetry.addData("Lfrontforward", robot.Lfrontforward);
        telemetry.addData("Rfrontforward", robot.Rfrontforward);
        telemetry.addData("Lbackforward", robot.Lbackforward);
        telemetry.addData("Rbackforward", robot.Rbackforward);

        telemetry.addData("Lfronttmp", robot.Lfronttmp);
        telemetry.addData("Rfronttmp", robot.Rfronttmp);
        telemetry.addData("Lbacktmp", robot.Lbacktmp);
        telemetry.addData("Rbacktmp", robot.Rbacktmp);

        telemetry.addData("Flip", robot.Flip.getPower());

        //Player2
        telemetry.update();
    }
    @Override
    public void stop(){

    }
}
