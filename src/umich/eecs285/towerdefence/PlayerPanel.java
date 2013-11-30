package umich.eecs285.towerdefence;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
  private ControlPanel cp;
  private InfoPanel ip;
  private EarningPanel ep;
  private ClientBridge cb;
  
    PlayerPanel(){
        setBounds(0, 600, 1000, 200);
        setLayout(null);
        
        cp = new ControlPanel();
        add(cp);
        ip = new InfoPanel();
        add(ip);
        ep = new EarningPanel();
        add(ep);
        
        
    }

    public void displayInfo() {
      ip.displayInfo();
    }
    
    public int CreateIndex() {
      return cp.CreationIndex();
    }
    
    public void CreateButtonRecover() {
      cp.recoverCreateButton(cp.CreationIndex());
    }
    
    public void setClientBridge(ClientBridge cb) {
      this.cb = cb;
      cp.setClientBridge(cb);
    }
    
    public void paintComponent(Graphics g) {
        ImageIcon Icon = new ImageIcon("res/o.jpg");
        g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
    }
}
