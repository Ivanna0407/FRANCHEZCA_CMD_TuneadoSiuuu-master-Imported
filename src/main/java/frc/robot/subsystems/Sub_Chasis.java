package frc.robot.subsystems;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.InvertType;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;

import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.NavX.AHRS;

import edu.wpi.first.networktables.*;

public class Sub_Chasis extends SubsystemBase {
  private double Volts;

  //Build motores
  private final WPI_TalonFX MasterRightMotor = new WPI_TalonFX(10);
  private final WPI_TalonFX SlaveRightMotor = new WPI_TalonFX(11);
  private final WPI_TalonFX MasterLeftMotor = new WPI_TalonFX(12);
  private final WPI_TalonFX SlaveLeftMotor = new WPI_TalonFX(13);

  //gyroscopio
  AHRS ahrs = new AHRS(SPI.Port.kMXP, (byte)66);
  
  
  public Sub_Chasis() {
    MasterRightMotor.configFactoryDefault();  SlaveRightMotor.configFactoryDefault();
    MasterLeftMotor.configFactoryDefault();  SlaveLeftMotor.configFactoryDefault();

    MasterLeftMotor.setInverted(true);
    SlaveRightMotor.follow(MasterRightMotor); SlaveRightMotor.setInverted(InvertType.FollowMaster);
    SlaveLeftMotor.follow(MasterLeftMotor); SlaveLeftMotor.setInverted(InvertType.FollowMaster);

    MasterRightMotor.setNeutralMode(NeutralMode.Brake); SlaveRightMotor.setNeutralMode(NeutralMode.Brake);
    MasterLeftMotor.setNeutralMode(NeutralMode.Brake); SlaveLeftMotor.setNeutralMode(NeutralMode.Brake);

    SetOpenLoopedS(0);

    MasterRightMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);
    MasterLeftMotor.configSelectedFeedbackSensor(FeedbackDevice.IntegratedSensor);

    MasterRightMotor.configSelectedFeedbackCoefficient((6*Math.PI)/(2048*10.9375));
    MasterLeftMotor.configSelectedFeedbackCoefficient((6*Math.PI)/(2048*10.9375));

    MasterRightMotor.set(0);
    MasterLeftMotor.set(0);

    Volts = RobotController.getBatteryVoltage();
  }

  @Override
  public void periodic() { 
    //SmartDashboard.putNumber("Velocidad Chasis", avgSpeed());
    SmartDashboard.putNumber("temperatura", gettemperature());
    SmartDashboard.putNumber("encoderizquierdo", getLeftEncoder());
    SmartDashboard.putNumber("encoderderecho", getRightEncoder());
    SmartDashboard.putNumber("Yaw", ahrs.getYaw());
    SmartDashboard.putNumber("RightSpeed", MasterRightMotor.get());
    SmartDashboard.putNumber("LeftSpeed", MasterLeftMotor.get());
    Volts = RobotController.getBatteryVoltage();
    
  }
  
  public void CalibrateMaxVoltage(){
    MasterRightMotor.configVoltageCompSaturation(Volts);
    MasterLeftMotor.configVoltageCompSaturation(Volts);
    MasterRightMotor.configVoltageCompSaturation(Volts);
    MasterLeftMotor.configVoltageCompSaturation(Volts);
  }

  public void resetEncoders(){
    MasterLeftMotor.setSelectedSensorPosition(0);
    MasterRightMotor.setSelectedSensorPosition(0);
  }
  
  public void resetYaw(){
    ahrs.reset();
  }

  public double getLeftEncoder(){
    return MasterLeftMotor.getSelectedSensorPosition();
  }

  public double getRightEncoder(){
    return MasterRightMotor.getSelectedSensorPosition();
  }

  public double getpromencoders(){
    return (getRightEncoder()+getLeftEncoder())/2;
  }
  
  public double gettemperature(){
    return MasterLeftMotor.getTemperature();
  }
  public void setSpeed(double RightSpeed,double LeftSpeed){
    if(Math.abs(RightSpeed) >= 0.8){RightSpeed = (RightSpeed/Math.abs(RightSpeed))*0.8;}

    if(Math.abs(LeftSpeed) >= 0.8){LeftSpeed = (LeftSpeed/Math.abs(LeftSpeed))*0.8;}

    MasterRightMotor.set(RightSpeed);
    MasterLeftMotor.set(LeftSpeed);
  }

  public double getYaw(){
    //return ahrs.getYaw();
    return ahrs.getYaw();
  }

  public void SetOpenLoopedS(double S){
    MasterRightMotor.configOpenloopRamp(S); MasterLeftMotor.configOpenloopRamp(S);
    SlaveRightMotor.configOpenloopRamp(S); SlaveLeftMotor.configOpenloopRamp(S);
  }

  public double getTx(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("tx").getDouble(0);
  }

  public double getTy(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ta").getDouble(0);
  }

  public double getTa(){
    return NetworkTableInstance.getDefault().getTable("limelight-abtomat").getEntry("ta").getDouble(10);
  }

}
