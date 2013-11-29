package umich.eecs285.towerdefence;

import javax.swing.JFrame;

public class TowerDefenseGame implements TowerDefensedataArray{
	private static Controller control;
	private static TowerDefenseDataBase DB;
	private static ClientBridge client_bridge;
	private static MainFrame mainFrame;
	private static Player player;
	private static int turn;
	private static int timestamp;
	//Increase Automatically
	private static int clientId=0;
	
	private void init() {
		mainFrame=new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player=new Player();
		turn=1;
		control = new Controller();
		DB = new TowerDefenseDataBase();
		DB.init();
		client_bridge = new ClientBridge();
	}

	public static void main(String args[]) {
		TowerDefenseGame game=new TowerDefenseGame();
		game.init();
		// Round 1
		
		TowerDefense_TransData data;
		
		// paint data
		// Prep
		while(true){
			timestamp++;
			
			//Look up Bridge
			game.checkprepBridge();
			data=control.getInfo(clientId, timestamp);
			//paint data
			mainFrame.nextFrame(data);
			if(timestamp==10){
				//Time control will be determined by the Timer
				break;
			}
			
		}
		//Running
		control.startTurn(turn, 0, null);
		while(!control.isEnd()){
			timestamp++;
			game.checkRunBridge();
			control.run();
			data=control.getInfo(clientId, timestamp);
			//paint
			if(timestamp%10000==1){
				mainFrame.nextFrame(data);
				System.out.println(data.toString());
			
			}
			if(control.isDead()){
				break;
			}
		}
		control.endTurn();
		player.addCandy(1, control.hasReachedKing());
		// TODO player automatically increase money
	}

	private void checkprepBridge() {
		// TODO Auto-generated method stub
		if(client_bridge.isCreateAttackUnitRequest()){
			
		}
		if(client_bridge.isCreateUnitRequest()){
			if(player.canCreateUnit(client_bridge.getId())){
				control.addUnit(client_bridge.getId(), client_bridge.getX(), client_bridge.getY(), 0);
				player.createUnit(client_bridge.getId());
			}
		}
		if(client_bridge.isMeoMeoNumIncreaseRequest()){
			
		}
		if(client_bridge.isMeoMeoTechUpgradeRequest()){
			
		}
		if(client_bridge.isUnitLevelupRequest()){
			
		}
	}
	private void checkRunBridge() {
		// TODO Auto-generated method stub
		if(client_bridge.isCreateAttackUnitRequest()){
			
		}
		
		if(client_bridge.isMeoMeoNumIncreaseRequest()){
			
		}
		if(client_bridge.isMeoMeoTechUpgradeRequest()){
			
		}
		
	}
}
