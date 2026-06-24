// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.intake;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

// Intake Roller, Storage Roller, and Indexer
public class IntakeStorageSubsystem extends SubsystemBase {
  TalonFX m_intakeMotor = new TalonFX(0);
  /*Two indexers, but im stupid so i called one storage */
  TalonFX m_storageMotor = new TalonFX(0);
  TalonFX m_indexerMotor = new TalonFX(0);
  /** Creates a new IntakeSubsystem. */
  public IntakeStorageSubsystem() {}

  @Override
  public void periodic() {

    // This method will be called once per scheduler run
  }

  // Base intake ball through storage method
  public void intakeBall() {
    m_intakeMotor.set(-1);
    m_storageMotor.set(1);
  }

  // customizable intake ball through storage method
  public void intakeBall(double speed) {
    m_intakeMotor.set(speed);
    m_storageMotor.set(speed);
  }

  // Method to use incase of storage roller getting stuck.
  public void storageRoll() {
    m_storageMotor.set(-1);

  }
}
