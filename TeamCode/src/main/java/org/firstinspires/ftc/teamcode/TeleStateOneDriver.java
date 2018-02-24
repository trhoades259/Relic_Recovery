package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dave on 11/11/17.
 */

@TeleOp
public class TeleStateOneDriver extends OpMode {

    Belt belt = new Belt();
    Chassis chassis = new Chassis(true);

    double turnMagnitude;

    static double[][] powerMatrix = new double[2][2];
    static double[][] angleMatrix = new double[2][2];

    int launchCycle = 3;

    @Override
    public void init() {
        //init hardware
        belt.init(hardwareMap);
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {

        //belt
        if (gamepad1.left_bumper) belt.turnBlock(-0.5);
        else if (gamepad1.right_bumper) belt.turnBlock(0.5);
        else if (gamepad1.a) belt.setPower(1.0);
        else if (gamepad1.b) belt.setPower(-1.0);
        else {
            belt.bottomPower(gamepad1.right_trigger);
            belt.topPower(gamepad1.left_trigger);
        }

        //chassis
        turnMagnitude = gamepad1.right_stick_x*0.9;
        driveAngle(-gamepad1.left_stick_x, gamepad1.left_stick_y);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i] = (1.0-Math.abs(turnMagnitude))*angleMatrix[n][i];
        chassis.frontLeftDrive.setPower(Math.pow(powerMatrix[0][0]+turnMagnitude,3));
        chassis.backLeftDrive.setPower(Math.pow(powerMatrix[0][1]+turnMagnitude,3));
        chassis.frontRightDrive.setPower(Math.pow(powerMatrix[1][0]-turnMagnitude,3));
        chassis.backRightDrive.setPower(Math.pow(powerMatrix[1][1]-turnMagnitude,3));

        if(gamepad1.x&&launchCycle==0) {
            launchCycle = 1;
        } if (!gamepad1.x&&launchCycle==1){
            launchCycle = 2 ;
        }
        if(launchCycle==2){
            chassis.launcher.setPosition(Chassis.UP);
        }
        if (gamepad1.x&&launchCycle==2){
            launchCycle = 3 ;
        } if (!gamepad1.x&&launchCycle==3){
            launchCycle = 0;
        }
        if(launchCycle==0){
            chassis.launcher.setPosition(Chassis.INIT);
        }
//togglemyass3
    }
    public static void driveAngle(double x, double y) {
        for(int n=0; n<2; n++) angleMatrix[n][n] = angleDriveLeft(x,y);
        for(int n=0; n<2; n++) angleMatrix[1-n][n] = angleDriveRight(x,y);
        double mult = getMagnitude(x,y)/getMax();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) angleMatrix[n][i]*=mult;
    }
    public static double angleDriveLeft(double x, double y) {
        return Math.sin(fixAngle(getAngle(x,y)-Math.PI/4))*getMagnitude(x,y);
    }
    public static double angleDriveRight(double x, double y) {
        return Math.cos(fixAngle(getAngle(x,y)-Math.PI/4))*getMagnitude(x,y);
    }
    public static double getMagnitude(double x, double y){
        double mag = Math.sqrt(x*x + y*y);
        if (mag>1) mag=1.0;
        return mag;
    }
    public static double fixAngle(double angle) {
        while(angle<=(-Math.PI) || angle>Math.PI) angle+=(2*Math.PI*(-Math.signum(angle)));
        return angle;
    }
    public static double getMax() {
        double max=0.01;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(angleMatrix[n][i])>max) max=Math.abs(angleMatrix[n][i]);
        return max;
    }
    public static double getAngle(double x, double y){
        return Math.atan2(-y,x);
    }
}

