package umich.eecs285.towerdefence;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;
/**
 * ID query for Image filename
 * ID query for Unit Factory
 * Image Search Syntax: Tag+postfix
 * Unit Search Syntax: Tag+postfix
 * id range: 
 * postfix 0 for king
 * postfix 1-10 for system enemies
 * postfix 11-16 for six player "tower" (primitive)
 * postfix 21-26 for the corresponding update (level-up)
 * postfix 31 for the 11-21-31 "tower" (ultimate upgrade)
 * @author Ye Jiayu
 *
 */
public class TowerDefenseDataBase {
	private static final int ID_SIZE = 100;
	private static final int ACTION_SIZE = 200;
	private static final double Speed_Ratio = 0.3;
	private static final double Cool_Down_Ratio = 2;
	private static final int Image_size = 14;
	private static final double Attack_Ratio=0.5;
	Hashtable<Integer, Hashtable<Integer, String>> image_table;
	
	Hashtable<Integer, Units> Unit_table;

	public TowerDefenseDataBase() {
		image_table = new Hashtable<Integer, Hashtable<Integer, String>>();
		Unit_table = new Hashtable<Integer, Units>();
	}

	/**
	 * Init the table with filenames
	 * 
	 */
	public void init() {
		for (int i = 0; i < ID_SIZE; i++) {
			for (int j = 0; j < ACTION_SIZE; j++) {
				String filename = "res/id";
				filename += i;
				filename += '/';
				filename += j;
				filename += ".png";

				this.insert(i, j, filename);
			}
		}
		File datasheet = new File("res/Test_Data/TD_data.csv");
		BufferedReader br = null;
		try {
			br = new BufferedReader(new FileReader(datasheet));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String line;

		try {
			// For king
			Units king = new Units(3000, 0, 1, 0, (int)(700*Speed_Ratio), 50, 1);
			Unit_table.put(0, king);
			// For others
			for (int i = 0; i < ID_SIZE && (line = br.readLine()) != null; i++) {

				String[] nums = line.split(",");
//				for (int j = 0; j < nums.length; j++)
//					System.out.print(j + ":" + nums[j] + " ");
//				System.out.print("\n");

				Units unit = new Units(Integer.parseInt(nums[2]),
						(int) (Double.parseDouble(nums[8])*Speed_Ratio*5), Image_size,
						(int) (500 * Speed_Ratio),
						(int) (Double.parseDouble(nums[7]) * Attack_Ratio),
						(int) Double.parseDouble(nums[3]),
						(int) (Double.parseDouble(nums[4]) * Cool_Down_Ratio));

				Unit_table.put(Integer.parseInt(nums[1]), unit);

			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		try {
			br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
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
	 * 
	 * @param action
	 * @param Direction
	 * @param FrameId
	 * @return
	 */
	public int generateActionId(int action,int Direction,int FrameId){
		int ActionCode=(action*8+Direction)*4+FrameId;
		/*Action: 0(still), 1(attack), 2(move), 3(dead)*/
		return ActionCode;
	}

	/**
	 * Obtain a filename string by id and action
	 * 
	 * @param id
	 * @param action
	 * @return filename
	 */
	public String searchImage(int rawId, int action) {
		int Action=action/32;//0-3
		int Direction=(action/4)%8;
		int FrameId=action%4;
		if(Action==0){
			int newActionId=this.generateActionId(0, Direction, 1);
			return image_table.get(this.processID(rawId)).get(newActionId);
		}
		if(Action==1){
			return image_table.get(this.processID(rawId)).get(action);
		}
		if(Action==2){
			if(FrameId==3||FrameId==1){
				int newActionId=this.generateActionId(0, Direction, 1);
				return image_table.get(this.processID(rawId)).get(newActionId);
			}
			else{
				int newActionId=this.generateActionId(0, Direction, FrameId);
				return image_table.get(this.processID(rawId)).get(newActionId);
			}
		}
		if(Action==3)
			return image_table.get(this.processID(rawId)).get(100);
		
		
		return "Error Not Found";
		
		
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
