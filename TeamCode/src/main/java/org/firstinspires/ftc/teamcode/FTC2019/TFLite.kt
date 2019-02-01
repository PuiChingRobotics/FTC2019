package org.firstinspires.ftc.teamcode.FTC2019

import org.firstinspires.ftc.robotcore.external.ClassFactory
import org.firstinspires.ftc.robotcore.external.tfod.TFObjectDetector

class TFLite(private val master: MasterVision) {
    companion object {
        private const val TFOD_MODEL_ASSET = "RoverRuckus.tflite"
        private const val LABEL_GOLD_MINERAL = "Gold Mineral"
        private const val LABEL_SILVER_MINERAL = "Silver Mineral"
    }

    private var tfod: TFObjectDetector? = null
    private val tfodMoniterViewId = master.hMap.appContext.resources.getIdentifier("tfodMonitorViewId", "id", master.hMap.appContext.packageName)
    private val parameters = TFObjectDetector.Parameters(tfodMoniterViewId)

    fun init() {
        if (tfod == null) {
            tfod = ClassFactory.getInstance().createTFObjectDetector(parameters, master.vuforiaLocalizer)
            tfod?.loadModelFromAsset(TFOD_MODEL_ASSET, LABEL_GOLD_MINERAL, LABEL_SILVER_MINERAL)
        }
    }

    var lastKnownSampleOrder = SampleRandomizedPositions.UNKNOWN

    internal fun updateSampleOrder() {
        if (tfod != null) {
            val updatedRecognitions = tfod?.updatedRecognitions
            if (updatedRecognitions != null) {
                if (updatedRecognitions.size == 3 || updatedRecognitions.size == 2) {
                    var goldMineralX: Float? = null
                    var silverMineral1X: Float? = null
                    var silverMineral2X: Float? = null

                    for (recognition in updatedRecognitions) {
                        if (recognition.label == LABEL_GOLD_MINERAL)
                            goldMineralX = recognition.left
                        else if (silverMineral1X == null)
                            silverMineral1X = recognition.left
                        else
                            silverMineral2X = recognition.left
                    }
                    when (master.tfLiteAlgorithm) {
                        MasterVision.TFLiteAlgorithm.INFER_NONE  -> if (goldMineralX != null && silverMineral1X != null && silverMineral2X != null)
                            if (updatedRecognitions.size == 3) {
                                lastKnownSampleOrder =
                                        if (goldMineralX < silverMineral1X && goldMineralX < silverMineral2X)
                                            SampleRandomizedPositions.LEFT
                                        else if (goldMineralX > silverMineral1X && goldMineralX > silverMineral2X)
                                            SampleRandomizedPositions.RIGHT
                                        else
                                            SampleRandomizedPositions.CENTER
                            }
                        MasterVision.TFLiteAlgorithm.INFER_LEFT  -> {
                            if(updatedRecognitions.size == 2) {
                                if (goldMineralX == null)
                                    lastKnownSampleOrder = SampleRandomizedPositions.LEFT
                                else if (silverMineral1X != null)
                                    lastKnownSampleOrder =
                                            if (goldMineralX < silverMineral1X)
                                                SampleRandomizedPositions.CENTER
                                            else
                                                SampleRandomizedPositions.RIGHT
                            }
                        }
                        MasterVision.TFLiteAlgorithm.INFER_RIGHT -> {
                            if(updatedRecognitions.size == 2) {
                                if (goldMineralX == null)
                                    lastKnownSampleOrder = SampleRandomizedPositions.RIGHT
                                else if (silverMineral1X != null)
                                    lastKnownSampleOrder =
                                            if (goldMineralX < silverMineral1X)
                                                SampleRandomizedPositions.LEFT
                                            else
                                                SampleRandomizedPositions.CENTER
                            }
                        }
                    }
                }
            }
        }
    }

    fun enable() {
        tfod?.activate()
    }

    fun disable() {
        tfod?.deactivate()
    }

    fun shutdown() {
        tfod?.shutdown()
    }

}