package frc.robot.commands;

import edu.wpi.first.wpilibj.command.CommandGroup;
import jaci.pathfinder.Waypoint;

public class TurnPIDTest extends CommandGroup {

	
	private Waypoint[] path = {new Waypoint(0, 0, 0), new Waypoint(5, 5, Math.PI/2)};

	public TurnPIDTest() {
		//addSequential(new TurnDegrees(90, 6));
		addSequential(new PathExecuter(path, "Test Path"));
		
	}
}
