package umich.eecs285.towerdefence;

import javax.swing.JPanel;

public class PlayerPanel extends JPanel{
  private ControlPanel cp;
  private InfoPanel ip;
  private EarningPanel ep;
  private ClientBridge cb;
  
    PlayerPanel(){
        setBounds(Map.CELL_SIZE * Map.WIDE, 0, 150, Map.CELL_SIZE * Map.HEIGHT);
        setLayout(null);
        
        cp = new ControlPanel();
        add(cp);
        ip = new InfoPanel();
        add(ip);
        ep = new EarningPanel();
        add(ep);
        
        
    }

    public void displayInfo(int ID, int CurrentLife) {
      ip.displayInfo(ID, CurrentLife);
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
      ip.setClientBridge(cb);
    }

    public void addMeoMeo() {
      ep.addMeoMeo();
    }

    public void upgradeMeoMeo() {
      ep.LevelUp();
    }

    public void refreshInfo() {
      ip.refresh();
    }
}
