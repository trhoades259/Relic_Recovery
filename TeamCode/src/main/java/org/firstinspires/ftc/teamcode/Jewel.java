package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 10/17/2017.
 */

public class Jewel {

    //creates objects
    Servo arm;

    ColorSensor color;

    //servo position values
    static final double UP = 1.0;
    static final double DOWN = 0.43;

    private HardwareMap hwm;

    Jewel() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connects hardware
        arm = hwm.get(Servo.class,"arm");
        color = hwm.get(ColorSensor.class, "color");

        //init servo
        arm.setPosition(UP);
    }
    //sets servo position
    public void up() {
        arm.setPosition(UP);
    }
    public void down() {
        arm.setPosition(DOWN);
    }
    //checks for match between input allience color and read jewel color
    public boolean match(String allience) {
        return (color.red()>color.blue() && allience.equals("Red"))||(color.blue()>color.red() && allience.equals("Blue"));
    }
}