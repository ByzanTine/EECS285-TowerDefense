package umich.eecs285.towerdefence;

import java.awt.BorderLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class MainFrame extends JFrame implements TowerDefensedataArray {

  final static int ButtonSize = 40;
  
  private JPanel all;
  private LogPanel lp;
  private MainPanel mP;
  private PlayerPanel pp;
  private JLabel text;
  private ClientBridge cb;

  MainFrame() {

    super("Pokemon Tower Defence");
    this.setSize(755, 770);
    this.setResizable(false);
    this.setLayout(null);
    
    all = new JPanel();
    all.setBounds(0, 0, 755, 770);
    all.setLayout(null);

    lp = new LogPanel();
    all.add(lp);
    add(all);
    
    setVisible(true);
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  }
  
  public void start() {
//    removeAll();
    all.removeAll();
    pp = new PlayerPanel();
    all.add(pp);
    pp.setClientBridge(cb);

    mP = new MainPanel(pp);
    all.add(mP);
    mP.setClientBridge(cb);

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

    all.repaint();
  }

  public void nextFrame(TowerDefense_TransData t) {
    checkEarning();
    mP.nextPanel(t);

  }

  private void checkEarning() {
    if (cb.isCanCreateMeoMeo()) {
      pp.addMeoMeo();
      cb.setCanCreateMeoMeo(false);
    }
    if (cb.isCanUpgradeMeoMeo()) {
      pp.upgradeMeoMeo();
      cb.setCanUpgradeMeoMeo(false);
    }
  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
    lp.setClientBridge(cb);
  }
  
  public void turnOnRound(String Info) {
    text = new JLabel(Info);
    text.setBounds(100, 250, 400, 200);
    mP.add(text);
    mP.repaint();
  }
  
  public void turnOffRound() {
    mP.remove(text);
    mP.repaint();
  }
  
  public void turnOnInput() {
        lp.turnOnInput();
  }
  
//  public void turnOffInput() {
//    mP.remove(InputPanel);
//    mP.repaint();
//  }
  
}
