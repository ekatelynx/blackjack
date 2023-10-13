package com.kate.blackjack;

import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.Collections;

public class Dealer {

    private Integer betMin;
    private Integer betMax;
    private ArrayList<Integer> dealersHand = new ArrayList<>();

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

    public void shuffleDeckWithForEach(ArrayList deck) {
        for(Object card : deck) {

           Object tempVar = card;
           int nextRandom = rand.nextInt(deck.size());
           card=deck.get(nextRandom);
           deck.set(nextRandom, tempVar);
        }
    }


    private void dealCards() {

    }



    private void checkForNaturals() {

    }

    public void playersMove() {
        System.out.println("Your move: hit or stand?");
        String usersMove = newScanner.nextLine();
    }


}
