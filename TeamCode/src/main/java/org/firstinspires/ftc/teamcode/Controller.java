package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by User on 10/31/2017.
 */

public class Controller {

    private double Kp = 0, Ki = 0, Kd = 0, Ka, Klead, Klag, Tlead, Tlag, Alead, Alag;
    private double target;
    private double error, errorPrev;
    private double I = 0, D, Lead, Lag;
    private double in, out;
    private double time, endTime;
    private boolean useP = true, useI = true, useD = true, useLead = false, useLag = false;
    private double limit;
    private boolean limiter;
    private  boolean antiWind = false;

    private ElapsedTime runTime = new ElapsedTime();

    public Controller() {
    }
    public Controller(String type) {
        useP = type.contains("P");
        useI = type.contains("I");
        useD = type.contains("D");
        useLead = type.contains("Lead");
        useLag = type.contains("Lag");
    }

    public void setKp(double p) {
        Kp = p;
    }
    public void setKi(double i) {
        Ki = i;
    }
    public void antiWind(boolean a) {
        antiWind = a;
    }
    public void setKd(double d) {
        Kd=d;
    }
    public void setKa(double a) {
        Ka=a;
    }
    public void setKlead(double le) {
        Klead=le;
    }
    public void setKlag(double la) {
        Klag=la;
    }
    public void setTlead(double le) {
        Tlead=le;
    }
    public void setTlag(double la) {
        Tlag=la;
    }
    public void setALead(double le) {
        Alead=le;
    }
    public void setALag(double la) {
        Alag=la;
    }
    public void resetConstants() {
        Kp=0;
        Ki=0;
        Kd=0;
        Ka=0;
        Klead=0;
        Klag=0;
    }
    public void setTarget(double t) {
        target=t;
    }
    public void setType(String type) {
        useP=type.contains("P");
        useI=type.contains("I");
        useD=type.contains("D");
        useLead=type.contains("Lead");
        useLag=type.contains("Lag");
    }

    public void updateIn(double i) {
        in=i;
    }
    public void updateError() {
        error=target-in;
    }
    public void updateTime() {
        time=runTime.time()-endTime;
        endTime=time;
    }
    public void updateI() {
        double iError=error, sum=error*time;
        if(antiWind) iError-=antiWind(sum);
        sum=iError*time;
        I+=sum;
    }
    public double antiWind(double in) {
        double antiWindI, o=(Kd*D)+(Kp*error)+(Ki*in);
        if(Math.abs(o)<= limit) antiWindI = 0;
        else {
            antiWindI = Ka*o;
        }
        return antiWindI;
    }
    public void updateD() {
        D=(error-errorPrev)/time;
        errorPrev=error;
    }
    public void updateLead() {
        if(!useD) updateD();
        Lead=Klead*(((Tlead*D)+1)/((Alead*Tlead*D)+1));
    }
    public void updateLag() {
        if(!useD && !useLead) updateD();
        Lag=Klag*Alag*(((Tlag*D)+1)/((Alag*Tlag*D)+1));
    }
    public void update(double in) {
        updateIn(in);
        updateError();
        updateTime();
        updateD();
        updateI();
        if(useLead) updateLead();
        if(useLag) updateLag();
    }
    public void calcOut() {
        if(useP || useI || useD) out = 0;
        else out=1;
        if(useP) out+=(Kp*error);
        if(useI) out+=(Ki*I);
        if(useD) out+=(Kd*D);
        if(useLead) out*=Lead;
        if(useLag) out*=Lag;
    }
    public double getOut() {
        calcOut();
        if(limiter) capOut();
        return out;
    }
    public void setLimit(double lim) {
        limit=Math.abs(lim);
        limiter=true;
    }
    public void capOut() {
        if(Math.abs(out)>limit) {
            if(out>0) out=limit;
            if(out<0) out=(-limit);
        }
    }
    public void turnOff(String off) {
        if(off.contains("P")) useP=false;
        if(off.contains("I")) useI=false;
        if(off.contains("D")) useD=false;
        if(off.contains("Lead")) useLead=false;
        if(off.contains("Lag")) useLag=false;
    }
    public static double getAngle(double x, double y){
        return Math.atan2(-y,x);
    }
    public static double getMagnitude(double x, double y){
        double mag = Math.sqrt(x*x + y*y);
        if (mag>1) mag=1.0;
        return mag;
    }
    public static double angleDriveLeft(double x, double y) {
        return Math.sin(getAngle(x,y)-Math.PI/4)*getMagnitude(x,y);
    }
    public static double angleDriveRight(double x, double y) {
        return Math.cos(getAngle(x,y)-Math.PI/4)*getMagnitude(x,y);
    }
    public static void toPosition(DcMotor[] motors, int[] positions, double[] speeds) {
        for(int n=0; n<motors.length; n++) {
            motors[n].setTargetPosition(positions[n]);
            motors[n].setMode(DcMotor.RunMode.RUN_TO_POSITION);
            motors[n].setPower(speeds[n]);
        }
        while(isBusy(motors));
        for(DcMotor motor : motors) {
            motor.setPower(0.0);
            motor.setMode(DcMotor.RunMode.RUN_USING_ENCODER);
        }

    }
    public static void toPosition(DcMotor motor, int position, double speed) {
        DcMotor[] motors = {motor};
        int[] positions = {position};
        double[] speeds = {speed};
        toPosition(motors,positions,speeds);
    }
    private static boolean isBusy(DcMotor[] motors) {
        for(DcMotor motor : motors) if(motor.isBusy()) return true;
        return false;
    }
    public static boolean timer(double startTime, double currentTime, double timeLimit) {
        if((startTime+timeLimit)<currentTime) return true;
        return false;
    }
}
