// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.turret;

import com.ctre.phoenix6.configs.Slot0Configs;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.configs.TalonFXConfigurator;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.Turret.*;
public class TurretSubsystem extends SubsystemBase {
  private TalonFX m_turretMotor = new TalonFX(TURRET_MOTOR_ID);
  private TalonFX m_leftShooterMotor = new TalonFX(L_SHOOTER_MOTOR_ID);
  private TalonFX m_rightShooterMotor = new TalonFX(R_SHOOTER_MOTOR_ID);

  private TalonFXConfigurator m_turretConfigurator = m_turretMotor.getConfigurator();
  public static TalonFXConfiguration m_turretConfigs = new TalonFXConfiguration();

  @SuppressWarnings("unused")
  private TalonFXConfigurator m_leftShooterConfigurator = m_leftShooterMotor.getConfigurator();
  public static TalonFXConfiguration m_shooterConfigs = new TalonFXConfiguration();

  private Slot0Configs Slot0 = m_turretConfigs.Slot0;

  private PositionVoltage m_turretRequest;

  InterpolatingDoubleTreeMap m_linearInterpolator = new InterpolatingDoubleTreeMap();

  /** Creates a new TurretSubsystem. */
  public TurretSubsystem() {
    m_turretConfigurator.apply(Slot0);
    
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public void setSpeedShootBall(double speed) {
    // The method will shoot the ball
    m_leftShooterMotor.set(-speed);
    m_rightShooterMotor.set(speed);
  }

  public void basicShootBall() {
    // Default speed of motor set.
    m_leftShooterMotor.set(-1);
    m_rightShooterMotor.set(1);
  }

  public void advShootBall(double aprilTagDist) {
    /*
     * The method will shoot the ball, but takes into account the distance of the select AprilTag and decide speed from there.
     * Plans to use Linear Interpolation for this with PhotonVision.
     */
    m_leftShooterMotor.set(-interpolateDistance(aprilTagDist));
    m_rightShooterMotor.set(interpolateDistance(aprilTagDist));
  }

  // Outputs the speed of motor depending on distance based on a given set
  public double interpolateDistance(double distance) {
    // Needs measurements.
    m_linearInterpolator.put(1.0, 1.0); // key -> meters away from hub, value -> motor speed from (0 - 1)
    return m_linearInterpolator.get(distance);
  }

  // Use TalonFX to steer turret to tag based on given yaw.
  public void turretToTagYaw(double targetYaw) {
    m_turretRequest = new PositionVoltage(targetYaw); // PID given a position.
    m_turretMotor.setControl(m_turretRequest);
  }
  
}
