package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="FTC_2019_TestBot_Manual", group="FTC 2018")
public class FTC_2019_TestBot_Manual extends OpMode{

    FTC_2019_TestBot_Init robot = new FTC_2019_TestBot_Init();
    private boolean cp = true;
    private boolean clipb = true;
    private boolean kk = true;
    private boolean kick = true;
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

        robot.Lfront.setPower(robot.Lfronttmp);
        robot.Lback.setPower(robot.Lbacktmp);
        robot.Rfront.setPower(robot.Rfronttmp);
        robot.Rback.setPower(robot.Rbacktmp);
        if (gamepad1.y) {
            robot.Claim.setPosition(robot.ClaimLevel);  //Bk to Claim level
        }
        if (gamepad1.x) {
            robot.Claim.setPosition(robot.ClaimThrow); //Claim throw
        }
        if (gamepad2.x && clipb){
            clipb=false;
            if (cp) {
                robot.Clip.setPower(-1);
                cp=false;
            } else {
                robot.Clip.setPower(1);
                cp=true;
            }
        }
        if (gamepad2.x == false && clipb == false ){
            if (cp){
                robot.Clip.setPower(0);
            }
            clipb=true;
        }

        if (gamepad2.y && kick){
            kick=false;
            if (kk) {
                robot.Lkick.setPosition(robot.kickopen);
                robot.Rkick.setPosition(robot.kickopen);
                kk=false;
            } else {
                robot.Lkick.setPosition(0);
                robot.Rkick.setPosition(0);
                kk=true;
            }
        }
        if (gamepad2.y == false && kick == false ){
            kick=true;
        }
    /*    if (gamepad2.y){
            robot.Clip.setPower(-1);
        }*/
        if (gamepad2.dpad_left){
            robot.Park.setPosition(1);
        }
        else if (gamepad2.dpad_right) {
            robot.Park.setPosition(0.7);
        }
        if (gamepad2.right_bumper){
            robot.Flip.setPower(1);       //Flip Up
        }
        else if (gamepad2.left_bumper){
            robot.Flip.setPower(-1);      //Flip Down
        }
        else if (gamepad2.dpad_down){
            robot.Flip.setPower(-0.4);
        }
        else if (gamepad2.dpad_up){
            robot.Flip.setPower(0.4);
        }
        else  {
            robot.Flip.setPower(0);
        }

        /* if (gamepad2.right_bumper) {
            robot.Clip.setPower(1.0);
        }
        else if (gamepad2.left_bumper) {
            robot.Clip.setPower(-1);
        }*/


        if (gamepad1.left_bumper){
            robot.Latching.setPower(1);       //Latching DOWN
            robot.Latching2.setPower(1);       //Latching DOWN
        }
        else if (gamepad1.right_bumper){
            robot.Latching.setPower(-1);      //Latching UP
            robot.Latching2.setPower(-1);       //Latching UP

        }
        else  {
            robot.Latching.setPower(0);
            robot.Latching2.setPower(0);
        }
 /*       if (gamepad2.right_bumper) {
            robot.Clip.setPower(1.0);
        }else if (gamepad2.left_bumper) {
            robot.Clip.setPower(-1);
        }*/


/*        if (gamepad1.x){
            robot.runModeSetLatching("reset");
        }

        if (gamepad1.y) {
            robot.runModeSetLatching("encoder");
        }*/
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

            /*if (gamepad1.right_trigger > 0) {
                robot.Lfront.setPower(0.5);
                robot.Lback.setPower(0.5);
                robot.Rfront.setPower(0.5);
                robot.Rback.setPower(0.5);
            } else if (gamepad1.left_trigger > 0) {
                robot.Lfront.setPower(-0.5);
                robot.Lback.setPower(-0.5);
                robot.Rfront.setPower(-0.5);
                robot.Rback.setPower(-0.5);
            }*/

        //Player2
        telemetry.update();
    }
    @Override
    public void stop(){

    }
}
