package umich.eecs285.towerdefence;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  private TowerDefenseDataBase d;
  
  InfoPanel() {
    setBounds(300, 0, 300, 200);
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
    c.setBounds(10, 10, 50, 50);
    add(c);
    JLabel LifeLabel = new JLabel(CurrentLife.toString()+ " / " + obj.MaxHP);
    LifeLabel.setBounds(80, 10, 150, 20);
    add(LifeLabel);
    repaint();
  }
}
