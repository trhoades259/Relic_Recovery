package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by User on 10/17/2017.
 */

public class Conveyor {

    public DcMotor lift;
    public DcMotor belt;

    private HardwareMap hwm;

    static final int HIGHPOSITION = 1000;

    Conveyor() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        lift = hwm.get(DcMotor.class, "lift");
        belt = hwm.get(DcMotor.class, "belt");

        lift.setDirection(DcMotor.Direction.FORWARD);
        belt.setDirection(DcMotor.Direction.FORWARD);

        lift.setPower(0.0);
        belt.setPower(0.0);

        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        belt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        belt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    public void stop() {
        belt.setPower(0.0);
    }
    public void raise() {
        Controller.toPosition(lift,HIGHPOSITION,1.0);
    }
    public void lower() {
        Controller.toPosition(lift,0,1.0);
    }
}