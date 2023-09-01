// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;
import java.util.function.Supplier;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Sub_Arm;

public class Cmd_ManualArm extends CommandBase {
  /** Creates a new Cmd_ManualArm. */
  private final Sub_Arm Arm;
  private final Supplier<Double> RT, LT;
  private final Supplier<Boolean> Abutton,Bbutton;
  public Cmd_ManualArm(Sub_Arm arm,Supplier<Double> RT, Supplier<Double> LT,Supplier<Boolean>Abutton,Supplier<Boolean>Bbutton) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
    this.Arm=arm;
    this.RT=RT;
    this.LT=LT;
    this.Abutton=Abutton;
    this.Bbutton=Bbutton;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    Arm.resetEncodersArm();
    Arm.resetEncodersWrist();
    Arm.SetOpenLoopedSArm(1);
    Arm.SetOpenLoopedSWrist(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double up=RT.get()-LT.get();

    if(Arm.getRightArmEncoder()<=145 || Arm.getLeftArmEncoder()<=145){
        Arm.setSpeedArm(up, up);
    }
    else{
      Arm.setSpeedArm(0, 0);
      Arm.setSpeedWrist(0);
    }
    

    
    if(Bbutton.get()){
      Arm.setSpeedWrist(.2);
    }
    if(Abutton.get()){
      Arm.setSpeedWrist(-.2);
    }
    

  
  
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {}

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
