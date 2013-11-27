package umich.eecs285.towerdefence;

import java.util.Hashtable;

public class TowerDefenseDataBase {
	private static final int ID_SIZE = 100;
	private static final int ACTION_SIZE = 50;
	Hashtable<Integer, Hashtable<Integer, String>> image_table;
	Hashtable<Integer, Units> Unit_table;

	public TowerDefenseDataBase() {
		image_table = new Hashtable<Integer, Hashtable<Integer, String>>();
		Unit_table = new Hashtable<Integer, Units>();
	}

	/**
	 * Init the table with filenames
	 */
	public void init() {
		for (int i = 0; i < ID_SIZE; i++) {
			for (int j = 0; j < ACTION_SIZE; j++) {
				String filename = "id";
				filename += i;
				filename += '/';
				filename += j;

				this.insert(i, j, filename);
			}
		}
		for (int i = 0; i < ID_SIZE; i++) {
			Units unit=new Units(1,1,1,1,1,1,1);
			Unit_table.put(i, unit);

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
	 * 
	 * @param id
	 * @param action
	 * @return filename
	 */
	public String searchImage(int rawId, int action) {
		return image_table.get(this.processID(rawId)).get(action);
	}

	/**
	 * Obtain a pre-defined Unit by id
	 * 
	 * @param id
	 * @return Unit
	 */
	public Units searchUnit(int rawId) {
		return Unit_table.get(this.processID(rawId));
	}

	/**
	 * 
	 * @param rawId
	 * @return processedId
	 */
	private int processID(int rawId) {
		return rawId % ID_SIZE;
	}

}
