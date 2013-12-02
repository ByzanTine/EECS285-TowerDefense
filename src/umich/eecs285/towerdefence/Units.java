package umich.eecs285.towerdefence;

import umich.eecs285.towerdefence.TowerDefensedataArray.*;
public class Units {
	public int HP, MaxHP, Speed, Radius, ID, Action, Sight, Range, Attack,
			Cooldown, AttackFrequency;
	public int positionX, positionY, Group, pointX, pointY, Face;
	// Action: 0(still), 1(attack), 2(move), 3(dead)
	public int X[], Y[];

	public Units() {
		Cooldown = 0;
		X = new int[8];
		Y = new int[8];
	}

	/**
	 * Basic Property Setup
	 * 
	 * @param MaxHP
	 * @param Speed
	 * @param Radius
	 * @param Sight
	 * @param Range
	 * @param Attack
	 * @param AttackFrequency
	 */
	public Units(int MaxHP, int Speed, int Radius, int Sight, int Range,
			int Attack, int AttackFrequency) {
		this.MaxHP = MaxHP;
		this.HP=MaxHP;
		this.Speed = Speed;
		this.Radius = Radius;
		this.Sight = Sight;
		this.Range = Range;
		this.Attack = Attack;
		this.AttackFrequency = AttackFrequency;
	}

	public Units(Units Unit) {
		this.MaxHP = Unit.MaxHP;
		this.HP=Unit.MaxHP;
		this.Speed = Unit.Speed;
		this.Radius = Unit.Radius;
		this.Sight = Unit.Sight;
		this.Range = Unit.Range;
		this.Attack = Unit.Attack;
		this.AttackFrequency = Unit.AttackFrequency;
	}

	/**
	 * Set Logic element
	 * 
	 * @param x
	 * @param y
	 * @param hitpoint
	 * @param num
	 * @param face
	 * @param group
	 */
	public void set(int x, int y, int num, int face, int group) {
		positionX = x;
		positionY = y;
		Face = face;
		Group = group;
		Cooldown = 0;
		Action = 0;
		ID=num;
		X = new int[8];
		Y = new int[8];
		for (int i = 0; i < 8; i++) {
			X[i] = x;
			Y[i] = y;
		}
	}

	public void attack() {
		Cooldown = AttackFrequency;
		Action = 1;
		for (int i = 0; i < 8; i++) {
			X[i] = positionX;
			Y[i] = positionY;
		}
	}

	public boolean attacked(int damage) {
		HP -= damage;
		if (HP <= 0)
			return true;
		return false;
	}

	/**
	 * 
	 * @param x
	 * @param y
	 */
	public void move(int x, int y) {
		for (int i = 0; i < 8; i++) {
			X[i] = positionX + x * (8 - i) / 8;
			Y[i] = positionY + y * (8 - i) / 8;
		}
		positionX += x;
		positionY += y;
		Action = 2;
	}

	public void still() {
		Action = 0;
		for (int i = 0; i < 8; i++) {
			X[i] = positionX;
			Y[i] = positionY;
		}
	}

	public void dead() {
		Action = 3;
		for (int i = 0; i < 8; i++) {
			X[i] = positionX;
			Y[i] = positionY;
		}
	}

	public void reNew() {
		HP = MaxHP;
		positionX = pointX;
		positionY = pointY;
		Action = 0;
		for (int i = 0; i < 8; i++) {
			X[i] = pointX;
			Y[i] = pointY;
		}
	}

	public boolean isFree() {
		if (Cooldown <= 0)
			return true;
		else {
			Cooldown--;
			Action = 0;
			for (int i = 0; i < 8; i++) {
				X[i] = positionX;
				Y[i] = positionY;
			}
			Action = 0;
			return false;
		}
	}

	public void levelUp(Units Unit) {
		this.MaxHP = Unit.MaxHP;
		this.HP=Unit.MaxHP;
		this.Speed = Unit.Speed;
		this.Radius = Unit.Radius;
		this.Sight = Unit.Sight;
		this.Range = Unit.Range;
		this.Attack = Unit.Attack;
		this.AttackFrequency = Unit.AttackFrequency;
		this.ID+=10;
	}

	public String toString() {
		String basic_info = "Basic:";
		basic_info += " MaxHP:";
		basic_info += MaxHP;
		basic_info += " HP:";
		basic_info += HP;
		basic_info += " Speed:";
		basic_info += Speed;
		basic_info += " Radius:";
		basic_info += Radius;
		basic_info += " Sight:";
		basic_info += Sight;
		basic_info += " Range:";
		basic_info += Range;
		basic_info += " Attack:";
		basic_info += Attack;
		basic_info += " AttackFrequency:";
		basic_info += AttackFrequency;
		
		return basic_info;
	}
	public TowerDefenseObject getInfo(int frame){
		return new TowerDefenseObject(ID,HP,X[frame],Y[frame],(Action*8+Face)*4+frame/2);//Map.HEIGHT*Map.CELL_SIZE-
	}


}
