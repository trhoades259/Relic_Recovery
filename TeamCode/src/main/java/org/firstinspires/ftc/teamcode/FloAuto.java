package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.Disabled;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by User on 11/7/2017.
 */
@Autonomous
@Disabled
public class FloAuto extends LinearOpMode{

    Conveyor conveyor = new Conveyor();
    Jewel jewel = new Jewel();
    Chasis chasis = new Chasis();

    ElapsedTime runtime = new ElapsedTime();

    private String color = "Red";
    private String side = "Right";
    private static final double BREAKPOINT = 15.0;


    @Override public void runOpMode() {
        conveyor.init(hardwareMap);
        jewel.init(hardwareMap);
        chasis.init(hardwareMap);

        VuforiaTrackables relicTrackables = this.chasis.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        while (!opModeIsActive()) {
            if(gamepad1.x || gamepad2.x) color = "Blue";
            if (gamepad1.b || gamepad2.b) color = "Red";
            telemetry.addData("Color {X (Blue), B (Red)}", color);

            if(gamepad1.a || gamepad2.a) side = "Left";
            if (gamepad1.y || gamepad2.y) side = "Right";
            telemetry.addData("Side {A (Left), Y (Right)}", side);

            telemetry.update();
        }
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            int coulum = 2;
            double startTime=runtime.time();
            while(Controller.timer(startTime,runtime.time(),3.0)) {
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    if (vuMark == RelicRecoveryVuMark.LEFT) coulum--;
                    else if (vuMark == RelicRecoveryVuMark.RIGHT) coulum++;
                    break;
                }
            }
            if(side.equals("Right")) coulum=5-coulum;

            jewel.down();
            startTime = runtime.time();
            while(jewel.color.red()<BREAKPOINT && jewel.color.blue()<BREAKPOINT && Controller.timer(startTime,runtime.time(),1.0)) ;
            if(jewel.match(color))  chasis.toAbsoluteAngle(Math.PI/6);
            else chasis.toAbsoluteAngle(-Math.PI/6);
            jewel.up();
            chasis.toAbsoluteAngle(0.0);

            int direction = 1;
            if(side.equals("Right")) direction = -1;
            chasis.toPositon(4000,direction);
            chasis.toAbsoluteAngle(Math.PI/2);
            chasis.strafeLeft(direction);
            for (int n=0; n < coulum; n++) {
                while(chasis.Distance.getDistance(DistanceUnit.CM) > 6);
                if((n-1)!=coulum) while(chasis.Distance.getDistance(DistanceUnit.CM) < 6);
            }
            chasis.stop();

            conveyor.belt.setPower(1.0);
            sleep(3000);
            conveyor.belt.setPower(0.0);
        }
    }
}
