package org.firstinspires.ftc.teamcode;

import static java.lang.Math.abs;

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

        DcMotor linearActuator = hardwareMap.dcMotor.get("test");
        DcMotor rotate = hardwareMap.dcMotor.get("rotate");

        Servo myServo = hardwareMap.servo.get("servo");

        double speed = 0;
        int togSP = 1;

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

            if (gamepad1.back || gamepad2.back) {
                togSP = abs(togSP - 1);
            }

            if (togSP == 1) {
                speed = 0.5;
            }

// Denominator is the largest motor power (absolute value) or 1
// This ensures all the powers maintain the same ratio,
// but only if at least one is out of the range [-1, 1]
            double denominator = Math.max(abs(y) + abs(x) + abs(rx), 1);
            double frontLeftPower = (y + x + rx) / denominator;
            double backLeftPower = (y - x + rx) / denominator;
            double frontRightPower = (y - x - rx) / denominator;
            double backRightPower = (y + x - rx) / denominator;

            // Speed
            if (gamepad1.a || gamepad2.a) {
                speed = 0;
            } else if (gamepad1.right_bumper || gamepad2.x) {
                speed = 0.25;
            } else if (gamepad1.left_bumper || gamepad2.y) {
                speed = 0.75;
            } else if (gamepad2.left_stick_button && (gamepad2.left_trigger > 0 && gamepad2.right_trigger > 0)) {
                speed = 1;
            } else if (gamepad1.start || gamepad2.b) {
                speed = 0.5;
            }

            // Airplane
            if ((gamepad1.b && gamepad2.b) || gamepad1.b && (gamepad1.left_trigger > 0 && gamepad1.right_trigger > 0)) {
                myServo.setPosition(0.5);
            }

            // Lift
            if (gamepad1.dpad_up) {
                // Extend the linear actuator
                linearActuator.setPower(1.0);  // Adjust power as needed
                telemetry.addData("Lift", "True");
            } else if (gamepad1.dpad_down) {
                // Retract the linear actuator
                linearActuator.setPower(-1.0);  // Adjust power as needed
                telemetry.addData("Lift", "True");
            } else {
                // Stop the linear actuator when neither button is pressed
                linearActuator.setPower(0.0);
                telemetry.addData("Lift", "False");
            }
            if (gamepad1.dpad_right) {
                // Rotate counterclockwise when X button is pressed
                rotate.setPower(-0.3); // Adjust power level as needed
            } else {
                // Stop the motor when neither X nor Y is pressed
                rotate.setPower(0);
            }

            frontLeftMotor.setPower(frontLeftPower * speed);
            backLeftMotor.setPower(backLeftPower * speed);
            frontRightMotor.setPower(frontRightPower * speed);
            backRightMotor.setPower(backRightPower * speed);

            telemetry.addData("Speed", speed * 100+"%");
            telemetry.addData("Servo", myServo.getPosition());
            telemetry.addData("TogSP", togSP);
            telemetry.update();
        }
    }
}