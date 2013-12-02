package umich.eecs285.towerdefence;

import java.applet.AudioClip;
import java.io.File;
import java.net.MalformedURLException;

public class BackgroundMusic {
  
  private AudioClip welcome;
  private AudioClip battle;
  
  public BackgroundMusic() {
    try {
      welcome = java.applet.Applet.newAudioClip(new File("res/welcome.wav").toURI().toURL());
      battle = java.applet.Applet.newAudioClip(new File("res/battle.wav").toURI().toURL());
    } catch (MalformedURLException e) {
      e.printStackTrace();
    }
  }
  
  public void startWelcome() {
    welcome.loop();
  }

  public void stopWelcome() {
    welcome.stop();
  }

  public void startBattle() {
    battle.loop();
  }
  
  public void stopBattle() {
    battle.stop();
  }
  
}
