package umich.eecs285.towerdefence;
/**
 * @author Jiaxi Luo
 * This is a utility class providing method to transfer TowerDefense_TransData to JSON or vice versa.
 */
import java.io.IOException;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public final class JSONUtility {
  
  @SuppressWarnings("unchecked")
  public static String arrayToJSON(TowerDefense_TransData towerDefense_TransData) throws IOException {
    JSONObject obj = new JSONObject();
    JSONArray arrayObj = new JSONArray();
    obj.put("clientId", towerDefense_TransData.getClientId());
    obj.put("timestamp", towerDefense_TransData.getTimeStamp());
    obj.put("size", towerDefense_TransData.getSize());
    obj.put("transmitType", towerDefense_TransData.getTransmitType());
    for(int i = 0; i < towerDefense_TransData.getSize(); i++) {
      JSONObject arrayData = new JSONObject();
      arrayData.put("id", towerDefense_TransData.TowerDefense_TransArray[i].getId());
      arrayData.put("life", towerDefense_TransData.TowerDefense_TransArray[i].getLife());
      arrayData.put("x", towerDefense_TransData.TowerDefense_TransArray[i].getX());
      arrayData.put("y", towerDefense_TransData.TowerDefense_TransArray[i].getY());
      arrayData.put("action", towerDefense_TransData.TowerDefense_TransArray[i].getAction());
      arrayObj.add(arrayData);
    }
    obj.put("arrayObj", arrayObj);
    return obj.toString();
  }
  
  public static TowerDefense_TransData JOSNToArray(String jsonString) {
    JSONParser parser = new JSONParser();
    TowerDefense_TransData towerDefense_TransData = null;
    try {
      JSONObject jsonObject = (JSONObject) parser.parse(jsonString);
      towerDefense_TransData = new TowerDefense_TransData( 
              ((Long) jsonObject.get("clientId")).intValue(),
              (Long) jsonObject.get("timestamp"),
              ((Long) jsonObject.get("size")).intValue(),
              ((Long) jsonObject.get("transmitType")).byteValue()
          );
      JSONArray arrayObj = (JSONArray) jsonObject.get("arrayObj");
      for (int i = 0; i < towerDefense_TransData.getSize(); i++) {
        JSONObject data = (JSONObject) arrayObj.get(i);
        towerDefense_TransData.TowerDefense_TransArray[i] = new TowerDefenseObject();
        towerDefense_TransData.TowerDefense_TransArray[i].setId( ((Long) data.get("id")).intValue() );
        towerDefense_TransData.TowerDefense_TransArray[i].setLife( ((Long) data.get("life")).intValue() );
        towerDefense_TransData.TowerDefense_TransArray[i].setX( ((Long) data.get("x")).intValue() );
        towerDefense_TransData.TowerDefense_TransArray[i].setY( ((Long) data.get("y")).intValue() );
        towerDefense_TransData.TowerDefense_TransArray[i].setAction( ((Long) data.get("action")).intValue() );
      }
    } catch (ParseException e) {
      e.printStackTrace();
    }
    return towerDefense_TransData;
  }
}
