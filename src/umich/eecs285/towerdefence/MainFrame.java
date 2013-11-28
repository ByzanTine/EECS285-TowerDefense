package umich.eecs285.towerdefence;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JFrame;
import javax.swing.Timer;

public class MainFrame extends JFrame implements TowerDefensedataArray {

    private MainPanel mP;
    private int frameID;

    public Timer timer;

    MainFrame() {

        super("Tower Defence");
        this.setSize(1200, 900);
        this.setResizable(false);
        this.setLayout(null);
        this.frameID = 0;

        mP = new MainPanel();
        add(mP);
    }

    public void nextFrame(TowerDefense_TransData t, int delay) {
        timer = new Timer(delay / 3, new TimerListener(t));
        timer.start();

    }

    private class TimerListener implements ActionListener {
        private TowerDefense_TransData t;

        public TimerListener(TowerDefense_TransData t2) {
            // TODO Auto-generated constructor stub
            t = t2;

        }

        public void actionPerformed(ActionEvent e) {
            if (frameID < 3) {
                mP.nextPanel(t, frameID);
                repaint();
                ++frameID;
            } else {
                frameID = 0;
                timer.stop();
            }
        }
    }

}