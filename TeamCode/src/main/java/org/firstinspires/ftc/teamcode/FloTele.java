package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class FloTele extends OpMode {

    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
    Jewel jewel = new Jewel();
    Chasis chasis = new Chasis();

    Toggle drive = new Toggle();
    Toggle beltSpeed = new Toggle();
    Toggle liftPos = new Toggle();
    Toggle liftControl = new Toggle();
    Toggle grabber = new Toggle(relic.grabber);

    Gamepad gamepad;

    DcMotor motor;

    boolean liftSet, posHold = false;

    @Override
    public void init() {
        conveyor.init(hardwareMap);
        relic.init(hardwareMap);
        jewel.init(hardwareMap);
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
        if(gamepad.right_stick_x!=0) chasis.turn(gamepad.right_stick_x);
        chasis.setPower();


        conveyor.belt.setPower(beltSpeed.toggleValue(gamepad1.x)*(gamepad1.left_trigger-gamepad1.right_trigger));

        liftSet = liftPos.toggle(gamepad1.a);
        if(liftSet!=posHold) {
            posHold = liftSet;
            if(liftSet) conveyor.up();
            else conveyor.down();
        }


        if(liftControl.toggle(gamepad2.a)) motor = relic.horzLift;
        else motor = relic.vertLift;
        motor.setPower(gamepad2.left_trigger-gamepad2.right_trigger);

        grabber.toggleServo(gamepad2.b);
    }
}

