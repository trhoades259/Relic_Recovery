package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by User on 11/21/2017.
 */
@TeleOp
public class LiftGlift_Tele extends OpMode {
    DcMotor lift ;
    DcMotor glift;

    @Override
    public void init_loop() {
        lift = hardwareMap.dcMotor.get("lift");
        glift = hardwareMap.dcMotor.get("glift");
        lift.setPower(0.0);
        glift.setPower(0.0);
    }


    @Override
    public void init() {

    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if(gamepad1.right_trigger>0.1) {
            lift.setPower(gamepad1.right_trigger);

        }
        //move backwards
        else if (gamepad1.left_trigger>0.1) {
            lift.setPower(-gamepad1.left_trigger);
        }
        else if (gamepad1.dpad_up){
            glift.setPower(1.0);
        }
        else if (gamepad1.dpad_down){
            glift.setPower(-1.0);
        }
        else{
            glift.setPower(0.0);
            lift.setPower(0.0);
        }

    }
    @Override
    public void stop() {
    }
}
