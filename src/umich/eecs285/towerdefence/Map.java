package umich.eecs285.towerdefence;

public class Map {
	public MapCells [][]cell;
	static final int order[][]={{0,1},{1,1},{1,0},{1,-1},{0,-1},{-1,-1},{-1,0},{-1,1}};
	static final int mod[]={1,2,1,2,1,2,1,2},WIDE=4,HEIGHT=6,CELL_SIZE=160,MAX_R=35;
	Map(){
		cell=new MapCells[HEIGHT][];
		for(int i=0;i<HEIGHT;i++){
			cell[i]=new MapCells[WIDE];
			for(int j=0;j<WIDE;j++)
				cell[i][j]=new MapCells();
		}
	}
	public void addMoveUnits(Units target){
		int i=target.positionY/CELL_SIZE;
		int j=target.positionX/CELL_SIZE;
		cell[i][j].move.add(target);
	}
	public void addStillUnits(Units target){
		int i=target.positionY/CELL_SIZE;
		int j=target.positionX/CELL_SIZE;
		cell[i][j].still.add(target);
	}
	public void deleteUnits(Units target){
		int i=target.positionY/CELL_SIZE;
		int j=target.positionX/CELL_SIZE;
		if(target.Group==0)
			cell[i][j].move.remove(target);
		else
			cell[i][j].move.add(target);
	}
	public void move(Units target,int x,int y){
		int i=target.positionY/CELL_SIZE;
		int j=target.positionX/CELL_SIZE;
		target.move(x, y);
		if(target.positionX/CELL_SIZE!=j||target.positionY/CELL_SIZE!=i){
			cell[i][j].move.remove(target);
			i=target.positionY/CELL_SIZE;
			j=target.positionX/CELL_SIZE;
			cell[i][j].move.add(target);
		}
	}
	//given x , y and R (Range or Sight), return the first unit that in the area
	public Units ACT(Units target,int x,int y,int R,int face,int group,boolean all){
		int i=y/CELL_SIZE,centerY=i*CELL_SIZE+CELL_SIZE/2,current=face;
		int j=x/CELL_SIZE,centerX=j*CELL_SIZE+CELL_SIZE/2;
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
		for(int i=0;i<HEIGHT;i++)
			for(int j=0;j<WIDE;j++)
				cell[i][j].move.removeAllElements();
	}
	private Units searchHelp(Units target,int current,int i,int j,int x,int y,int centerX,int centerY,int R,int group,boolean all){
		if(mod[current]==1){
			if(j+order[current][0]>=0&&j+order[current][0]<WIDE&&i+order[current][1]>=0&&i+order[current][1]<HEIGHT&&
					(Math.abs(x+R*order[current][0]-centerX)>CELL_SIZE/2-MAX_R
					||Math.abs(y+R*order[current][1]-centerY)>CELL_SIZE/2-MAX_R))
				return search(target,cell[i+order[current][1]][j+order[current][0]],x,y,R,group,all);
		}
		else{
			if(j+order[current][0]>=0&&j+order[current][0]<WIDE&&i+order[current][1]>=0&&i+order[current][1]<HEIGHT
					&&Math.abs(x+R*order[current][0]/2-centerX)+Math.abs(y+R*order[current][1]/2-centerY)>CELL_SIZE-MAX_R)
				return search(target,cell[i+order[current][1]][j+order[current][0]],x,y,R,group,all);
		}
		return null;
	}
	private Units search(Units target,MapCells current,int x,int y,int R,int group,boolean all){
		Units temp;
		for(int i=0;i<current.move.size();i++){
			temp=current.move.get(i);
			if((group!=temp.Group||(all&&(target==null||target.ID!=temp.ID)))&&Math.abs(temp.positionX-x)+Math.abs(temp.positionY-y)-temp.Radius<R)
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
