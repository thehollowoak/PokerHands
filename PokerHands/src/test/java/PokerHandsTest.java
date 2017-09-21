// PokerHandsTest
package PokerHands;

import org.junit.Test;
import static org.junit.Assert.*;

public class PokerHandsTest {
    @Test public void testCompareHands() {
        PokerHands testObject = new PokerHands();
		
		// Test Straight Flush
        assertEquals("Straight Flush with Ace wins to Straight Flush with King", "Black wins. - with Straight Flush: Ace", testObject.compareHands("Black: AH KH QH JH TH", "White: KD QD JD TD 9D"));
		
        assertEquals("Straight Flush with Ace ties to Straight Flush with Ace", "Tie.", testObject.compareHands("Black: AH KH QH JH TH", "White: KD QD JD TD AD"));
		
        assertEquals("Straight Flush wins to Four of a Kind", "Black wins. - with Straight Flush: Queen", testObject.compareHands("White: KD KH JD KS KC", "Black: JH 8H QH 9H TH"));
		
		// Test Four of a Kind
        assertEquals("Four of a Kind with 5 wins to Four of a Kind with 2", "Black wins. - with Four of a Kind: 5", testObject.compareHands("Black: 9S 5S 5D 5C 5H", "White: 2D 2S 2C KS 2H"));
		
        assertEquals("Four of a Kind wins to Full House", "White wins. - with Four of a Kind: 2", testObject.compareHands("Black: KS KH KD 5C 5H", "White: 2D 2S 2C KS 2H"));
		
		// Test Full House
        assertEquals("Full House with 9 wins to Full House with 8", "Black wins. - with Full House: 9 over 5", testObject.compareHands("Black: 9S 5S 9D 9C 5H", "White: KD 8S 8C KS 8H"));
		
        assertEquals("Full House wins to Flush", "Black wins. - with Full House: 5 over 9", testObject.compareHands("White: KC 8C 9C QC 6C", "Black: 9S 5S 5D 9D 5H"));
		
		// Test Flush
        assertEquals("Flush with high 10 wins to Flush with high 8", "Black wins. - with Flush: 10", testObject.compareHands("Black: 9S 5S TS 4S 2S", "White: 2D 8D 4D 3D 6D"));
		
        assertEquals("Straight Flush wins to Flush", "White wins. - with Straight Flush: King", testObject.compareHands("Black: JH KH QH JH TH", "White: KD QD JD TD 9D"));
		
		// Test Straight
        assertEquals("Straight with high King ties to Straight with high King", "Tie.", testObject.compareHands("White: KD QC JD TS 9D", "Black: 9S KH QH JC TH"));
		
        assertEquals("Flush wins to Straight", "White wins. - with Flush: 8", testObject.compareHands("Black: 9S 8H TS JS 7H", "White: 2D 8D 4D 3D 6D"));
		
		// Test Three of a Kind
		assertEquals("Three of a Kind wins to Two Pair", "Black wins. - with Three of a Kind: Ace", testObject.compareHands("Black: AC AH AS 9S 8S", "White: 8C 8D KS QS 3H"));
		
		assertEquals("Three of a Kind with Queens wins to Three of a Kind with Jacks", "Black wins. - with Three of a Kind: Queen", testObject.compareHands("White: JS JC JD TS AH", "Black: QS KH QH QC TH"));
		
		// Test Two Pairs
		assertEquals("Two Pairs ties to Two Pairs with same values", "Tie.", testObject.compareHands("White: TD JC JD TS QD", "Black: JS JH QH TC TH"));
		
		assertEquals("Two Pairs with 7 8 9 wins to Two Pairs with 7 8 6", "Black wins. - with Two Pairs: 8 over 7", testObject.compareHands("White: 7S 8C 7D 6S 8H", "Black: 8S 7H 9H 7C 8D"));
		
		// Test Pair
		assertEquals("Pair with 5 wins to Pair with 3", "Black wins. - with Pair: 5", testObject.compareHands("Black: 5H 5S QD 4H 2H", "White: 8S 7H 9H 3C 3D"));
		
		assertEquals("Pair that ties, high card wins", "White wins. - with Pair: 8", testObject.compareHands("Black: 8H 8S QD 4H 2H", "White: 8C 8D AH JS 4C"));
		
		// Test High Card
		assertEquals("High Card that ties, next high card wins", "Black wins. - with High Card: Ace", testObject.compareHands("White: AC 9S 6C 5D 3S", "Black: AD QH TC 7S 2H"));
    }
}
