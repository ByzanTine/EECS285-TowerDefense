package umich.eecs285.towerdefence;

import java.sql.Timestamp;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public final class JSONUtility implements TowerDefensedataArray{
  String arrayToJSON(int clientId, TowerDefense_TransData towerDefense_TransData) {
    JSONObject obj = new JSONObject();
    JSONArray arrayObj = new JSONArray();
    try {
      obj.put("clientId", clientId);
      obj.put("timestamp", new Timestamp(System.currentTimeMillis()));
      for(int i = 0; i < TowerDefenseObject_Array_Size; i++) {
        JSONObject arrayData = new JSONObject();
        arrayData.put("id", TowerDefense_TransData.TowerDefense_TransArray[i].getId());
        arrayObj.put(arrayData);
      }
      obj.put("arrayObj", arrayObj);
    } catch (JSONException e) {
      e.printStackTrace();
    }
    return null;
  }
}
