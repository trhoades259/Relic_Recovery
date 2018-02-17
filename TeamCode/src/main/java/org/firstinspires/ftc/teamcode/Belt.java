package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by User on 10/17/2017.
 */

public class Belt {

    //create objects
    public DcMotor bottomLeft;
    public DcMotor bottomRight;
    public DcMotor topLeft;
    public DcMotor topRight;

    private HardwareMap hwm;

    Belt() {}

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connect to hardware
        bottomLeft = hwm.get(DcMotor.class, "bottom_left");
        bottomRight = hwm.get(DcMotor.class, "bottom_right");
        topLeft = hwm.get(DcMotor.class, "top_left");
        topRight = hwm.get(DcMotor.class, "top_right");

        //set motor direction
        bottomLeft.setDirection(DcMotor.Direction.FORWARD);
        bottomRight.setDirection(DcMotor.Direction.REVERSE);
        topLeft.setDirection(DcMotor.Direction.FORWARD);
        topRight.setDirection(DcMotor.Direction.REVERSE);


        //set init power
        bottomLeft.setPower(0.0);
        bottomRight.setPower(0.0);
        topLeft.setPower(0.0);
        topRight.setPower(0.0);

        //reset encoders and set modes
        bottomLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        bottomRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        topLeft.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        topRight.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }
    public void stop() {
        beltPower(0.0);
    }
    public void beltPower(double power) {
        bottomLeft.setPower(power);
        bottomRight.setPower(power);
        topLeft.setPower(power);
        topRight.setPower(power);
    }
    public void turnBlock(double power) {
        bottomLeft.setPower(power);
        bottomRight.setPower(-power);
    }
    public void bottomPower(double power) {
        bottomLeft.setPower(power);
        bottomRight.setPower(power);
    }
    public void topPower(double power) {
        topLeft.setPower(power);
        topRight.setPower(power);
    }
}