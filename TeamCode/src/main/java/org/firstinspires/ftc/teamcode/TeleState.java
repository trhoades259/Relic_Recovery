package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.util.ElapsedTime;

/**
 * Created by dave on 11/11/17.
 */
@TeleOp
public class TeleState extends OpMode {

    Belt belt = new Belt();
    Chassis chassis = new Chassis(true);

    ElapsedTime timer = new ElapsedTime();

    @Override
    public void init() {
        //init hardware
        belt.init(hardwareMap);
        chassis.init(hardwareMap);
    }

    @Override
    public void loop() {

        //level program
        double target=0.0, response, Kp=1, I=0, D, Kd=0.1, Ki=.3, error, errorPrev=0, timePrev=timer.time(), timeReal, timeDif, sensor,x,y;
        while(gamepad1.a) {
            sensor=chassis.pitchError();
            error=target-sensor;
            timeReal = timer.time();
            timeDif = timeReal-timePrev;
            I+=error*(timeDif);
            D=(error-errorPrev)/timeDif;
            response=(Kp*error+Ki*I+Kd*D);

            x=Math.cos(chassis.targetYaw())*response;
            y=Math.sin(chassis.targetYaw())*response;
            chassis.driveAngle(x,y);

            errorPrev=error;
            timePrev=timeReal;
        }

        //belt
        if (Math.abs(gamepad2.right_stick_x) > 0.1) belt.turnBlock(gamepad2.right_stick_x);
        else if (Math.abs(gamepad2.left_stick_y) > 0.1) belt.beltPower(-gamepad2.left_stick_y);
        else {
            belt.bottomPower(gamepad2.right_trigger);
            belt.topPower(gamepad2.left_trigger);
        }

        //chassis
        chassis.driveAngle(gamepad1.left_stick_x, gamepad1.left_stick_y);
        chassis.turn(gamepad1.right_stick_x);
        chassis.dampen(gamepad1.right_trigger);
        chassis.setPower();

    }
}

