// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.turret;

import static frc.robot.turret.TurretSubsystem.*;

/** Contains the Turret Configurations + Shoot Configs (Doubt there's really any though) */
public class TurretConfigs {

// Turret Steer Configs
public static double kP = m_turretConfigs.Slot0.kP = 0.008;
public static double KI = m_turretConfigs.Slot0.kI = 0.0;
public static double kD = m_turretConfigs.Slot0.kD = 0.0;

// This stuff is probably unnecessary and would only need to really be put for swerve current limits.
public static double statorCurrentLimit = m_turretConfigs.CurrentLimits.StatorCurrentLimit = 0;
public static double supplyCurrentLimit = m_turretConfigs.CurrentLimits.SupplyCurrentLimit = 0;
public static boolean statorCurrentLimitOn = m_turretConfigs.CurrentLimits.StatorCurrentLimitEnable = true;
public static boolean supplyCurrentLimitOn = m_turretConfigs.CurrentLimits.StatorCurrentLimitEnable = true;

}
