import java.util.ArrayList;

/**
 * Contain the card deck, player and dealer cards
 *
 */
public class Table {
	private ArrayList<Card> deck = new ArrayList<Card>();
	private ArrayList<Card> playerHand = new ArrayList<Card>();
	private ArrayList<Card> dealerHand = new ArrayList<Card>();

	/**
	 * Return a specific card
	 * 
	 * @param person
	 * @param index
	 * @return a Card
	 */
	public Card getCard(String person, int index) {
		if (person == "dealer") {
			return dealerHand.get(index);
		}
		return playerHand.get(index);
	}

	/**
	 * Create card deck
	 */
	Table() {
		makeDeck();
	}

	private void makeDeck() {
		deck.clear();
		for (int i = 1; i < 5; i++) {
			for (int j = 1; j < 14; j++) {
				this.deck.add(new Card(j, i));
			}
		}
	}

	private Card randomCard() {
		int random = (int) (Math.random() * deck.size());
		Card cardDraw = deck.get(random);
		deck.remove(random);
		return cardDraw;
	}

	/**
	 * Draw 3 Cards for player
	 */
	public void drawPlayerHand() {
		playerHand.clear();
		for (int i = 0; i < 3; i++) {
			playerHand.add(randomCard());
		}
	}

	/**
	 * Draw 3 Cards for dealer
	 */
	public void drawDealerHand() {
		dealerHand.clear();
		for (int i = 0; i < 3; i++) {
			dealerHand.add(randomCard());
		}
	}

	/**
	 * Replace a specific player card
	 * 
	 * @param index
	 */
	public void replaceCard(int index) {
		playerHand.set(index, randomCard());
	}

	/**
	 * Reset the deck and clear player and dealer cards
	 */
	public void reset() {
		makeDeck();
		dealerHand.clear();
		playerHand.clear();
	}

	/**
	 * Return if the player won
	 */
	public boolean playerWins() {
		int playerSpecialCardCount = 0;
		int dealerSpecialCardCount = 0;
		int playerNumCardSum = 0;
		int dealerNumCardSum = 0;

		for (Card card : dealerHand) {
			if (card.number > 10) {
				dealerSpecialCardCount++;
			} else {
				dealerNumCardSum += card.number;
			}
		}
		for (Card card : playerHand) {
			if (card.number > 10) {
				playerSpecialCardCount++;
			} else {
				playerNumCardSum += card.number;
			}
		}

		if (playerSpecialCardCount > dealerSpecialCardCount) {
			return true;
		}

		if (playerSpecialCardCount < dealerSpecialCardCount) {
			return false;
		}

		if (playerNumCardSum % 10 > dealerNumCardSum % 10) {
			return true;
		}

		return false;
	}

}
