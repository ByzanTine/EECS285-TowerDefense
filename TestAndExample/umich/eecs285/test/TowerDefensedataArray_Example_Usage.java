package umich.eecs285.test;

import umich.eecs285.towerdefence.TowerDefensedataArray;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

/*
 * Example of use
 */
public class  TowerDefensedataArray_Example_Usage implements TowerDefensedataArray {
  public static final int TowerDefenseObject_Array_Size = 30;
  
  public static void main(String[] args) {
    TowerDefense_TransData t = new TowerDefense_TransData(0, 100, Transmit_Type_Regular); // initialize with clientId
    for(int i = 0; i < t.getSize(); i++)
      t.TowerDefense_TransArray[i] = new TowerDefenseObject();
    System.out.println(t.toString());
  }
}
