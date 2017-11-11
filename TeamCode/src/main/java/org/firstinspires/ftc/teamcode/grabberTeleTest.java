package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by User on 11/11/2017.
 */
@TeleOp
//@Disabled
public class grabberTeleTest extends OpMode {

    Servo grabber;
    int camCycle =3;

    @Override
    public void init() {
        grabber= hardwareMap.get(Servo.class, "grabber");

        grabber.setPosition(0.0);

    }
    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {

        if(gamepad2.a&&camCycle==0) {
            camCycle = 1;
        } if (!gamepad2.a&&camCycle==1){
            camCycle = 2 ;
        }
        if(camCycle==2){
            grabber.setPosition(0.1);
        }
        if (gamepad2.a&&camCycle==2){
            camCycle = 3 ;
        } if (!gamepad2.a&&camCycle==3){
            camCycle = 0;
        }
        if(camCycle==0){
            grabber.setPosition(0.0);
        }
    }
    @Override
    public void stop() {
    }
}
