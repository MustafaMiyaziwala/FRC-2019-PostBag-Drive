package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;

import edu.wpi.first.wpilibj.AnalogInput;
import edu.wpi.first.wpilibj.Solenoid;
import edu.wpi.first.wpilibj.command.Subsystem;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Robot;
import frc.robot.RobotMap;
import frc.robot.commands.IntakeControl;
import frc.robot.util.Debouncer;
import frc.robot.util.RawInput;

public class Intake extends Subsystem {

	WPI_TalonSRX masterIntakeMotor, slaveIntakeMotor, innerIntakeMotor;
	Solenoid intakeSolenoid, slaveIntakeSolenoid;
	AnalogInput beamBreak;
	RawInput beamBreakInput;
	Debouncer beamBreakDebouncer;

	public static final int BALL_IS_IN_CURRENT = 0;

	public Intake() {
		masterIntakeMotor = new WPI_TalonSRX(RobotMap.RIGHT_INTAKE_MOTOR);
		slaveIntakeMotor = new WPI_TalonSRX(RobotMap.LEFT_INTAKE_MOTOR);

		masterIntakeMotor.setNeutralMode(NeutralMode.Brake);
		slaveIntakeMotor.setNeutralMode(NeutralMode.Brake);

		masterIntakeMotor.setInverted(true);
		slaveIntakeMotor.setInverted(false);

		slaveIntakeMotor.follow(masterIntakeMotor);

		innerIntakeMotor = new WPI_TalonSRX(RobotMap.INNER_INTAKE_MOTOR);
		innerIntakeMotor.setInverted(true);
		innerIntakeMotor.setNeutralMode(NeutralMode.Brake);

		beamBreak = new AnalogInput(RobotMap.INTAKE_SENSOR);

		beamBreakInput = () -> getRawCargoPosition();

		beamBreakDebouncer = new Debouncer(beamBreakInput, 13);

		intakeSolenoid = new Solenoid(RobotMap.INTAKE_SOLENOID);

		

	}

	public void extendIntake() {
		intakeSolenoid.set(true);
	}

	public void retractIntake() {
		intakeSolenoid.set(false);
	}

	public boolean getIntakeSolenoidState() {
		return intakeSolenoid.get();
	}

	/**
	 * @return sets power to all three intake motors, looking at conditions of
	 *         whether there is cargo and elevator position
	 * @param power
	 */
	public void setIntakePower(double power) {

		boolean isCargo = isCargo();
		
		if (isCargo || Robot.elevator.getElevatorEncoderOutput() > Elevator.MIN_ENCODER_LIMIT) {
			masterIntakeMotor.set(0);
		} else {
			masterIntakeMotor.set(power);
		}

		if (power != 0) {
			if (isCargo) {
				retractIntake();
				if (power < 0) {
					if(Robot.elevator.getElevatorEncoderOutput() > Elevator.MIN_ENCODER_LIMIT){
						if(innerIntakeMotor.getOutputCurrent() > BALL_IS_IN_CURRENT){
							innerIntakeMotor.set(power/4);
						} else {
							innerIntakeMotor.set(0);
						}
					} else {
						innerIntakeMotor.set(0);
					}
				} else {
					innerIntakeMotor.set(power);
				}
			} else {
				innerIntakeMotor.set(power);
			}
		} else {
			innerIntakeMotor.set(0);
		}

	}

	/**
	 * @return if cargo is in the intake it returns true, if false there is a hatch.
	 */
	private boolean getRawCargoPosition() {
		return beamBreak.getValue() < RobotMap.INTAKE_SENSOR_THRESHOLD;
	}

	public boolean isCargo(){
		return beamBreakDebouncer.compute();
	}

	@Override
	public void initDefaultCommand() {
		setDefaultCommand(new IntakeControl());
	}

	public void setIntakeDataOnDisplay(){
		SmartDashboard.putBoolean("Is Cargo Inside", isCargo());
		SmartDashboard.putBoolean("Are Intake Kebabs Extended", getIntakeSolenoidState());
		SmartDashboard.putNumber("Intake Kebabs Power", masterIntakeMotor.get());
		SmartDashboard.putNumber("Inner Intake Power", innerIntakeMotor.get());

	}
}