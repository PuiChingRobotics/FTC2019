package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

@Autonomous(name="Vision", group ="FTC 2019")

public class Vision extends LinearOpMode {

    MasterVision vision;
    SampleRandomizedPositions goldPosition;

    public void initial(){
    }

    @Override public void runOpMode() {

        initial();

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters();
        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        parameters.vuforiaLicenseKey = "AXkVpHb/////AAABmTAE3zZYuEAqmWdab0pJQ9EH65J/3Dw/hnjqLlsJ6Lj4NKskFaXCfQ0yl5QyhVTIJinYPJ/553/NPU1F9fkSkX8xtgKVMEWdDwF5DC6tqN4D74iEIEyJzvye3/W1Mmryu9dmxyAdWJq+zVxqTRE+ELaw2cZDPMHnXVQ2NFeHvM6Eq9hNgkxzB1dy0WiC5BdftcPrsPdVuKsGRaWhKwXD8N87uO4+xeZIkx6lw7R3wWDW9IcLL6fQophrM1bA4kvOUA/GHk+paW6bSr07BfWCckBbFduvgTLtL5VwRXMr8MqHF9Vk80oWQYWYin5KevhfgiN9UUdoVFfl01O4RfqSbDOJg/FH+adPJl5io3PahBsj\n";

        vision = new org.firstinspires.ftc.teamcode.FTC2019.MasterVision(parameters, hardwareMap, false, MasterVision.TFLiteAlgorithm.INFER_NONE);
        vision.init();// enables the camera overlay
        vision.enable();// enables the tracking algorithms

        waitForStart();

        while (true) {
            telemetry.addData("g1: ", vision.getTfLite().getG1());
            telemetry.addData("s1: ", vision.getTfLite().getS1());
            telemetry.addData("s2: ", vision.getTfLite().getS2());
            telemetry.addData("GOLD AFTERRRR: ", vision.getTfLite().getLastKnownSampleOrder());

            goldPosition = vision.getTfLite().getLastKnownSampleOrder();

            telemetry.addData("Gold: ", goldPosition);

            telemetry.update();

        }

    }
}

