package umich.eecs285.towerdefence;
import java.util.Vector;


public class MapCells {
	public Vector<Units> move;
	public Vector<Units> still;
	MapCells(){
		move=new Vector<Units>();
		still=new Vector<Units>();
	}

}
