package umich.eecs285.towerdefence;

import java.util.Hashtable;
import java.util.Vector;

import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefenseObject;
import umich.eecs285.towerdefence.TowerDefensedataArray.TowerDefense_TransData;

public class Player {

  public static final int initialMoney = 75;
  private static final int maximumMeoNumber = 6;
  public static final int initialCandy = 3330;
  private static final int meomeoCreateCost = 50; // subject to change
  private static final int meomeoUpdateCost = 60; // subject to change
  private static final int meomeoMaximumLevel = 3; // subject to change
  private static final int initialmeomeoIncome = 10;
  private static final int updatemeomeoIncome = 10; // meomeoIncome increase
  private static final int meomeoIncomeFactor = 10;
  // by this value
  // every time it
  // upgrades.
  private static final double hasReachedKingRatio = 0.8;
  private static final int roundProfit[] = { 45, 60, 60, 75, 120, 90, 105, 120,
      120, 147 };

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
  private Vector<Integer> attackingId = new Vector<Integer>();

  public Player() {

    meomeoLevel = 0;
    money = initialMoney;
    candy = initialCandy;
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
    int[] temp = new int[attackingId.size()];
    for (int i = 0; i < attackingId.size(); i++)
      temp[i] = attackingId.get(i);
    return temp;
  }
  public void clearAttackingId(){
	  attackingId.removeAllElements();
  }
  public TowerDefense_TransData getAttackingData(int clientId) {
    int[] temp = getAttackingId();
    TowerDefense_TransData towerDefense_TransData 
        = new TowerDefense_TransData(clientId, attackingId.size(), TowerDefensedataArray.Transmit_Type_New_Round);
    for (int j = 0; j < towerDefense_TransData.getSize(); j++) {
      towerDefense_TransData.TowerDefense_TransArray[j] = new TowerDefenseObject();
      int id = temp[j];
      int life = 0;
      int x = 0;
      int y = 0;
      int action = 0;
      towerDefense_TransData.TowerDefense_TransArray[j].setId(id);
      towerDefense_TransData.TowerDefense_TransArray[j].setLife(life);
      towerDefense_TransData.TowerDefense_TransArray[j].setX(x);
      towerDefense_TransData.TowerDefense_TransArray[j].setY(y);
      towerDefense_TransData.TowerDefense_TransArray[j].setAction(action);
    }
    return towerDefense_TransData;
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
    money += meomeoIncome * meomeoNum * meomeoIncomeFactor;
  }

  public void createUnit(int id) {
	  
    candy -= candyTable.get(id);
    System.out.println("Candy"+candy);
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
