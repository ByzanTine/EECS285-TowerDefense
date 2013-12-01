package umich.eecs285.towerdefence;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainFrame extends JFrame implements TowerDefensedataArray {

  final static int ButtonSize = 40;
  
  private MainPanel mP;
  private PlayerPanel pp;
  private JPanel RoundPanel;
  private ClientBridge cb;

  MainFrame() {

    super("Tower Defence");
    this.setSize(770, 750);
    this.setResizable(false);
    this.setLayout(null);

    pp = new PlayerPanel();
    add(pp);

    mP = new MainPanel(pp);
    add(mP);
    
    RoundPanel = new JPanel();
    RoundPanel.setOpaque(false);
    RoundPanel.setBounds(100, 250, 400, 200);

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
  
  public void turnOnRound(Integer RoundNumber) {
    JLabel text = new JLabel("Round " + RoundNumber.toString());
    RoundPanel.add(text);
    add(RoundPanel);
  }
  
  public void turnOnRound() {
    remove(RoundPanel);
  }
  
}
