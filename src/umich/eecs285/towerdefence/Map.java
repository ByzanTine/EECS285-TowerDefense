package umich.eecs285.towerdefence;

public class Map {
	public MapCells [][]cell;
	static final int order[][]={{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
	static final int mod[]={1,2,1,2,1,2,1,2};
	Map(){
		cell=new MapCells[4][];
		for(int i=0;i<4;i++){
			cell[i]=new MapCells[8];
			for(int j=0;j<8;j++)
				cell[i][j]=new MapCells();
		}
	}
	public void addMoveUnits(Units target){
		int i=target.positionY/160;
		int j=target.positionX/160;
		cell[i][j].move.add(target);
	}
	public void addStillUnits(Units target){
		int i=target.positionY/160;
		int j=target.positionX/160;
		cell[i][j].still.add(target);
	}
	public void deleteUnits(Units target){
		int i=target.positionY/160;
		int j=target.positionX/160;
		if(target.Group==0)
			cell[i][j].move.remove(target);
		else
			cell[i][j].move.add(target);
	}
	public void move(Units target,int x,int y){
		int i=target.positionY/160;
		int j=target.positionX/160;
		target.move(x, y);
		if(target.positionX/160!=j||target.positionY/160!=i){
			cell[i][j].move.remove(target);
			i=target.positionY/160;
			j=target.positionX/160;
			cell[i][j].move.add(target);
		}
	}
	//given x , y and R (Range or Sight), return the first unit that in the area
	public Units ACT(Units target,int x,int y,int R,int face,int group,boolean all){
		int i=y/160,centerY=i*160+80,current=face;
		int j=x/160,centerX=j*160+80;
		Units temp=null;
		temp=search(target,cell[i][j],x,y,R,group,all);
		if(temp!=null)
			return temp;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+7)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+1)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+6)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+2)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+5)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+3)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		current=(face+4)%8;
		temp=searchHelp(target,current,i,j,x,y,centerX,centerY,R,group,all);
		if(temp!=null)
			return temp;
		return null;
	}
	public void endTurn(){
		for(int i=0;i<4;i++)
			for(int j=0;j<8;j++)
				cell[i][j].move.removeAllElements();
	}
	private Units searchHelp(Units target,int current,int i,int j,int x,int y,int centerX,int centerY,int R,int group,boolean all){
		if(mod[current]==1){
			if(j+order[current][0]>=0&&j+order[current][0]<8&&Math.abs(x+R*order[current][0]-centerX)>80
					||i+order[current][1]>=0&&i+order[current][1]<4&&Math.abs(y+R*order[current][1]-centerY)>80)
				return search(target,cell[i+order[current][1]][j+order[current][0]],x,y,R,group,all);
		}
		else{
			if(j+order[current][0]>=0&&j+order[current][0]<8&&i+order[current][1]>=0&&i+order[current][1]<4
					&&Math.abs(x+R*order[current][0]-centerX)/2+Math.abs(y+R*order[current][1]/2-centerY)>160)
				return search(target,cell[i+order[current][1]][j+order[current][0]],x,y,R,group,all);
		}
		return null;
	}
	private Units search(Units target,MapCells current,int x,int y,int R,int group,boolean all){
		Units temp;
		for(int i=0;i<current.move.size();i++){
			temp=current.move.get(i);
			if((group!=temp.Group||(all&&target.number!=temp.number))&&Math.abs(temp.positionX-x)+Math.abs(temp.positionY-y)-temp.Radius<R)
				return temp;
		}
		if(all){
			for(int i=0;i<current.still.size();i++){
				temp=current.still.get(i);
				if(Math.abs(temp.positionX-x)+Math.abs(temp.positionY-y)-temp.Radius<R)
					return temp;
			}
		}
		return null;
	}

}
