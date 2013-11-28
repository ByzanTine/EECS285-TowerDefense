package umich.eecs285.test;
import umich.eecs285.towerdefence.*;
public class DataBase_Example {
	public static void main(String args[]){
		TowerDefenseDataBase DB=new TowerDefenseDataBase();
		DB.init();
		System.out.println(DB.searchImage(10, 10));
		System.out.println(DB.searchUnit(31));
		Units a=new Units(DB.searchUnit(31));
		a.Attack=5;
		System.out.println(DB.searchUnit(31));
	}
}
