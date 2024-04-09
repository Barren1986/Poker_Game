package helpers;

import java.util.Arrays;

import hand.Hand;

public class PokerSolver {
	private static String[] cardFaces = {"1", "2", "3", "4", "5", "6", "7", "8", "9", "T", "J", "Q", "K", "A"};
	private static String[] suits = {"Spades", "Hearts", "Diamonds", "Clubs"};
	private static int[] handFace;
	private static int[] handSuit;
	private static int handScore = 0;
	private static int deuces;
	private static int rank = 0;
	private static String gameType = "";
	private static boolean aceTwo = false;
	
	/* Evaluate hand to determine rank:
			High Card: 1
			Pair: 2
			Two Pair: 3
			Three of a Kind: 4
			Straight: 5
			Flush: 6
			Full House: 7
			Four of a Kind: 8
			Straight Flush: 9
			Five of a Kind: 10       (Deuces Wild)
			Wild Royal Flush: 11     (Deuces Wild)
			Four Deuces: 12          (Deuces Wild)
			Royal Flush: 13
			Natural Royal Flush: 13  (Deuces Wild)
	*/
	
	public static void evaluateHand(Hand hand, String[] handString) {
		//This is for games with no wild cards
		prepareHand(hand, handString);
		deuces = 0;
		evaluateCards(hand, handString);

	}
	
	public static void evaluateHand(Hand hand, String[] handString, String game) {
		gameType = game;
		prepareHand(hand, handString);		
		deuces = (gameType == "DeucesWild") ? countDeuces() : 0;
		evaluateCards(hand, handString);
	}

	
	private static void prepareHand(Hand hand, String[] handString) {
		int handSize = handString.length;
		//Create arrays to hold card values and card suits
		handFace = new int[handSize];
		handSuit = new int[handSize];
		handScore = 0;
		
		getHandData(handString);
		
		hand.setAltCards(handFace);
	}
		
	private static void getHandData(String[] cards) {
		String faceString;
		
		//Break the cards down into face and suit
		for(int i=0; i<cards.length; i++) {
			//change the face to a card value
			faceString = cards[i].substring(0,1);
			switch(faceString) {
				case "2":
				case "3":
				case "4":
				case "5":
				case "6":
				case "7":
				case "8":
				case "9":
					handFace[i] = Integer.parseInt(faceString);
					break;
				case "T":
					handFace[i] = 10;
					break;
				case "J":
					handFace[i] = 11;					
					break;
				case "Q":
					handFace[i] = 12;
					break;
				case "K":
					handFace[i] = 13;
					break;
				case "A":
					handFace[i] = 14;
					break;				
			}
						
			handSuit[i] = getHandSuitIndex(cards[i].substring(1));
		}
		
		
		//Now sort each array
		Arrays.sort(handFace);
		Arrays.sort(handSuit);			
	}
	
	private static int getHandSuitIndex(String suit) {
		int index = -1;
		
		switch(suit) {
			case "s":
				index = 0;
				break;
			case "h":
				index = 1;
				break;
			case "d":
				index = 2;
				break;
			case "c":
				index = 3;
				break;
		}
		
		return index;
	}

	private static int countDeuces() {
		int count = 0;
		
		for(int i=0; i<handFace.length; i++) {
			if(handFace[i] == 2) {
				count++;
			} else {
				break;
			}
		}
		
		return count;
	}
	
	private static void evaluateCards(Hand hand, String[] handString) {
		//First check for groups: pair, 2 pair, trips, full house, 4 of a kind
		boolean hasSet = checkGroups(hand);
		
		//If no group exists or deuces wild being played, continue evaluation
		if((deuces == 0 && !hasSet) || (deuces>0 && rank<12)) {
			boolean straight = checkStraits(hand);
			boolean flush = checkFlush(hand, handSuit, handString, straight);
			
			if(straight && flush) {
				straightFlush(hand);
			} else if (!straight && !flush){ //just a high card
				if(rank < 2)
					setHighCard(hand);
			}
		}

	}
	
	private static boolean checkGroups(Hand hand) {
		int[] pairCounts = {1,1}; //Set default
		int[] pairFace = {0,0};
		int[] altCards = {0,0,0,0,0};
		
		boolean pairFound = false;
		boolean foundFirstGroup = false;
		boolean foundGroup = true;
		
		int groupNum = 0; //Max of two groups possible with 5 cards
		
		int checkRank = 0;
		String suffix = "";
		
		for(int i=deuces+1; i<handFace.length; i++) {
			if(handFace[i] == handFace[i-1]) {
				if(foundFirstGroup) {
					if(!pairFound) {
						groupNum++;
					}
				}
				
				foundFirstGroup = true;
				pairFound = true;
				
				pairCounts[groupNum]++;
				pairFace[groupNum] = handFace[i];
			} else { 
				pairFound = false;
			}
		}
		
		//Now see what we have
		if(deuces == 4) {
			checkRank = 12;  //4 Deuces
		} else 	if(pairCounts[0] > 1) {
			//Adjust for wild cards
			
			//5 of a kind
			if(checkRank == 0 && (deuces == 1 && pairCounts[0] == 4)
					|| (deuces == 2 && pairCounts[0] == 3)
					|| (deuces == 3 && pairCounts[0] == 2)) {
				checkRank = 10;
				handScore = handFace[handFace.length-1] * 5;
			}
			
			//4 of a Kind With wild?
			if(checkRank == 0 && (deuces == 1 && pairCounts[0] == 3) 
					|| (deuces == 2 && pairCounts[0] == 2)) {
				checkRank = 8;
				handScore = pairFace[0] * 4; 
			} 
					
			//4 of a Kind Without wild?
			if(checkRank == 0 && (deuces == 0 && pairCounts[0] == 4)) {
				checkRank = 8;
				handScore = pairFace[0] * 4;
			}

			//Full house with wild?
			if(checkRank == 0 && (deuces == 1 &&  pairCounts[0] == 2 && pairCounts[1] == 2)) {
				checkRank = 7;
				handScore = pairFace[1] * 3 + pairFace[0] * 2;
			}
			
			//Full house without wild?
			if(checkRank == 0 && deuces == 0  && (pairCounts[0] + pairCounts[1]) == 5) {
				checkRank = 7;
				handScore = pairFace[0] * pairCounts[0] + pairFace[1] * pairCounts[1];
			}
			
			//Trips with wild?
			if(checkRank == 0 && (deuces == 1 &&  pairCounts[0] == 2)) {
				checkRank = 4;
				handScore = pairFace[0] * 3;
			}
			
			//Trips without wild?
			if(checkRank == 0 && pairCounts[0] == 3) {
				checkRank = 4;
				handScore = pairFace[0] * 3;
			}
			
			//Two pair without wild (two pair will not occur with a wild)?
			if(checkRank == 0  && pairCounts[0] == 2 && pairCounts[1] == 2) {
				checkRank = 3;
				handScore = pairFace[0] * pairCounts[0] + pairFace[1] * pairCounts[1];
			}
			
			//One pair without wild?
			if(checkRank == 0 && pairCounts[0] == 2) {
				checkRank = 2;
				handScore = pairFace[0] * 2;
			}

		} else {  //pairCounts[0] = 1
			if(checkRank == 0) {
				if(deuces == 3) {
					pairFace[0] = handFace[handFace.length-1];  //Make 4 of a kind with high card
					checkRank = 8;
					handScore = handFace[handFace.length-1] * 4;
				} else if(deuces == 2) {
					pairFace[0] = handFace[handFace.length-1];  //Make 3 of a kind with high card
					checkRank = 4;
					handScore = handFace[handFace.length-1] * 3;
				} else if(deuces == 1) {
					pairFace[0] = handFace[handFace.length-1];  //Make 2 of a kind with high card
					checkRank = 2;
					handScore = handFace[handFace.length-1] * 2;
				}
			}
		}
		
		


		//Update the attributes
		rank = checkRank;
		hand.setHandRank(rank);
		
		if(pairFace[0] > 0) {
			suffix = cardFaces[pairFace[0] - 1] + "s";
			
			if(pairFace[1] > 0) {
				suffix = (pairFace[0] > pairFace[1])
						? suffix + " & " + cardFaces[pairFace[1] - 1] + "s"
						: cardFaces[pairFace[1] - 1] + "s & " +  suffix;
			}
		}
				
		int counter = 0;
		switch (rank) {
			case 2: //pair
				hand.setHandDescr("Pair " + suffix);
				for(int i=handFace.length-1; i>=deuces; i--) {
					if(handFace[i] != pairFace[0]) {
						altCards[counter] = handFace[i];
						counter++;
					}
				}
				break;
			case 3: // 2 pair
				hand.setHandDescr("Two Pair " + suffix);
				for(int i=handFace.length-1; i>=0; i--) {
					if(handFace[i] != pairFace[0] || handFace[i] != pairFace[1]) {
						altCards[counter] = handFace[i];
						counter++;
					}
				}
				break;
			case 4: //trips
				hand.setHandDescr("Three of a Kind " + suffix);
				for(int i=handFace.length-1; i>=deuces; i--) {
					if(handFace[i] != pairFace[0]) {
						altCards[counter] = handFace[i];
						counter++;
					}
				}
				break;
			case 7: //full house
				hand.setHandDescr("Full House " + suffix);
				break;
			case 8: //4 of a Kind
				hand.setHandDescr("Four of a Kind " + suffix);
				for(int i=handFace.length-1; i>=deuces; i--) {
					if(handFace[i] != pairFace[0]) {
						altCards[counter] = handFace[i];
						counter++;
					}
				}
				break;
			case 10:
				hand.setHandDescr("Five of a Kind " + suffix);
				break;
			case 12:
				hand.setHandDescr("Four Deuces" + suffix);
				break;				
			default: //no groups
				foundGroup = false;
		}
		
		hand.setAltCards(altCards);
		hand.setHandScore(handScore);
		
		return foundGroup;
	}

	private static boolean checkStraits(Hand hand) {
		boolean straight = true;
		boolean hasAce;
		aceTwo = false;
		
		//Check if ace exists, since it could be A, 2, ... or ...Q, K, A
		hasAce = (handFace[handFace.length-1] == 14) ? true : false;
		
		if(deuces == 0) {
			for(int i=0; i<handFace.length; i++) {
				if(i==0 && handFace[i]==2 && hasAce) {
					aceTwo = true;
					continue;
				} else if(i==handFace.length-1 && aceTwo) {
					//ignore since we handled this already
					continue;
				} else if(i != 0) {
					if(handFace[i] - handFace[i-1] == 1) {
						continue;
					} else {
						straight = false;
						break;
					}
				}			
			}
		} else {  //Deuces wild with 2s in the hand
			int[] nonWilds = new int[handFace.length-deuces];
			for(int i=deuces, j=0; i<handFace.length; i++, j++) {
				nonWilds[j] = handFace[i];
			}
			
			int deucesCount = deuces;
			for(int i=1; i<nonWilds.length; i++) {
				if(nonWilds[i] - nonWilds[i-1] > 1) {
					deucesCount -= nonWilds[i] - nonWilds[i-1] - 1;
				}
			}
			 
			straight = (deucesCount >=0) ? true : false;
			
			if(hasAce && !straight) {
//				int tempAce = nonWilds[nonWilds.length-1];
				for(int i=nonWilds.length-1; i>0; i--) {
					nonWilds[i] = nonWilds[i-1];
				}
				nonWilds[0] = 1;
				
				deucesCount = deuces;
				for(int i=1; i<nonWilds.length; i++) {
					if(nonWilds[i] - nonWilds[i-1] > 1) {
						deucesCount -= nonWilds[i] - nonWilds[i-1] - 1;
					}
				}
				 
				straight = (deucesCount >=0) ? true : false;
				aceTwo = straight ? true : false;
			}
		}
		
		//Update attributes
		if(straight && deuces == 0) {
			handScore = (!aceTwo) ? handFace[handFace.length-1] : 5;
		} else if(straight && deuces > 0) {
			if(rank < 8) {
				setWildStraightHandScore();
			}
		}
		
		if(straight && rank < 8) {
			hand.setHandDescr("Straight");
			hand.setHandRank(5);
			hand.setHandScore(handScore);							
		}
		
		return straight;
	}
	
	private static void setWildStraightHandScore() {
		int[] nonWilds = new int[handFace.length-deuces];
		for(int i=deuces, j=0; i<handFace.length; i++, j++) {
			nonWilds[j] = handFace[i];
		}
		
		int deucesCount = deuces;
		for(int i=1; i<nonWilds.length; i++) {
			if(nonWilds[i] - nonWilds[i-1] > 1) {
				deucesCount -= nonWilds[i] - nonWilds[i-1] - 1;
				if(deuces == 0) break;
			}
		}
		

		int tempHigh = handFace[handFace.length-1];
		if(aceTwo) {
			handScore = 5;
		} else {
			handScore = (tempHigh + deucesCount > 14) ? 14 : tempHigh + deucesCount;	
		}
	}

	private static boolean checkFlush(Hand hand, int[] handSuit, String[] cards, boolean straight) {
		boolean flush = true;
		int[] altCards = {0,0,0,0,0};

		if(deuces == 0) {
			for(int i=1; i<handSuit.length; i++) {
				if(handSuit[i] == handSuit[i-1]) {
					continue;
				} else {
					flush = false;
					break;
				}
			}			
		} else {  //Deuces wild with 2s in hand
			if((straight && rank < 9) || rank < 8) {
				String[] sortedCards = sortHandString(cards);
				for(int i=deuces+1; i<sortedCards.length; i++) {
					if(sortedCards[i].substring(1).equals(sortedCards[i-1].substring(1))) {
						continue;
					} else {
						flush = false;
						break;
					}
				}				
			} else {
				flush = false;
			}
		}
		
		//Update attributes
		String suitType;
		if(flush) {
			if(deuces == 0) {
				handScore = handFace[handFace.length-1];
				suitType = suits[handSuit[0]];
				for(int i=handFace.length-1, j=0; i>=0; i--, j++) {
					altCards[j] = handFace[i];
				}
			} else {
				int index = getHandSuitIndex(cards[cards.length-1].substring(1));
				suitType = suits[index];
				
				if(!straight) {
					handScore = handFace[handFace.length-1];

					int replaceCard = 14;
					for(int i=0; i<handFace.length; i++) {
						boolean cardReplaced = false;
						if(handFace[i] == 2) {
							//Loop to replace 2 with A, then K, then Q, etc/
							
							while(!cardReplaced) {
								for(int j=handFace.length-1; j>i; j--) {
									if(handFace[j] == replaceCard) {
										replaceCard--;
										break;
									} else {
										altCards[i] = replaceCard;
										cardReplaced = true;
										replaceCard--;
										break;
									}
								}
							}
						} else {
							altCards[i] = handFace[i];
						}
					}
				} 
				
				//Sort then reverse order when dealing with wilds
				Arrays.sort(altCards);
				for(int i=0, j=altCards.length-1; i<altCards.length/2; i++, j--) {
					int temp = altCards[j];
					altCards[j] = altCards[i];
					altCards[i] = temp;
				}
			}

			hand.setHandDescr("Flush " + suitType);
			hand.setHandRank(6);
			if(!straight) hand.setHandScore(handScore);
			hand.setAltCards(altCards);
		}
		
		return flush;
	}

	private static String[] sortHandString(String[] cards) {
		String[] sortedCards = new String[cards.length];
		
		for(int i=0; i<cards.length; i++) {
			sortedCards[i] = cards[i];
		}
		Arrays.sort(sortedCards);
		
		return sortedCards;
	}
	private static void straightFlush(Hand hand) {
		//First, default attributes to a straight flush
		hand.setHandDescr("Straight Flush");
		hand.setHandRank(9);
		
		if(deuces > 0) {
			setWildStraightHandScore();
			hand.setHandScore(handScore);
		}
		
		//Now check for royal flush
		if(deuces == 0) {
			if(handFace[0] == 10 && handFace[handFace.length - 1] == 14) {
				//Update attributes
				if(gameType == "DeucesWild") {
					hand.setHandDescr("Natural Royal Flush");
				} else {
					hand.setHandDescr("Royal Flush");					
				}
				
				hand.setHandRank(13);
			}			
		} else {
			if(handFace[deuces] >= 10) {
				hand.setHandDescr("Wild Royal Flush");
				hand.setHandRank(11);
				handScore = 14;
			}
		}
		
	}

	private static void setHighCard(Hand hand) {
		int[] altCards = {0,0,0,0,0};

		hand.setHandDescr("High Card " + cardFaces[handFace[handFace.length-1]-1]);
		hand.setHandRank(1);
		hand.setHandScore(handFace[handFace.length-1]);
		
		for(int i=handFace.length-1, j=0; i>=0; i--, j++) {
			altCards[j] = handFace[i];
		}

		hand.setAltCards(altCards);
	}
	
//=========================================================================================
//=========================================================================================
//The rest of the code is for determining a poker hand winner
	
	public static int[] evaluatePokerGame(Hand...hands) {
		int[] rankings = new int[hands.length];
		int[] handRanks = new int[hands.length];
		int[] handScores = new int[hands.length];
		int[][] altCards = new int[hands.length][hands[0].getCards().length];
				
		//Set default order based on order that was sent
		//Set the array for hand rankings
		for(int i=0; i<rankings.length; i++) {
			rankings[i] = i;
			handRanks[i] = hands[i].getHandRank();
			handScores[i] = hands[i].getHandScore();
			altCards[i] = hands[i].getAltCards();
		}

		//Now sort the arrays by handRank
		sortByHandRank(rankings, handRanks, handScores, altCards);
		
		
		//Now determine the results
		//Set up a new array for results
		//0 - lose, 1 - win, 2 - tie
		int[] results = new int[rankings.length];
		
		//Set results
		results[rankings[0]] = 1; //Assume first place ranking won
		for(int i=1; i<handRanks.length; i++) {
			if(handRanks[i] == handRanks[i-1]) {  //same rank, check score
				int handScoreCompare = handScores[i] > handScores[i-1] ? 1 : handScores[i] < handScores[i-1] ? -1 : 0;
				if(handScoreCompare == 0) { //we have a tie, check alt cards
					boolean tie = true;
					for(int j=0; j<altCards[i].length; j++) {
						if(altCards[i][j] == altCards[i-1][j]) {
							continue; //Still a tie
						} else {
							if(altCards[i][j] > altCards[i-1][j]) {
								results[rankings[i-1]] = 0;
								results[rankings[i]] = 1;								
							} else {
								results[rankings[i-1]] = 1;
								results[rankings[i]] = 0;
							}
							tie = false;
							break;
						}
					}
					if(tie) { //We have a tie
						results[rankings[i-1]] = 2;
						results[rankings[i]] = 2;
					} 
				} else if(handScoreCompare == 1){
					results[rankings[i-1]] = 0;
					results[rankings[i]] = 1;
				}
			} else {
				break; //No more ties
			}
		}
		
		return results;
	}
	
	private static void sortByHandRank(int[] rankings, int[] handRanks, int[] handScores, int[][] altCards) {
	    //Insertion Sort
		int unsortedRanking;
		int unsortedHandRank;
		int unsortedHandScore;
		int[] unsortedAltCards;
		
		int scan;
		
		for(int i=1; i<handRanks.length; i++) {
			unsortedHandRank = handRanks[i];
			unsortedRanking = rankings[i];
			unsortedHandScore = handScores[i];
			unsortedAltCards = altCards[i];

	        scan = i;

	        while(scan > 0 && handRanks[scan - 1] < unsortedHandRank) {
	        	handRanks[scan] = handRanks[scan-1];
	        	rankings[scan] = rankings[scan-1];
	        	handScores[scan] = handScores[scan-1];
	        	altCards[scan] = altCards[scan-1];

	            scan--;
	        }
	        
	        handRanks[scan] = unsortedHandRank;
	        rankings[scan] = unsortedRanking;
	        handScores[scan] = unsortedHandScore;
	        altCards[scan] = unsortedAltCards;
		}
	}		
}

