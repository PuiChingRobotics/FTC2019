package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import java.util.List;
import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer.CameraDirection;
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector;
import org.firstinspires.ftc.robotcore.external.tfod.Recognition;

@Autonomous(name="VisionJava", group ="FTC 2019")

public class VisionJava extends LinearOpMode {
    private static final String TFOD_MODEL_ASSET = "RoverRuckus.tflite";
    private static final String LABEL_GOLD_MINERAL = "Gold Mineral";
    private static final String LABEL_SILVER_MINERAL = "Silver Mineral";

    private static final String VUFORIA_KEY = "AXkVpHb/////AAABmTAE3zZYuEAqmWdab0pJQ9EH65J/3Dw/hnjqLlsJ6Lj4NKskFaXCfQ0yl5QyhVTIJinYPJ/553/NPU1F9fkSkX8xtgKVMEWdDwF5DC6tqN4D74iEIEyJzvye3/W1Mmryu9dmxyAdWJq+zVxqTRE+ELaw2cZDPMHnXVQ2NFeHvM6Eq9hNgkxzB1dy0WiC5BdftcPrsPdVuKsGRaWhKwXD8N87uO4+xeZIkx6lw7R3wWDW9IcLL6fQophrM1bA4kvOUA/GHk+paW6bSr07BfWCckBbFduvgTLtL5VwRXMr8MqHF9Vk80oWQYWYin5KevhfgiN9UUdoVFfl01O4RfqSbDOJg/FH+adPJl5io3PahBsj\\n";

    private VuforiaLocalizer vuforia;
    private TFObjectDetector tfod;

    public String goldmineral = null;

    public int g1 = 0;
    public int s1 = 0;
    public int s2 = 0;

    private void initVuforia() {
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();

        parameters.vuforiaLicenseKey = VUFORIA_KEY;
        parameters.cameraDirection = CameraDirection.BACK;

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













    }

}