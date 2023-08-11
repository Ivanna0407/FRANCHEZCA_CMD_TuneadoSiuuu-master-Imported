// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Sub_Arm extends SubsystemBase {
  private final CANSparkMax RightArmMotor= new CANSparkMax(0, MotorType.kBrushless);
  private final CANSparkMax LeftArmMotor= new CANSparkMax(0, MotorType.kBrushless);
  private final CANSparkMax WristMotor= new CANSparkMax(0, MotorType.kBrushless);
  public Sub_Arm() {
    RightArmMotor.setIdleMode(IdleMode.kBrake);
    LeftArmMotor.setIdleMode(IdleMode.kBrake);
    WristMotor.setIdleMode(IdleMode.kBrake);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
