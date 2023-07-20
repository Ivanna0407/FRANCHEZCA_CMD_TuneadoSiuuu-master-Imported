package frc.robot.commands;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Sub_Chasis;

public class Cmd_MoveChasis extends CommandBase {
  //Variables para un control dual de PID del chasis
  private final Sub_Chasis Chasis;
  private double Setpoint, Dt, LastDt, I_Zone;
  private double RightErrorP, RightErrorI, RightErrorD, RightLastError, RightSpeed;
  private double LeftErrorP, LeftErrorI, LeftErrorD, LeftLastError, LeftSpeed, ErrorTeta;
  private double kP, kI, kD, kT;

  //Constructor
  public Cmd_MoveChasis(Sub_Chasis sub_Chasis, double setpoint) {
    //Referencia a variables locales y requerimientos
    this.Chasis = sub_Chasis;
    this.Setpoint = setpoint;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    //Reinicio e inicializacion de variables
    resetAll();
    kP = 0.013; kI = 0.01; kD = 0.0022; kT=0.0012;
    Chasis.resetEncoders();
    Chasis.CalibrateMaxVoltage();
    Chasis.SetOpenLoopedS(1);
    Chasis.resetYaw();
    
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    
    //Delta de tiempo
    Dt = Timer.getFPGATimestamp() - LastDt;

    //P
    RightErrorP = Setpoint - Chasis.getRightEncoder();
    LeftErrorP = Setpoint  - Chasis.getLeftEncoder();

    //angulo
   


    //I
    if(Math.abs(RightErrorP) <= I_Zone){ RightErrorI += RightErrorP * Dt; }else{ RightErrorI = 0; }
    if(Math.abs(LeftErrorP) <= I_Zone){ LeftErrorI += LeftErrorP * Dt; }else{ LeftErrorI = 0; }

    //D
    RightErrorD = (RightErrorP-RightLastError)/Dt;
    LeftErrorD = (LeftErrorP-LeftLastError)/Dt;


    ErrorTeta = Chasis.getYaw();

    //Control de velocidad
    RightSpeed = (RightErrorP * kP) + (RightErrorI * kI) + (RightErrorD * kD)+(ErrorTeta * kT);
    LeftSpeed = (LeftErrorP * kP) + (LeftErrorI * kI) + (LeftErrorD * kD)-(ErrorTeta * kT);

    //Set a los motores
    Chasis.setSpeed(RightSpeed, LeftSpeed);

    //Retroalimentacion de errores y tiempos
    RightLastError = RightErrorP;
    LeftLastError = LeftErrorP;
    LastDt = Timer.getFPGATimestamp();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    Chasis.SetOpenLoopedS(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    //Control de error al 1%
    if(Math.abs(Chasis.getpromencoders()) == Math.abs(Setpoint)){ 
      Chasis.SetOpenLoopedS(0);
      return true; }else{ return false; }
  }

  public void resetAll(){
    Dt = 0; LastDt = 0; I_Zone = Math.abs(Setpoint*0.20);
    RightErrorP = 0; RightErrorI = 0; RightErrorD = 0; RightLastError = 0; RightSpeed = 0;
    LeftErrorP = 0; LeftErrorI = 0; LeftErrorD = 0; LeftLastError = 0; LeftSpeed = 0;
  }
}
