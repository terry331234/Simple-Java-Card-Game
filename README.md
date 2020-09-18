# Simple Java Card Game
Coursework of COMP2396 Object-oriented programming and Java

# Rules
1. Both Dealer and Player are given 3 cards after setting the bet.
2. The player can change at most 2 cards before the dealer reveal its deck.
3. The one with the better deck win, determined by the rules below
	- A deck with more special cards (J,Q,K) is better.
	- If both deck has the same number of special cards, divide the sum of card face value by 10, the deck with the higher remainder is better.
	- If it is a draw, the dealer wins.
4. The game ends if the player run out of money.

# Compiling
`javac CardGame.java`