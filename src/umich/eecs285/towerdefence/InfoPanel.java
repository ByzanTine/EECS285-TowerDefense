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

    addCandyInfo(Player.initialCandy);
    addMoneyInfo(Player.initialMoney);

    d = new TowerDefenseDataBase();
    d.init();
  }

  public void displayInfo(int ID, Integer CurrentLife) {
    removeAll();
    addCandyInfo(cb.getCandy());
    addMoneyInfo(cb.getMoney());
    if (CurrentLife > 0) {
      Units obj = d.searchUnit(ID);
      JLabel info = new JLabel(new ImageIcon("res/Info/"
          + ((Integer) (ID % 100)).toString() + ".png"));
      info.setBounds(20, 105, MainFrame.ButtonSize, MainFrame.ButtonSize);
      add(info);

      JLabel LifeLabel = new JLabel(CurrentLife.toString() + " / " + obj.MaxHP);
      LifeLabel.setBounds(70, 110, 100, 20);
      add(LifeLabel);

      JLabel Attack = new JLabel(new ImageIcon("res/Attack.png"));
      Attack.setBounds(20, 150, MainFrame.ButtonSize, MainFrame.ButtonSize);
      add(Attack);

      JLabel AttackLabel = new JLabel(((Integer) obj.Attack).toString());
      AttackLabel.setBounds(70, 150, 150, 20);
      add(AttackLabel);
      if (ID % 100 == 11 || ID % 100 == 21) {
        TowerDefense_Button upgradeButton = new TowerDefense_Button(
            "res/upgradePokemon.png", "res/upgradePokemon.png");
        upgradeButton.setBounds(15, 200, MainFrame.ButtonSize,
            MainFrame.ButtonSize);
        upgradeButton.addActionListener(new UpgradeListener(ID));
        add(upgradeButton);
      }
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

  public void addCandyInfo(Integer candyNumber) {
    TowerDefense_Button candy = new TowerDefense_Button("res/candy.png",
        "res/candy.png");
    JLabel candyQuantity = new JLabel(candyNumber.toString());

    candy.setBounds(10, 10, MainFrame.ButtonSize, MainFrame.ButtonSize);
    candyQuantity.setBounds(70, 15, 80, 20);

    add(candy);
    add(candyQuantity);
  }

  public void addMoneyInfo(Integer money) {
    TowerDefense_Button MoneyButton = new TowerDefense_Button("res/money.png",
        "res/money.png");
    JLabel MoneyAmount = new JLabel(money.toString());

    MoneyButton.setBounds(10, 60, MainFrame.ButtonSize, MainFrame.ButtonSize);
    MoneyAmount.setBounds(70, 70, 80, 20);

    add(MoneyButton);
    add(MoneyAmount);
  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
  }

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/Panel/Siderbar.png");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

  public void refresh() {
    removeAll();
    addCandyInfo(cb.getCandy());
    addMoneyInfo(cb.getMoney());
    repaint();
  }

}
