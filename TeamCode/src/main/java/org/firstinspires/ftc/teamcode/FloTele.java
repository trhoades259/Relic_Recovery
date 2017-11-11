package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;

import java.util.Locale;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class FloTele extends OpMode {
    MechHardware robot = new MechHardware();

    @Override
    public void init() {
        robot.init(hardwareMap);
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
        telemetry.addData("Alpha", robot.Color.alpha());
        telemetry.addData("Red  ", robot.Color.red());
        telemetry.addData("Green", robot.Color.green());
        telemetry.addData("Blue ", robot.Color.blue());
        telemetry.addData("Distance (cm)", String.format(Locale.US, "%.02f", robot.Distance.getDistance(DistanceUnit.CM)));
        /*  FORZA STYLE
        if (gamepad1.right_trigger>0.1&&(gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1)){
            if (gamepad1.right_stick_x>0.1){
                robot.frontLeftDrive.setPower(gamepad1.right_trigger);
                robot.backLeftDrive.setPower(gamepad1.right_trigger);
                robot.frontRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
                robot.backRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
            }
            if (gamepad1.right_stick_x<-0.1){
                robot.frontLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                robot.backLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                robot.frontRightDrive.setPower(gamepad1.right_trigger);
                robot.backRightDrive.setPower(gamepad1.right_trigger);
            }
        }
        else if(gamepad1.right_trigger>0.1) {
            robot.frontLeftDrive.setPower(gamepad1.right_trigger);
            robot.backLeftDrive.setPower(gamepad1.right_trigger);
            robot.frontRightDrive.setPower(gamepad1.right_trigger);
            robot.backRightDrive.setPower(gamepad1.right_trigger);
        }
        //move backwards
        else if (gamepad1.left_trigger>0.1) {
            robot.frontLeftDrive.setPower(-gamepad1.left_trigger);
            robot.backLeftDrive.setPower(-gamepad1.left_trigger);
            robot.frontRightDrive.setPower(-gamepad1.left_trigger);
            robot.backRightDrive.setPower(-gamepad1.left_trigger);
        }
        //move right
        else if (gamepad1.dpad_right) {
            robot.frontLeftDrive.setPower(1.0);
            robot.backLeftDrive.setPower(-1.0);
            robot.frontRightDrive.setPower(-1.0);
            robot.backRightDrive.setPower(1.0);
        }
        //move left
        else if (gamepad1.dpad_left) {
            robot.frontLeftDrive.setPower(-1.0);
            robot.backLeftDrive.setPower(1.0);
            robot.frontRightDrive.setPower(1.0);
            robot.backRightDrive.setPower(-1.0);
        }
        //rotation right
        else if (gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1){
            robot.frontLeftDrive.setPower(gamepad1.right_stick_x);
            robot.backLeftDrive.setPower(gamepad1.right_stick_x);
            robot.frontRightDrive.setPower(-gamepad1.right_stick_x);
            robot.backRightDrive.setPower(-gamepad1.right_stick_x);
        }
        else {
            robot.backLeftDrive.setPower(0.0);
            robot.frontRightDrive.setPower(0.0);
            robot.frontLeftDrive.setPower(0.0);
            robot.backRightDrive.setPower(0.0);
            */
        /*Tank Drive
        if (gamepad1.left_stick_y>0.1){
            robot.backLeftDrive.setPower(-1.0);
            robot.frontLeftDrive.setPower(-1.0);
        }
        if(gamepad1.right_stick_y<-0.1){
            robot.backLeftDrive.setPower(1.0);
            robot.frontLeftDrive.setPower(1.0);
        }
        if (gamepad1.right_stick_y>0.1){
            robot.frontRightDrive.setPower(-1.0);
            robot.backRightDrive.setPower(-1.0);
        }
        if (gamepad1.right_stick_y<-0.1){
            robot.frontRightDrive.setPower(1.0);
            robot.backRightDrive.setPower(1.0);
        }
        else {
            robot.backRightDrive.setPower(0.0);
            robot.frontRightDrive.setPower(0.0);
            robot.backLeftDrive.setPower(0.0);
            robot.frontLeftDrive.setPower(0.0);
        }
        */

    }
}

