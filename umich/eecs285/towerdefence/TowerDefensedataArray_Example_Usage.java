package umich.eecs285.towerdefence;

/*
 * Example of use
 */
public class  TowerDefensedataArray_Example_Usage implements TowerDefensedataArray{
   
  public static void main(String[] args) {
    TowerDefense_TransData t = new TowerDefense_TransData();
    for(int i=0;i<TowerDefenseObject_Array_Size;i++)
      TowerDefense_TransData.TowerDefense_TransArray[i]=new TowerDefenseObject();
    System.out.println(t.toString());
    
  }
}
