package umich.eecs285.towerdefence;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;

public class LogPanel extends JPanel {
  private JButton create;
  private JButton join;
  private JButton info;
  private JButton exit;
  private JLabel titleLabel;
  private ClientBridge cb;

  LogPanel() {
    titleLabel = new JLabel(new ImageIcon("res/Title.png"));
    create = new LogButton("Create a new game", Color.orange, Color.cyan);
    join = new LogButton("Join a new game", Color.orange, Color.cyan);
    exit = new LogButton("Exit", Color.orange, Color.cyan);
    info = new LogButton("About Us", Color.orange, Color.cyan);
    // create.putClientProperty("JButton.buttonType", "roundRect" );
    // join.putClientProperty("JButton.buttonType", "roundRect" );
    // exit.putClientProperty("JButton.buttonType", "help" );
    // repaint();
    setBounds(0, 0, 755, 770);
    setLayout(null);
    titleLabel.setBounds(50, 0, 600, 265);
    
    create.setBounds(275, 260, 200, 30);
    create.addActionListener(new createGameListener());
    
    join.setBounds(275, 330, 200, 30);
    join.addActionListener(new joinGameListener());
    
    exit.setBounds(275, 400, 200, 30);
    exit.addActionListener(new exitGameListener());
    
    info.setBounds(275, 470, 200, 30);
    
    add(titleLabel);
    add(create);
    add(join);
    add(exit);
    add(info);
    repaint();
  }
  
  private class createGameListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      cb.setCreateGameRequest(true);
    }
    
  }
  
  private class joinGameListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      cb.setJoinGameRequest(true);
    }
    
  }
  
  private class exitGameListener implements ActionListener {

    public void actionPerformed(ActionEvent e) {
      System.exit(0);
    }
    
  }

  public void setClientBridge(ClientBridge cb) {
    this.cb = cb;
  }
  
  public void paintComponent(Graphics g) {
    ImageIcon Icon = new ImageIcon("res/background.png");
    g.drawImage(Icon.getImage(), 0, 0, getSize().width, getSize().height, this);
  }
}
