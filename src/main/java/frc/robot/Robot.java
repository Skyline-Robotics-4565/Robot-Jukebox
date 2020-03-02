/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import java.util.ArrayList;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonFX;
import com.ctre.phoenix.music.Orchestra;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  private static final String kDefaultAuto = "Default";
  private static final String kCustomAuto = "My Auto";
  private String m_autoSelected;
  private final SendableChooser<String> m_chooser = new SendableChooser<>();

  Orchestra theBoys;

  WPI_TalonFX boy1= new WPI_TalonFX(2);
  WPI_TalonFX boy2= new WPI_TalonFX(3);
  WPI_TalonFX boy3= new WPI_TalonFX(4);
  WPI_TalonFX boy4= new WPI_TalonFX(5);

  WPI_TalonFX[] list = {boy1, boy2, boy3, boy4};

  int pos = 0;

  String[] playlist = { 
  "american_idiot.chrp", 
  "baby.chrp", 
  "back_in_black.chrp", 
  "beat_it.chrp", 
  "billie_jean.chrp", 
  "bohemian_rhapsody.chrp", 
  "boulevard_of_broken_dreams.chrp", 
  "clint_eastwood.chrp", 
  "country_roads.chrp", 
  "csi.chrp", 
  "dancing_queen.chrp", 
  "dare.chrp", 
  "e1m1.chrp", 
  "everytime_we_touch.chrp", 
  "family_guy.chrp", 
  "feel_good_inc.chrp", 
  "final_countdown.chrp", 
  "fireflies.chrp", 
  "fresh_prince.chrp", 
  "friends.chrp", 
  "gangnam_style.chrp", 
  "happy_birthday.chrp", 
  "he_is_a_pirate.chrp", 
  "hot_n_cold.chrp", 
  "in_the_end.chrp", 
  "indiana_jones.chrp", 
  "james_bond.chrp", 
  "just_dance.chrp", 
  "lose_yourself.chrp", 
  "mario.chrp", 
  "matrix.chrp", 
  "megalovania.chrp", 
  "mission_impossible.chrp", 
  "numb.chrp", 
  "piano_man.chrp", 
  "pink_panther.chrp", 
  "poker_face.chrp", 
  "ring_of_fire.chrp", 
  "sweet_home_alabama.chrp", 
  "theme_a.chrp", 
  "us.chrp", 
  "viva_la_vida.chrp", 
  "we_will_rock_you.chrp", 
  "ymca.chrp", 
  "you_belong_with_me.chrp"};

  ArrayList <String> print = new ArrayList();
  

  XboxController controller;
  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_chooser.setDefaultOption("Default Auto", kDefaultAuto);
    m_chooser.addOption("My Auto", kCustomAuto);
    SmartDashboard.putData("Auto choices", m_chooser);

    theBoys = new Orchestra();
    controller = new XboxController(0);

    for (int e = 0; e < list.length; e++){
      theBoys.addInstrument(list[e]);
    }

    for (int e = 0; e < playlist.length; e++){
      print.add(playlist[e]);
    }

    theBoys.loadMusic(playlist[pos]);
    theBoys.play();
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString line to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional comparisons to
   * the switch structure below with additional strings. If using the
   * SendableChooser make sure to add them to the chooser code above as well.
   */
  @Override
  public void autonomousInit() {
    m_autoSelected = m_chooser.getSelected();
    // m_autoSelected = SmartDashboard.getString("Auto Selector", kDefaultAuto);
    System.out.println("Auto selected: " + m_autoSelected);
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    switch (m_autoSelected) {
      case kCustomAuto:
        // Put custom auto code here
        break;
      case kDefaultAuto:
      default:
        // Put default auto code here
        break;
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    if (controller.getAButtonPressed()){
      theBoys.play();
      System.out.println("Now Playing " + print.get(pos) + "\nSong number " + pos);
    }
    if (controller.getXButtonPressed()){
      theBoys.pause();
      System.out.println("Paused");
    }
    if (controller.getBButtonPressed()){
      theBoys.stop();
      System.out.println("Stopped");
    }
    if (controller.getBumperPressed(Hand.kRight)){
      pos++;
      if (pos == playlist.length){
        pos = 0;
        System.out.println("Looped to bottom");
      }
      theBoys.loadMusic(playlist[pos]);
      System.out.println("Next Song " + print.get(pos) + "\nSong number " + pos);
    }
    if (controller.getBumperPressed(Hand.kLeft)){
      pos--;
      if (pos == -1){
        pos = playlist.length-1;
        System.out.println("Looped to top");
      }
      theBoys.loadMusic(playlist[pos]);
      System.out.println("Previous Song " + print.get(pos) + "\nSong number " + pos);
    }
    if (controller.getYButtonPressed()){
      pos = (int)(Math.random() * ((playlist.length)));
      theBoys.loadMusic(playlist[pos]);
      System.out.println("Random Song " + print.get(pos) + "\nSong number " + pos);

    }
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
  }
}
