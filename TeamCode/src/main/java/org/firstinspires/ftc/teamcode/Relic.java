package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 10/17/2017.
 */

public class Relic {

    //creates objects
    public DcMotor vertLift;
    public DcMotor horzLift;

    public Servo grabber;

    //servo position values
    static final double OPEN = 0.35;
    static final double CLOSE = 0.7;

    private HardwareMap hwm;

    Relic() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connect to hardware
        vertLift = hwm.get(DcMotor.class, "vertLift");
        horzLift = hwm.get(DcMotor.class, "horzLift");
        grabber = hwm.get(Servo.class,"grabber");

        //set motor direction
        vertLift.setDirection(DcMotor.Direction.FORWARD);
        horzLift.setDirection(DcMotor.Direction.FORWARD);

        //init hardware
        vertLift.setPower(0.0);
        horzLift.setPower(0.0);
        grabber.setPosition(OPEN);

        //reset encoders and set mode
        vertLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horzLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        vertLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horzLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    //set servo position
    public void open() {
        grabber.setPosition(OPEN);
    }
    public void close() {
        grabber.setPosition(CLOSE);
    }
}