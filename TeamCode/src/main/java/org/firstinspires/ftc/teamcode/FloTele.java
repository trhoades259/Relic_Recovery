package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
@Disabled
public class FloTele extends OpMode {

    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
    Chasis chasis = new Chasis();

    Toggle drive = new Toggle();
    Toggle beltSpeed = new Toggle();
    Toggle liftPos = new Toggle();
    Toggle grabber = new Toggle(relic.grabber);

    Gamepad gamepad;

    boolean liftSet, posHold = false;

    @Override
    public void init() {
        conveyor.init(hardwareMap);
        relic.init(hardwareMap);
        chasis.init(hardwareMap);

        beltSpeed.setEnds(1.0,0.5);
        grabber.setEnds(Relic.OPEN,Relic.CLOSE);
    }

    @Override
    public void loop() {
        if(drive.toggle(gamepad1.b || gamepad2.b)) gamepad = gamepad2;
        else gamepad = gamepad1;
        chasis.reset();
        if(gamepad.left_stick_x!=0.0 || gamepad.left_stick_y!=0.0) chasis.driveAngle(gamepad.left_stick_x,gamepad.left_stick_y);
        chasis.turn(gamepad.right_stick_x);
        chasis.setPower();

        if(gamepad2.a) chasis.level();


        conveyor.belt.setPower(beltSpeed.toggleValue(gamepad1.x)*(gamepad1.left_trigger-gamepad1.right_trigger));

        liftSet = liftPos.toggle(gamepad1.a);
        if(liftSet!=posHold) {
            posHold = liftSet;
            if(liftSet) conveyor.up();
            else conveyor.down();
        }


        relic.horzLift.setPower(gamepad2.right_trigger);

        if(gamepad2.right_bumper) relic.vertLift.setPower(1.0);
        else if(gamepad2.left_bumper) relic.vertLift.setPower(-1.0);
        else relic.vertLift.setPower(0.0);

        grabber.toggleServo(gamepad2.b);
    }
}

