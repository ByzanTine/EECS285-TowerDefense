package umich.eecs285.towerdefence;

import java.util.Vector;

import javax.swing.JFrame;

public class TowerDefenseGame implements TowerDefensedataArray {
	private static Controller control;
	private static TowerDefenseDataBase DB;
	private static ClientBridge client_bridge;
	private static MainFrame mainFrame;
	private static Player player;
	private static int turn;
	private static long timestamp;
	// Increase Automatically
	private static int clientId = 0;

	private void init() {
		
		mainFrame = new MainFrame();
		mainFrame.setVisible(true);
		mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		player = new Player();
		turn = 1;
		control = new Controller();
		DB = new TowerDefenseDataBase();
		DB.init();
		client_bridge = new ClientBridge();
	}
	private void setTimestamp(){
		timestamp=System.currentTimeMillis();
	}
	public static void main(String args[]) {
		TowerDefenseGame game = new TowerDefenseGame();
		game.init();
		// Round 1
		
		TowerDefense_TransData data;
		game.setTimestamp();
		// paint data
		// Prep
		long prep = timestamp;
		while (true) {
			game.setTimestamp();
			
			// Look up Bridge
			game.checkprepBridge();
			data = control.getInfo(clientId, timestamp);
			// paint data
			mainFrame.nextFrame(data);
			if (prep+1000 <= timestamp) {
				// Time control will be determined by the Timer
				break;
			}

		}
		// Running
		int[] attackingUnits = player.getAttackingId();
		control.startTurn(turn, attackingUnits.length, attackingUnits);
		long run=timestamp;
		while (!control.isEnd()) {
			game.setTimestamp();
			if (run+80<=timestamp) {
				run=timestamp;
				game.checkRunBridge();
				control.run();
				data = control.getInfo(clientId, timestamp);
				// paint

				mainFrame.nextFrame(data);
				//System.out.println(data.toString());
				if(control.isDead()){
					for(int i=0;i<3000;i++){
						
					
						control.run();
						data=control.getInfo(clientId, timestamp);
						//paint
						mainFrame.nextFrame(data);
					}
					break;
				}

			}
			//System.out.println(timestamp);
//			if (control.isDead()) {
//				break;
//			}
		}
		for(int i=0;i<3000;i++){
			
			
			control.run();
			data=control.getInfo(clientId, timestamp);
			//paint
			mainFrame.nextFrame(data);
		}
		System.out.print("End Round");
		control.endTurn();
		player.addCandy(1, control.hasReachedKing());
		// TODO player automatically increase money
	}

	private void checkprepBridge() {
		// TODO Auto-generated method stub
		if (client_bridge.isCreateAttackUnitRequest()) {
			if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
				player.createAttackingUnit(client_bridge.getAttackUnitId());

			}
		}
		if (client_bridge.isCreateUnitRequest()) {
			if (player.canCreateUnit(client_bridge.getId())) {
				control.addUnit(client_bridge.getId(), client_bridge.getX(),
						client_bridge.getY(), 0);
				player.createUnit(client_bridge.getId());
			}
		}
		if (client_bridge.isMeoMeoNumIncreaseRequest()) {
			if(player.canCreateMeoMeo()){
				player.createMeoMeo();
			}
		}
		if (client_bridge.isMeoMeoTechUpgradeRequest()) {
			if(player.canUpdateMeoMeo()){
				player.updateMeoMeo();
			}
		}
		if (client_bridge.isUnitLevelupRequest()) {
			if(player.canUpdateUnit(client_bridge.getLevelupId())){
				player.updateUnit(client_bridge.getLevelupId());
				control.levelUp(client_bridge.getLevelupId());
			}
		}
	}

	private void checkRunBridge() {
		// TODO Auto-generated method stub
		if (client_bridge.isCreateAttackUnitRequest()) {
			if (player.canCreateAttackingUnit(client_bridge.getAttackUnitId())) {
				player.createAttackingUnit(client_bridge.getAttackUnitId());

			}
		}
		if (client_bridge.isMeoMeoNumIncreaseRequest()) {
			if(player.canCreateMeoMeo()){
				player.createMeoMeo();
			}
		}
		if (client_bridge.isMeoMeoTechUpgradeRequest()) {
			if(player.canUpdateMeoMeo()){
				player.updateMeoMeo();
			}
		}

	}
}
