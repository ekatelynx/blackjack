package com.kate.blackjack;

import java.util.ArrayList;

public class DeckOfCards {

    ArrayList<Card> deck;

    public DeckOfCards () {
        createDeck();
    }
    public ArrayList<Card> createDeck() {
        deck = new ArrayList<>();

        for (String suit: Card.getAcceptableSuits()) {
            for (String rank: Card.getAcceptableRanks()) {
                Card newCard = Card.createCard(suit, rank);
                deck.add(newCard);
            }
        }

        return deck;
    }




    public void setDeck(ArrayList<Card> deck) {

        this.deck = deck;
    }

    public ArrayList<Card> getDeck() {

        return deck;
    }

    @Override
    public String toString() {

        return "DeckOfCards{" + deck + "}\n";
    }
}
