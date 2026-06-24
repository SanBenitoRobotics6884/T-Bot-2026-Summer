// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.turret.t_commands;

import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.turret.TurretSubsystem;
import frc.robot.vision.VisionSubsystem;

/* You should consider using the more terse Command factories API instead https://docs.wpilib.org/en/stable/docs/software/commandbased/organizing-command-based.html#defining-commands */
public class TurretToAprilTag extends Command {
  VisionSubsystem m_visionSubsystem;
  TurretSubsystem m_turretSubsystem;

  /** Creates a new TurretToAprilTag. */
  public TurretToAprilTag(TurretSubsystem turretSubsystem, VisionSubsystem visionSubsystem) {
    m_turretSubsystem = turretSubsystem;
    m_visionSubsystem = visionSubsystem; 
    addRequirements(getRequirements());
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    // Rotates the Turret to AprilTag, assumes no offset so you might wanna place the camera at the front of the shooter.
    var targetYaw = m_visionSubsystem.targetYaw();
    m_turretSubsystem.turretToTagYaw(targetYaw);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
