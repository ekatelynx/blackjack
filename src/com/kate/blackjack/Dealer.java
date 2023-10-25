package com.kate.blackjack;

import java.util.*;

public class Dealer {


    private ArrayList<Card> dealersHand = new ArrayList<>();
    private String mode = "testing";
    private boolean dealThis = true;


    Scanner newScanner = new Scanner(System.in);
    Random rand = new Random();

    private void collectBets() {

    }


/*

    SHUFFLE METHOD USING FOR LOOP

*/


    public void shuffleDeck(ArrayList<Card> deck) {
        for (int i = 0; i < deck.size(); i++) {

            Card tempVar = deck.get(i);
            int nextRandom = rand.nextInt(deck.size());
            deck.set(i, deck.get(nextRandom));
            deck.set(nextRandom, tempVar);
        }
    }


/*

    USING FOR EACH LOOP

*/

    public void shuffleDeckWithForEach(ArrayList<Card> deck) {
        for(Card card : deck) {

           Card tempVar = card;
           int nextRandom = rand.nextInt(deck.size());
           card=deck.get(nextRandom);
           deck.set(nextRandom, tempVar);
        }
    }


    public ArrayList<Card> dealCards(ArrayList<Card> aHand, ArrayList<Card> aDeck, Integer numberOfCards) {

        if (mode == "testing" && dealThis == true) {

            ArrayList<Card> testDeck = new ArrayList<>(Arrays.asList(Card.createCard("Diamonds", "A"), Card.createCard("Diamonds", "2")));
            for (int i = 0; i < numberOfCards;i++) {
                aHand.add(testDeck.get(0));
                testDeck.remove(0);
            }
            dealThis = false;
        }
        else{
            for (int i = 0; i < numberOfCards; i++) {
                aHand.add(aDeck.get(0));
                aDeck.remove(0);
            }
        }

        System.out.println(aHand);
        return aHand;
    }




    public ArrayList<Card> getDealersHand() {
        return dealersHand;
    }

    public void setDealersHand(ArrayList<Card> dealersHand) {
        this.dealersHand = dealersHand;
    }
}
