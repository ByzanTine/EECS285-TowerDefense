package umich.eecs285.towerdefence;

public class AttackUnits extends Units{
	public AttackUnits(Units model){
		super(model);
	}
	public void set(int x, int y, int num, int face, int group){
		super.set(x, y, num, face, group);
		this.pointX=Controller.KingX;
		this.pointY=Controller.KingY;
	}

}
