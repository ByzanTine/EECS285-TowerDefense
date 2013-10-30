import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class WheelOfFortune extends JDialog {
  JTextField nameField;
  JButton okButton;
  
  public WheelOfFortune(JFrame mainFrame, String title) {
    super(mainFrame, title, true);
    JPanel middlePan = new JPanel(new FlowLayout());
    middlePan.add(new JTextField(45));
    
    setLayout(new BorderLayout());
    JPanel topPan=new JPanel(new FlowLayout(FlowLayout.LEFT));
    topPan.add(new JLabel("Enter the random generator seed (int) "));
    add(topPan,BorderLayout.NORTH);
    add(middlePan,BorderLayout.CENTER);
    okButton = new JButton("OK");
    okButton.addActionListener(new ActionListener() {
      public void actionPerformed(ActionEvent e) {
        setVisible(false);
      }
    });
    JPanel ButtonPan = new JPanel(new FlowLayout());
    ButtonPan.add(okButton);
    
    add(ButtonPan,BorderLayout.SOUTH);
    pack();
    setVisible(true);
  }

  public static void main(String argv[]){
    JFrame mainFrame = null;
    JDialog a=new WheelOfFortune(mainFrame, "abc");
  }
}