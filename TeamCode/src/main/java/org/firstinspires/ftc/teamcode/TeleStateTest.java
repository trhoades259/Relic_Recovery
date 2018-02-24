package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class TeleStateTest extends OpMode {

    Chassis chassis = new Chassis();

    static double[][] angleMatrix = new double[2][2];
    static double[][] powerMatrix = new double[2][2];

    double turnMagnitude;

    int launchCycle = 3;

    @Override
    public void init() {

        //init hardware
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {

        turnMagnitude = gamepad1.right_stick_x*0.9;
        driveAngle(gamepad1.left_stick_x, gamepad1.left_stick_y);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i] = (1.0-Math.abs(turnMagnitude))*angleMatrix[n][i];
        chassis.frontLeftDrive.setPower((1-gamepad1.right_trigger)*(powerMatrix[0][0]+turnMagnitude));
        chassis.backLeftDrive.setPower((1-gamepad1.right_trigger)*(powerMatrix[0][1]+turnMagnitude));
        chassis.frontRightDrive.setPower((1-gamepad1.right_trigger)*(powerMatrix[1][0]-turnMagnitude));
        chassis.backRightDrive.setPower((1-gamepad1.right_trigger)*(powerMatrix[1][1]-turnMagnitude));

        if(gamepad1.a&&launchCycle==0) {
            launchCycle = 1;
        } if (!gamepad1.a&&launchCycle==1){
            launchCycle = 2 ;
        }
        if(launchCycle==2){
            chassis.launcher.setPosition(Chassis.UP);
        }
        if (gamepad1.a&&launchCycle==2){
            launchCycle = 3 ;
        } if (!gamepad1.a&&launchCycle==3){
            launchCycle = 0;
        }
        if(launchCycle==0){
            chassis.launcher.setPosition(Chassis.INIT);
        }

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
    public static void turn(double power) {
        power/=2;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) angleMatrix[n][i]*=(1-Math.abs(power));
        powerMatrix[0][0]=power;
        powerMatrix[0][1]=power;
        powerMatrix[1][0]=-power;
        powerMatrix[1][1]=-power;
        if(!isZero()) for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]+=angleMatrix[n][i];
    }
    public static void dampen(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=(1-power);
    }
    public static void capPower() {
        double[][] capMatrix = {{1.0,1.0},{1.0,1.0}};
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(powerMatrix[n][i]<0) capMatrix[n][i]=(-1.0);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])>1) powerMatrix[n][i]=capMatrix[n][i];
    }
    public void setPower() {
        capPower();
        chassis.frontLeftDrive.setPower(powerMatrix[0][0]);
        chassis.backLeftDrive.setPower(powerMatrix[0][1]);
        chassis.frontRightDrive.setPower(powerMatrix[1][0]);
        chassis.backRightDrive.setPower(powerMatrix[1][1]);
        //for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[n][i].setPower(powerMatrix[n][i]);
    }
    public static boolean isZero() {
        int counter = 0;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])<0.02) counter++;
        return (counter!=4);
    }
}

