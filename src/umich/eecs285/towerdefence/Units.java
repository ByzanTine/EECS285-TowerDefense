package umich.eecs285.towerdefence;

public abstract class Units {
	public int HP,MaxHP,Speed,Radius,ID,Action,Sight,Range,Attack,Cooldown,AttackFrequency;
	public int positionX,positionY,Group,pointX,pointY,number,Face;
	//Action: 0(still), 1(attack), 2(move)
	Units(){
		Cooldown=0;
	}
	Units(int x,int y,int hitpoint,int num,int face,int group){
		pointX=x;
		pointY=y;
		positionX=x;
		positionY=y;
		HP=hitpoint;
		MaxHP=hitpoint;
		number=num;
		Face=face;
		Group=group;
		Cooldown=0;
	}
	public void attack(){
		Cooldown=AttackFrequency;
		Action=1;
	}
	public boolean attacked(int damage){
		HP-=damage;
		if(HP<=0)
			return true;
		return false;
	}
	public void move(int x,int y){
		positionX+=x;
		positionY+=y;
		Action=2;
	}
	public void reNew(){
		HP=MaxHP;
		positionX=pointX;
		positionY=pointY;
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
