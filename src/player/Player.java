package player;

import hand.Hand;

public class Player {
	private String id;
	private String name;
	private int bank;
	
	private Hand hand;
	
	public Player() {
		this("00000", "Guest", 1000);
	}
	
	public Player(String id, String name) {
		this(id, name, 1000);
	}

	public Player(String id, String name, int bank) {
		this.id = id;
		this.name = name;
		this.bank = bank;	
		hand = new Hand();
	}

	
	public String getName() {
		return name;
	}

	public String getId() {
		return id;
	}

	public int getBank() {
		return bank;
	}

	public void setBank(int bank) {
		this.bank = bank;
	}

	public Hand getHand() {
		return hand;
	}

	public String toString() {
		return "Player " + name + ", id: " + id + " has a bank of " + bank; 
	}
}
