package umich.eecs285.test;

import java.util.Vector;

import umich.eecs285.towerdefence.Player;

public class playerTest {

	
	 public static void main(String args[]) {
		 
		 Player myPlayer = new Player();
		 
		 System.out.println(myPlayer.getMoney());
		 System.out.println(myPlayer.getBall());
		 System.out.println(myPlayer.getCandy());
		 System.out.println(myPlayer.getMeomeoNum());
		 System.out.println(myPlayer.getMeomeoLevel());
		 
		 //win a round;
		 //usage: addCandy (int roundNum, boolean hasReachedking)
		 myPlayer.addCandy(1,false);
		 myPlayer.addCandy(2,true);
		 System.out.println(myPlayer.getCandy()); //should print out 45 + 60 * 0.8 = 93
		 
		 myPlayer.addCandy(3,true);
		 myPlayer.addCandy(4,true);
		 myPlayer.addCandy(5,false);
		 System.out.println(myPlayer.getCandy()); //should print out 321
		 
		 System.out.println(myPlayer.canCreateAttackingUnit(55));  //should print true
		 myPlayer.createAttackingUnit(55);
		 System.out.println(myPlayer.getMoney()); //should print 200
		 
		 System.out.println(myPlayer.canCreateAttackingUnit(56));  //should print true
		 myPlayer.createAttackingUnit(56);
		 System.out.println(myPlayer.getMoney()); //should print 50
		 
		 // get the attackingList
		 Vector <Integer> attackingList = myPlayer.getAttackingId();
		  
		 System.out.println("I am going to print the attacking unit ID");
		 for (int i = 0; i< attackingList.size(); i++)
		 {
			 System.out.println(attackingList.get(i));
		 }
		 System.out.println("END OF the attacking unit ID");
		 System.out.println(myPlayer.canCreateAttackingUnit(54));  //should print false
		
		 // no money, create meomeo
		 System.out.println(myPlayer.canCreateMeoMeo()); //true, since meomeo creating cost 100 candy, 
		 // but we have 321 now.
		 
		 myPlayer.createMeoMeo();
		 System.out.println(myPlayer.getCandy()); 
		 
		 System.out.println(myPlayer.canUpdateMeoMeo()); // true, since memomeo update cost 200 candy
		 System.out.println(myPlayer.getMeomeoLevel()); //should print 0;
		 myPlayer.updateMeoMeo();
		 System.out.println(myPlayer.getMeomeoLevel()); // should print 1;
		 System.out.println(myPlayer.getCandy());  //should print 21
		 
		 System.out.println(myPlayer.getMoney()); //should print 50
		 myPlayer.addMoney();
		 System.out.println(myPlayer.getMoney()); //should print 70, one meomeo at level 1
		 
		 System.out.println(myPlayer.getCandy()); //should print 21
		 System.out.println(myPlayer.canCreateUnit(11)); //should print true
		 myPlayer.createUnit(11);
		 System.out.println(myPlayer.getCandy()); //should print 11
		 System.out.println(myPlayer.getBall()); //should print 1
		 
		 System.out.println(myPlayer.canCreateUnit(14)); //should print false
		 myPlayer.addCandy(6,false);
		 myPlayer.addCandy(7,true);
		 System.out.println(myPlayer.getCandy()); //should print 185
		 System.out.println(myPlayer.canCreateUnit(14)); //should print true
		 myPlayer.createUnit(14);
		 System.out.println(myPlayer.getCandy()); //should print 110
		 
		 //attention: to update a unit, the id after updating needs to be passed.
		 // if you want to update unit 14, and after updating it should be unit 24,
		 // then you need to call canUpdate(24) and updateUnit(24)
		 System.out.println(myPlayer.canUpdateUnit(24)); //should print true
		 myPlayer.updateUnit(24);
		 System.out.println(myPlayer.getCandy()); //should print 110 - 80 = 30;
		 System.out.println(myPlayer.getBall()); //should print out 2.
			 
	 }
}
