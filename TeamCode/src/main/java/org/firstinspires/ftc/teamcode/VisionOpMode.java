package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;

import org.firstinspires.ftc.teamcode.CSVisionProcessor;
import org.firstinspires.ftc.vision.VisionPortal;

@Autonomous
public abstract class VisionOpMode extends LinearOpMode {

    private CSVisionProcessor visionProcessor;
    private VisionPortal visionPortal;

    public abstract void goLeft();

    public abstract void goRight();

    public abstract void goCenter();

    @Override
    public void runOpMode() {

        //pixelDropperYellow = new PixelDropper(hardwareMap, telemetry, "pixelDropperYellow");

        visionProcessor = new CSVisionProcessor();
        visionPortal = VisionPortal.easyCreateWithDefaults(hardwareMap.get(WebcamName.class, "MavCam"), visionProcessor);

        CSVisionProcessor.StartingPosition startingPos = CSVisionProcessor.StartingPosition.NONE;
        //driveChassis = new MecanumDriveChassis(hardwareMap, telemetry);
        //waitForStart();

        while (!this.isStarted() && !this.isStopRequested()) {
            startingPos = visionProcessor.getStartingPosition();
            telemetry.addData("Identified", visionProcessor.getStartingPosition());
            telemetry.update();
        }

        visionPortal.stopStreaming();

        // run until the end of the match (driver presses STOP)
        while (opModeIsActive()) {
            //use the value of startingPos to determine your location
            switch (startingPos) {
                case LEFT:
                    goLeft();
                    break;
                case RIGHT:
                    goRight();
                    break;
                case CENTER:
                    goCenter();
                    break;
                case NONE:
                    // Handle the case where no object is detected
                    break;
            }
        }
    }
}