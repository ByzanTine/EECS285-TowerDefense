package umich.eecs285.towerdefence;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  InfoPanel() {
    setBounds(300, 0, 300, 200);
    setLayout(null);
  }

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/cat.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

  public void displayInfo(int ID, Integer CurrentLife) {
    removeAll();
    CreatePokemonButton c = new CreatePokemonButton("res/alien.gif", "res/alien.gif");
    c.setBounds(10, 10, 50, 50);
    add(c);
    JLabel LifeLabel = new JLabel(CurrentLife.toString());
    LifeLabel.setBounds(80, 10, 50, 20);
    add(LifeLabel);
    repaint();
  }
}
