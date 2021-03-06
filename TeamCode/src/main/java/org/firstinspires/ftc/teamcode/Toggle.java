package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 11/13/2017.
 */

public class Toggle {

    private DcMotor motor;
    private Servo servo;
    private double init, change;
    private int cycle = 3;
    private boolean mode = false, hold=false;

    Toggle() {}
    Toggle(Object obj) {
        if(obj instanceof DcMotor) motor = (DcMotor) obj;
        if(obj instanceof Servo) servo = (Servo) obj;
    }

    public void setObject(Object obj) {
        if(obj instanceof DcMotor) motor = (DcMotor) obj;
        if(obj instanceof Servo) servo = (Servo) obj;
    }
    public void setEnds(double in, double cha) {
        init = in;
        change = cha;
    }
    public void toggleMotor(boolean button) {
        if(button&&cycle==0) {
            cycle = 1;
        } if (!button&&cycle==1){
            cycle = 2 ;
        }
        if(cycle==2){
            motor.setPower(change);
        }
        if (button&&cycle==2){
            cycle = 3 ;
        } if (!button&&cycle==3){
            cycle = 0;
        }
        if(cycle==0){
            motor.setPower(init);
        }
    }
    public void toggleServo(boolean button) {
        if(button&&cycle==0) {
            cycle = 1;
        } if (!button&&cycle==1){
            cycle = 2 ;
        }
        if(cycle==2){
            servo.setPosition(change);
        }
        if (button&&cycle==2){
            cycle = 3 ;
        } if (!button&&cycle==3){
            cycle = 0;
        }
        if(cycle==0){
            servo.setPosition(init);
        }
    }
    public double toggleValue(boolean button) {
        if(toggle(button)) return change;
        return init;
    }
    public boolean toggle(boolean button) {
        if(button) hold=!hold;
        if(hold&&!button) {
            hold=!hold;
            mode=!mode;
        }
        if(mode) return true;
        else return false;
    }
}
