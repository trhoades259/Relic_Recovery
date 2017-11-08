package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.ColorSensor;
import com.qualcomm.robotcore.hardware.DcMotor;

/**
 * Created by QUARK on 11/16/2015.
 */
public class AutoGolf extends LinearOpMode{

    DcMotor FleftMotor;
    DcMotor FrightMotor;
    DcMotor BleftMotor;
    DcMotor BrightMotor;
    //DcMotor camMotor ;
    //Servo doorServo;
    //Servo capServo;
    //Servo buttonServo;

    //ColorSensor colorSensor;
    ColorSensor reflectSensor ;
    //OpticalDistanceSensor distanceSensorLeft ;
    //OpticalDistanceSensor distanceSensorRight;

    String color = "Red";

    final static int tile = 1300;
    //final static int tileStraffe = 1765;
    final static int turn360 = 1161 ;

    DcMotor FinsideMotor;
    DcMotor BinsideMotor;
    DcMotor FoutsideMotor;
    DcMotor BoutsideMotor;


    @Override
    public void runOpMode () throws InterruptedException {


        FleftMotor = hardwareMap.dcMotor.get("front_left_drive");
        BleftMotor = hardwareMap.dcMotor.get("back_left_drive");
        FrightMotor = hardwareMap.dcMotor.get("front_right_drive");
        BrightMotor = hardwareMap.dcMotor.get("back_right_drive");
        FrightMotor.setDirection(DcMotor.Direction.REVERSE);
        BrightMotor.setDirection(DcMotor.Direction.REVERSE);

        //colorSensor = hardwareMap.colorSensor.get("color");
        reflectSensor = hardwareMap.colorSensor.get("reflect");
        //distanceSensorLeft = hardwareMap.opticalDistanceSensor.get("ldistance_sensor");
        //distanceSensorRight = hardwareMap.opticalDistanceSensor.get("rdistance_sensor");

        //camMotor = hardwareMap.dcMotor.get("cam_motor");

        //doorServo = hardwareMap.servo.get("door_servo");
        //capServo = hardwareMap.servo.get("cap_servo");
        //buttonServo = hardwareMap.servo.get("button_servo");

        //buttonServo.setPosition(0.5);
        //doorServo.setPosition(0.6);
        //capServo.setPosition(0.3);

        //colorSensor.enableLed(false);
        reflectSensor.enableLed(true);


        waitOneFullHardwareCycle();
        while(!opModeIsActive()){
            //color selection
            if(gamepad1.x || gamepad2.x) {
                color = "Blue";
                sleep(200);
            }
            if (gamepad1.b || gamepad2.b){
                color = "Red";
                sleep(200);
            }
            telemetry.addData("Color {X, B}", color);
        }
        waitForStart();
        //determine which motors are inside and outside based on allience color
        if (color == "Red"){
            FinsideMotor = FrightMotor;
            BinsideMotor = BrightMotor;
            FoutsideMotor = FleftMotor;
            BoutsideMotor = BleftMotor;
        }
        if (color == "Blue"){
            FinsideMotor = FleftMotor;
            BinsideMotor = BleftMotor;
            FoutsideMotor = FrightMotor;
            BoutsideMotor = BrightMotor;
        }
        //wait the selected amount of time

        FinsideMotor.setPower(1.0);
        BinsideMotor.setPower(1.0);
        FoutsideMotor.setPower(1.0);
        BoutsideMotor.setPower(1.0);
        while (reflectSensor.blue()<15) {
            telemetry.addData("value", reflectSensor.blue());
        }
        FinsideMotor.setPower(0);
        BinsideMotor.setPower(0);
        FoutsideMotor.setPower(0);
        BoutsideMotor.setPower(0);
        sleep(500);
        int turnIntFI = FinsideMotor.getCurrentPosition();
        int turnIntBI = BinsideMotor.getCurrentPosition();
        int turnIntFO = FoutsideMotor.getCurrentPosition();
        int turnIntBO = BoutsideMotor.getCurrentPosition();
        int turn45=turn360/8;
        FinsideMotor.setTargetPosition(turnIntFI-turn45);
        FoutsideMotor.setTargetPosition(turnIntFO-turn45);
        BinsideMotor.setTargetPosition(turnIntBI+turn45);
        BoutsideMotor.setTargetPosition(turnIntBO+turn45);

        FinsideMotor.setPower(0.4);
        BinsideMotor.setPower(0.4);
        FoutsideMotor.setPower(0.4);
        BoutsideMotor.setPower(0.4);
        while(FleftMotor.isBusy()&&BleftMotor.isBusy()&&FrightMotor.isBusy()&&BrightMotor.isBusy()){
        }
        FinsideMotor.setPower(0);
        BinsideMotor.setPower(0);
        FoutsideMotor.setPower(0);
        BoutsideMotor.setPower(0);
        /*
        waitOneFullHardwareCycle();
        FinsideMotor.setPower(0.4);
        BinsideMotor.setPower(0.4);
        FoutsideMotor.setPower(0.4);
        BoutsideMotor.setPower(0.4);
        double distanceAVG = (distanceSensorLeft.getLightDetected()+distanceSensorRight.getLightDetected())/2;
        while (distanceAVG<0.6) {
        }
        FinsideMotor.setPower(0);
        BinsideMotor.setPower(0);
        FoutsideMotor.setPower(0);
        BoutsideMotor.setPower(0);
        waitOneFullHardwareCycle();
        if (((color=="Red")&&(colorSensor.red()>colorSensor.blue()))||((color=="Blue")&&(colorSensor.red()<colorSensor.blue()))){
            buttonServo.setPosition(0.7);
        }
        else {
            buttonServo.setPosition(0.3);
        }

        sleep(2000);
        buttonServo.setPosition(0.5);
        waitOneFullHardwareCycle();
        //start cam to launch first particle
        camMotor.setPower(-1.0);
        sleep(3000);
        //open servo to set second ball
        doorServo.setPosition(0.8);
        sleep(3000);
        //shut servo
        doorServo.setPosition(0.6);
        sleep(5000);
        //turn off cam after launching second ball
        camMotor.setPower(0.0);
        */
    }
}
