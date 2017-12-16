package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

/**
 * Created by User on 11/7/2017.
 */
@Autonomous
//@Disabled
public class toAngleTest extends LinearOpMode{

    Chasis chasis = new Chasis(true);


    @Override public void runOpMode() {
        chasis.init(hardwareMap);

        waitForStart();

        chasis.toAbsoluteAngle(Math.PI/2);
    }
}
