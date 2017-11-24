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


    private MechHardware robot = new MechHardware();

    ElapsedTime runtime = new ElapsedTime();

    private String color = "Red";
    private String side = "Right";


    @Override public void runOpMode() {

        robot.init(hardwareMap);

        VuforiaTrackables relicTrackables = this.robot.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();

        while (!opModeIsActive()) {
            if(gamepad1.x || gamepad2.x) {
                color = "Blue";
            }
            if (gamepad1.b || gamepad2.b){
                color = "Red";
            }
            telemetry.addData("Color {X (Blue), B (Red)}", color);

            if(gamepad1.a || gamepad2.a) {
                side = "Left";
            }
            if (gamepad1.y || gamepad2.y){
                side = "Right";
            }
            telemetry.addData("Side {A (Left), Y (Right)}", side);

            telemetry.update();
        }
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            robot.jewelArm.setPosition(0.5);
            double compare = runtime.time();
            while(robot.Color.red()<15 || robot.Color.blue()<15 || (runtime.time()-compare)<2) ;
            if((robot.Color.red()>15 && color.equals("Red"))||(robot.Color.blue()>15 && color.equals("Blue"))) {
                //robot.turnRight();
                //robot.turnLeft();
            }
            else {
                //robot.turnLeft();
              //  robot.turnRight();
            }

            //robot.turnLeft();
            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            int coulum = 2;
            compare=runtime.time();
            while((runtime.time()-compare)<3) {
                if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                    if (vuMark == RelicRecoveryVuMark.LEFT) coulum--;
                    else if (vuMark == RelicRecoveryVuMark.RIGHT) coulum++;
                    break;
                }
            }
            if(side.equals("Right")) {
                coulum=3-coulum;
            }
            //robot.turnRight();
            //if(side.equals("Right")) robot.strafeLeft();
            //else robot.strafeRight();
            for (int n=0; n < coulum; n++) {
                while(robot.Distance.getDistance(DistanceUnit.CM) > 6);
                if((n-1)!=coulum) while(robot.Distance.getDistance(DistanceUnit.CM) < 6);
            }
            robot.stop();

            robot.conveyor.setPower(1.0);
            sleep(3000);
            robot.conveyor.setPower(0);
        }
    }
}
