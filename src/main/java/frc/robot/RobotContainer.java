// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.simulation.XboxControllerSim;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;

public class RobotContainer {
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
    return Commands.print("No autonomous command configured");
  }
}
