package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;

import java.util.List;

@Autonomous(name="VisionJava", group ="FTC 2019")

public class FTC2019_Finalbot_USA_Auto_Base extends LinearOpMode {

    FTC_2019_TestBot_Init robot = new FTC_2019_TestBot_Init();

    String turn;

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

    public void turn (double Angle, double Power) {
        double TargetAngle = 0;

        Orientation orientation = robot.imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);
        robot.runModeSet("encoder");

        if (Angle < 0){
            turn = "left";
            TargetAngle = 360 + Angle;
        }
        else {
            turn = "right";
            TargetAngle = Angle;
        }

        if(turn == "right"){
                while (orientation.thirdAngle < TargetAngle && opModeIsActive()){
                    robot.Lfront.setPower(Power);
                    robot.Rfront.setPower(Power*-1);
                    robot.Lback.setPower(Power);
                    robot.Rback.setPower(Power*-1);
                }
        }
        else{           //turn left
            while (orientation.thirdAngle > TargetAngle){
                robot.Lfront.setPower(Power*-1);
                robot.Rfront.setPower(Power);
                robot.Lback.setPower(Power*-1);
                robot.Rback.setPower(Power);
            }
        }

        robot.Lfront.setPower(0);
        robot.Rfront.setPower(0);
        robot.Lback.setPower(0);
        robot.Rback.setPower(0);

    }


    public void left(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,-1,1,1,-1);
    }
    public void right(double Distance, double Power)
    {
        SetDistanceToGo(Distance, Power,1,-1,-1,1);
    }

    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AXkVpHb/////AAABmTAE3zZYuEAqmWdab0pJQ9EH65J/3Dw/hnjqLlsJ6Lj4NKskFaXCfQ0yl5QyhVTIJinYPJ/553/NPU1F9fkSkX8xtgKVMEWdDwF5DC6tqN4D74iEIEyJzvye3/W1Mmryu9dmxyAdWJq+zVxqTRE+ELaw2cZDPMHnXVQ2NFeHvM6Eq9hNgkxzB1dy0WiC5BdftcPrsPdVuKsGRaWhKwXD8N87uO4+xeZIkx6lw7R3wWDW9IcLL6fQophrM1bA4kvOUA/GHk+paW6bSr07BfWCckBbFduvgTLtL5VwRXMr8MqHF9Vk80oWQYWYin5KevhfgiN9UUdoVFfl01O4RfqSbDOJg/FH+adPJl5io3PahBsj\\n";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public String goldmineral = "UNKNOWN";

    public int g1 = 0;
    public int s1 = 0;
    public int s2 = 0;

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;

        //parameters.cameraName = robot.Webcam;  //use this when use webcam

        parameters.cameraDirection = CameraDirection.BACK; //use this when use phone

        vuforia = ClassFactory.getInstance().createVuforia(parameters);

    }

    private void initTfod() {
        int tfodMonitorViewId = hardwareMap.appContext.getResources().getIdentifier(
                "tfodMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        TFObjectDetector.Parameters tfodParameters = new TFObjectDetector.Parameters(tfodMonitorViewId);
        tfod = ClassFactory.getInstance().createTFObjectDetector(tfodParameters, vuforia);
        tfod.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL);
    }

    public void runOpMode() {

        robot.init(hardwareMap);

        initVuforia();

        if (ClassFactory.getInstance().canCreateTFObjectDetector()) {
            initTfod();
        } else {
            telemetry.addData("Sorry!", "This device is not compatible with TFOD");
        }

        telemetry.addData(">", "Press Play to start tracking");
        telemetry.update();
        waitForStart();
        ElapsedTime Timer = new ElapsedTime();
        Timer.reset();
        if (opModeIsActive()) {
            if (tfod != null) {
                tfod.activate();
            }

            while (opModeIsActive() && (goldmineral != null || Timer.seconds() > 10)) {
                if (tfod != null) {
                    // getUpdatedRecognitions() will return null if no new information is available since
                    // the last time that call was made.
                    List<Recognition> updatedRecognitions = tfod.getUpdatedRecognitions();
                    if (updatedRecognitions != null) {
                        telemetry.addData("# Object Detected", updatedRecognitions.size());
                        if (updatedRecognitions.size() == 3) {
                            int goldMineralX = -1;
                            int silverMineral1X = -1;
                            int silverMineral2X = -1;
                            for (Recognition recognition : updatedRecognitions) {
                                if (recognition.getLabel().equals(LABEL_GOLD_MINERAL)) {
                                    goldMineralX = (int) recognition.getTop();
                                } else if (silverMineral1X == -1) {
                                    silverMineral1X = (int) recognition.getTop();
                                } else {
                                    silverMineral2X = (int) recognition.getTop();
                                }
                            }

                            g1 = goldMineralX;
                            s1 = silverMineral1X;
                            s2 = silverMineral2X;

                            if (goldMineralX != -1 && silverMineral1X != -1 && silverMineral2X != -1) {
                                if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X) {
                                    goldmineral = "Left";
                                    telemetry.addData("Gold Mineral Position", "Left");
                                } else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X) {
                                    goldmineral = "Right";
                                    telemetry.addData("Gold Mineral Position", "Right");
                                } else {
                                    goldmineral = "Center";
                                    telemetry.addData("Gold Mineral Position", "Center");
                                }
                            }
                        }
                        telemetry.addData("Timer",Timer);
                        telemetry.addData("g1: ", g1);
                        telemetry.addData("s1: ", s1);
                        telemetry.addData("s2: ", s2);
                        telemetry.update();
                    }
                }
            }
        }

        if (tfod != null) {
            tfod.shutdown();
        }
        //Codes put bellow here
        switch (goldmineral){
            case "Left":



                break;
            case "Center":



                break;
            case "Right":



                break;
            case "UNKNOWN":



                break;
        }












    }

}