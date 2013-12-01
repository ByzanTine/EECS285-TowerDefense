package umich.eecs285.towerdefence;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  private TowerDefenseDataBase d;
  protected ClientBridge cb;
  
  InfoPanel() {
    setBounds(0, 250, 150, 250);
    setLayout(null);
    d = new TowerDefenseDataBase();
    d.init();
  }

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/cat.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

  public void displayInfo(int ID, Integer CurrentLife) {
    removeAll();
    Units obj = d.searchUnit(ID);
    CreatePokemonButton c = new CreatePokemonButton("res/alien.gif", "res/alien.gif");
    c.setBounds(10, 10, MainFrame.ButtonSize, MainFrame.ButtonSize);
    add(c);
    JLabel LifeLabel = new JLabel(CurrentLife.toString()+ " / " + obj.MaxHP);
    LifeLabel.setBounds(60, 15, 150, 20);
    add(LifeLabel);
    JLabel AttackLabel = new JLabel(((Integer) obj.Attack).toString());
    AttackLabel.setBounds(60, 50, 150, 20);
    add(AttackLabel);
    if (ID % 100 >= 11 && ID % 100 <= 21) {
      TowerDefense_Button upgradeButton = new TowerDefense_Button("res/alien_selected.gif", "res/alien.gif");
      upgradeButton.setBounds(10, 100, MainFrame.ButtonSize, MainFrame.ButtonSize);
      upgradeButton.addActionListener(new UpgradeListener(ID));
      add(upgradeButton);
    }
    repaint();
  }
  private class UpgradeListener implements ActionListener {
    private int ID;
    
    
    public UpgradeListener(int ID) {
      this.ID = ID;
    }

    public void actionPerformed(ActionEvent e) {
      System.out.println("level up " + ID);
      cb.setUnitLevelupRequest(true);
      cb.setLevelupId(ID);
    }
    
  }
  
  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
  }
}
