// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import com.revrobotics.CANSparkMax;
import com.revrobotics.RelativeEncoder;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

public class Sub_Arm extends SubsystemBase {
  private final CANSparkMax RightArmMotor= new CANSparkMax(6, MotorType.kBrushless);
  private final CANSparkMax LeftArmMotor= new CANSparkMax(7, MotorType.kBrushless);
  private final CANSparkMax WristMotor= new CANSparkMax(8, MotorType.kBrushless);

    //Encoders 
    private final RelativeEncoder EncoderR=RightArmMotor.getEncoder();
    private final  RelativeEncoder EncoderL=LeftArmMotor.getEncoder();
    private final  RelativeEncoder EncoderWrist=WristMotor.getEncoder();

  DigitalInput limitswitch= new DigitalInput(0);
  boolean limits=limitswitch.get();
    
  public Sub_Arm() {
    //Brake cuando no se mueve 
    RightArmMotor.setIdleMode(IdleMode.kBrake);
    LeftArmMotor.setIdleMode(IdleMode.kBrake);
    WristMotor.setIdleMode(IdleMode.kBrake);

  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putBoolean("Limit", limitswitch.get());
  }
    public void resetEncodersArm(){
      EncoderR.setPosition(0);
      EncoderL.setPosition(0);
    }

    public void resetEncodersWrist(){
      EncoderWrist.setPosition(0);
    }

    public double getLeftArmEncoder(){
      return EncoderL.getPosition();
    }

    public double getRightArmEncoder(){
      return EncoderR.getPosition();
    }
    
   public double getWristEncoder(){
      return EncoderWrist.getPosition();
    }
    public void setSpeedArm(double RightSpeed,double LeftSpeed){
      if(limitswitch.get()){
        if(Math.abs(LeftSpeed) >= 0.8){LeftSpeed = Math.abs(LeftSpeed/Math.abs(LeftSpeed))*0.8;}
        if(Math.abs(RightSpeed) >= 0.8){RightSpeed = Math.abs(RightSpeed/Math.abs(RightSpeed))*0.8;}
      }
  
      if(Math.abs(LeftSpeed) >= 0.8){LeftSpeed = (LeftSpeed/Math.abs(LeftSpeed))*0.8;}
      if(Math.abs(RightSpeed) >= 0.8){RightSpeed = (RightSpeed/Math.abs(RightSpeed))*0.8;}
  
      RightArmMotor.set(RightSpeed*.5);LeftArmMotor.set(LeftSpeed*.5);
    }

    public void setSpeedWrist(double Wristspeed){
      if(Math.abs(Wristspeed) >= 0.8){Wristspeed = (Wristspeed/Math.abs(Wristspeed))*0.8;}
  
      WristMotor.set(Wristspeed*.5);
    }

    public void SetOpenLoopedSArm(double S){
      RightArmMotor.setClosedLoopRampRate(S); LeftArmMotor.setClosedLoopRampRate(S);
    }
    public void SetOpenLoopedSWrist(double S){
     // WristMotor.setClosedLoopRampRate(S); 
    }

}
