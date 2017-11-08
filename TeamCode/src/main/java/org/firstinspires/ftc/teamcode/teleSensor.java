package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * Created by User on 10/17/2017.
 */
@TeleOp

public class teleSensor extends OpMode {
    ColorSensor Color;
    DistanceSensor Distance;

    DcMotor frontLeftDrive ;
    DcMotor backLeftDrive ;
    DcMotor frontRightDrive ;
    DcMotor backRightDrive ;

    @Override
    public void init() {
        Color = hardwareMap.get(ColorSensor.class, "ColorD");
        Distance = hardwareMap.get(DistanceSensor.class, "ColorD");

        frontLeftDrive = hardwareMap.dcMotor.get("front_left_drive");
        backLeftDrive = hardwareMap.dcMotor.get("back_left_drive");
        frontRightDrive = hardwareMap.dcMotor.get("front_right_drive");
        backRightDrive = hardwareMap.dcMotor.get("back_right_drive");

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        telemetry.update();
        telemetry.addData("Alpha", Color.alpha());
        telemetry.addData("Red  ", Color.red());
        telemetry.addData("Green", Color.green());
        telemetry.addData("Blue ", Color.blue());
        telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", Distance.getDistance(DistanceUnit.CM)));




        if (gamepad1.right_trigger>0.1&&(gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1)){
            if (gamepad1.right_stick_x>0.1){
                frontLeftDrive.setPower(gamepad1.right_trigger);
                backLeftDrive.setPower(gamepad1.right_trigger);
                frontRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
                backRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
            }
            if (gamepad1.right_stick_x<-0.1){
                frontLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                backLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                frontRightDrive.setPower(gamepad1.right_trigger);
                backRightDrive.setPower(gamepad1.right_trigger);
            }
        }
        else if(gamepad1.right_trigger>0.1) {
            frontLeftDrive.setPower(gamepad1.right_trigger);
            backLeftDrive.setPower(gamepad1.right_trigger);
            frontRightDrive.setPower(gamepad1.right_trigger);
            backRightDrive.setPower(gamepad1.right_trigger);
        }
        //move backwards
        else if (gamepad1.left_trigger>0.1) {
            frontLeftDrive.setPower(-gamepad1.left_trigger);
            backLeftDrive.setPower(-gamepad1.left_trigger);
            frontRightDrive.setPower(-gamepad1.left_trigger);
            backRightDrive.setPower(-gamepad1.left_trigger);
        }
        //move right
        else if (gamepad1.dpad_right) {
            frontLeftDrive.setPower(1.0);
            backLeftDrive.setPower(-1.0);
            frontRightDrive.setPower(-1.0);
            backRightDrive.setPower(1.0);
        }
        //move left
        else if (gamepad1.dpad_left) {
            frontLeftDrive.setPower(-1.0);
            backLeftDrive.setPower(1.0);
            frontRightDrive.setPower(1.0);
            backRightDrive.setPower(-1.0);
        }
        //rotation right
        else if (gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1){
            frontLeftDrive.setPower(gamepad1.right_stick_x);
            backLeftDrive.setPower(gamepad1.right_stick_x);
            frontRightDrive.setPower(-gamepad1.right_stick_x);
            backRightDrive.setPower(-gamepad1.right_stick_x);
        }
        else {
            backLeftDrive.setPower(0.0);
            frontRightDrive.setPower(0.0);
            frontLeftDrive.setPower(0.0);
            backRightDrive.setPower(0.0);
        }
    }

    @Override
    public void stop() {
    }
}
