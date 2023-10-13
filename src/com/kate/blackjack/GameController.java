package com.kate.blackjack;

public class GameController {

        public GameController() {

//      Creating a dealer, a deck and shuffling the deck

            Dealer newDealer = new Dealer();
            DeckOfCards newDeck = new DeckOfCards();
            newDealer.shuffleDeck(newDeck.getDeck());


            System.out.println(newDeck.getDeck());
        }

}
