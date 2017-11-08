package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;

/**
 * Created by User on 11/6/2017.
 */

public class ControllerTest extends OpMode {

    MechHardware robot = new MechHardware();
    Controller PID = new Controller();
    ;double zOrientation;
    @Override
    public void init() {
        robot.init(hardwareMap);
        PID.setKp(0.25);
        PID.setKi(0.25);
        PID.setKd(0.25);
        PID.setLimit(1.0);
    }

    @Override
    public void init_loop() {

    }


    @Override
    public void start() {
    }

    @Override
    public void loop() {

    }

    @Override
    public void stop() {
    }
}
