package com.kate.blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class Dealer {


    private ArrayList<Card> dealersHand = new ArrayList<>();


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

        for (int i = 0; i < numberOfCards; i++) {
            aHand.add(aDeck.get(i));
            aDeck.remove(i);

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
