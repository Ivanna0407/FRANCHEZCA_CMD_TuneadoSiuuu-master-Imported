package frc.robot;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import frc.robot.commands.Cmd_ManualDriveChasis;
import frc.robot.commands.Cmd_MoveChasis;
import frc.robot.commands.Cmd_VisionAlign;
import frc.robot.commands.Cmd_gyro;
import frc.robot.subsystems.Sub_Chasis;


public class RobotContainer {
  CommandXboxController ChasisControl = new CommandXboxController(0);
  CommandXboxController SubsystemControl = new CommandXboxController(1);
  Sub_Chasis chasis = new Sub_Chasis();



  public RobotContainer() {
    chasis.setDefaultCommand(new Cmd_ManualDriveChasis(chasis, () -> ChasisControl.getRightTriggerAxis(), () -> ChasisControl.getLeftTriggerAxis(), () -> ChasisControl.getLeftX(), () -> ChasisControl.b().getAsBoolean()));
    configureBindings();
  }


  private void configureBindings() {
    ChasisControl.a().whileTrue(new Cmd_MoveChasis(chasis, 224));
    //ChasisControl.y().whileTrue(new Cmd_gyro(chasis,90.0));
  }

  public Command getAutonomousCommand() {
    //Nota: PID no esta para distancias cortas 
    return new SequentialCommandGroup(//
        new Cmd_MoveChasis(chasis, -20),//
        new Cmd_gyro(chasis, 180),
        new Cmd_MoveChasis(chasis, 100),
        new Cmd_gyro(chasis, 180),
        new Cmd_MoveChasis(chasis, 100),
        new Cmd_VisionAlign(chasis)
      );
    
  }

  
}
