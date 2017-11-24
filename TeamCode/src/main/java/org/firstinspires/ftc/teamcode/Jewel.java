package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 10/17/2017.
 */

public class Jewel {

    public Servo arm;

    public ColorSensor color;

    private static final double UP = 1.0;
    private static final double DOWN = 0.0;

    private HardwareMap hwm;

    Jewel() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        arm = hwm.get(Servo.class,"arm");

        arm.setPosition(UP);
    }
    public void up() {
        arm.setPosition(UP);
    }
    public void down() {
        arm.setPosition(DOWN);
    }
}