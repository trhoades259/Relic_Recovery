package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

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
        robot.leftVector(Controller.angleDriveLeft(gamepad1.left_stick_x,gamepad1.left_stick_y));
        robot.rightVector(Controller.angleDriveRight(gamepad1.left_stick_x,gamepad1.left_stick_y));

    }
}

