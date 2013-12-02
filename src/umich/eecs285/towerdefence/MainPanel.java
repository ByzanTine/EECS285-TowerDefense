package umich.eecs285.towerdefence;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements TowerDefensedataArray {
  private PlayerPanel pp;
  private ClientBridge cb;
  private boolean InfoShown;
  private int InfoID;

  MainPanel(PlayerPanel pp) {
    this.setLayout(null);
    this.setBounds(0, 0, Map.CELL_SIZE * Map.WIDE, Map.CELL_SIZE * Map.HEIGHT);
    InfoShown = false;

    this.pp = pp;
  }

  public void nextPanel(TowerDefense_TransData t) {
    removeAll();
    TowerDefenseDataBase d = new TowerDefenseDataBase();
    d.init();

    for (Integer i = 0; i < t.getSize(); ++i) {
      String temp = d.searchImage(t.TowerDefense_TransArray[i].getId(),
          t.TowerDefense_TransArray[i].getAction());
      PokemonButton b = new PokemonButton("res/alien_selected.gif", temp,
          t.TowerDefense_TransArray[i].getId());
      b.addActionListener(new PokemonButtonListener(
          t.TowerDefense_TransArray[i].getId(), t.TowerDefense_TransArray[i]
              .getLife()));
      b.setBounds(t.TowerDefense_TransArray[i].getX() - 14,
          t.TowerDefense_TransArray[i].getY() - 21, MainFrame.ButtonSize,
          MainFrame.ButtonSize);
      add(b);
      Units obj = d.searchUnit(t.TowerDefense_TransArray[i].getId());
      Integer LifeRatio = 10 * t.TowerDefense_TransArray[i].getLife() / obj.MaxHP;
      JLabel LifeBar = new JLabel(new ImageIcon("res/HP/blood-" + LifeRatio.toString() + ".png"));
      LifeBar.setBounds(t.TowerDefense_TransArray[i].getX() - 14, t.TowerDefense_TransArray[i].getY() - 28, 20, 3);
      add(LifeBar);
      if (InfoShown && t.TowerDefense_TransArray[i].getId() == InfoID) {
        if (t.TowerDefense_TransArray[i].getLife() <= 0)
          InfoShown = false;
        pp.displayInfo(InfoID, t.TowerDefense_TransArray[i].getLife());
      }
    }
    
    if (!InfoShown) {
      pp.refreshInfo();
    }
    
    for (Integer i = 0; i < t.getSize(); ++i) {
      Units obj = d.searchUnit(t.TowerDefense_TransArray[i].getId());
      Integer LifeRatio = (int) Math.ceil(10 * t.TowerDefense_TransArray[i].getLife() / obj.MaxHP);
      JLabel LifeBar = new JLabel(new ImageIcon("res/HP/blood-" + LifeRatio.toString() + ".png"));
      LifeBar.setBounds(t.TowerDefense_TransArray[i].getX() - 14, t.TowerDefense_TransArray[i].getY() - 28, 20, 3);
      add(LifeBar);
    }

    repaint();

  }

  private class PokemonButtonListener implements ActionListener {

    private int ID;
    private int CurrentLife;

    public PokemonButtonListener(int id, int CurrentLife) {
      this.ID = id;
      this.CurrentLife = CurrentLife;
    }

    public void actionPerformed(ActionEvent e) {
      System.out.println("fighting " + ID);
      pp.displayInfo(ID, CurrentLife);
      InfoShown = true;
      InfoID = ID;
    }

  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
  }

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/Background/back.png");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

}
