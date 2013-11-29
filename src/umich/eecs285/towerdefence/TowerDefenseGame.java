package umich.eecs285.towerdefence;

public class TowerDefenseGame implements TowerDefensedataArray{
	private static Controller control;
	private static TowerDefenseDataBase DB;
	private static ClientBridge client_bridge;
	private static Player player;
	private static int turn;
	private static int timestamp;
	private static int clientId=0;
	private void init() {
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
		control.startTurn(turn, 0, null);
		TowerDefense_TransData data=control.getInfo(clientId, timestamp);
		// paint data
		// Prep
		while(true){
			timestamp++;
			control.run();
			//Look up Bridge
			game.checkprepBridge();
			data=control.getInfo(clientId, timestamp);
			//paint data
			
			if(timestamp==10){
				//Time control will be determined by the Timer
				break;
			}
		}
		//Running
		while(!control.isEnd()){
			game.checkRunBridge();
			control.run();
			data=control.getInfo(clientId, timestamp);
			//paint
			if(control.isDead()){
				break;
			}
		}
		
		
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
