package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.util.ElapsedTime;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by User on 11/7/2017.
 */
@Autonomous
//@Disabled
public class StateAuto extends LinearOpMode{

    //creating hardware objects
    Chassis chassis = new Chassis(true,true);
    Particle particle = new Particle();
    Belt belt = new Belt();

    //init input varibles
    private String color = "Red";
    private String side = "Left";
    private String target = "Center";

    //encoder values
    static final int TILE = 560;
    static final int TURN_QUARTER = 385;

    ElapsedTime runtime = new ElapsedTime();


    @Override public void runOpMode() {
        //init hardware
        chassis.init(hardwareMap);
        particle.init(hardwareMap);
        belt.init(hardwareMap);

        //vuforia setup
        VuforiaTrackables relicTrackables = this.chassis.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        //side/color input for match
        while (!opModeIsActive()) {
            if(gamepad1.x || gamepad2.x) color = "Blue";
            if (gamepad1.b || gamepad2.b) color = "Red";
            telemetry.addData("Color {Blue (X), Red (B)}", color);

            if(gamepad1.a || gamepad2.a) side = "Left";
            if (gamepad1.y || gamepad2.y) side = "Right";
            telemetry.addData("Side {Left (A), Right (Y)}", side);

            if(gamepad1.left_stick_button || gamepad2.left_stick_button) side = "Center";
            if (gamepad1.right_stick_button || gamepad2.right_stick_button) side = "Far";
            telemetry.addData("Target {Center (Left Stick), Far (Right Stick)}", target);

            telemetry.update();
        }
        waitForStart();

        //lowers jewel arm
        particle.setRotation(Particle.OUT);
        particle.setElevation(Particle.DOWN);

        //calculates target coulum using vuforia input
        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        int coulum = 1;
        sleep(3000);
        if (vuMark == RelicRecoveryVuMark.LEFT) coulum++;
        else if (vuMark == RelicRecoveryVuMark.RIGHT) coulum=-2;

        //knocks off appropriate jewel
        particle.hit(particle.match(color) ? 1.0 : -1.0);
        sleep(500);
        particle.setElevation(Particle.ELEVATORINIT);
        sleep(500);
        particle.setRotation(Particle.ROTATIONINIT);
        sleep(800);

        //setup robot infront of center of cryptobox
        if(target.equals("Center")) {
            int direction = 1;
            if (side.equals("Right")) direction = -1;
            chassis.drivePower(direction / 1.5);
            sleep(1450);
            chassis.turnPower(-0.4);
            sleep(1200);
        }
        else {
            double colorNum = color.equals("Red") ? 1 : -1;
            chassis.drivePower(colorNum / 1.5);
            sleep(1450);
            chassis.strafePower(-0.6);
            sleep(800);
            if (colorNum == 1) {
                chassis.turnPower(0.4);
                sleep(2400);
            }
        }
        chassis.drivePower(0.4);
        sleep(2200);
        chassis.drivePower(-0.2);
        sleep(200);

        //strafes to appropriate coulum according to earlier calculation
        boolean end = false;
        double timer = runtime.time();
        chassis.strafePower(Math.signum(coulum) / 2);
        for (int n = 0; n < Math.abs(coulum); n++) {
            while (chassis.getDistance() > 20) {
                if (runtime.time() > (timer + 10.0)) {
                    end = true;
                    break;
                }
            }
            if (end) break;
            if ((n + 1) != coulum) while (chassis.getDistance() < 20) ;
        }
        chassis.stop();

        //place glyph in crytobox
        chassis.drivePower(-0.3);
        sleep(250);
        chassis.stop();
        belt.setPower(1.0);
        sleep(2000);
        belt.stop();

        //release/push glyph into coulum
        chassis.drivePower(-0.4);
        sleep(800);
        chassis.drivePower(0.4);
        sleep(1000);
        chassis.drivePower(-0.3);
        sleep(250);
        chassis.stop();
    }
}
