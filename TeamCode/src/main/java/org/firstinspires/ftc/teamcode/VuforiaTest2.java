package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.matrices.OpenGLMatrix;
import org.firstinspires.ftc.robotcore.external.navigation.DistanceUnit;
import org.firstinspires.ftc.robotcore.external.navigation.RelicRecoveryVuMark;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackable;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaTrackables;

/**
 * Created by User on 11/7/2017.
 */
@Autonomous

public class VuforiaTest2 extends LinearOpMode{
    public static final String TAG = "Vuforia VuMark Sample";

    OpenGLMatrix lastLocation = null;

    VuforiaLocalizer vuforia;

    MechHardware robot = new MechHardware();

    @Override public void runOpMode() {

        robot.init(hardwareMap);

        int cameraMonitorViewId = hardwareMap.appContext.getResources().getIdentifier("cameraMonitorViewId", "id", hardwareMap.appContext.getPackageName());
        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        parameters.vuforiaLicenseKey = "AXdOcVz/////AAAAGSXgHubMS0r6roxUdjf9DWKA7GTut2LGpmgusuOdBmgcr9vnOOQAc9l3bXYlX+3nFwmePFZh1Brz4BsbF1h6zhAXHx5VvmWWpVeNoCLbxDnyaPBtmZ3k2ZgHLFLTjS2ST/arbrmCSAVUTX9xOgenNw+pcCuZYKQxr34uWWppyhJPPIPP152Gud24gReY/Sg8hM20JYH49E1nRLpIjYA1FTxWy135xOu5SCns1p48AgTLxyy51v0WRBALpIWAW/Qe30gGHb9W3swgPlBj1EVMFgLdCXgp5NAlKNvID6T6G8NaN2dYY64Cv601JDKkcGHM1KNU5N4vn6spJkE0tZI2NqfiSaBKT59+nPsbv5AGQ0IA";

        parameters.cameraDirection = VuforiaLocalizer.CameraDirection.BACK;
        this.vuforia = ClassFactory.createVuforiaLocalizer(parameters);

        VuforiaTrackables relicTrackables = this.vuforia.loadTrackablesFromAsset("RelicVuMark");
        VuforiaTrackable relicTemplate = relicTrackables.get(0);
        relicTemplate.setName("relicVuMarkTemplate");

        telemetry.addData(">", "Press Play to start");
        telemetry.update();
        waitForStart();

        relicTrackables.activate();

        while (opModeIsActive()) {

            RelicRecoveryVuMark vuMark = RelicRecoveryVuMark.from(relicTemplate);
            int coulum = 2;
            if (vuMark != RelicRecoveryVuMark.UNKNOWN) {
                if (vuMark == RelicRecoveryVuMark.LEFT) coulum--;
                else if (vuMark == RelicRecoveryVuMark.LEFT) coulum++;
            }
            //deppendant on allience color, coulum=3-coulum;
            robot.frontLeftDrive.setPower(1);
            for (int n=0; n < coulum; n++) {
                while(robot.Distance.getDistance(DistanceUnit.CM) > 6);
                if((n-1)!=coulum) while(robot.Distance.getDistance(DistanceUnit.CM) < 6);
            }
            robot.frontLeftDrive.setPower(0);
        }
    }
}
