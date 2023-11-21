package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

@TeleOp
public class Drive extends LinearOpMode {
    @Override
    public void runOpMode() throws InterruptedException {
// Declare our motors
// Make sure your ID's match your configuration
        DcMotor frontLeftMotor = hardwareMap.dcMotor.get("Left Front Motor");
        DcMotor backLeftMotor = hardwareMap.dcMotor.get("Left Back Motor");
        DcMotor frontRightMotor = hardwareMap.dcMotor.get("Right Front Motor");
        DcMotor backRightMotor = hardwareMap.dcMotor.get("Right Back Motor");

        Servo myServo = hardwareMap.servo.get("servo");

// Reverse the right side motors. This may be wrong for your setup.
// If your robot moves backwards when commanded to go forwards,
// reverse the left side instead.
// See the note about this earlier on this page.
        frontLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);
        backLeftMotor.setDirection(DcMotorSimple.Direction.REVERSE);

        waitForStart();

        if (isStopRequested()) return;

        while (opModeIsActive()) {
            double y = -gamepad1.left_stick_y; // Remember, Y stick value is reversed
            double x = gamepad1.left_stick_x * 1.1; // Counteract imperfect strafing
            double rx = gamepad1.right_stick_x;
            double speed = 0;

// Denominator is the largest motor power (absolute value) or 1
// This ensures all the powers maintain the same ratio,
// but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(Math.abs(y) + Math.abs(x) + Math.abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            if (gamepad1.a || gamepad2.a) {
                speed = 0;
            } else if (gamepad1.right_bumper || gamepad2.x) {
                speed = 0.25;
            } else if (gamepad1.left_bumper || gamepad2.y) {
                speed = 0.75;
            } else if (gamepad2.left_stick_button && (gamepad2.left_trigger > 0 && gamepad2.right_trigger > 0)){
                speed = 1;
            } else {
                speed = 0.5;
            }
            if ((gamepad1.b && gamepad2.b) || gamepad1.b && (gamepad1.left_trigger > 0 && gamepad1.right_trigger > 0)) {
                myServo.setPosition(0.5);
            }
            frontLeftMotor.setPower(frontLeftPower * speed);
            backLeftMotor.setPower(backLeftPower * speed);
            frontRightMotor.setPower(frontRightPower * speed);
            backRightMotor.setPower(backRightPower * speed);

            telemetry.addData("Speed", speed);
            telemetry.addData("Servo", myServo.getPosition());
            telemetry.update();
        }
    }
}