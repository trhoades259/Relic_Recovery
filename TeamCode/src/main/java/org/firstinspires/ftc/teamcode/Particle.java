package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 10/17/2017.
 */

public class Particle {

    //creates objects
    Servo elevation;
    Servo rotation;

    ColorSensor color;

    private HardwareMap hwm;

    final static double ELEVATORINIT = 0.0;
    final static double ROTATIONINIT = 0.0;
    public final static double OUT = 0.5;
    public final static double DOWN = 0.5;

    Particle() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connects hardware
        color = hwm.get(ColorSensor.class, "color");
        elevation = hwm.get(Servo.class, "elevation");
        rotation = hwm.get(Servo.class, "rotation");

        //init servos
        elevation.setPosition(ELEVATORINIT);
        rotation.setPosition(ROTATIONINIT);
    }
    public void setAll(double elevate, double rotate) {
        elevation.setPosition(elevate);
        rotation.setPosition(rotate);
    }
    public void reset() {
        setAll(ELEVATORINIT,ROTATIONINIT);
    }
    public void setRotation(double rotate) {
        rotation.setPosition(rotate);
    }
    public void setElevation(double elevate) {
        elevation.setPosition(elevate);
    }

    //checks for match between input allience color and read jewel color
    public boolean match(String allience) {
        return (color.red()>color.blue() && allience.equals("Red"))||(color.blue()>color.red() && allience.equals("Blue"));
    }
}