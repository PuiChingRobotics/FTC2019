package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.Gamepad;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;


@TeleOp(name="Latching_Testing", group="FTC 2019")
public class Latching_Testing extends OpMode{

    FTC_2019_USA_Init robot = new FTC_2019_USA_Init();

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

        if (gamepad1.dpad_up){
            robot.Latch.setPower(1);
        }
        else if (gamepad1.dpad_down){
            robot.Latch.setPower(-1);
        }
        else {
            robot.Latch.setPower(0);
        }

        //reset
        if (gamepad1.x){
            robot.Latch.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        }

        telemetry.addData("Encoder",robot.Latch.getCurrentPosition());
        telemetry.update();
    }
    @Override
    public void stop(){

    }
}
