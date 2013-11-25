package umich.eecs285.towerdefence;
/**
 * @author Jiaxi Luo
 * This is a utility class providing method to transfer TowerDefense_TransData to JSON or vice versa.
 */
import java.io.IOException;
import java.sql.Timestamp;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public final class JSONUtility {
  
  @SuppressWarnings("unchecked")
  public String arrayToJSON(int clientId, TowerDefense_TransData towerDefense_TransData) throws IOException {
    JSONObject obj = new JSONObject();
    JSONArray arrayObj = new JSONArray();
    obj.put("clientId", clientId);
    obj.put("timestamp", new Timestamp(System.currentTimeMillis()));
    for(int i = 0; i < TowerDefensedataArray.TowerDefenseObject_Array_Size; i++) {
      JSONObject arrayData = new JSONObject();
      arrayData.put("id", TowerDefense_TransData.TowerDefense_TransArray[i].getId());
      arrayData.put("life", TowerDefense_TransData.TowerDefense_TransArray[i].getLife());
      arrayData.put("x", TowerDefense_TransData.TowerDefense_TransArray[i].getX());
      arrayData.put("y", TowerDefense_TransData.TowerDefense_TransArray[i].getY());
      arrayData.put("action", TowerDefense_TransData.TowerDefense_TransArray[i].getAction());
      arrayObj.add(arrayData);
    }
    obj.put("arrayObj", arrayObj);
    return obj.toString();
  }
  
  public TowerDefense_TransData JOSNToArray(String jsonString) {
    JSONParser parser = new JSONParser();
    TowerDefense_TransData towerDefense_TransData = new TowerDefense_TransData();
    try {
      JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
      for (int i = 0; i < TowerDefensedataArray.TowerDefenseObject_Array_Size; i++) {
        TowerDefense_TransData.TowerDefense_TransArray[i] = new TowerDefenseObject();
//        int id = (Integer) jsonObject.get("id");
//        int life = (Integer) jsonObject.get("life");
//        int x = (Integer) jsonObject.get("x");
//        int y = (Integer) jsonObject.get("y");
//        int action = (Integer) jsonObject.get("action");
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return null;
  }
}
