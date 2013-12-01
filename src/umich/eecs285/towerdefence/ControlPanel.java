package umich.eecs285.towerdefence;

import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

public class ControlPanel extends JPanel {
  final int CreateButtonSize = 6;
  final int SendButtonSize = 6;
  private CreatePokemonButton[] CreateButtons;
  private UpgradeEarningButton addMeoMeo;
  private UpgradeEarningButton upgradeMeoMeo;
  private SendPokemonButton[] SendButtons;
  private ClientBridge cb;

  ControlPanel() {
    setBounds(0, 0, 150, 250);
    setLayout(null);

    CreateButtons = new CreatePokemonButton[CreateButtonSize];
    for (int i = 0; i < CreateButtonSize; ++i) {
      CreateButtons[i] = new CreatePokemonButton("res/alien_selected.gif",
          "res/dragonate.png");
      CreateButtons[i].setBounds(5, 5 + (MainFrame.ButtonSize + 2) * i,
          MainFrame.ButtonSize, MainFrame.ButtonSize);
      CreateButtons[i].addActionListener(new CreationListener(i));
      add(CreateButtons[i]);
    }

    addMeoMeo = new UpgradeEarningButton("res/alien.gif", "res/alien.gif");
    upgradeMeoMeo = new UpgradeEarningButton("res/alien.gif", "res/alien.gif");

    addMeoMeo.setBounds(52, 10, MainFrame.ButtonSize, MainFrame.ButtonSize);
    upgradeMeoMeo.setBounds(52, 50, MainFrame.ButtonSize, MainFrame.ButtonSize);

    addMeoMeo.addActionListener(new addMeoMeoListener());
    upgradeMeoMeo.addActionListener(new upgradeMeoMeoListener());

    add(addMeoMeo);
    add(upgradeMeoMeo);

    TowerDefense_Button SwitchButton = new TowerDefense_Button(
        "res/meomeo.png", "res/meomeo.png");
    SwitchButton.setBounds(52, 207, MainFrame.ButtonSize, MainFrame.ButtonSize);
    SwitchButton.addActionListener(new ActionListener() {

      public void actionPerformed(ActionEvent e) {
        cb.setChangeViewRequest(true);
      }

    });
    add(SwitchButton);

    SendButtons = new SendPokemonButton[SendButtonSize];
    for (int i = 0; i < CreateButtonSize; ++i) {
      SendButtons[i] = new SendPokemonButton("res/alien_selected.gif",
          "res/alien.gif");
      SendButtons[i].setBounds(100, 10 + MainFrame.ButtonSize * i,
          MainFrame.ButtonSize, MainFrame.ButtonSize);
      SendButtons[i].addActionListener(new SendListener(i));
      add(SendButtons[i]);
    }
  }

  public int CreationIndex() {
    for (int i = 0; i < CreateButtonSize; ++i) {
      if (CreateButtons[i].IsClicked())
        return i;
    }
    return -1;
  }

  private class CreationListener implements ActionListener {
    private int index;

    public CreationListener(int i) {
      index = i;
    }

    public void actionPerformed(ActionEvent e) {
      CreateButtons[index].setIsClicked(true);
      System.out.println("create " + index);
    }

  }

  private class addMeoMeoListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      cb.setMeoMeoNumIncreaseRequest(true);
    }

  }

  private class upgradeMeoMeoListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      cb.setMeoMeoTechUpgradeRequest(true);
    }

  }

  private class SendListener implements ActionListener {
    private int index;

    public SendListener(int i) {
      index = i;
    }

    public void actionPerformed(ActionEvent e) {
      cb.setCreateAttackUnitRequest(true);
      cb.setAttackUnitId(index);
      System.out.println("send " + index);
    }

  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
  }

  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/Panel/Pattern.jpg");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }

  public void recoverCreateButton(int creationIndex) {
    CreateButtons[creationIndex].setIsClicked(false);

  }

}
