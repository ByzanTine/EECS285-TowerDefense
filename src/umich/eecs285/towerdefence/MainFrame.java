package umich.eecs285.towerdefence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MainFrame extends JFrame implements TowerDefensedataArray {

  private MainPanel mP;
  private PlayerPanel pp;
  private int frameID;

  MainFrame() {

    super("Tower Defence");
    this.setSize(1200, 900);
    this.setResizable(false);
    this.setLayout(null);
    this.frameID = 0;

    pp = new PlayerPanel();
    add(pp);

    mP = new MainPanel(pp);
    add(mP);

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (pp.CreateIndex() != -1) {
          System.out.println(pp.CreateIndex());
          pp.CreateButtonRecover();
        }
      }
    });
  }

  public void nextFrame(TowerDefense_TransData t, int delay) {
    mP.nextPanel(t, frameID);

  }

}
