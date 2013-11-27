package umich.eecs285.towerdefence;
import java.util.Hashtable;

public class TowerDefenseDataBase {
	private static final int ID_SIZE=100;
	private static final int ACTION_SIZE=50;
	Hashtable<Integer, Hashtable<Integer, String>> image_table;

	public TowerDefenseDataBase() {
		image_table = new Hashtable<Integer, Hashtable<Integer, String>>();

	}

	/**
	 * Init the table with filenames
	 */
	public void init() {
		for(int i=0;i<ID_SIZE;i++){	
			for(int j=0;j<ACTION_SIZE;j++){
				String filename = "";
				filename+=i;
				filename+='_';
				filename+=j;
				
				this.insert(i, j, filename);
			}
		}
	}

	private void insert(int id, int action, String filename) {
		Hashtable<Integer, String> actionTable;
		if (image_table.get(id) == null) {
			actionTable = new Hashtable<Integer, String>();

		} else {
			actionTable = image_table.get(id);
		}
		actionTable.put(action, filename);
		image_table.put(id, actionTable);
	}
	/**
	 * Obtain a filename string by id and action
	 * @param id
	 * @param action
	 * @return
	 */
	public String search(int id, int action) {
		return image_table.get(id).get(action);
	}

}
