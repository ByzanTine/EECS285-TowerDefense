package umich.eecs285.towerdefence;

import java.applet.AudioClip;

public class BackgroundMusic {
  
  private AudioClip welcome;
  private AudioClip battle;
  
  public BackgroundMusic() {
    welcome = java.applet.Applet.newAudioClip(this.getClass().getResource("res/welcome.mp3"));
    battle = java.applet.Applet.newAudioClip(this.getClass().getResource("res/battle.mp3"));
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
