package umich.eecs285.towerdefence;

public class King extends Units{
	public King(Units model){
		super(model);
	}
	public void set(int x, int y, int num, int face, int group){
		super.set(x, y, num, face, group);
		this.pointX=x;
		this.pointY=y;
	}

}
