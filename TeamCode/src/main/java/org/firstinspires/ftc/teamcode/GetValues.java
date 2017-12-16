package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class GetValues extends OpMode {

    Chasis chasis = new Chasis();

    @Override
    public void init() {

        chasis.init(hardwareMap);

        chasis.frontLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chasis.frontRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chasis.backLeftDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chasis.backRightDrive.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        chasis.frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chasis.frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chasis.backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        chasis.backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void loop() {
        if(gamepad1.right_stick_x > 0.1) chasis.turnRight(gamepad1.right_stick_x);
        else if(gamepad1.dpad_right) chasis.strafeRight(1.0);
        else chasis.driveForward(-gamepad1.right_trigger+gamepad1.left_trigger);
        telemetry.addData("drive",chasis.frontLeftDrive.getCurrentPosition());
        telemetry.addData("drive",chasis.backLeftDrive.getCurrentPosition());
        telemetry.addData("drive",chasis.frontRightDrive.getCurrentPosition());
        telemetry.addData("drive",chasis.backRightDrive.getCurrentPosition());

        telemetry.update();
    }
}

