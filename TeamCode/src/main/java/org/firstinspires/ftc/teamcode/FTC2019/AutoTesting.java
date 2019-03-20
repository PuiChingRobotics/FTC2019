package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;

@Autonomous(name="AutoTesting", group ="FTC 2019")

public class AutoTesting extends Nav {

    /*MasterVision vision;
    SampleRandomizedPositions goldPosition;*/
    FTC_2019_TestBot_Init robot = new FTC_2019_TestBot_Init();

    public void initial(){

        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        robot.runModeSet("reset");
        robot.runModeSet("encoder");
    }

    public void Latching(double Power, int Posistion){
        robot.Latching.setTargetPosition(Posistion);
        robot.Latching2.setTargetPosition(Posistion);

        robot.Latching.setPower(Power);
        robot.Latching2.setPower(Power);
    }

    public void SetDistanceToGo(double DistanceInCm, double LocalPowerAll, int LfrontEncoder, int RfrontEncoder, int LbackEncoder, int RbackEncoder){
        double DiameterOfWheel = 10;
        final double EncoderValue = 1680;
        final double GearRatio = 1;
        final double Pi = Math.PI;
        double Circumference = DiameterOfWheel*Pi;
        double DistanceOfEachCircleOfMotor = Circumference*GearRatio;
        double ValueForEncoderFor1Cm = (EncoderValue/DistanceOfEachCircleOfMotor);
        double ValueForEncoderTemp = 0;
        int ValueForEncoder = 0;
        int tmp = 0;

        ValueForEncoderTemp = ValueForEncoderFor1Cm*DistanceInCm;
        ValueForEncoder = (int) ValueForEncoderTemp;

        robot.runModeSet("reset");
        robot.runModeSet("position");

        robot.Lfront.setTargetPosition(ValueForEncoder*LfrontEncoder);
        robot.Rfront.setTargetPosition(ValueForEncoder*RfrontEncoder);
        robot.Lback.setTargetPosition(ValueForEncoder*LbackEncoder);
        robot.Rback.setTargetPosition(ValueForEncoder*RbackEncoder);

        robot.Lfront.setPower(LocalPowerAll);
        robot.Rfront.setPower(LocalPowerAll);
        robot.Lback.setPower(LocalPowerAll);
        robot.Rback.setPower(LocalPowerAll);

        while (opModeIsActive() && robot.Lfront.isBusy() && robot.Rfront.isBusy() && robot.Lback.isBusy() && robot.Rback.isBusy()){
            telemetry.update();
        }

        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);
    }

    public void forward(double Distance, double Power)
    {

        SetDistanceToGo(Distance, Power,1,1,1,1);
    }

    public void backward(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,-1,-1,-1,-1);
    }

    public void turnleft(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,-1,1,-1,1);
    }

    public void turnright(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,1,-1,1,-1);
    }
    public void left(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,-1,1,1,-1);
    }
    public void right(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,1,-1,-1,1);
    }

    @Override public void runOpMode() {

        initial();

        Nav_Init();

        //go_forward(14, 135, -.4, true);

        go_sideways(90, 0, .5, 15);

        telemetry.addData("encoder",robot.Lfront.getCurrentPosition());
        telemetry.addData("encoder2",robot.Rfront.getCurrentPosition());
        telemetry.addData("encoder3",robot.Lback.getCurrentPosition());
        telemetry.addData("encoder4",robot.Rback.getCurrentPosition());

        telemetry.update();

        //Code for testing in Auto
        /*backward(37,1);
        Latching(1,0);
        sleep(500);
        turnleft(45,0.6);
        sleep(500);
        backward(30,1);
        sleep(500);
        turnright(45,0.6);
        sleep(500);
        backward(50,1);
        sleep(1000);
        turnright(20,0.6);
        sleep(500);
        backward(40,1);
        sleep(1000);
        turnleft(48,0.6);
        sleep(500);
        left(60,1);
        robot.Claim.setPosition(robot.ClaimThrow);
        sleep(500);
        forward(240,1);
        robot.Claim.setPosition(robot.ClaimLevel);
        robot.Park.setPosition(0);
        sleep(9000);*/

   /*     turnleft(28,0.6);
        sleep(500);
        left(40,1);
        robot.Claim.setPosition(robot.ClaimThrow);
        sleep(500);
        forward(315,1);
        robot.Claim.setPosition(robot.ClaimLevel);
        robot.Park.setPosition(0);*/

        sleep(10000);

    }
}

