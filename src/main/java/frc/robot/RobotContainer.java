// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import static edu.wpi.first.units.Units.MetersPerSecond;
import static edu.wpi.first.units.Units.RadiansPerSecond;
import static edu.wpi.first.units.Units.RotationsPerSecond;

import com.ctre.phoenix6.swerve.SwerveModule.DriveRequestType;
import com.ctre.phoenix6.swerve.SwerveRequest;

import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.RobotModeTriggers;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine.Direction;
import frc.robot.Constants.TunerConstants;
import frc.robot.swerve.CommandSwerveDrivetrain;
import frc.robot.swerve.Telemetry;

public class RobotContainer {
  private double MaxSpeed = 1.0 * TunerConstants.kSpeedAt12Volts.in(MetersPerSecond); // kSpeedAt12Volts desired top speed
  private double MaxAngularRate = RotationsPerSecond.of(0.75).in(RadiansPerSecond); // 3/4 of a rotation per second max angular velocity

    /* Setting up bindings for necessary control of the swerve drive platform */
  private final SwerveRequest.FieldCentric drive = new SwerveRequest.FieldCentric()
          .withDeadband(MaxSpeed * 0.1).withRotationalDeadband(MaxAngularRate * 0.1) // Add a 10% deadband
          .withDriveRequestType(DriveRequestType.OpenLoopVoltage); // Use open-loop control for drive motors
  private final SwerveRequest.SwerveDriveBrake brake = new SwerveRequest.SwerveDriveBrake();
  private final SwerveRequest.PointWheelsAt point = new SwerveRequest.PointWheelsAt();

  private final Telemetry logger = new Telemetry(MaxSpeed);

  public final CommandSwerveDrivetrain drivetrain = TunerConstants.createDrivetrain();
  // Reused from code Lucas made in 2024 (because he's goated) :skull:
  public enum BindingsSetting {
    PITS,
    COMPETITION;
  }

  // Currently not in SmartDashboard, need to add soon
  // Bindings settings :)
  private BindingsSetting m_setting = BindingsSetting.COMPETITION;
  // Honestly unsure if it works.
  private int team = (int) SmartDashboard.getNumber("Team ID", 0);

  private CommandXboxController m_driverController = new CommandXboxController(0);
  private CommandXboxController m_operatorController = new CommandXboxController(0);

  // would be surprised if we used this but it's available if anyone wanted to sim stuff lol.
  @SuppressWarnings("unused")
  private XboxControllerSim m_driverControllerSim = new XboxControllerSim(0);
  @SuppressWarnings("unused")
  private XboxControllerSim m_operatorControllerSim = new XboxControllerSim(0);

  public RobotContainer() {
    switch (m_setting) {
      case PITS:
        configureBindings();
        configureBindingsPits();
        break;
      case COMPETITION:
        configureBindings();
        configureBindingsComp(team);
        break;
      default:
        configureBindings();
        configureBindingsComp(team);
        break;
    }

  }

  // Bindings used in both comp. and pits
  private void configureBindings() {
    drivetrain.setDefaultCommand(
            // Drivetrain will execute this command periodically
            drivetrain.applyRequest(() ->
                drive.withVelocityX(-m_driverController.getLeftY() * MaxSpeed) // Drive forward with negative Y (forward)
                    .withVelocityY(-m_driverController.getLeftX() * MaxSpeed) // Drive left with negative X (left)
                    .withRotationalRate(-m_driverController.getRightX() * MaxAngularRate) // Drive counterclockwise with negative X (left)
            )
        );

        // Idle while the robot is disabled. This ensures the configured
        // neutral mode is applied to the drive motors while disabled.
        final var idle = new SwerveRequest.Idle();
        RobotModeTriggers.disabled().whileTrue(
            drivetrain.applyRequest(() -> idle).ignoringDisable(true)
        );

        m_driverController.a().whileTrue(drivetrain.applyRequest(() -> brake));
        m_driverController.b().whileTrue(drivetrain.applyRequest(() ->
            point.withModuleDirection(new Rotation2d(-m_driverController.getLeftY(), -m_driverController.getLeftX()))
        ));

        // Run SysId routines when holding back/start and X/Y.
        // Note that each routine should be run exactly once in a single log.
        m_driverController.back().and(m_driverController.y()).whileTrue(drivetrain.sysIdDynamic(Direction.kForward));
        m_driverController.back().and(m_driverController.x()).whileTrue(drivetrain.sysIdDynamic(Direction.kReverse));
        m_driverController.start().and(m_driverController.y()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kForward));
        m_driverController.start().and(m_driverController.x()).whileTrue(drivetrain.sysIdQuasistatic(Direction.kReverse));

        // Reset the field-centric heading on left bumper press.
        m_driverController.leftBumper().onTrue(drivetrain.runOnce(drivetrain::seedFieldCentric));

        drivetrain.registerTelemetry(logger::telemeterize);
    

  }

  // Competition only bindings
  private void configureBindingsComp(int teamID) {

    // We currently don't know who is going to be on each specific team.
    switch (teamID) {
      case 1:
      // Driver Team 1 Configs

      case 2:
      // Driver Team 2 Configs

      default:
      //Controller bindings that enables if no specified driver team is selected.
      // If no team ID is selected, it will automatically choose 0 based on the previous code on line 26.

      break;
    }

  }

  private void configureBindingsPits() {

  }

  public Command getAutonomousCommand() {
    // Simple drive forward auton
        final var idle = new SwerveRequest.Idle();
        return Commands.sequence(
            // Reset our field centric heading to match the robot
            // facing away from our alliance station wall (0 deg).
            drivetrain.runOnce(() -> drivetrain.seedFieldCentric(Rotation2d.kZero)),
            // Then slowly drive forward (away from us) for 5 seconds.
            drivetrain.applyRequest(() ->
                drive.withVelocityX(0.5)
                    .withVelocityY(0)
                    .withRotationalRate(0)
            )
            .withTimeout(5.0),
            // Finally idle for the rest of auton
            drivetrain.applyRequest(() -> idle)
        );
  }
}
