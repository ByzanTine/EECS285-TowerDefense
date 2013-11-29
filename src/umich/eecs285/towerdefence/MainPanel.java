package umich.eecs285.towerdefence;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class MainPanel extends JPanel implements TowerDefensedataArray {
  PlayerPanel pp;

  MainPanel(PlayerPanel pp) {
    this.setLayout(null);
    this.setBounds(0, 0, 1200, 600);
    
    this.pp = pp;
  }

  public void nextPanel(TowerDefense_TransData t, int frameID) {
    // TODO Auto-generated method stub
    removeAll();
    TowerDefenseDataBase d = new TowerDefenseDataBase();
    d.init();

    for (Integer i = 0; i < t.getSize(); ++i) {
      String temp = d.searchImage(7, t.TowerDefense_TransArray[i].getAction());
      PokemonButton b = new PokemonButton("res/alien_selected.gif", temp,
          t.TowerDefense_TransArray[i].getId());
      b.addActionListener(new PokemonButtonListener());
      b.setBounds(t.TowerDefense_TransArray[i].getX(),
          t.TowerDefense_TransArray[i].getY(), 50, 50);
      add(b);
    }

    repaint();

  }

  private class PokemonButtonListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      System.out.println("fighting");
      pp.displayInfo();
    }

  }

 
  
  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/cat.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

}
