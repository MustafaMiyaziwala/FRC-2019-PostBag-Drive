package frc.robot;

import frc.robot.commands.PIDElevatorToPosition;
import frc.robot.commands.GraspHatch;
import frc.robot.commands.PathExecuter;
import frc.robot.commands.ReleaseHatch;
import frc.robot.commands.SlideHatchIn;
import frc.robot.commands.SlideHatchOut;
import frc.robot.controllers.Logitech;
import frc.robot.subsystems.Elevator;
import jaci.pathfinder.Waypoint;

/**
 * This class is the glue that binds the controls on the physical operator
 * interface to the commands and command groups that allow control of the robot.
 */
public class OI {

	public Logitech driveStick;
	public Logitech secondStick;

	public OI() {
		driveStick = new Logitech(0);
		secondStick = new Logitech(1);
		Waypoint[] test = new Waypoint[]{
			new Waypoint(0, 0, 0),
			new Waypoint(5, 5, Math.PI/2),
		};
		//driveStick.buttonA.whenPressed(new PathExecuter("TestPath"));

		secondStick.buttonLJoystick.whenPressed(new PIDElevatorToPosition(Elevator.ElevatorPosition.DOWN));
		secondStick.buttonX.whenPressed(new PIDElevatorToPosition(Elevator.ElevatorPosition.ROCKET_SECOND));
		secondStick.buttonA.whenPressed(new PIDElevatorToPosition(Elevator.ElevatorPosition.ROCKET_FIRST));
		secondStick.buttonY.whenPressed(new PIDElevatorToPosition(Elevator.ElevatorPosition.ROCKET_THIRD));
		secondStick.buttonB.whenPressed(new PIDElevatorToPosition(Elevator.ElevatorPosition.CARGO_SHIP));

		secondStick.buttonRBumper.whenPressed(new GraspHatch());
		secondStick.buttonLBumper.whenPressed(new ReleaseHatch());

		secondStick.buttonStart.whenPressed(new SlideHatchOut());
		secondStick.buttonBack.whenPressed(new SlideHatchIn());

		// TODO: need to add controls for extending and retracting intake

	}

}
