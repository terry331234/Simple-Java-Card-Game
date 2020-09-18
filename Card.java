import javax.swing.ImageIcon;

/**
 * Represent a card
 *
 */
public class Card {

	int number;
	int suit;

	/**
	 * Construct a Card
	 * 
	 * @param number
	 * @param suit
	 */
	Card(int number, int suit) {
		this.number = number;
		this.suit = suit;
	}

	/**
	 * Return the image of the card
	 * 
	 * @return The ImageIcon of the card
	 */
	public ImageIcon getIamge() {
		return new ImageIcon("img/card_" + suit + number + ".gif");
	}
}
