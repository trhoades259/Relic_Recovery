package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Gamepad;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class GetValues extends OpMode {

    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
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
        chasis.init(hardwareMap);

        beltSpeed.setEnds(1.0,0.5);
        grabber.setEnds(Relic.OPEN,Relic.CLOSE);
    }

    @Override
    public void loop() {
        chasis.setPower(gamepad1.right_trigger-gamepad1.left_trigger);

    }
}

