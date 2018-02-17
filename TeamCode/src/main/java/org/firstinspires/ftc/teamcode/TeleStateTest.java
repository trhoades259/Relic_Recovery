package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class TeleStateTest extends OpMode {

    Chassis chassis = new Chassis();

    double[] powers = new double[4];

    double forwardMagnitude, turnMagnitude;

    @Override
    public void init() {

        //init hardware
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {

        if (gamepad1.dpad_left) {
            powers[0]=-1.0;
            powers[1]=1.0;
            powers[2]=1.0;
            powers[3]=-1.0;
        }
        else if (gamepad1.dpad_right) {
            powers[0]=1.0;
            powers[1]=-1.0;
            powers[2]=-1.0;
            powers[3]=1.0;
        }
        //driving magnitude adjusts for tur magnitude, fordza-like controls
        else {
            turnMagnitude = gamepad1.right_stick_x*0.9;
            forwardMagnitude = (1.0-Math.abs(turnMagnitude))*(gamepad1.right_trigger-gamepad1.left_trigger);
            powers[0]=forwardMagnitude+turnMagnitude;
            powers[1]=forwardMagnitude+turnMagnitude;
            powers[2]=forwardMagnitude-turnMagnitude;
            powers[3]=forwardMagnitude-turnMagnitude;
        }
        //set powers of drivetrain
        chassis.frontLeftDrive.setPower(powers[0]);
        chassis.backLeftDrive.setPower(powers[1]);
        chassis.frontRightDrive.setPower(powers[2]);
        chassis.backRightDrive.setPower(powers[3]);

    }
}

