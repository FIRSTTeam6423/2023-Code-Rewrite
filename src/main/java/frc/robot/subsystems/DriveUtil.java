// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.


package frc.robot.subsystems;


import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.kinematics.SwerveModuleState;
import edu.wpi.first.math.kinematics.SwerveDriveKinematics;
import edu.wpi.first.math.kinematics.SwerveDriveOdometry;
import edu.wpi.first.math.kinematics.SwerveModulePosition;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.DriverStation.Alliance;
import edu.wpi.first.wpilibj.smartdashboard.Field2d;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import frc.robot.Constants;
import frc.robot.RobotContainer;


public class DriveUtil extends SubsystemBase {
   // P denotes Pivoting, D driving
   private final Translation2d m_frontLeftLoc = new Translation2d(Constants.TOPLEFT_X, Constants.TOPLEFT_Y);
   private final Translation2d m_frontRightLoc = new Translation2d(Constants.TOPRIGHT_X, Constants.TOPRIGHT_Y);
   private final Translation2d m_backLeftLoc = new Translation2d(Constants.BOTTOMLEFT_X, Constants.TOPLEFT_Y);
   private final Translation2d m_backRightLoc = new Translation2d(Constants.BOTTOMRIGHT_X, Constants.BOTTOMRIGHT_Y);
   private boolean started = false;
   private Field2d f2d = new Field2d();
   private AHRS gyro = new AHRS();


   private final SwerveModule m_frontLeft = new SwerveModule(
           Constants.FRONTLEFT_DRIVE,
           true,
           Constants.FRONTLEFT_PIVOT,
           Constants.TOPLEFT_ABS_ENCODER, true);
   private final SwerveModule m_frontRight = new SwerveModule(
           Constants.FRONTRIGHT_DRIVE,
           true,
           Constants.FRONTRIGHT_PIVOT,
           Constants.TOPRIGHT_ABS_ENCODER, true);
   private final SwerveModule m_backLeft = new SwerveModule(
           Constants.BACKLEFT_DRIVE,
           true,
           Constants.BACKLEFT_PIVOT,
           Constants.BOTTOMLEFT_ABS_ENCODER, true);
   private final SwerveModule m_backRight = new SwerveModule(
           Constants.BACKRIGHT_DRIVE,
           true,
           Constants.BACKRIGHT_PIVOT,
           Constants.BOTTOMRIGHT_ABS_ENCODER, true);
  
   public SwerveDriveKinematics kinematics = new SwerveDriveKinematics(m_frontLeftLoc, m_frontRightLoc,
           m_backLeftLoc, m_backRightLoc);

   // getPosition is just placeholder for getting distance with encoders even
   // though wpilib uses it as an example
  
   private final SwerveDriveOdometry m_odometry = new SwerveDriveOdometry(kinematics, getHeading2d(),
           new SwerveModulePosition[] {
                   m_frontLeft.getPosition(),
                   m_frontRight.getPosition(),
                   m_backLeft.getPosition(),
                   m_backRight.getPosition()
           }, new Pose2d(0.0, 0.0, new Rotation2d()));
}


public void start() {
       RobotContainer.allianceOrientation = 180;
   // 180 no matter Red or Blue Alliance
}
public double deadzone(double input){
    if(Math.abs(input) >= Constants.XBOX_STICK_DEADZONE_WIDTH){
        return input;
    } else {
        return 0;
    }
}

public void driveRobot(boolean fieldRelative) {
    int xSign = (int)Math.signum(RobotContainer.getDriverLeftXboxY());
    double xSpeed = xSign * Math.pow(deadzone(RobotContainer.getDriverLeftXboxY()), 2) 
                    * Constants.MAX_LINEAR_SPEED 
                    * Math.cos(Math.toRadians(RobotContainer.allianceOrientation))
                    * ((RobotContainer.getDriverRightXboxTrigger() > .5) ? .25 : 1); //reversed x and y so that up on controller is

    int ySign = (int)Math.signum(RobotContainer.getDriverLeftXboxX());
    double ySpeed = ySign * Math.pow(deadzone(RobotContainer.getDriverLeftXboxX()), 2) 
                    * Constants.MAX_LINEAR_SPEED 
                    * Math.cos(Math.toRadians(RobotContainer.allianceOrientation))
                    * ((RobotContainer.getDriverRightXboxTrigger() > .5) ? .25 : 1); //reversed x and y so that up on controller is

    double omega = deadzone(RobotContainer.getDriverRightXboxX()) 
                    * Math.toRadians(Constants.MAX_ANGULAR_SPEED) 
                    * ((RobotContainer.getDriverRightXboxTrigger() > .5) ? .25 : 1);

    var swerveModuleStates = kinematics.toSwerveModuleStates(
            fieldRelative
                    ? ChassisSpeeds.fromFieldRelativeSpeeds(
                            xSpeed, //reversed x and y so that up on controller is
                            ySpeed, //forward from driver pov
                            omega, 
                            m_odometry.getPoseMeters().getRotation())
                    : new ChassisSpeeds(RobotContainer.getDriverLeftXboxY() * Constants.MAX_LINEAR_SPEED,
                            RobotContainer.getDriverLeftXboxX() * Constants.MAX_LINEAR_SPEED,//Note y and x swapped for first 2 arguments is not intuitive, x is "forward"
                            RobotContainer.getDriverRightXboxX() * Constants.MAX_ANGULAR_SPEED));

    //SwerveDriveKinematics.desaturateWheelSpeeds(swerveModuleStates, Constants.MAX_LINEAR_SPEED);

    m_frontLeft.setDesiredState(swerveModuleStates[0]);
    m_frontRight.setDesiredState(swerveModuleStates[1]);
    m_backLeft.setDesiredState(swerveModuleStates[2]);
    m_backRight.setDesiredState(swerveModuleStates[3]);
}

public void setChassisSpeeds(ChassisSpeeds chassisSpeeds) {
    setSwerveModuleStates(kinematics.toSwerveModuleStates(chassisSpeeds));
}

public void setSwerveModuleStates(SwerveModuleState[] states) {
    m_frontLeft.setDesiredState(states[0]);
    m_frontRight.setDesiredState(states[1]);
    m_backLeft.setDesiredState(states[2]);
    m_backRight.setDesiredState(states[3]);
}

public void setSwerveModuleStatesFromPathPlanner(SwerveModuleState[] states) {
    m_frontLeft.setDesiredStateFromPathPlanner(states[0]);
    m_frontRight.setDesiredStateFromPathPlanner(states[1]);
    m_backLeft.setDesiredStateFromPathPlanner(states[2]);
    m_backRight.setDesiredStateFromPathPlanner(states[3]);
}

public Rotation2d getHeading2d() {
    return gyro.getRotation2d();
}

public Pose2d getPose() {
    return m_odometry.getPoseMeters();
}

public Pose2d getAngleNegatedPose(){
    Pose2d p=getPose();
    return new Pose2d(p.getTranslation(), p.getRotation().times(-1));
}

public double getHeading() {
    return gyro.getYaw();
}

public void resetGyro() {
    gyro.reset();
}

public void resetPose(Pose2d pose) {
    m_odometry.resetPosition(gyro.getRotation2d(), new SwerveModulePosition[] {
        m_frontLeft.getPosition(),
        m_frontRight.getPosition(),
        m_backLeft.getPosition(),
        m_backRight.getPosition()
    }, pose);
}

public void calibrateGyro() {
    gyro.calibrate();
}

@Override
public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("x pos",m_odometry.getPoseMeters().getX());
    SmartDashboard.putNumber("y pos",m_odometry.getPoseMeters().getY());
    SmartDashboard.putNumber("odo angle",m_odometry.getPoseMeters().getRotation().getDegrees());

    SmartDashboard.putNumber("yaw", gyro.getYaw());
    //removed pitch and roll as they are apparently irrelevant
    

    m_odometry.update(gyro.getRotation2d(),
            new SwerveModulePosition[] {
                    m_frontLeft.getPosition(), m_frontRight.getPosition(),
                    m_backLeft.getPosition(), m_backRight.getPosition()
            });

    
    f2d.setRobotPose(getPose());
    SmartDashboard.putData(f2d);
}
