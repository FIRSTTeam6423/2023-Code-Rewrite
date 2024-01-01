// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants. This class should not be used for any other purpose. All constants should be declared
 * globally (i.e. public static). Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  /**
     * DriveUtil Constants
     */
    public static final int FRONTLEFT_DRIVE = 1;
    public static final int FRONTLEFT_PIVOT = 2;
    public static final int FRONTRIGHT_DRIVE = 3;
    public static final int FRONTRIGHT_PIVOT = 4;
    public static final int BACKLEFT_DRIVE = 5;
    public static final int BACKLEFT_PIVOT = 6;
    public static final int BACKRIGHT_DRIVE = 7;
    public static final int BACKRIGHT_PIVOT = 8;

    public static final int TOPLEFT_ABS_ENCODER = 0;
    public static final int TOPRIGHT_ABS_ENCODER = 1;
    public static final int BOTTOMLEFT_ABS_ENCODER = 2;
    public static final int BOTTOMRIGHT_ABS_ENCODER = 3;

    public static final double TOPLEFT_ABS_ENCODER_OFFSET = 35.6;
    public static final double TOPRIGHT_ABS_ENCODER_OFFSET = 158.2;
    public static final double BOTTOMLEFT_ABS_ENCODER_OFFSET = 152.5;
    public static final double BOTTOMRIGHT_ABS_ENCODER_OFFSET = 105.7;

    public static final double[] ABS_ENCODER_OFFSETS = {
        TOPLEFT_ABS_ENCODER_OFFSET,
        TOPRIGHT_ABS_ENCODER_OFFSET,
        BOTTOMLEFT_ABS_ENCODER_OFFSET,
        BOTTOMRIGHT_ABS_ENCODER_OFFSET
    };

  public static final double MAX_ANGULAR_SPEED = 2500;
  public static final double MAX_LINEAR_SPEED = 29.5; //meters per second


  public static class OperatorConstants {
    public static final int kDriverControllerPort = 0;
  }

  public static final double TOPLEFT_X = 0.224;
  public static final double TOPLEFT_Y = 0.224;
  public static final double TOPRIGHT_X = 0.224;
  public static final double TOPRIGHT_Y = -0.224;
  public static final double BOTTOMLEFT_X = -0.224;
  public static final double BOTTOMLEFT_Y = 0.224;
  public static final double BOTTOMRIGHT_X = -0.224;
  public static final double BOTTOMRIGHT_Y = -0.224;

  public static final int XBOX_DRIVER = 1;

  public static final double XBOX_STICK_DEADZONE_WIDTH=0.05;
}
