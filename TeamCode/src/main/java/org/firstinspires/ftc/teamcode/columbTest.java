package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by User on 11/7/2017.
 */
@Autonomous
//@Disabled
public class columbTest extends LinearOpMode{

    Chasis chasis = new Chasis(true);

    private String side = "Left";


    @Override public void runOpMode() {
        chasis.init(hardwareMap);

        VuforiaTrackables relicTrackables = this.chasis.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        while (!opModeIsActive()) {
            if(gamepad1.a || gamepad2.a) side = "Left";
            if (gamepad1.y || gamepad2.y) side = "Right";
            telemetry.addData("Side {A (Left), Y (Right)}", side);

            telemetry.update();
        }
        waitForStart();

        relicTrackables.activate();
        RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
        int coulum = 2;
        sleep(3000);
        if (vuMark == RelicRecoveryVuMark.LEFT) coulum--;
        else if (vuMark == RelicRecoveryVuMark.RIGHT) coulum++;
        if(side.equals("Right")) coulum=5-coulum;
        telemetry.addData("Columb: ", coulum);
        telemetry.update();

        for (int n=0; n < coulum; n++) {
            while(chasis.getDistance() > 20);
            if((n+1)!=coulum) while(chasis.getDistance() < 20);
        }
    }
}
