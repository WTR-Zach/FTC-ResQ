/* Copyright (c) 2014 Qualcomm Technologies Inc

All rights reserved.

Redistribution and use in source and binary forms, with or without modification,
are permitted (subject to the limitations in the disclaimer below) provided that
the following conditions are met:

Redistributions of source code must retain the above copyright notice, this list
of conditions and the following disclaimer.

Redistributions in binary form must reproduce the above copyright notice, this
list of conditions and the following disclaimer in the documentation and/or
other materials provided with the distribution.

Neither the name of Qualcomm Technologies Inc nor the names of its contributors
may be used to endorse or promote products derived from this software without
specific prior written permission.

NO EXPRESS OR IMPLIED LICENSES TO ANY PARTY'S PATENT RIGHTS ARE GRANTED BY THIS
LICENSE. THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
"AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR CONTRIBUTORS BE LIABLE
FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE. */

package com.qualcomm.ftcrobotcontroller.opmodes;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.*;


/**
 * TeleOp Mode
 * <p>
 * Enables control of the robot via the gamepad
 */
public class Color extends OpMode {

	/*
	 * Note: the configuration of the servos is such that
	 * as the arm servo approaches 0, the arm position moves up (away from the floor).
	 * Also, as the claw servo approaches 0, the claw opens up (drops the game element).
	 */
	// TETRIX VALUES.


	LightSensor light2;
	LightSensor light;

	/**
	 * Constructor
	 */
	public Color() {

	}

    public void turn(int degreesToTurn) {

    }


	/*
	 * Code to run when the op mode is first enabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#start()
	 */
	@Override
	public void init() {

		light2 = hardwareMap.lightSensor.get("light2");
		light = hardwareMap.lightSensor.get("light");

	}

	/*
	 * This method will be called repeatedly in a loop
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#run()
	 */
	int redCount = 0;
	String answer = "Calibrating...";
	//boolean done = false;

	@Override
	public void loop() {

		light2.enableLed(true);
		light.enableLed(true);

		double level = light.getLightDetectedRaw();
		double level2 = light2.getLightDetectedRaw();


		telemetry.addData("level", level);
		telemetry.addData("level2", level2);

		telemetry.addData("answer", answer);

		if(time > 5) {



				if(level > level2) {
					answer = "Blue is to the Left";
				} else if(level2 > level) {
					answer = "Blue is to the Right";

				}
		}



	}


	//	telemetry.addData("string", string);



	/*
	 * Code to run when the op mode is first disabled goes here
	 * 
	 * @see com.qualcomm.robotcore.eventloop.opmode.OpMode#stop()
	 */
	@Override
	public void stop() {

	}

    	
	/*
	 * This method scales the joystick input so for low joystick values, the 
	 * scaled value is less than linear.  This is to make it easier to drive
	 * the robot more precisely at slower speeds.
	 */
	double scaleInput(double dVal)  {
		double[] scaleArray = { 0.0, 0.05, 0.09, 0.10, 0.12, 0.15, 0.18, 0.24,
				0.30, 0.36, 0.43, 0.50, 0.60, 0.72, 0.85, 1.00, 1.00 };
		
		// get the corresponding index for the scaleInput array.
		int index = (int) (dVal * 16.0);
		
		// index should be positive.
		if (index < 0) {
			index = -index;
		}

		// index cannot exceed size of array minus 1.
		if (index > 16) {
			index = 16;
		}

		// get value from the array.
		double dScale = 0.0;
		if (dVal < 0) {
			dScale = -scaleArray[index];
		} else {
			dScale = scaleArray[index];
		}

		// return scaled value.
		return dScale;
	}

}
