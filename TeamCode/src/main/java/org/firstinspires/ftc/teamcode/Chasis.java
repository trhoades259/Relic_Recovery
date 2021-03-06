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

import static org.firstinspires.ftc.teamcode.Controller.getMagnitude;

/**
 * Created by User on 10/17/2017.
 */

public class Chasis {

    //create drivetrain objects
    DcMotor frontLeftDrive;
    DcMotor backLeftDrive;
    DcMotor frontRightDrive;
    DcMotor backRightDrive;

    //setup matrix
    double[][] powerMatrix = {{0,0},{0,0}};
    DcMotor[][] drivetrain = {{frontLeftDrive,frontRightDrive},{backLeftDrive,backRightDrive}};

    //init other objects
    DistanceSensor Distance;

    HardwareMap hwm;

    VuforiaLocalizer vuforia;

    BNO055IMU imu;

    Orientation angles;

    //vuforia usage boolean
    boolean view = false;

    //constructors
    Chasis() {};
    Chasis(boolean vision) {view=vision;};

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

        //connect distance sensor
        Distance = hwm.get(DistanceSensor.class, "distance");

        //setup vuforia if enabled
        if(view) {
            int cameraMonitorViewId = hwm.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hwm.appContext.getPackageName());
            VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

            parameters.vuforiaLicenseKey = "AXdOcVz/////AAAAGSXgHubMS0r6roxUdjf9DWKA7GTut2LGpmgusuOdBmgcr9vnOOQAc9l3bXYlX+3nFwmePFZh1Brz4BsbF1h6zhAXHx5VvmWWpVeNoCLbxDnyaPBtmZ3k2ZgHLFLTjS2ST/arbrmCSAVUTX9xOgenNw+pcCuZYKQxr34uWWppyhJPPIPP152Gud24gReY/Sg8hM20JYH49E1nRLpIjYA1FTxWy135xOu5SCns1p48AgTLxyy51v0WRBALpIWAW/Qe30gGHb9W3swgPlBj1EVMFgLdCXgp5NAlKNvID6T6G8NaN2dYY64Cv601JDKkcGHM1KNU5N4vn6spJkE0tZI2NqfiSaBKT59+nPsbv5AGQ0IA";

            parameters.cameraDirection = VuforiaLocalizer.CameraDirection.FRONT;
            this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);
        }

        //setup imu
        BNO055IMU.Parameters parametersIMU = new BNO055IMU.Parameters();
        parametersIMU.angleUnit = BNO055IMU.AngleUnit.RADIANS;
        imu = hwm.get(BNO055IMU.class, "imu");
        imu.initialize(parametersIMU);
        angles = imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS);
    }
    //basic cardnial drive with power methods
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
    //groups vectors  due to mechanum wheel setup
    public void leftVector(double power) {
        frontLeftDrive.setPower(power);
        backRightDrive.setPower(power);
    }
    public void rightVector(double power) {
        backLeftDrive.setPower(power);
        frontRightDrive.setPower(power);
    }
    //sets up powers for matrix for angular drivetrain movement
    public void driveAngle(double x, double y) {
        for(int n=0; n<2; n++) powerMatrix[n][n] += Controller.angleDriveLeft(x,y);
        for(int n=0; n<2; n++) powerMatrix[1-n][n] += Controller.angleDriveRight(x,y);
        double mult = getMagnitude(x,y)/getMax();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=mult;
    }
    public void setAngle(double angle, double mag) {
        for(int n=0; n<2; n++) powerMatrix[n][n] = Math.sin(Controller.fixAngle(Math.toRadians(angle)+Math.PI/4));
        for(int n=0; n<2; n++) powerMatrix[1-n][n] =  Math.cos(Controller.fixAngle(Math.toRadians(angle)+Math.PI/4));
        double mult = mag/getMax();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=mult;
    }
    //finds max value in matrix
    public double getMax() {
        double max=0;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])>max) max=Math.abs(powerMatrix[n][i]);
        return max;
    }
    //adds turn modificaton to angle drive
    public void turn(double power) {
        power/=2;
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]*=(1-power);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[i][n]+=power-(2*power*n);
    }
    //set power methods using matrix
    public void reset() {
        setPower(0.0);
    }
    public void setPower() {
        capPower();
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) drivetrain[n][i].setPower(powerMatrix[n][i]);
    }
    public void setPower(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[n][i]=power;
        setPower();
    }
    //sets a limiter for power outputs
    public void capPower() {
        double[][] capMatrix = {{1.0,1.0},{1.0,1.0}};
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(powerMatrix[n][i]<0) capMatrix[n][i]=(-1.0);
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) if(Math.abs(powerMatrix[n][i])>1) powerMatrix[n][i]=capMatrix[n][i];
    }
    //set rotation using matrix and power
    public void setRotation(double power) {
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) powerMatrix[i][n]=power-(2*power*n);
        setPower();
    }
    //accesors for imu
    public double getYaw() {
        return (double) imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).firstAngle;
    }
    public double getRoll() {
        return -((double) imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).secondAngle);
    }
    public double getPitch() {
        return (double) imu.getAngularOrientation(AxesReference.INTRINSIC, AxesOrder.ZYX, AngleUnit.RADIANS).thirdAngle;
    }
    //calculates necassary values for level method
    public double targetYaw() {
        return Math.atan2(Math.cos(getPitch()),Math.cos(getRoll()));
    }
    public double pitchError() {
        return Math.atan2(Math.sin(getRoll())+Math.sin(getPitch()),getMagnitude(Math.cos(getPitch()),Math.cos(getRoll())));
    }
    //levels bot on balance pad using PID controller and imu
    public void level() {
        Controller level = new Controller("PID");
        level.setConstants(0.25,0.25,0.25);
        level.setTarget(0.0);
        level.setLimit(1.0);
        level.init(pitchError());
        while(Math.abs(level.getError())>5.0) {
            level.update(pitchError());
            setAngle(targetYaw(),level.getOut());
        }
    }
    //drives to a specific yaw
    public void toRelativeAngle(double angle) {
        toAbsoluteAngle(angle+getYaw());
    }
    public void toAbsoluteAngle(double angle) {
        Controller orientation = new Controller("PID");
        orientation.setConstants(0.7,0.1,0.2);
        orientation.setTarget(angle);
        orientation.setLimit(1.0);
        orientation.init(getYaw());
        while(Math.abs(orientation.getError())>(Math.PI*(3/180))) {
            orientation.update(getYaw());
            setRotation(-orientation.getOut());
        }
    }
    //use encoder method
    public void toPositon(int position, double power) {
        DcMotor[] motors = new DcMotor[4];
        int[] positions = new int[4];
        double[] powers = new double[4];
        for(int n=0; n<2; n++) for(int i=0; i<2; i++) motors[2*n+i]=drivetrain[n][i];
        for(int n=0; n<4; n++) {
            positions[n]=position;
            powers[n]=power;
        }
        Controller.toPosition(motors,positions,powers);
    }
    //corrects NaN issue for distance sensors when out of range
    public double getDistance() {
        double d = Distance.getDistance(DistanceUnit.CM);
        if(isNaN(d)) d = 100.0;
        return d;
    }
    private boolean isNaN(double x){return x != x;}
}