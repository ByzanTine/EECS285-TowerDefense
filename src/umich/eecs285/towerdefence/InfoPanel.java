package umich.eecs285.towerdefence;

import java.awt.Graphics;

import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class InfoPanel extends JPanel {

  private TowerDefenseDataBase d;
  
  InfoPanel() {
    setBounds(0, 400, 150, 200);
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
    LifeLabel.setBounds(10, 60, 150, 20);
    add(LifeLabel);
    JLabel AttackLabel = new JLabel(((Integer) obj.Attack).toString());
    AttackLabel.setBounds(10, 80, 150, 20);
    add(AttackLabel);
    if (ID % 100 >= 11 && ID % 100 <= 21) {
      TowerDefense_Button upgradeButton = new TowerDefense_Button("res/alien_selected.gif", "res/alien.gif");
      upgradeButton.setBounds(10, 100, MainFrame.ButtonSize, MainFrame.ButtonSize);
      add(upgradeButton);
    }
    repaint();
  }
}
