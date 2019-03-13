package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name="FTC_2019_Auto_Red1", group ="FTC 2019")

public class FTC_2019_Auto_Red1 extends LinearOpMode {

    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    FTC_2019_TestBot_Init robot = new FTC_2019_TestBot_Init();

    public void initial(){

        robot.init(hardwareMap);
        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Rback.setPower(0);
        robot.Lback.setPower(0);

        robot.runModeSet("encoder");
        robot.runModeSet("reset");
        robot.runModeSet("position");

        robot.runModeSetLatching("encoder");
        robot.runModeSetLatching("reset");
        robot.runModeSetLatching("position");

        robot.Lkick.setPosition(0);
        robot.Rkick.setPosition(1);
        robot.Park.setPosition(0.56);
        robot.Claim.setPosition(robot.ClaimLevel);
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

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AXkVpHb/////AAABmTAE3zZYuEAqmWdab0pJQ9EH65J/3Dw/hnjqLlsJ6Lj4NKskFaXCfQ0yl5QyhVTIJinYPJ/553/NPU1F9fkSkX8xtgKVMEWdDwF5DC6tqN4D74iEIEyJzvye3/W1Mmryu9dmxyAdWJq+zVxqTRE+ELaw2cZDPMHnXVQ2NFeHvM6Eq9hNgkxzB1dy0WiC5BdftcPrsPdVuKsGRaWhKwXD8N87uO4+xeZIkx6lw7R3wWDW9IcLL6fQophrM1bA4kvOUA/GHk+paW6bSr07BfWCckBbFduvgTLtL5VwRXMr8MqHF9Vk80oWQYWYin5KevhfgiN9UUdoVFfl01O4RfqSbDOJg/FH+adPJl5io3PahBsj\n";

        vision = new org.firstinspires.ftc.teamcode.FTC2019.MasterVision(parameters, hardwareMap, true, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay
        vision.enable();// enables the tracking algorithms

        waitForStart();
        telemetry.addData("g1: ", vision.getTfLite().getG1());
        telemetry.addData("s1: ", vision.getTfLite().getS1());
        telemetry.addData("s2: ", vision.getTfLite().getS2());
        telemetry.addData("GOLD AFTERRRR: ", vision.getTfLite().getLastKnownSampleOrder());

        goldPosition = vision.getTfLite().getLastKnownSampleOrder();

        vision.disable();// disables tracking algorithms
        Latching(1,-24000);  // -23500

        telemetry.addData("g1: ", vision.getTfLite().getG1());
        telemetry.addData("s1: ", vision.getTfLite().getS1());
        telemetry.addData("s2: ", vision.getTfLite().getS2());
        telemetry.addData("Gold: ", goldPosition);
        telemetry.update();
        switch (goldPosition){
            case LEFT:
                telemetry.addLine("LEFT");
                sleep(9000);
                backward(35,1);  //from 37
                sleep(500);
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
                sleep(9000);
                break;


                case CENTER:
                    telemetry.addLine("CENTER");
                    sleep(9000);
                    backward(115,1);
                    Latching(1,0);
                    sleep(500);
                    turnleft(22.5,0.6);
                    sleep(500);
                    left(80,1);
                    sleep(500);
                    backward(40,1);                 //+40
                    robot.Claim.setPosition(robot.ClaimThrow);
                    sleep(500);
                    forward(225,1);  //-40
                    robot.Claim.setPosition(robot.ClaimLevel);
                    robot.Park.setPosition(0);
                    sleep(9000);
                    break;

                case RIGHT:
                    telemetry.addLine("RIGHT");
                    sleep(9000);
                    backward(33,0.6);
                    Latching(1,0);
                    sleep(500);
                    turnright(45,0.6);
                    backward(55,0.7);
                    turnleft(45,0.6);
    //                left(85,0.7);
                    robot.Rkick.setPosition(robot.kickopen);
                    sleep(500);
                    backward(30,1);
                    sleep(500);
                    robot.Rkick.setPosition(0); // close
                    sleep(500);
                    turnleft(17,0.7);
                    sleep(500);
                    left(50,0.6);
                    sleep(500);
                    backward(80,0.7);
                    robot.Claim.setPosition(robot.ClaimThrow);
                    sleep(1000);
                    forward(200,1);
                    robot.Rkick.setPosition(1);
                    robot.Claim.setPosition(robot.ClaimLevel);
                    robot.Park.setPosition(0);
                    sleep(9000);
                    break;

                case UNKNOWN:
                    telemetry.addLine("UNKNOWN");
                    sleep(9000);
                    backward(115,1);
                    Latching(1,0);
                    sleep(500);
                    turnleft(22.5,0.6);
                    sleep(500);
                    left(80,1);
                    sleep(500);
                    backward(40,1);                 //+40
                    robot.Claim.setPosition(robot.ClaimThrow);
                    sleep(500);
                    forward(225,1);  //-40
                    robot.Claim.setPosition(robot.ClaimLevel);
                    robot.Park.setPosition(0);
                    sleep(9000);
                    break;
        }
        robot.Park.setPosition(0.5);
        telemetry.update();

        /*robot.Lkick.setPosition(0.5);
        robot.Rkick.setPosition(0.5);*/

        //Latching(1,-23500);

        vision.shutdown();

    }
}

