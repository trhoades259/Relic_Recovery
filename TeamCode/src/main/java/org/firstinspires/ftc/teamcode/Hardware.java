package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.hardware.HardwareMap;

/**
 * Created by User on 10/17/2017.
 */

public class Hardware {

    Conveyor conveyor = new Conveyor();
    Relic relic = new Relic();
    Jewel jewel = new Jewel();
    Chasis chasis = new Chasis();

    Hardware() {};

    public void init(HardwareMap hwm) {
        conveyor.init(hwm);
        relic.init(hwm);
        jewel.init(hwm);
        chasis.init(hwm);
    }
}