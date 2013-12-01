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
}
