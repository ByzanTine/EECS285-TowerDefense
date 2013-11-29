package umich.eecs285.test;

import umich.eecs285.towerdefence.*;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class TowerDefenseControllerTest {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		Controller test=new Controller();
		TowerDefense_TransData data;
		test.startTurn(1, 0, null);
		test.run();
		data=test.getInfo(1, 0);
		for(int i=0;i<data.getSize();i++)
			System.out.println(data.TowerDefense_TransArray[i]);
		test.run();
		data=test.getInfo(1, 0);
		for(int i=0;i<data.getSize();i++)
			System.out.println(data.TowerDefense_TransArray[i]);

	}
}
