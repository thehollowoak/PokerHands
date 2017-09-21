//PokerHands
package PokerHands;
import java.util.*;

public class PokerHands {
	public static void main(String[] args) {
		// accepts args of the style "Player1: VS VS VS VS VS  Player2: VS VS VS VS VS" 
		// or "Player1: VS VS VS VS VS" "Player2: VS VS VS VS VS"
		// or if no args, makes random hands
		if (args.length > 0) {
			String[] hands;
			if (args.length == 1)
				hands = args[0].split("  ");
			else 
				hands = args;
			Hand hand1 = new Hand(hands[0]);
			Hand hand2 = new Hand(hands[1]);
			System.out.println(hand1);
			System.out.println(hand2);
			System.out.println(compareHands(hand1, hand2));
			return;
		}
		
		// make two random hands to compare
		Random rand = new Random();
		// use set to insure no duplicate cards
		Set<Card> h1 = new HashSet<Card>();
		// Add 5 unique cards to the set
		while (h1.size() < 5) {
			h1.add(new Card(rand.nextInt(13) + 2, suits[rand.nextInt(4)]));
		}
		Set<Card> h2 = new HashSet<Card>();
		Card c1;
		// add 5 cards, none matching cards from the previous hand
		whileLoop:
		while (h2.size() < 5) {
			c1 = new Card(rand.nextInt(13) + 2, suits[rand.nextInt(4)]);
			// check if new card matches any in the previous hand
			for (Card c2 : h1) {
				// if it matches, continue the while loop to get a new card
				if (c1.equals(c2))
					continue whileLoop;
			}
			h2.add(c1);
		}
		// change the sets to strings (without the brackets) to make a new Hand
		Hand hand1 = new Hand("Black", h1.toString().substring(1,19));
		Hand hand2 = new Hand("White", h2.toString().substring(1,19));
		System.out.println(hand1);
		System.out.println(hand2);
		System.out.println(compareHands(hand1, hand2));
	}
	
	// to make random cards
	public static char[] suits = {'S', 'H', 'D', 'C'};
	
	// compares two poker hands and returns a string declaring the winner
	public static String compareHands(Hand h1, Hand h2) {
		Hand winner = h1;
		// if their ranks are the same, compare cards in order to find the highest
		if (h1.rank == h2.rank) {
			for (int i = 0; i < 5; i++) {
				// if the cards are not equal, winner is the higher one, and break to stop comparing
				if (h1.cards[i].value != h2.cards[i].value) {
					winner = (h1.cards[i].value > h2.cards[i].value ? h1 : h2);
					break;
				}
				// if we get to i=4 without breaking, then all 5 cards are the same value, so they tie
				if (i == 4) {
					return ("Tie.");
				}
			}
		}
		// otherwise winner is the higher rank
		else
			winner = (h1.rank > h2.rank ? h1 : h2);
		
		return (winner.player + " wins. - with " + winner.rankString());
	}
	
	// compares two poker hands, accepts strings instead of hands
	public static String compareHands(String h1, String h2) {
		Hand hand1 = new Hand(h1);
		Hand hand2 = new Hand(h2);
		return compareHands(hand1, hand2);
	}
}

// holds five cards, their poker rank, and the player it belongs to
class Hand {
	protected String player;
	protected int rank = 0;
	static String[] rankName = {"High Card", "Pair", "Two Pairs", "Three of a Kind", "Straight", "Flush", "Full House", "Four of a Kind", "Straight Flush"};
	protected Card[] cards = new Card[5];
	
	// accepts input in the style of "Player: VS VS VS VS VS", where VS is the value and suit of a card
	Hand (String input) {
		String[] part = input.split(": ");
		player = part[0];
		setCards(part[1].split(" "));
	}
	// accepts input in two strings, first the player's name and second card strings separated by commas and spaces
	Hand (String playerName, String cardString) {
		player = playerName;
		// make Card objects from the strings and store them in cards
		String[] part = cardString.split(", ");
		setCards(part);
	}
	
	// make Card objects from strings and store them in cards
	public void setCards(String[] part) {
		for (int i = 0; i < 5; i++) {
			cards[i] = new Card(part[i]);
		}
		// sort cards based on their value, highest first
		Arrays.sort(cards, new CardValue());
		findRank();
	}
	
	// figures out the rank for the hand
	private void findRank() {
		// check for straight
		boolean straight = true;
		int compareValue = cards[0].value;
		for (Card c : cards) {
			if (c.value != compareValue)
				straight = false;
			compareValue = c.value - 1;
		}
		// check for flush
		char s = cards[0].suit;
		boolean flush = true;
		for (Card c : cards) {
			if (c.suit != s)
				flush = false;
		}
		// store straight/flush and return
		if (straight) {
			if (flush)
				rank = 8;
			else
				rank = 4;
			return;
		}
		else if (flush) {
			rank = 5;
			return;
		}
		
		// check for sets
		// for each card that matches another, add to its set
		for (Card first : cards) {
			for (Card second : cards) {
				if (first.value == second.value)
					first.set++;
			}
		}
		// go through cards to find highest sets
		int set2 = 0;
		boolean set3 = false;
		for (Card c : cards) {
			switch (c.set) {
				// if theres a 4, set rank at 4 of a kind, sort by set, and return
				case 4: rank = 7;
						Arrays.sort(cards, new CardSet());
						return;
				// if theres a 3, set set3 to true and continue, in case of full house
				case 3: set3 = true;
						break;
				// count up how many pairs we have, in case of two pair
				case 2: ++set2;
						break;
			}
		}
		// check for 3 of a kind and full house
		if (set3) {
			if (set2 == 2)
				rank = 6;
			else
				rank = 3;
			Arrays.sort(cards, new CardSet());
			return;
		}
		// check for two pair and pair
		if (set2 > 1) {
			if (set2 == 4)
				rank = 2;
			else
				rank = 1;
			Arrays.sort(cards, new CardSet());
		}
		// otherwise rank is already 0 (high card)
	}
	
	static String[] faceName = {"Jack", "Queen", "King", "Ace"};
	
	// returns the rank's name and values
	public String rankString() {
		// use stringbuiler
		StringBuilder sb = new StringBuilder();
		// start with the rank's name
		sb.append(rankName[rank]);
		sb.append(": ");
		// add the value of the high card or largest set
		if (cards[0].value > 10) {
			sb.append(faceName[cards[0].value-11]);
		}
		else
			sb.append(cards[0].value);
		// if its a two pair or full house, give the value of the second set
		switch (rank) {
			case 2:
			case 6: sb.append(" over ");
					if (cards[3].value > 10) {
						sb.append(faceName[cards[3].value-11]);
					}
					else
						sb.append(cards[3].value);
					break;
		}
		// return the string
		return sb.toString();
	}
	
	// prints in the style "Player: VS VS VS VS VS"
	public String toString() {
		StringBuilder sb = new StringBuilder(); 
		sb.append(player);
		sb.append(":");
		for (Card c : cards) {
			sb.append(" ");
			sb.append(c);
		}
		return sb.toString();
	}
}

// holds the value and suit of a single card
class Card {
	protected int value; // values are stored as ints so they can easily be compared for which ones higher
	protected char suit;
	static char[] faceValue = {'T', 'J', 'Q', 'K', 'A'};
	protected int set = 0; // stores if the card is part of a set
	
	// initializes a card with a number and suit
	Card (int v, char s) {
		value = v;
		suit = s;
	}
	// initializes a card with a face value and suit
	Card (char v, char s) {
		// call Card(int, char) to initialize v if v is a number
		this(Character.getNumericValue(v), s);
		// if v is a letter, change it to the correct number
		switch (v) {
			case 'T': value = 10;
					  break;
			case 'J': value = 11;
					  break;
			case 'Q': value = 12;
					  break;
			case 'K': value = 13;
					  break;
			case 'A': value = 14;
					  break;
		}
	}
	// initializes a card from a string
	Card (String str) {
		// turn the string into chars
		this(str.charAt(0), str.charAt(1));
	}
	
	// prints out Card as ValueSuit
	public String toString() {
		if (value > 9)
			return "" + faceValue[value-10] + suit;
		return "" + value + suit;
	}
	// override equals and hashCode to prevent multiples of the same card in a set
	@Override
	public boolean equals(Object other) {
		if (this == other)
			return true;
		if (other == null) 
			return false;
		if (getClass() != other.getClass())
			return false;
		Card c = (Card) other;
		return (value == c.value && suit == c.suit);
	}
	@Override
	public int hashCode() {
		return value;
	}
}

// compares Cards based on their values
class CardValue implements Comparator<Card> {
	public int compare(Card c1, Card c2) {
		return (c1.value > c2.value ? -1 : (c1.value == c2.value ? 0 : 1));
	}
} 

// compares Cards based on their sets (if set is the same, orders by value)
class CardSet implements Comparator<Card> {
	public int compare(Card c1, Card c2) {
		return (c1.set > c2.set ? -1 : (c1.set == c2.set ? (c1.value > c2.value ? -1 : (c1.value == c2.value ? 0 : 1)) : 1));
	}
} 