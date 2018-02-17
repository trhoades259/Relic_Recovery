package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class LevelTest extends OpMode {

    Chassis chassis = new Chassis(true);

    ElapsedTime timer = new ElapsedTime();

    private double target=0.0, response, g=1, Kp=0.1, I=0, D, Kd=0.1, Ki=.3, error, errorPrev=0, timePrev=timer.time(), timeReal, timeDif, sensor,x,y;


    @Override
    public void init() {
        //init hardware
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {

        //level program
        while(gamepad1.a) {
            sensor=chassis.pitchError();
            error=target-sensor;
            timeReal = timer.time();
            timeDif = timeReal-timePrev;
            I+=error*(timeDif);
            D=(error-errorPrev)/timeDif;
            response=(Kp*error+Ki*I+Kd*D)*g;

            x=Math.cos(chassis.targetYaw())*response;
            y=Math.sin(chassis.targetYaw())*response;
            chassis.driveAngle(x,y);

            errorPrev=error;
            timePrev=timeReal;
        }
    }
}

