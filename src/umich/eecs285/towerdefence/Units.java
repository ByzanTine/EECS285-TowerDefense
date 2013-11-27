package umich.eecs285.towerdefence;

public abstract class Units {
	public int HP,MaxHP,Speed,Radius,ID,Action,Sight,Range,Attack,Cooldown,AttackFrequency;
	public int positionX,positionY,Group,pointX,pointY,number,Face;
	//Action: 0(still), 1(attack), 2(move), 3(dead)
	public int X[],Y[];
	Units(){
		Cooldown=0;
		X=new int[8];
		Y=new int[8];
	}
	/**
	 * 
	 * @param x
	 * @param y
	 * @param hitpoint
	 * @param num
	 * @param face
	 * @param group
	 */
	Units(int x,int y,int hitpoint,int num,int face,int group){
		positionX=x;
		positionY=y;
		HP=hitpoint;
		MaxHP=hitpoint;
		number=num;
		Face=face;
		Group=group;
		Cooldown=0;
		Action=0;
		X=new int[8];
		Y=new int[8];
		for(int i=0;i<8;i++){
			X[i]=x;
			Y[i]=y;
		}
	}
	public void attack(){
		Cooldown=AttackFrequency;
		Action=1;
		for(int i=0;i<8;i++){
			X[i]=positionX;
			Y[i]=positionY;
		}
	}
	public boolean attacked(int damage){
		HP-=damage;
		if(HP<=0)
			return true;
		return false;
	}
	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void move(int x,int y){
		for(int i=0;i<8;i++){
			X[i]=positionX+x*(8-i)/8;
			Y[i]=positionY+y*(8-i)/8;
		}
		positionX+=x;
		positionY+=y;
		Action=2;
	}
	public void still(){
		Action=0;
		for(int i=0;i<8;i++){
			X[i]=positionX;
			Y[i]=positionY;
		}
	}
	public void dead(){
		Action=3;
		for(int i=0;i<8;i++){
			X[i]=positionX;
			Y[i]=positionY;
		}
	}
	public void reNew(){
		HP=MaxHP;
		positionX=pointX;
		positionY=pointY;
		Action=0;
		for(int i=0;i<8;i++){
			X[i]=pointX;
			Y[i]=pointY;
		}
	}
	public boolean isFree(){
		if(Cooldown<=0)
			return true;
		else{
			Cooldown--;
			Action=0;
			return false;
		}
	}
	public abstract void levelUp();
	public abstract String toString();

}
