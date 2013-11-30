package umich.eecs285.towerdefence;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;

public class MainFrame extends JFrame implements TowerDefensedataArray {

  private MainPanel mP;
  private PlayerPanel pp;
  private ClientBridge cb;

  MainFrame() {

    super("Tower Defence");
    this.setSize(1200, 900);
    this.setResizable(false);
    this.setLayout(null);

    pp = new PlayerPanel();
    add(pp);

    mP = new MainPanel(pp);
    add(mP);

    addMouseListener(new MouseAdapter() {
      public void mouseClicked(MouseEvent e) {
        if (pp.CreateIndex() != -1) {
          cb.setCreateUnitRequest(true);
          cb.setX(e.getPoint().x);
          cb.setY(e.getPoint().y);
          cb.setId(pp.CreateIndex() + 11);
          System.out.print(e.getPoint().x + " " + e.getPoint().y + " ");
          System.out.println(pp.CreateIndex());
          pp.CreateButtonRecover();
        }
      }
    });
    
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }

  public void nextFrame(TowerDefense_TransData t) {
    mP.nextPanel(t);

  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
    pp.setClientBridge(cb);
    mP.setClientBridge(cb);
  }
  
  
}
