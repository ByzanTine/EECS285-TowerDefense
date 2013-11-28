package umich.eecs285.towerdefence;


public class Controller {
	static final int MAX_SOLDIERS=20,MAX_UNITS=100,CYCLE=8;
	private Units []soldiers=new Units[MAX_UNITS],deadArray=new Units[MAX_UNITS];
	private Map mymap=new Map();
	private int enemy,cooldown,mysoldiers,totlesoldiers,deadnum,deadpre,deadstart;
	private boolean reachKing;
	private TowerDefenseDataBase Data=new TowerDefenseDataBase();
	static final int order[][]={{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
	static final int mod[]={1,2,1,2,1,2,1,2},KingX=40,KingY=40;
	public Controller(){
		for(int i=0;i<MAX_UNITS;i++){
			soldiers[i]=null;
			deadArray[i]=null;
		}
		enemy=0;
		cooldown=0;
		mysoldiers=0;
		totlesoldiers=0;
		deadpre=0;
		deadstart=0;
		deadnum=0;
		reachKing=false;
		addUnit(0,KingX,KingY,1);
		mysoldiers++;
		totlesoldiers++;
	}
	public boolean addUnit(int ID,int x,int y,int Group){
		if(Group<10&&totlesoldiers>=MAX_SOLDIERS||Group>10&&enemy>=MAX_UNITS-MAX_SOLDIERS)
			return false;
		if(mymap.ACT(null, x, y, 15, 0, Group, true)!=null)
			return false;
		int tag=totlesoldiers+enemy;
		if(ID%100==0){
			soldiers[tag]=new King(Data.searchUnit(ID));
			soldiers[tag].set(x, y, ID+tag*100, 0, Group);
			mysoldiers++;
		}
		else if(ID%100<=10){
			soldiers[tag]=new AttackUnits(Data.searchUnit(ID));
			soldiers[tag].set(x, y, ID+tag*100, 4, Group);
			enemy++;
		}
		else{
			soldiers[tag]=new DefenceUnits(Data.searchUnit(ID));
			soldiers[tag].set(x, y, ID+tag*100, 0, Group);
			mysoldiers++;
		}
		return true;
	}
	public void run(){
		Units temp;
		if(cooldown==0){
			deadpre=deadstart;
			deadstart=deadnum;
			for(int i=deadpre;i<deadstart;i++){
				deadArray[i].dead();
				mymap.deleteUnits(deadArray[i]);
			}
			for(int i=0;i<MAX_UNITS;i++){
				if(soldiers[i]!=null&&soldiers[i].HP>0&&soldiers[i].isFree()){
					temp=mymap.ACT(soldiers[i],soldiers[i].positionX, soldiers[i].positionY, soldiers[i].Range, soldiers[i].Face, soldiers[i].Group, false);
					if(temp!=null){
						soldiers[i].attack();
						if(temp.attacked(soldiers[i].Attack)){
							deadArray[deadnum]=temp;
							deadnum++;
							if(temp.Group>10)
								enemy--;
							else
								mysoldiers--;
						}
						soldiers[i].Face=getFace(temp.positionX-soldiers[i].positionX,temp.positionY-soldiers[i].positionY);
					}
					else if(soldiers[i].Speed>0){
						temp=mymap.ACT(soldiers[i],soldiers[i].positionX, soldiers[i].positionY, soldiers[i].Sight, soldiers[i].Face, soldiers[i].Group, false);
						int dx,dy,face;
						if(temp!=null){
							dx=temp.positionX-soldiers[i].positionX;
							dy=temp.positionY-soldiers[i].positionY;
						}
						else{
							dx=soldiers[i].pointX-soldiers[i].positionX;
							dy=soldiers[i].pointY-soldiers[i].positionY;
						}
						face=getFace(dx,dy);
						if(moveSoldier(soldiers[i],face)==false){
							if(moveSoldier(soldiers[i],(face+7)%8)==false)
								if(moveSoldier(soldiers[i],(face+1)%8))
									soldiers[i].still();
						}
					}
				}
			}
			cooldown=8;
		}
		else{
			cooldown--;
		}
	}
	public boolean isEnd(){
		return enemy==0;
	}
	public boolean isDead(){
		return mysoldiers==0;
	}
	public void startTurn(int n,int []ids){
		int height=mymap.HEIGHT*mymap.CELL_SIZE;
		int wide=mymap.HEIGHT*mymap.CELL_SIZE;
		int k=wide/(mymap.MAX_R*2);
		for(int i=0;i<n;i++){
			addUnit(ids[i],(n%k*2+1)*mymap.MAX_R,height-(n/k*2+1)*mymap.MAX_R,11);
		}
		deadpre=0;
		deadstart=0;
		deadnum=0;
		enemy=n;
		reachKing=false;
	}
	public void endTurn(){
		if(mysoldiers<2)
			reachKing=true;
		cooldown=0;
		mymap.endTurn();
		mysoldiers=totlesoldiers;
		for(int i=0;i<100;i++){
			if(soldiers[i].Group>2)
				soldiers[i]=null;
			else{
				soldiers[i].reNew();
				mymap.addMoveUnits(soldiers[i]);
			}
		}
	}
	public boolean hasReachedKing(){
		return reachKing;
	}
	public TowerDefensedataArray.TowerDefense_TransData getInfo(int clientId, long timestamp){
		int size=mysoldiers+enemy+deadnum-deadpre,i,k=0;
		TowerDefensedataArray.TowerDefense_TransData temp=new TowerDefensedataArray.TowerDefense_TransData(clientId,timestamp,size,(byte) 0);
		for(i=0;i<MAX_UNITS;i++)
			if(soldiers!=null&&soldiers[i].HP>0){
				temp.TowerDefense_TransArray[k]=soldiers[i].getInfo(cooldown);
				k++;
			}
		for(i=deadpre;i<deadnum;i++){
			temp.TowerDefense_TransArray[k]=deadArray[i].getInfo(cooldown);
			k++;
		} 
		return temp;
	}
	private int getFace(int dx,int dy){
		int tan,cot;
		if(dx==0){
			if(dy>0)
				return 0;
			else
				return 4;
		}
		else if(dy==0){
			if(dx>0)
				return 2;
			else
				return 6;
		}
		else{
			tan=dy/dx;
			cot=dx/dy;
			if(tan>=2||tan<-2){
				if(dy>0)
					return 0;
				else
					return 4;
			}
			else if(cot>=2||cot<-2){
				if(dx>0)
					return 2;
				else
					return 6;
			}
			else if(dx>0){
				if(dy>0)
					return 1;
				else
					return 3;
			}
			else if(dy>0)
				return 7;
			else
				return 5;
		}
	}
	private boolean moveSoldier(Units target,int face){
		Units temp;
		int dx,dy;
		dx=target.Speed*order[face][0]/mod[face];
		dy=target.Speed*order[face][1]/mod[face];
		temp=mymap.ACT(target,target.positionX+dx, target.positionY+dy, target.Radius, face, target.Group, true);
		if(temp==null){
			mymap.move(target, dx, dy);
			target.Face=face;
			return true;
		}
		else
			return false;
	}

}
