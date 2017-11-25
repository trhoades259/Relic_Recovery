package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 10/17/2017.
 */

public class Relic {

    public DcMotor vertLift;
    public DcMotor horzLift;

    public Servo grabber;

    static final double OPEN = 1.0;
    static final double CLOSE = 0.0;

    private HardwareMap hwm;

    Relic() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        vertLift = hwm.get(DcMotor.class, "vertLift");
        horzLift = hwm.get(DcMotor.class, "horzLift");
        grabber = hwm.get(Servo.class,"grabber");

        vertLift.setDirection(DcMotor.Direction.FORWARD);
        horzLift.setDirection(DcMotor.Direction.FORWARD);

        vertLift.setPower(0.0);
        horzLift.setPower(0.0);
        grabber.setPosition(CLOSE);

        vertLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
        horzLift.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        vertLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        horzLift.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void open() {
        grabber.setPosition(OPEN);
    }
    public void close() {
        grabber.setPosition(CLOSE);
    }
}