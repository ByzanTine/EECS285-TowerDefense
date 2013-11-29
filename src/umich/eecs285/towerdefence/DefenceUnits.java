package umich.eecs285.towerdefence;

public class DefenceUnits extends Units{

	public DefenceUnits(Units model) {
		// TODO Auto-generated constructor stub
		super(model);
	}
	public void set(int x, int y, int num, int face, int group){
		super.set(x, y, num, face, group);
		this.pointX=x;
		this.pointY=y;
	}

}
