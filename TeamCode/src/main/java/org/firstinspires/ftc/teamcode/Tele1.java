/* Copyright (c) 2017 FIRST. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without modification,
 * are permitted (subject to the limitations in the disclaimer below) provided that
 * the following conditions are met:
 *
 * Redistributions of source code must retain the above copyright notice, this list
 * of conditions and the following disclaimer.
 *
 * Redistributions in binary form must reproduce the above copyright notice, this
 * list of conditions and the following disclaimer in the documentation and/or
 * other materials provided with the distribution.
 *
 * Neither the name of FIRST nor the names of its contributors may be used to endorse or
 * promote products derived from this software without specific prior written permission.
 *
 * NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
 * LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 * "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package org.firstinspires.ftc.teamcode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.eventloop.opmode.TeleOp;
import com.qualcomm.robotcore.hardware.DcMotor;

@TeleOp

public class Tele1 extends OpMode {

    DcMotor frontLeftDrive ;
    DcMotor backLeftDrive ;
    DcMotor frontRightDrive ;
    DcMotor backRightDrive ;


    @Override
    public void init() {
        frontLeftDrive = hardwareMap.dcMotor.get("front_left_drive");
        backLeftDrive = hardwareMap.dcMotor.get("back_left_drive");
        frontRightDrive = hardwareMap.dcMotor.get("front_right_drive");
        backRightDrive = hardwareMap.dcMotor.get("back_right_drive");

        frontLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        backLeftDrive.setDirection(DcMotor.Direction.FORWARD);
        frontRightDrive.setDirection(DcMotor.Direction.REVERSE);
        backRightDrive.setDirection(DcMotor.Direction.REVERSE);

        frontLeftDrive.setPower(0);
        backLeftDrive.setPower(0);
        frontRightDrive.setPower(0);
        backRightDrive.setPower(0);

        frontLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backLeftDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        frontRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        backRightDrive.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void init_loop() {
    }

    @Override
    public void start() {
    }

    @Override
    public void loop() {
        if (gamepad1.right_trigger>0.1&&(gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1)){
            if (gamepad1.right_stick_x>0.1){
                frontLeftDrive.setPower(gamepad1.right_trigger);
                backLeftDrive.setPower(gamepad1.right_trigger);
                frontRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
                backRightDrive.setPower(gamepad1.right_trigger-(gamepad1.right_stick_x));
            }
            if (gamepad1.right_stick_x<-0.1){
                frontLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                backLeftDrive.setPower(gamepad1.right_trigger+(gamepad1.right_stick_x));
                frontRightDrive.setPower(gamepad1.right_trigger);
                backRightDrive.setPower(gamepad1.right_trigger);
            }
        }
        else if(gamepad1.right_trigger>0.1) {
            frontLeftDrive.setPower(gamepad1.right_trigger);
            backLeftDrive.setPower(gamepad1.right_trigger);
            frontRightDrive.setPower(gamepad1.right_trigger);
            backRightDrive.setPower(gamepad1.right_trigger);
        }
        //move backwards
        else if (gamepad1.left_trigger>0.1) {
            frontLeftDrive.setPower(-gamepad1.left_trigger);
            backLeftDrive.setPower(-gamepad1.left_trigger);
            frontRightDrive.setPower(-gamepad1.left_trigger);
            backRightDrive.setPower(-gamepad1.left_trigger);
        }
        //move right
        else if (gamepad1.dpad_right==true) {
            frontLeftDrive.setPower(1.0);
            backLeftDrive.setPower(-1.0);
            frontRightDrive.setPower(-1.0);
            backRightDrive.setPower(1.0);
        }
        //move left
        else if (gamepad1.dpad_left==true) {
            frontLeftDrive.setPower(-1.0);
            backLeftDrive.setPower(1.0);
            frontRightDrive.setPower(1.0);
            backRightDrive.setPower(-1.0);
        }
        //rotation right
        else if (gamepad1.right_stick_x>0.1||gamepad1.right_stick_x<-0.1){
            frontLeftDrive.setPower(gamepad1.right_stick_x);
            backLeftDrive.setPower(gamepad1.right_stick_x);
            frontRightDrive.setPower(-gamepad1.right_stick_x);
            backRightDrive.setPower(-gamepad1.right_stick_x);
        }
        else {
            backLeftDrive.setPower(0.0);
            frontRightDrive.setPower(0.0);
            frontLeftDrive.setPower(0.0);
            backRightDrive.setPower(0.0);
        }
    }

    @Override
    public void stop() {
    }
}
