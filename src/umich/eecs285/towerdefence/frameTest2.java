package umich.eecs285.towerdefence;

import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JFrame;

public class frameTest2 implements TowerDefensedataArray {

    final int delay = 200;
    
    public static void main(String arg[]) {
        frameTest ft = new frameTest();
        MainFrame mf = new MainFrame();
        TowerDefense_TransData t = new TowerDefense_TransData(0, 60, (byte) 0);
        for (int i = 0; i < t.getSize(); ++i)
            t.TowerDefense_TransArray[i] = new TowerDefenseObject(0, 0,
                    (i % 20) * 50, i / 20 * 50, 0);

        mf.setVisible(true);
        mf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        ft.start(t, mf);

    }

    public void start(TowerDefense_TransData t, MainFrame mf) {

         Timer timer = new Timer();
         timer.schedule(new MyTimerTask(t, mf), 0, delay);


    }

    public class MyTimerTask extends TimerTask {
        private TowerDefense_TransData t;
        private MainFrame mf;

        public MyTimerTask(TowerDefense_TransData t_t, MainFrame mf2) {
            // TODO Auto-generated constructor stub
            t = t_t;
            mf = mf2;
        }

        public void run() {
            for (int i = 0; i < t.getSize(); ++i) {
                t.TowerDefense_TransArray[i].setY(t.TowerDefense_TransArray[i]
                        .getY() );
                if (t.TowerDefense_TransArray[i].getY() > 600)
                    t.TowerDefense_TransArray[i].setY(0);
            }

            mf.nextFrame(t);
        }
    }

  

}
