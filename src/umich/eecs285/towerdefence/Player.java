package umich.eecs285.towerdefence;

import java.util.Hashtable;
import java.util.Vector;

public class Player {

	private static final int initialMoney = 300;
	private static final int initialBall = 0;
	private static final int maximumMeoNumber = 7;
	private static final int initialCandy = 0;
	private static final int meomeoCreateCost = 100; // subject to change
	private static final int meomeoUpdateCost = 200; // subject to change
	private static final int meomeoMaximumLevel = 3; // subject to change
	private static final int initialmeomeoIncome = 10;
	private static final int updatemeomeoIncome = 10; // meomeoIncome increase
														// by this value
														// every time it
														// upgrades.
	private static final double hasReachedKingRatio = 0.8;
	private static final int roundProfit[] = { 45, 60, 60, 75, 120, 90, 105,
			120, 120, 147 };

	private Hashtable<Integer, Integer> candyTable; // for buying protecting
													// unit
	private Hashtable<Integer, Integer> moneyTable; // for sending attacking
													// unit

	private int meomeoLevel;
	private int money;
	private int candy;
	private int ball;
	private int meomeoNum;
	private int meomeoIncome; // used for updating the meomeo, high level meomeo
								// have high income
	private Vector<Integer> attackingId = new Vector();

	public Player() {

		meomeoLevel = 0;
		money = initialMoney;
		candy = initialCandy;
		ball = initialBall;
		meomeoNum = 0;
		meomeoIncome = initialmeomeoIncome;

		candyTable = new Hashtable<Integer, Integer>();
		moneyTable = new Hashtable<Integer, Integer>();
		candyTable.put(11, 10);
		candyTable.put(12, 25);
		candyTable.put(13, 50);
		candyTable.put(14, 75);
		candyTable.put(15, 100);
		candyTable.put(16, 150);
		candyTable.put(21, 50);
		candyTable.put(22, 60);
		candyTable.put(23, 75);
		candyTable.put(24, 80);
		candyTable.put(25, 100);
		candyTable.put(26, 150);
		candyTable.put(31, 200);
		// moneyTable value has not been given!!
		moneyTable.put(51, 10);
		moneyTable.put(52, 25);
		moneyTable.put(53, 50);
		moneyTable.put(54, 75);
		moneyTable.put(55, 100);
		moneyTable.put(56, 150);
	}

	public int[] getAttackingId() {
		int[] temp=new int[attackingId.size()];
		for(int i=0;i<attackingId.size();i++)
			temp[i]=attackingId.get(i);
		return temp;
	}

	public int getMeomeoNum() {
		return meomeoNum;
	}

	public int getMoney() {
		return money;
	}

	public int getCandy() {
		return candy;
	}

	public int getBall() {
		return ball;
	}

	public int getMeomeoLevel() {
		return meomeoLevel;
	}

	// judge the ability to create defending unit
	public boolean canCreateUnit(int id) {

		if (id >= 11 && id <= 16 && candy >= candyTable.get(id))
			return true;

		else
			return false;
	}

	public boolean canUpdateUnit(int id) {

		if (((id >= 21 && id <= 26) || id == 31) && candy >= candyTable.get(id))
			return true;

		else
			return false;
	}

	public boolean canCreateMeoMeo() {

		return (meomeoNum < maximumMeoNumber && candy >= meomeoCreateCost);
	}

	public boolean canUpdateMeoMeo() {

		return (meomeoLevel < meomeoMaximumLevel && candy >= meomeoUpdateCost);

	}

	public void addCandy(int roundNum, boolean hasReachedKing) {

		if (hasReachedKing)
			candy += roundProfit[roundNum - 1] * hasReachedKingRatio;

		else
			candy += roundProfit[roundNum - 1];
	}

	public void addMoney() {
		money += meomeoIncome * meomeoNum;
	}

	public void createUnit(int id) {

		candy -= candyTable.get(id);
		ball++;

	}

	public void updateUnit(int id) {

		candy -= candyTable.get(id);

	}

	public void createMeoMeo() {

		candy -= meomeoCreateCost;
		meomeoNum++;
	}

	public void updateMeoMeo() {

		candy -= meomeoUpdateCost;
		meomeoLevel++;
		meomeoIncome += updatemeomeoIncome;
	}

	public void createAttackingUnit(int id) {

		money -= moneyTable.get(id);
		attackingId.add(id);

	}

	public boolean canCreateAttackingUnit(int id) {

		if (id >= 51 && id <= 56 && money >= moneyTable.get(id))
			return true;

		else
			return false;

	}

}

// nothing
