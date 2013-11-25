/*
 * Basic DataStruct
 */
public interface TowerDefensedataArray {
  /*
   * For the primitive usage Instance included: Array of TowerDefenseObject
   */
  public static final int TowerDefenseObject_Array_Size = 100;
  
  public static class TowerDefense_TransData{
    public static TowerDefenseObject[] TowerDefense_TransArray;
    public long timestamp;
    public TowerDefense_TransData(){
      TowerDefense_TransArray = new TowerDefenseObject[TowerDefenseObject_Array_Size];
    }
    public String toString(){
      String result = "timestamp: ";
      result+=timestamp;
      
      for(int i=0;i<TowerDefenseObject_Array_Size;i++){
        result+="\n";
        result+=TowerDefense_TransArray[i].toString();
        
      }
      return result;
    }
  }
  public static class TowerDefenseObject {
    /*
     * Instance included: object id, life, location(x,y) and action type
     */
    private int id;
    private int life;
    private int x;
    private int y;
    private int action;
    
    /*
     * Constructor for simple test
     */
    public TowerDefenseObject() {
      id = 0;
      life = 0;
      x = 0;
      y = 0;
      action = 0;
    }
    
    public TowerDefenseObject(int id, int life, int x, int y, int action) {
      this.id = id;
      this.life = life;
      this.x = x;
      this.y = y;
      this.action = action;
      
    }
    
    public String toString() {
      return "id: " + id + " life: " + life + " location: " + x + " " + y
          + " Action: " + action;
      
    }
  }
  
}
