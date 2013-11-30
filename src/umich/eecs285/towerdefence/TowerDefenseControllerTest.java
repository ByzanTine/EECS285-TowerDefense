package umich.eecs285.towerdefence;

import javax.swing.JFrame;
public class TowerDefenseControllerTest implements TowerDefensedataArray{
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
		TowerDefenseControllerTest game=new TowerDefenseControllerTest();
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
		data=control.getInfo(clientId, timestamp);
		mainFrame.nextFrame(data);
		while(!control.isEnd()){
			timestamp++;
			if(timestamp%3000==1){
				game.checkRunBridge();
				control.run();
				data=control.getInfo(clientId, timestamp);
				//paint
					mainFrame.nextFrame(data);
					System.out.println(data.toString());
				
				
				if(control.isDead()||timestamp>10000000){
					for(int i=0;i<16;i++){
						
					
						control.run();
						data=control.getInfo(clientId, timestamp);
						//paint
						mainFrame.nextFrame(data);
					}
					break;
				}
			}
			System.out.println(timestamp);
		}/**/
		for(int i=0;i<16;i++){
			
			
			control.run();
			data=control.getInfo(clientId, timestamp);
			//paint
			mainFrame.nextFrame(data);
		}
		
		control.endTurn();
		player.addCandy(1, control.hasReachedKing());
		turn++;
		/*control.startTurn(turn, 0, null);
		data=control.getInfo(clientId, timestamp);
		
		mainFrame.nextFrame(data);
		while(!control.isEnd()){
			timestamp++;
			if(timestamp%3000==1){
				game.checkRunBridge();
				control.run();
				data=control.getInfo(clientId, timestamp);
				//paint
					mainFrame.nextFrame(data);
					System.out.println(data.toString());
				
				
				if(control.isDead()||timestamp>1000000){
					for(int i=0;i<16;i++){
						
					
						control.run();
						data=control.getInfo(clientId, timestamp);
						//paint
						mainFrame.nextFrame(data);
					}
					break;
				}
			}
			System.out.println(timestamp);
		}
		
		control.endTurn();*/
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
