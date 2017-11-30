package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class GetValues extends OpMode {

    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
    Chasis chasis = new Chasis();
    Jewel jewel = new Jewel();

    Toggle beltSpeed = new Toggle();
    Toggle grabber = new Toggle(relic.grabber);

    String[][] drive = {{"frontLeftDrive","frontRightDrive"},{"backLeftDrive","backRightDrive"}};

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
        for(int n=0; n<chasis.drivetrain.length; n++) for(int i=0; i<chasis.drivetrain[n].length; n++) telemetry.addData(drive[n][i],chasis.drivetrain[n][i].getCurrentPosition());

        conveyor.lift.setPower(-gamepad1.right_stick_y);
        telemetry.addData("lift position: ",conveyor.lift.getCurrentPosition());

        if(gamepad1.dpad_up) jewel.arm.setPosition(jewel.arm.getPosition()+0.025);
        else if(gamepad1.dpad_down) jewel.arm.setPosition(jewel.arm.getPosition()-0.025);
        telemetry.addData("jew arm", jewel.arm.getPosition());

        if(gamepad1.dpad_right) relic.grabber.setPosition(relic.grabber.getPosition()+0.025);
        else if(gamepad1.dpad_left) relic.grabber.setPosition(relic.grabber.getPosition()-0.025);
        telemetry.addData("grabber servo", relic.grabber.getPosition());

        telemetry.update();
    }
}

