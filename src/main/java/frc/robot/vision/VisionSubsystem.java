// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.vision;

import java.util.Optional;

import org.photonvision.PhotonCamera;
import org.photonvision.simulation.PhotonCameraSim;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class VisionSubsystem extends SubsystemBase {
  private PhotonCamera m_visionCamera = new PhotonCamera(getName()); // Refers to the physical vision cam.
  private PhotonCameraSim m_simCam = new PhotonCameraSim(m_visionCamera); // Simulated vision cam.
  private double targetYaw = 0.0;
  private double targetDistanceInMeters = 0.0;
  private boolean targetVisible = false;
  private boolean hubTargetVisible = false;

  Optional<Alliance> alliance = DriverStation.getAlliance();
  
  /** Creates a new VisionSubsystem. */
  public VisionSubsystem() {}

  @Override
  public void periodic() {
    // Obtains a list of ALL results
    var results = m_visionCamera.getAllUnreadResults();

    if (!results.isEmpty()) {
      // Obtains a singular result from the results list.
      var result = results.get(results.size() - 1);
      if (result.hasTargets()) {
        targetVisible = true;
        // Obtains the targets, stores, and loops through each one.
        for (var target : result.getTargets()) {
          targetYaw = target.getYaw();
          if (target.getFiducialId() == 27) {
            hubTargetVisible = true;
            double hubTarget = target.getYaw();
            // If the cam sees the target hub, then it grabs that instead of bestTarget.
            targetYaw = hubTarget;
          }

        }
        var bestTarget = result.getBestTarget();
        double bestTargetYaw = bestTarget.getYaw();
        double bestTargetPitch =  bestTarget.getPitch();
        targetYaw = bestTargetYaw;
      } 
    }

    /* Smart Dashboard shit */
    SmartDashboard.getBoolean("Target Visible", targetVisible);
    SmartDashboard.getBoolean("Hub Target Visible", hubTargetVisible);
    SmartDashboard.getNumber("Yaw Value", targetYaw);
    
  }

  public boolean hubTargetIsVisible() {
    return hubTargetVisible;
  }

  // Obtains targetYaw so other classes can obtain it.
  public double targetYaw() {
    return targetYaw;
  }

  public double targetDistance() {
    return 0.0;
  }

}
