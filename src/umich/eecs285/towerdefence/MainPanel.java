package umich.eecs285.towerdefence;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements TowerDefensedataArray {
  private PlayerPanel pp;
  private ClientBridge cb;
  private boolean InfoShown;
  private int InfoID;

  MainPanel(PlayerPanel pp) {
    this.setLayout(null);
    this.setBounds(0, 0, 1200, 600);
    InfoShown = false;
    
    this.pp = pp;
  }

  public void nextPanel(TowerDefense_TransData t) {
    removeAll();
    TowerDefenseDataBase d = new TowerDefenseDataBase();
    d.init();

    for (Integer i = 0; i < t.getSize(); ++i) {
      String temp = d.searchImage(7, t.TowerDefense_TransArray[i].getAction());
      PokemonButton b = new PokemonButton("res/alien_selected.gif", temp,
          t.TowerDefense_TransArray[i].getId());
      b.addActionListener(new PokemonButtonListener(t.TowerDefense_TransArray[i].getId(), t.TowerDefense_TransArray[i].getLife()));
      b.setBounds(t.TowerDefense_TransArray[i].getX() - 25,
          t.TowerDefense_TransArray[i].getY() - 25, 50, 50);
      add(b);
      if (InfoShown && t.TowerDefense_TransArray[i].getId() == InfoID) {
        pp.displayInfo(InfoID, t.TowerDefense_TransArray[i].getLife());
      }
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
    ImageIcon Icon = new ImageIcon("res/cat.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

}
