package umich.eecs285.towerdefence;

public class ClientBridge {
	// attacking units
	private boolean createAttackUnitRequest = false;
	private int AttackUnitId;
	public boolean isCreateAttackUnitRequest() {
		return createAttackUnitRequest;
	}
	

	public void setCreateAttackUnitRequest(boolean createAttackUnitRequest) {
		this.createAttackUnitRequest = createAttackUnitRequest;
	}

	public boolean isCreateUnitRequest() {
		return createUnitRequest;
	}

	public void setCreateUnitRequest(boolean createUnitRequest) {
		this.createUnitRequest = createUnitRequest;
	}

	public boolean isMeoMeoNumIncreaseRequest() {
		return MeoMeoNumIncreaseRequest;
	}

	public void setMeoMeoNumIncreaseRequest(boolean meoMeoNumIncreaseRequest) {
		MeoMeoNumIncreaseRequest = meoMeoNumIncreaseRequest;
	}

	public boolean isMeoMeoTechUpgradeRequest() {
		return MeoMeoTechUpgradeRequest;
	}

	public void setMeoMeoTechUpgradeRequest(boolean meoMeoTechUpgradeRequest) {
		MeoMeoTechUpgradeRequest = meoMeoTechUpgradeRequest;
	}

	public boolean isUnitLevelupRequest() {
		return UnitLevelupRequest;
	}


	public void setUnitLevelupRequest(boolean unitLevelupRequest) {
		UnitLevelupRequest = unitLevelupRequest;
	}

	// creating units
	private boolean createUnitRequest = false;
	private int id;
	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public int getX() {
		return x;
	}


	public void setX(int x) {
		this.x = x;
	}


	public int getY() {
		return y;
	}


	public void setY(int y) {
		this.y = y;
	}


	public int getLevelupId() {
		return LevelupId;
	}


	public void setLevelupId(int levelupId) {
		LevelupId = levelupId;
	}

	public int getAttackUnitId() {
		return AttackUnitId;
	}


	public void setAttackUnitId(int attackUnitId) {
		AttackUnitId = attackUnitId;
	}

	public boolean isChangeViewRequest() {
		return ChangeViewRequest;
	}


	public void setChangeViewRequest(boolean changeViewRequest) {
		ChangeViewRequest = changeViewRequest;
	}

	public boolean isCreateGameRequest() {
		return CreateGameRequest;
	}


	public void setCreateGameRequest(boolean createGameRequest) {
		CreateGameRequest = createGameRequest;
	}

	public boolean isJoinGameRequest() {
		return JoinGameRequest;
	}


	public void setJoinGameRequest(boolean joinGameRequest) {
		JoinGameRequest = joinGameRequest;
	}

	public boolean isGameCreated() {
		return GameCreated;
	}


	public void setGameCreated(boolean gameCreated) {
		GameCreated = gameCreated;
	}

	public boolean isGameConnected() {
		return GameConnected;
	}


	public void setGameConnected(boolean gameConnected) {
		GameConnected = gameConnected;
	}

	public int getMoney() {
		return Money;
	}


	public void setMoney(int money) {
		Money = money;
	}

	public int getCandy() {
		return candy;
	}


	public void setCandy(int candy) {
		this.candy = candy;
	}

	public Integer getIp() {
		return ip;
	}


	public void setIp(Integer ip) {
		this.ip = ip;
	}

	private int x;
	private int y;

	// upgrade tech
	// two kinds
	private boolean MeoMeoNumIncreaseRequest = false;
	private boolean MeoMeoTechUpgradeRequest = false;

	// uplevel unit
	private boolean UnitLevelupRequest=false;
	private int LevelupId;
	
	private boolean ChangeViewRequest=false;
	private boolean CreateGameRequest=false;
	private boolean JoinGameRequest=false;
	private boolean GameCreated=false;
	private boolean GameConnected=false;
	private int Money=0;
	private int candy=0;
	private Integer ip=0;
}
