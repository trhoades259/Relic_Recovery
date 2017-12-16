package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by User on 10/17/2017.
 */

public class Conveyor {

    //create objects
    public DcMotor lift;
    public DcMotor belt;

    private HardwareMap hwm;

    //encoder positon
    public static final int HIGHPOSITION = 1000;

    Conveyor() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connect to hardware
        lift = hwm.get(DcMotor.class, "lift");
        belt = hwm.get(DcMotor.class, "belt");

        //set motor direction
        lift.setDirection(DcMotor.Direction.FORWARD);
        belt.setDirection(DcMotor.Direction.REVERSE);

        //set init power
        lift.setPower(0.0);
        belt.setPower(0.0);

        //reset encoders and set modes
        lift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        belt.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lift.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        belt.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void stop() {
        belt.setPower(0.0);
    }
    //set conveyor lift position using encoder
    public void up() {
        Controller.toPosition(lift,HIGHPOSITION,1.0);
    }
    public void down() {
        Controller.toPosition(lift,0,1.0);
    }
}