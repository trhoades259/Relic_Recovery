package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DistanceSensor;
import com.qualcomm.robotcore.hardware.HardwareMap;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

/**
 * Created by User on 10/17/2017.
 */

public class Chasis {

    DcMotor frontLeftDrive;
    DcMotor backLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backRightDrive;

    double powerMatrix[][] = {{0,0},{0,0}};

    DistanceSensor Distance;

    HardwareMap hwm;

    VuforiaLocalizer vuforia;

    Chasis() {};

    public void init(HardwareMap inhwm) {
        hwm = inhwm;

        frontLeftDrive = hwm.get(DcMotor.class, "front_left_drive");
        backLeftDrive = hwm.get(DcMotor.class, "back_left_drive");
        frontRightDrive = hwm.get(DcMotor.class, "front_right_drive");
        backRightDrive = hwm.get(DcMotor.class, "back_right_drive");

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        Distance = hwm.get(DistanceSensor.class, "distance");

        int cameraMonitorViewId = hwm.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwm.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AXdOcVz/////AAAAGSXgHubMS0r6roxUdjf9DWKA7GTut2LGpmgusuOdBmgcr9vnOOQAc9l3bXYlX+3nFwmePFZh1Brz4BsbF1h6zhAXHx5VvmWWpVeNoCLbxDnyaPBtmZ3k2ZgHLFLTjS2ST/arbrmCSAVUTX9xOgenNw+pcCuZYKQxr34uWWppyhJPPIPP152Gud24gReY/Sg8hM20JYH49E1nRLpIjYA1FTxWy135xOu5SCns1p48AgTLxyy51v0WRBALpIWAW/Qe30gGHb9W3swgPlBj1EVMFgLdCXgp5NAlKNvID6T6G8NaN2dYY64Cv601JDKkcGHM1KNU5N4vn6spJkE0tZI2NqfiSaBKT59+nPsbv5AGQ0IA";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
    }
    public void driveForward(double power) {
        frontLeftDrive.setPower(power);
        backLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void stop() {
        driveForward(0.0);
    }
    public void turnLeft(double power) {
        frontLeftDrive.setPower(-power);
        backLeftDrive.setPower(-power);
        frontRightDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void turnRight(double power) {
        turnLeft(-power);
    }
    public void strafeLeft(double power) {
        frontLeftDrive.setPower(power);
        backLeftDrive.setPower(-power);
        frontRightDrive.setPower(-power);
        backRightDrive.setPower(power);
    }
    public void strafeRight(double power) {
        strafeLeft(-power);
    }
    public void leftVector(double power) {
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void rightVector(double power) {
        backLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
    }
    public void driveAngle(double x, double y) {
        for(int n=0; n<2; n++) powerMatrix[n][n] += Controller.angleDriveLeft(x,y);
        for(int n=0; n<2; n++) powerMatrix[1-n][n] += Controller.angleDriveRight(x,y);
    }
    public double getMax() {
        double max=0;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(powerMatrix[n][i]>max) max=powerMatrix[n][i];
        return max;
    }
    public void turn(double mag) {
        mag/=2;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=(1-mag);
        double[][] turnMatrix = {{mag,-mag},{mag,-mag}};
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]+=turnMatrix[n][i];
    }
    public void reset() {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]=0.0;
    }
    public void setPower() {
        frontLeftDrive.setPower(powerMatrix[0][0]);
        backLeftDrive.setPower(powerMatrix[1][0]);
        frontRightDrive.setPower(powerMatrix[0][1]);
        backRightDrive.setPower(powerMatrix[1][1]);
    }
}