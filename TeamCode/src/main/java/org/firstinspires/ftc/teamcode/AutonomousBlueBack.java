package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import org.firstinspires.ftc.teamcode.VisionOpMode;

@com.qualcomm.robotcore.eventloop.opmode.Autonomous(name = "Blue Back")
public class AutonomousBlueBack extends VisionOpMode {

    public void goCenter() {
        telemetry.addLine("PROP DETECTION: Center");
        telemetry.update();

    }

    public void goRight() {
        telemetry.addLine("PROP DETECTION: Right");
        telemetry.update();

    }

    public void goLeft() {
        telemetry.addLine("PROP DETECTION: Left");
        telemetry.update();

    }


}