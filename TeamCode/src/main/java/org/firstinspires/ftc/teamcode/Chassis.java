package org.firstinspires.ftc.teamcode;

import com.qualcomm.hardware.bosch.BNO055IMU;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.AngleUnit;
import org.firstinspires.ftc.robotcore.external.navigation.AxesOrder;
import org.firstinspires.ftc.robotcore.external.navigation.AxesReference;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.Orientation;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import static java.lang.Double.isNaN;

/**
 * Created by User on 10/17/2017.
 */

public class Chassis {

    //create drivetrain objects
    DcMotor frontLeftDrive;
    DcMotor backLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backRightDrive;

    static double[][] powerMatrix = {{0,0},{0,0}};
    DcMotor[][] drivetrain = {{frontLeftDrive,frontRightDrive},{backLeftDrive,backRightDrive}};

    boolean vision=false, gyro=false;

    //init other objects
    HardwareMap hwm;

    BNO055IMU imu;

    Orientation angles;

    VuforiaLocalizer vuforia;

    DistanceSensor distance;

    //constructors
    Chassis() {};
    Chassis(boolean g) {gyro=g;}
    Chassis(boolean g, boolean v) {
        gyro=g;
        vision=v;
    }

    //hardware init method
    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        //connect to hardware
        frontLeftDrive = hwm.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hwm.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hwm.get(DcMotor.class, "front_right_drive");
        backRightDrive = hwm.get(DcMotor.class, "back_right_drive");

        //set drivetrain direction
        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        //set init power
        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        //set init mode
        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        distance = hwm.get(DistanceSensor.class, "distance");

        if(gyro) {
            BNO055IMU.Parameters parametersIMU = new BNO055IMU.Parameters();
            parametersIMU.angleUnit = BNO055IMU.AngleUnit.RADIANS;
            imu = hwm.get(BNO055IMU.class, "imu");
            imu.initialize(parametersIMU);
            angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
        }
        if(vision) {
            int cameraMonitorViewId = hwm.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwm.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            parameters.vuforiaLicenseKey = "AXdOcVz/////AAAAGSXgHubMS0r6roxUdjf9DWKA7GTut2LGpmgusuOdBmgcr9vnOOQAc9l3bXYlX+3nFwmePFZh1Brz4BsbF1h6zhAXHx5VvmWWpVeNoCLbxDnyaPBtmZ3k2ZgHLFLTjS2ST/arbrmCSAVUTX9xOgenNw+pcCuZYKQxr34uWWppyhJPPIPP152Gud24gReY/Sg8hM20JYH49E1nRLpIjYA1FTxWy135xOu5SCns1p48AgTLxyy51v0WRBALpIWAW/Qe30gGHb9W3swgPlBj1EVMFgLdCXgp5NAlKNvID6T6G8NaN2dYY64Cv601JDKkcGHM1KNU5N4vn6spJkE0tZI2NqfiSaBKT59+nPsbv5AGQ0IA";

            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        }
    }
    public void driveAngle(double x, double y) {
        for(int n=0; n<2; n++) powerMatrix[n][n] += angleDriveLeft(x,y);
        for(int n=0; n<2; n++) powerMatrix[1-n][n] += angleDriveRight(x,y);
        double mult = getMagnitude(x,y)/getMax();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=mult;
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
        double max=0.0;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])>max) max=Math.abs(powerMatrix[n][i]);
        return max;
    }
    public static double getAngle(double x, double y){
        return Math.atan2(-y,x);
    }
    public void turn(double power) {
        power/=2;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=(1-power);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[i][n]+=power-(2*power*n);
    }
    public void dampen(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=(1-power);
    }
    public static void capPower() {
        double[][] capMatrix = {{1.0,1.0},{1.0,1.0}};
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(powerMatrix[n][i]<0) capMatrix[n][i]=(-1.0);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])>1) powerMatrix[n][i]=capMatrix[n][i];
    }
    public void setPower() {
        capPower();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[n][i].setPower(powerMatrix[n][i]);
    }
    public double pitchError() {
        return Math.atan2(Math.sin(getRoll())+Math.sin(getPitch()),getMagnitude(Math.cos(getPitch()),Math.cos(getRoll())));
    }
    public double getRoll() {
        return -((double) imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).secondAngle);
    }
    public double getPitch() {
        return (double) imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).thirdAngle;
    }
    public double targetYaw() {
        return Math.atan2(Math.cos(getPitch()),Math.cos(getRoll()));
    }
    public void drivePower(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[n][i].setPower(power);
    }
    public void turnPower(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[i][n].setPower(power-(2*power*n));
    }
    public void strafePower(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[n][i].setPower(power*(-2*Math.abs(n-i)+1));
    }
    public double getDistance() {
        double d = distance.getDistance(DistanceUnit.CM);
        if(isNaN(d)) d = 100.0;
        return d;
    }
    public void stop() {
        drivePower(0.0);
    }
}