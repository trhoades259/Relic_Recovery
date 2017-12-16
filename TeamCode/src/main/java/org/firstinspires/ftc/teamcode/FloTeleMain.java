package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class FloTeleMain extends OpMode {

    //creates hardware objects
    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
    Chasis chasis = new Chasis();
    Jewel jewel = new Jewel();

    //variable creation/init
    boolean state=false, hold=false, button=false;

    double[] powers = new double[4];
    double forwardMagnitude, turnMagnitude, beltPower;

    int grabberCycle = 3;

    @Override
    public void init() {
        //init hardware
        conveyor.init(hardwareMap);
        relic.init(hardwareMap);
        chasis.init(hardwareMap);
        jewel.init(hardwareMap);
    }

    @Override
    public void loop() {

        //dpap strafe control
        if (gamepad1.dpad_left) {
            powers[0]=-1.0;
            powers[1]=1.0;
            powers[2]=1.0;
            powers[3]=-1.0;
        }
        else if (gamepad1.dpad_right) {
            powers[0]=1.0;
            powers[1]=-1.0;
            powers[2]=-1.0;
            powers[3]=1.0;
        }
        //driving magnitude adjusts for tur magnitude, fordza-like controls
        else {
            turnMagnitude = gamepad1.right_stick_x*0.9;
            forwardMagnitude = (1.0-Math.abs(turnMagnitude))*(gamepad1.right_trigger-gamepad1.left_trigger);
            powers[0]=forwardMagnitude+turnMagnitude;
            powers[1]=forwardMagnitude+turnMagnitude;
            powers[2]=forwardMagnitude-turnMagnitude;
            powers[3]=forwardMagnitude-turnMagnitude;
        }
        //set powers of drivetrain
        chasis.frontLeftDrive.setPower(powers[0]);
        chasis.backLeftDrive.setPower(powers[1]);
        chasis.frontRightDrive.setPower(powers[2]);
        chasis.backRightDrive.setPower(powers[3]);

        //two power toggle system for conveyor
        if(!state) {
            if((gamepad1.a || gamepad1.b) && !hold) {
                hold=true;
                if(gamepad1.a) button = true;
                if(gamepad1.b) button = false;
            }
            if(hold&&!gamepad1.a&&button){
                state = true;
                beltPower = 1.0;
                hold=false;
            }
            if(hold&&!gamepad1.b&&!button) {
                state =true;
                beltPower = 0.5;
                hold=false;
            }
        }
        else {
            if((gamepad1.a || gamepad1.b) && !hold) {
                hold=true;
                if(gamepad1.a) button = true;
                if(gamepad1.b) button = false;
            }
            if(hold&&!gamepad1.a&&button) {
                if(beltPower==  1.0) state=false;
                beltPower=1.0;
                hold=false;
            }
            if(hold&&!gamepad1.b&&!button) {
                if(beltPower==0.5) state=false;
                beltPower=0.5;
                hold=false;
            }
        }

        //bumper power override, set belt power
        if(gamepad1.right_bumper) conveyor.belt.setPower(0.75);
        else if(gamepad1.left_bumper) conveyor.belt.setPower(-0.75);
        else if(state) conveyor.belt.setPower(beltPower);
        else conveyor.belt.setPower(0.0);

        //set conveyor lift power
        conveyor.lift.setPower(-gamepad1.left_stick_y);

        //toggle grabber servo position
        if(gamepad2.a&&grabberCycle==0) {
            grabberCycle = 1;
        } if (!gamepad2.a&&grabberCycle==1){
            grabberCycle = 2 ;
        }
        if(grabberCycle==2){
            relic.grabber.setPosition(Relic.CLOSE);
        }
        if (gamepad2.a&&grabberCycle==2){
            grabberCycle = 3 ;
        } if (!gamepad2.a&&grabberCycle==3){
            grabberCycle = 0;
        }
        if(grabberCycle==0){
            relic.grabber.setPosition(Relic.OPEN);
        }

        //set relic extention
        if(gamepad2.right_trigger>0.1) relic.horzLift.setPower(gamepad2.right_trigger);
        if(gamepad2.dpad_down) relic.horzLift.setPower(-0.5);
        else relic.horzLift.setPower(0.0);

        //set relic lift angle
        relic.vertLift.setPower(-gamepad2.right_stick_y/2);
    }
}

