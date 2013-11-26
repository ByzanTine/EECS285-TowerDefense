package umich.eecs285.towerdefence;

import javax.swing.JPanel;

public class MainPanel extends JPanel implements TowerDefensedataArray {

    MainPanel() {
        this.setLayout(null);
        this.setBounds(0, 0, 1200, 900);
    }

    public void nextPanel(TowerDefense_TransData t, int frameID) {
        // TODO Auto-generated method stub
        removeAll();
        if (frameID == 0) {
            for (Integer i = 0; i < t.getSize(); ++i) {
                TowerDefense_Button b = new TowerDefense_Button(
                        "res/alien_selected.gif", "res/alien_selected.gif",
                        "res/alien.gif");
                b.setBounds(t.TowerDefense_TransArray[i].getX(),
                        t.TowerDefense_TransArray[i].getY(), 50, 50);
                add(b);
            }
        }
        if (frameID == 1) {
            for (Integer i = 0; i < t.getSize(); ++i) {
                TowerDefense_Button b = new TowerDefense_Button(
                        "res/alien_selected.gif", "res/alien_selected.gif",
                        "res/alien2.gif");
                b.setBounds(t.TowerDefense_TransArray[i].getX(),
                        t.TowerDefense_TransArray[i].getY(), 50, 50);
                add(b);
            }
        }
        if (frameID == 2) {
            for (Integer i = 0; i < t.getSize(); ++i) {
                TowerDefense_Button b = new TowerDefense_Button(
                        "res/alien_selected.gif", "res/alien_selected.gif",
                        "res/alien3.gif");
                b.setBounds(t.TowerDefense_TransArray[i].getX(),
                        t.TowerDefense_TransArray[i].getY(), 50, 50);
                add(b);
            }
        }
    }

}
