package frc.robot.commands;

import edu.wpi.first.wpilibj.command.Command;
import frc.robot.OI;
import frc.robot.Robot;
import frc.robot.subsystems.Elevator;

public class IntakeControl extends Command {

	public IntakeControl() {
		requires(Robot.intake);
	}

	// Called just before this Command runs the first time
	@Override
	protected void initialize() {
		Robot.intake.setIntakePower(0);
	}

	// Called repeatedly when this Command is scheduled to run
	@Override
	protected void execute() {
		// Controls for moving intake down and up using dpad
		/*if (Robot.oi.secondStick.isPOVDownish()) {
			Robot.intake.extendIntake();
		} else if (Robot.oi.secondStick.isPOVUpish() || Robot.elevator.getElevatorEncoderOutput() > Elevator.MIN_ENCODER_LIMIT) {
			Robot.intake.retractIntake();
		}*/
		
		boolean rTriggerOn = Robot.oi.secondStick.getRTrigger() > 0.2;
		boolean lTriggerOn = Robot.oi.secondStick.getLTrigger() > 0.2;
		// Sets power to the intake motors. Uses whichever trigger is more pressed
		if (rTriggerOn) {
			Robot.intake.setIntakePower(-1);
		} else if (lTriggerOn) {
			Robot.intake.setIntakePower(0.75);
		} else {
			Robot.intake.setIntakePower(0);
		}
	}

	// Make this return true when this Command no longer needs to run execute()
	@Override
	protected boolean isFinished() {
		return false;
	}

	// Called once after isFinished returns true
	@Override
	protected void end() {
		Robot.intake.setIntakePower(0);
	}

	// Called when another command which requires one or more of the same subsystems
	// is scheduled to run
	@Override
	protected void interrupted() {
		end();
	}
}