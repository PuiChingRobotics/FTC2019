package org.firstinspires.ftc.teamcode.FTC2019;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.hardware.bosch.JustLoggingAccelerationIntegrator;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.Position;
import org.firstinspires.ftc.robotcore.external.navigation.Velocity;


@TeleOp(name = "IMU", group = "FTC2019")
public class IMUTest extends LinearOpMode {

    FTC_2019_USA_Init robot = new FTC_2019_USA_Init();

    @Override public void runOpMode() {
        robot.imu = hardwareMap.get(BNO055IMU.class, "imu");

        BNO055IMU.Parameters parameters = new BNO055IMU.Parameters();
        parameters.angleUnit = BNO055IMU.AngleUnit.DEGREES;
        parameters.accelUnit = BNO055IMU.AccelUnit.METERS_PERSEC_PERSEC;
        parameters.calibrationDataFile = "BNO055IMUCalibration.json";
        parameters.accelerationIntegrationAlgorithm = new JustLoggingAccelerationIntegrator();

        robot.imu.initialize(parameters);
        robot.imu.startAccelerationIntegration(new Position(), new Velocity(), 1000);
        parameters.mode = BNO055IMU.SensorMode.IMU;

        telemetry.addData("Please Press Start", "");
        waitForStart();

        while (opModeIsActive()) {
            Orientation orientation = robot.imu.getAngularOrientation(AxesReference.EXTRINSIC, AxesOrder.XYZ, AngleUnit.DEGREES);

            telemetry.addData("X Heading", orientation.firstAngle);
            telemetry.addData("Y Heading", orientation.secondAngle);
            telemetry.addData("Z Heading", orientation.thirdAngle);
            telemetry.update();
        }
    }
}