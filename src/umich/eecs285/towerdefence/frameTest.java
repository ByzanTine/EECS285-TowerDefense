package umich.eecs285.towerdefence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class frameTest implements TowerDefensedataArray {

  final int delay = 1000;

  public static void main(String arg[]) {
    frameTest ft = new frameTest();
    MainFrame mf = new MainFrame();
    TowerDefense_TransData t = new TowerDefense_TransData(0, 60, (byte) 0);
    for (int i = 0; i < t.getSize(); ++i)
      t.TowerDefense_TransArray[i] = new TowerDefenseObject(7, 0,
          (i % 20) * 50, i / 20 * 50, 0);

    mf.setClientBridge(new ClientBridge());
    BackgroundMusic b = new BackgroundMusic();
    
    b.startBattle();

//    mf.start();

//    mf.turnOnRound("test");
    mf.turnOnInput();
    
    
//    ft.start(t, mf);

  }

  public void start(TowerDefense_TransData t, MainFrame mf) {

    Timer timer = new Timer(delay, new MyListener(t, mf));

    timer.start();
  }

  private class MyListener implements ActionListener {
    private TowerDefense_TransData t;
    private MainFrame mf;

    public MyListener(TowerDefense_TransData t_t, MainFrame mf2) {
      // TODO Auto-generated constructor stub
      t = t_t;
      mf = mf2;

    }

    public void actionPerformed(ActionEvent e) {
      for (int i = 0; i < t.getSize(); ++i) {
        t.TowerDefense_TransArray[i]
            .setX(t.TowerDefense_TransArray[i].getX() + 1);
        t.TowerDefense_TransArray[i]
            .setY(t.TowerDefense_TransArray[i].getY() + 1);
        t.TowerDefense_TransArray[i].setAction((t.TowerDefense_TransArray[i]
            .getAction() + 1) % 64);
        if (t.TowerDefense_TransArray[i].getY() > 600)
          t.TowerDefense_TransArray[i].setY(0);
      }

      mf.nextFrame(t);
      
      mf.turnOffRound();

    }
  }
}
