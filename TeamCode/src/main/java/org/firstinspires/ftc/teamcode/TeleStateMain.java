package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;

/**
 * Created by QUARK on 3/15/2016.
 */
public class TeleStateMain extends OpMode {
    //Initializes all motors and servos
    DcMotor FleftMotor;
    DcMotor BleftMotor;
    DcMotor FrightMotor;
    DcMotor BrightMotor;
    DcMotor brushMotor;
    DcMotor camMotor ;
    DcMotor lift ;
    Servo buttonServo ;
    Servo doorServo;
    Servo capServo ;

    //init variables
    int driveCycle = 3;
    int brushCycle=3;
    int camCycle = 3;
    int buttonCycle = 3;
    int doorCycle = 3;
    int capCycle = 3;
    int capMain = 0;

    @Override
    public void init() {
        //maps all motors and servos for the phone
        FleftMotor = hardwareMap.dcMotor.get("front_left_drive");
        BleftMotor = hardwareMap.dcMotor.get("back_left_drive");
        FrightMotor = hardwareMap.dcMotor.get("front_right_drive");
        BrightMotor = hardwareMap.dcMotor.get("back_right_drive");
        brushMotor = hardwareMap.dcMotor.get("brush_motor") ;
        camMotor = hardwareMap.dcMotor.get("cam_motor");
        lift = hardwareMap.dcMotor.get("lift");
        buttonServo = hardwareMap.servo.get("button_servo");
        doorServo = hardwareMap.servo.get("door_servo");
        capServo = hardwareMap.servo.get("cap_servo");
        //reverse right drive motors
        FrightMotor.setDirection(DcMotor.Direction.REVERSE);
        BrightMotor.setDirection(DcMotor.Direction.REVERSE);

        //init servo positions
        buttonServo.setPosition(0.3);
        doorServo.setPosition(0.4);
        capServo.setPosition(0);
    }
    @Override
    public void loop() {

        //find angle of gamepad1 left stick
        double leftStickAngle = Math.atan((-gamepad2.left_stick_y) / (gamepad2.left_stick_x));
        if (gamepad2.left_stick_y==-1){
            leftStickAngle=(Math.PI/2);
        }
        else if (gamepad2.left_stick_y==1){
            leftStickAngle=(3*Math.PI/2);
        }
        else if (gamepad2.left_stick_x==1){
            leftStickAngle=0;
        }
        else if (gamepad2.left_stick_x<0){
            leftStickAngle=leftStickAngle+Math.PI;
        }
        else if (gamepad2.left_stick_x>0&&gamepad2.left_stick_y>0){
            leftStickAngle=leftStickAngle+(2*Math.PI);
        }
        else if (gamepad2.left_stick_x<0.2&&gamepad2.left_stick_x>-0.2&&gamepad2.left_stick_y>0.1){
            leftStickAngle=(3*Math.PI/2);
        }
        else {
        }
        //display data
        telemetry.addData("Angle",(180*leftStickAngle/(Math.PI)));
        //have rotation override motion



        if((gamepad2.left_stick_button&&gamepad2.right_stick_button)&&driveCycle==0) {
            driveCycle = 1;
        } if (!(gamepad2.left_stick_button&&gamepad2.right_stick_button)&&driveCycle==1){
            driveCycle = 2 ;
        }
        if(driveCycle==2){
            if (gamepad2.right_stick_x>0.1||gamepad2.right_stick_x<-0.1){
                FleftMotor.setPower(gamepad2.right_stick_x/2);
                BleftMotor.setPower(gamepad2.right_stick_x/2);
                FrightMotor.setPower(-gamepad2.right_stick_x/2);
                BrightMotor.setPower(-gamepad2.right_stick_x/2);
            }
            //if left stick is being used, move according to the angle section the stick is within
            else if (gamepad2.left_stick_y>0.1||gamepad2.left_stick_y<-0.1||gamepad2.left_stick_x>0.1||gamepad2.left_stick_x<-0.1){
                if (leftStickAngle==0){
                    FleftMotor.setPower(-gamepad2.left_stick_x);
                    BleftMotor.setPower(gamepad2.left_stick_x);
                    FrightMotor.setPower(gamepad2.left_stick_x);
                    BrightMotor.setPower(-gamepad2.left_stick_x);
                }
                else if ((leftStickAngle > (Math.PI / 4)) && (leftStickAngle < (3*Math.PI / 4))){
                    FleftMotor.setPower(gamepad2.left_stick_y);
                    BleftMotor.setPower(gamepad2.left_stick_y);
                    FrightMotor.setPower(gamepad2.left_stick_y);
                    BrightMotor.setPower(gamepad2.left_stick_y);
                }
                else if ((leftStickAngle > (5*Math.PI / 4)) && (leftStickAngle < (7*Math.PI / 4))){
                    FleftMotor.setPower(gamepad2.left_stick_y);
                    BleftMotor.setPower(gamepad2.left_stick_y);
                    FrightMotor.setPower(gamepad2.left_stick_y);
                    BrightMotor.setPower(gamepad2.left_stick_y);
                }
                else if ((leftStickAngle > (7*Math.PI / 4)) && (leftStickAngle < (Math.PI / 4))||(leftStickAngle > (3*Math.PI / 4)) && (leftStickAngle < (5*Math.PI / 4))){
                    FleftMotor.setPower(-gamepad2.left_stick_x);
                    BleftMotor.setPower(gamepad2.left_stick_x);
                    FrightMotor.setPower(gamepad2.left_stick_x);
                    BrightMotor.setPower(-gamepad2.left_stick_x);
                }
            }
            else {
                BleftMotor.setPower(0.0);
                FrightMotor.setPower(0.0);
                FleftMotor.setPower(0.0);
                BrightMotor.setPower(0.0);
            }
        }
        if ((gamepad2.left_stick_button&&gamepad2.right_stick_button)&&driveCycle==2){
            driveCycle = 3 ;
        } if (!(gamepad2.left_stick_button&&gamepad2.right_stick_button)&&driveCycle==3){
            driveCycle = 0;
        }
        if(driveCycle==0){
            if (gamepad1.right_trigger>0.1&&(gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1)){
                if (gamepad1.right_stick_x>0.1){
                    FleftMotor.setPower(gamepad1.right_trigger);
                    BleftMotor.setPower(gamepad1.right_trigger);
                    FrightMotor.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
                    BrightMotor.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
                }
                if (gamepad1.right_stick_x<-0.1){
                    FleftMotor.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                    BleftMotor.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                    FrightMotor.setPower(gamepad1.right_trigger);
                    BrightMotor.setPower(gamepad1.right_trigger);
                }
            }
            else if(gamepad1.right_trigger>0.1) {
                FleftMotor.setPower(gamepad1.right_trigger);
                BleftMotor.setPower(gamepad1.right_trigger);
                FrightMotor.setPower(gamepad1.right_trigger);
                BrightMotor.setPower(gamepad1.right_trigger);
            }
            //move backwards
            else if (gamepad1.left_trigger>0.1) {
                FleftMotor.setPower(-gamepad1.left_trigger);
                BleftMotor.setPower(-gamepad1.left_trigger);
                FrightMotor.setPower(-gamepad1.left_trigger);
                BrightMotor.setPower(-gamepad1.left_trigger);
            }
            //move right
            else if (gamepad1.dpad_right) {
                FleftMotor.setPower(1.0);
                BleftMotor.setPower(-1.0);
                FrightMotor.setPower(-1.0);
                BrightMotor.setPower(1.0);
            }
            //move left
            else if (gamepad1.dpad_left) {
                FleftMotor.setPower(-1.0);
                BleftMotor.setPower(1.0);
                FrightMotor.setPower(1.0);
                BrightMotor.setPower(-1.0);
            }
            //rotation right
            else if (gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1){
                FleftMotor.setPower(gamepad1.right_stick_x);
                BleftMotor.setPower(gamepad1.right_stick_x);
                FrightMotor.setPower(-gamepad1.right_stick_x);
                BrightMotor.setPower(-gamepad1.right_stick_x);
            }
            else {
                BleftMotor.setPower(0.0);
                FrightMotor.setPower(0.0);
                FleftMotor.setPower(0.0);
                BrightMotor.setPower(0.0);
            }
        }
        if(gamepad1.b&&brushCycle==0) {
            brushCycle = 1;
        } if (!gamepad1.b&&brushCycle==1){
            brushCycle = 2 ;
        }
        if(brushCycle==2){
            brushMotor.setPower(-1.0);
        }
        if (gamepad1.b&&brushCycle==2){
            brushCycle = 3 ;
        }
        if ((!gamepad1.b&&brushCycle==3) || (!gamepad1.y&&brushCycle==6)){
            brushCycle = 0;
        }
        if(brushCycle==0){
            brushMotor.setPower(0.0);
        }
        if (brushCycle==0&&gamepad1.y){
            brushCycle = 4 ;
        }
        if(brushCycle==4&&!gamepad1.y){
            brushCycle=5;
        }
        if (brushCycle==5){
            brushMotor.setPower(1.0);
        }
        if (brushCycle==5&&gamepad1.y){
            brushCycle = 6;
        }
        if(brushCycle==5&&gamepad1.b){
            brushCycle = 2;
        }
        if(brushCycle==2&&gamepad1.y){
            brushCycle = 5;
        }
        //toggle cam power
        if(gamepad2.a&&camCycle==0) {
            camCycle = 1;
        } if (!gamepad2.a&&camCycle==1){
            camCycle = 2 ;
        }
        if(camCycle==2){
            camMotor.setPower(-1.0);
        }
        if (gamepad2.a&&camCycle==2){
            camCycle = 3 ;
        } if (!gamepad2.a&&camCycle==3){
            camCycle = 0;
        }
        if(camCycle==0){
            camMotor.setPower(0.0);
        }
        //toggle button position
        if(gamepad2.dpad_right&&buttonCycle==0) {
            buttonCycle = 1;
        } if (!gamepad2.dpad_right&&buttonCycle==1){
            buttonCycle = 2 ;
        }
        if(buttonCycle==2){
            buttonServo.setPosition(0.2);
        }
        if (gamepad2.dpad_right&&buttonCycle==2){
            buttonCycle = 3 ;
        }
        if ((!gamepad2.dpad_right&&buttonCycle==3) || (!gamepad2.dpad_left&&buttonCycle==6)){
            buttonCycle = 0;
        }
        if(buttonCycle==0){
            buttonServo.setPosition(0.3);
        }
        if (buttonCycle==0&&gamepad2.dpad_left){
            buttonCycle = 4 ;
        }
        if(buttonCycle==4&&!gamepad2.dpad_left){
            buttonCycle=5;
        }
        if (buttonCycle==5){
            buttonServo.setPosition(0.7);
        }
        if (buttonCycle==5&&gamepad2.dpad_left){
            buttonCycle = 6;
        }
        if(buttonCycle==5&&gamepad2.dpad_right){
            buttonCycle = 2;
        }
        if(buttonCycle==2&&gamepad2.dpad_left){
            buttonCycle = 5;

        }
        //toggle door servo position
        if(gamepad2.x&&doorCycle==0) {
            doorCycle = 1;
        } if (!gamepad2.x&&doorCycle==1){
            doorCycle = 2 ;
        }
        if(doorCycle==2){
            doorServo.setPosition(0.6);
        }
        if (gamepad2.x&&doorCycle==2){
            doorCycle = 3 ;
        } if (!gamepad2.x&&doorCycle==3){
            doorCycle = 0;
        }
        if(doorCycle==0){
            doorServo.setPosition(0.4);
        }
        // Cap ball servo arm
        if ((gamepad2.left_bumper&&gamepad2.right_bumper)&&capMain==0){
            capMain=1;
        }
        if (capMain==1) {
            if(gamepad2.b&&capCycle==0) {
                capCycle = 1;
            } if (!gamepad2.b&&capCycle==1){
                capCycle = 2 ;
            }
            if(capCycle==2){
                capServo.setPosition(0.5);
            }
            if (gamepad2.b&&capCycle==2){
                capCycle = 3 ;
            } if (!gamepad2.b&&capCycle==3){
                capCycle = 0;
            }
            if(capCycle==0){
                capServo.setPosition(1);
            }
        }
        //if right trigger is pressed, raise lift, if left is pressed, lower lift
        if (gamepad2.right_trigger > 0.1){
            lift.setPower(-gamepad2.right_trigger);
        }
        else if (gamepad2.left_trigger > 0.1){
            lift.setPower(gamepad2.left_trigger);
        }
        else {
            lift.setPower(0.0);
        }
    }
}


