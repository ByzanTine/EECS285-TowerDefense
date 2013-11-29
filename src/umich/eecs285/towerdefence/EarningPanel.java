package umich.eecs285.towerdefence;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class EarningPanel extends JPanel {
  private int numberOfMeoMeo;
  
  EarningPanel() {
    setBounds(0, 0, 300, 200);
    setLayout(null);
    
    numberOfMeoMeo = 1;
    
    TowerDefense_Button defaultMeoMeo = new TowerDefense_Button("res/alien.gif", "res/alien.gif");
    defaultMeoMeo.setBounds(10, 10, 50, 50);
    add(defaultMeoMeo);
    
  }
  
  public void addMeoMeo() {
    TowerDefense_Button newMeoMeo = new TowerDefense_Button("res/alien.gif", "res/alien.gif");
    newMeoMeo.setBounds(10 + 50 * numberOfMeoMeo, 10, 50, 50);
    add(newMeoMeo);
    repaint();
    
    ++numberOfMeoMeo;
  }
  

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/cat.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

}
