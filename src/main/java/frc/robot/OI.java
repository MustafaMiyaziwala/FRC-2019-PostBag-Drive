package frc.robot;

import frc.robot.commands.drive_controls.*;
import frc.robot.commands.basic_commands.*;
import frc.robot.controllers.Xbox;
import frc.robot.subsystems.Elevator;
import jaci.pathfinder.Waypoint;


/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public Xbox driveStick;
	public Xbox secondStick;

	public OI() {

		driveStick = new Xbox(0);
		secondStick = new Xbox(1);


		
		Waypoint[] test = new Waypoint[]{
			new Waypoint(0, 0, 0),
			new Waypoint(5, 5, Math.PI/2),
		};
		//driveStick.buttonA.whenPressed(new PathExecuter("TestPath"));

		secondStick.buttonLJoystick.whenPressed(new ElevatorToPosition(Elevator.ElevatorPosition.DOWN));
		secondStick.buttonX.whenPressed(new ElevatorToPosition(Elevator.ElevatorPosition.ROCKET_SECOND));
		secondStick.buttonA.whenPressed(new ElevatorToPosition(Elevator.ElevatorPosition.ROCKET_FIRST));
		secondStick.buttonY.whenPressed(new ElevatorToPosition(Elevator.ElevatorPosition.ROCKET_THIRD));
		secondStick.buttonB.whenPressed(new ElevatorToPosition(Elevator.ElevatorPosition.CARGO_SHIP));

		driveStick.buttonRBumper.whenPressed(new GraspHatch());
		driveStick.buttonLBumper.whenPressed(new ReleaseHatch());


		secondStick.buttonStart.whenPressed(new SlideHatchOut());
		secondStick.buttonBack.whenPressed(new SlideHatchIn());

		// TODO: need to add controls for extending and retracting intake
		

	}

}
