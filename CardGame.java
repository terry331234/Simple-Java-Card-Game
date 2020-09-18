import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

/**
 * 
 * Includes the GUI and ActionListeners
 *
 */

public class CardGame {

	public final ImageIcon card_back = new ImageIcon("img/card_back.gif");

	int money = 100;
	int bet = 0;
	int cardReplaced = 0;
	Table table;

	JFrame frame;
	JLabel pCard1;
	JLabel pCard2;
	JLabel pCard3;
	JLabel dCard1;
	JLabel dCard2;
	JLabel dCard3;
	JButton replace_card1_button;
	JButton replace_card2_button;
	JButton replace_card3_button;
	JTextField bet_input;
	JButton start;
	JButton result;
	JLabel current_bet;
	JLabel money_text;
	JMenuItem exit;
	JMenuItem instruction;

	/**
	 * Construct the GUI
	 */
	public CardGame() {
		table = new Table();
		frame = new JFrame("A Simple Card Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(new BoxLayout(frame.getContentPane(), BoxLayout.Y_AXIS));
		frame.setSize(500, 500);

		JMenuBar menuBar = new JMenuBar();
		JMenu control = new JMenu("Control");
		exit = new JMenuItem("Exit");
		exit.addActionListener(new Exit());
		JMenu help = new JMenu("Help");
		instruction = new JMenuItem("Instruction");
		instruction.addActionListener(new Instruction());

		control.add(exit);
		help.add(instruction);
		menuBar.add(control);
		menuBar.add(help);
		frame.setJMenuBar(menuBar);

		JPanel dcards = new JPanel();
		dcards.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));
		JPanel pcards = new JPanel();
		pcards.setBorder(BorderFactory.createEmptyBorder(10, 0, 0, 0));

		pCard1 = new JLabel(card_back);
		pCard2 = new JLabel(card_back);
		pCard3 = new JLabel(card_back);
		dCard1 = new JLabel(card_back);
		dCard2 = new JLabel(card_back);
		dCard3 = new JLabel(card_back);

		dcards.add(dCard1);
		dcards.add(dCard2);
		dcards.add(dCard3);
		pcards.add(pCard1);
		pcards.add(pCard2);
		pcards.add(pCard3);

		JPanel replace_buttons = new JPanel();
		replace_buttons.setBorder(BorderFactory.createEmptyBorder(20, 0, 10, 0));
		replace_card1_button = new JButton("Replace Card 1");
		replace_card2_button = new JButton("Replace Card 2");
		replace_card3_button = new JButton("Replace Card 3");
		JButton replaceButtons[] = { replace_card1_button, replace_card2_button, replace_card3_button };
		for (JButton b : replaceButtons) {
			b.addActionListener(new ReplaceCard());
			b.setEnabled(false);
			replace_buttons.add(b);
		}

		JPanel bet_panel = new JPanel();
		bet_input = new JTextField(10);
		start = new JButton("Start");
		start.addActionListener(new Start());
		result = new JButton("Result");
		result.addActionListener(new Result());
		result.setEnabled(false);

		bet_panel.add(new JLabel("Bet: $"));
		bet_panel.add(bet_input);
		bet_panel.add(start);
		bet_panel.add(result);

		JPanel status = new JPanel();

		current_bet = new JLabel("Please place your bet!");
		money_text = new JLabel("Amount of money you have: $" + money);
		status.add(current_bet);
		status.add(money_text);

		frame.add(dcards);
		frame.add(pcards);
		frame.add(replace_buttons);
		frame.add(bet_panel);
		frame.add(status);
		frame.getRootPane().setDefaultButton(start);
		frame.setVisible(true);
	}

	class Start implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			double temp = Double.valueOf(bet_input.getText());
			if (temp <= 0 || temp % 1 != 0) {
				JOptionPane.showMessageDialog(frame, "WARNING: The bet you place must be a postive integer!",
						"Incorrect Input", JOptionPane.INFORMATION_MESSAGE);
				bet_input.setText("");
				bet_input.grabFocus();
				return;
			}
			bet = (int) temp;

			if (bet > money) {
				JOptionPane.showMessageDialog(frame, "You do not have enough money!", "Need more money!!",
						JOptionPane.INFORMATION_MESSAGE);
				bet_input.setText("");
				return;
			}
			cardReplaced = 0;
			current_bet.setText("Your current bet is: $" + bet);

			dCard1.setIcon(card_back);
			dCard2.setIcon(card_back);
			dCard3.setIcon(card_back);

			table.drawPlayerHand();
			pCard1.setIcon(table.getCard("player", 0).getIamge());
			pCard2.setIcon(table.getCard("player", 1).getIamge());
			pCard3.setIcon(table.getCard("player", 2).getIamge());

			table.drawDealerHand();
			replace_card1_button.setEnabled(true);
			replace_card2_button.setEnabled(true);
			replace_card3_button.setEnabled(true);
			start.setEnabled(false);
			result.setEnabled(true);
		}
	}

	class Result implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			dCard1.setIcon(table.getCard("dealer", 0).getIamge());
			dCard2.setIcon(table.getCard("dealer", 1).getIamge());
			dCard3.setIcon(table.getCard("dealer", 2).getIamge());

			if (table.playerWins()) {
				money += bet;
				JOptionPane.showMessageDialog(frame, "Congrauations! You win this round!", "You won!",
						JOptionPane.INFORMATION_MESSAGE);
			} else {
				money -= bet;
				JOptionPane.showMessageDialog(frame, "Sorry! The dealer win this round!", "You lose!",
						JOptionPane.INFORMATION_MESSAGE);
			}

			money_text.setText("Amount of money you have: $" + money);
			table.reset();
			start.setEnabled(true);
			result.setEnabled(false);

			if (money == 0) {
				JOptionPane.showMessageDialog(frame, "Game over!\nYou have no more money!\nPlease start a new game",
						"You are broke!", JOptionPane.INFORMATION_MESSAGE);
				start.setEnabled(false);
				bet_input.setEnabled(false);
			}

			replace_card1_button.setEnabled(false);
			replace_card2_button.setEnabled(false);
			replace_card3_button.setEnabled(false);
			current_bet.setText("Please place your bet!");
			bet_input.grabFocus();
		}
	}

	class ReplaceCard implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JLabel cards[] = { pCard1, pCard2, pCard3 };
			int cardIndex = 0;
			if (event.getSource() == replace_card1_button) {
				cardIndex = 0;
			}
			if (event.getSource() == replace_card2_button) {
				cardIndex = 1;
			}
			if (event.getSource() == replace_card3_button) {
				cardIndex = 2;
			}
			table.replaceCard(cardIndex);
			cardReplaced++;
			cards[cardIndex].setIcon(table.getCard("player", cardIndex).getIamge());
			if (cardReplaced == 2) {
				replace_card1_button.setEnabled(false);
				replace_card2_button.setEnabled(false);
				replace_card3_button.setEnabled(false);
			}
			((JButton) event.getSource()).setEnabled(false);
		}
	}

	class Exit implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			System.exit(0);
		}
	}

	class Instruction implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			JOptionPane.showMessageDialog(frame, "J, Q, K are regarded as special cards.\n"
					+ "Rule 1: The one with more special cards wins.\n"
					+ "Rule 2: If both have the same number of special cards, add the face values of the other card(s) and take the remainder after dividing the sum by 10. The one with a bigger remainder wins. (Note: Ace = 1).\n"
					+ "Rule 3: The dealer wins if both rule 1 and rule 2 cannot distinguish the winner.", "Instruction",
					JOptionPane.INFORMATION_MESSAGE);
		}
	}

	public static void main(String[] args) {
		new CardGame();
	}

}