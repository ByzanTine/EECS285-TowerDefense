/*
 * Basic DataStruct
 */

package umich.eecs285.towerdefence;

public interface TowerDefensedataArray {
  /*
   * For the primitive usage Instance included: Array of TowerDefenseObject
   */
  public static final int TowerDefenseObject_Array_Size = 100;
  
  public class TowerDefense_TransData{
    public TowerDefenseObject[] TowerDefense_TransArray;
    private long timestamp;
    private int clientId;
    
    public TowerDefense_TransData(int clientId, long timestamp) {
      TowerDefense_TransArray = new TowerDefenseObject[TowerDefenseObject_Array_Size];
      this.clientId = clientId;
      this.timestamp = timestamp;
    }
    
    public TowerDefense_TransData(int clientId) {
      TowerDefense_TransArray = new TowerDefenseObject[TowerDefenseObject_Array_Size];
      this.clientId = clientId;
      this.timestamp = System.currentTimeMillis() / 1000L;
    }
    
    public static TowerDefense_TransData createEmptyTransData(int clientId) {
      TowerDefense_TransData towerDefense_TransData = new TowerDefense_TransData(clientId);
      return towerDefense_TransData;
    }
    
    public String toString(){
      String result = "timestamp: ";
      result += timestamp;
      
      for(int i = 0; i < TowerDefenseObject_Array_Size; i++) {
        result += "\n";
        result += TowerDefense_TransArray[i].toString();
        
      }
      return result;
    }
    
    public long getTimeStamp() {
      return this.timestamp;
    }
    
    public int getClientId() {
      return clientId;
    }
  }
    
  public class TowerDefenseObject {
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
    
    public void setId(int id) {
      this.id = id;
    }
    
    public int getId() {
      return id;
    }
    
    public void setLife(int life) {
      this.life = life;
    }
    
    public int getLife() {
      return life;
    }
    
    public void setX(int x) {
      this.x = x;
    }
    
    public int getX() {
      return x;
    }
    
    public void setY(int y) {
      this.y = y;
    }
    
    public int getY() {
      return y;
    }
    
    public void setAction(int action) {
      this.action = action;
    }
    
    public int getAction() {
      return action;
    }
    
    public String toString() {
      return "id: " + id + " life: " + life + " location: " + x + " " + y
          + " Action: " + action;
    }
  }
  
}
