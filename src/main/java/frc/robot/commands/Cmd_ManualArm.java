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
  private final Supplier<Double> RT, LT,Joystick;
  private final Supplier<Boolean>botonY;


  public Cmd_ManualArm(Sub_Arm arm,Supplier<Double> RT, Supplier<Double> LT,Supplier<Double> joytick, Supplier<Boolean>botonY) {
    // Use addRequirements() here to declare subsystem dependencies.
    addRequirements(arm);
    this.Arm=arm;
    this.RT=RT;
    this.LT=LT;
    this.Joystick=joytick;
    this.botonY=botonY;

  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    if(Arm.getlimitswitch())
    {
      Arm.resetEncodersArm();
    }
    Arm.SetOpenLoopedSArm(1);
    Arm.SetOpenLoopedSWrist(1);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    double up=RT.get()-LT.get();

    if(Arm.getRightArmEncoder() - Arm.getLeftArmEncoder() <=10){
        Arm.setSpeedArm(up, up);
    }
    else{
      Arm.setSpeedArm(0, 0);
      Arm.disablemotors();

    }
    double direccion=0;
    if(botonY.get())
    {

    }
    if(Math.abs(Joystick.get())<=.25){direccion=0;}else{direccion=Joystick.get();}
    Arm.setSpeedWrist(direccion*.5);   

    

    

  
  
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
