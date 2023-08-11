package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Cmd_Intake;
import frc.robot.commands.Cmd_ManualDriveChasis;
import frc.robot.commands.Cmd_MoveChasis;
import frc.robot.commands.Cmd_VisionAlign;
import frc.robot.commands.Cmd_gyro;
import frc.robot.subsystems.Sub_Chasis;
import frc.robot.subsystems.Sub_Intake;


public class RobotContainer { 
  CommandXboxController ChasisControl = new CommandXboxController(0);
  CommandXboxController SubsystemControl = new CommandXboxController(1);
  Sub_Chasis chasis = new Sub_Chasis();
  Sub_Intake intake = new Sub_Intake();


  public RobotContainer() {
    chasis.setDefaultCommand(new Cmd_ManualDriveChasis(chasis, () -> ChasisControl.getRightTriggerAxis(), () -> ChasisControl.getLeftTriggerAxis(), () -> ChasisControl.getLeftX(), () -> ChasisControl.b().getAsBoolean()));
    //intake.setDefaultCommand(new Cmd_Intake(intake,() -> SubsystemControl.getLeftY(), 0));

    configureBindings();
  }


  private void configureBindings() {
   // ChasisControl.a().whileTrue(new Cmd_MoveChasis(chasis, 224));
    //ChasisControl.y().whileTrue(new Cmd_gyro(chasis,180));
    ChasisControl.b().whileTrue(new Cmd_gyro(chasis, 220));
    SubsystemControl.y().whileTrue(new Cmd_Intake(intake,() -> SubsystemControl.getLeftY(), 0));
    SubsystemControl.x().whileTrue(new Cmd_Intake(intake, () -> SubsystemControl.getLeftY(), 1));
   // ChasisControl.b().whileTrue(new Cmd_VisionAlign(chasis, 0));
   // ChasisControl.x().whileTrue(new Cmd_VisionAlign(chasis, 1));
  }

  public Command getAutonomousCommand() {
    //Nota: PID no esta para distancias cortas 
    return new SequentialCommandGroup(//
       new Cmd_MoveChasis(chasis, 30),
       new Cmd_gyro(chasis, 180),
       new Cmd_MoveChasis(chasis, 30)
      );
    
  }

  
}
