package com.kate.blackjack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class Card {

    private String suit;
    private String rank;
    private Integer value;
    private static Set<String> acceptableSuits = new HashSet<>(Arrays.asList("Spades", "Clubs", "Diamonds","Hearts"));
    private static Set<String> acceptableRanks = new HashSet<>(Arrays.asList("2","3","4","5","6","7","8","9","10","A","J","Q","K"));


    private Card(String suit, String rank) {
        this.suit = suit;
        this.rank = rank;
        setCardValue(rank);
    }

    public void setCardValue(String rank) {
        switch (rank) {
            case "J": case "Q": case "K":
                this.value = 10;
                break;
            case "2": case "3": case "4": case "5": case "6": case "7": case "8": case "9": case "10":
                this.value = Integer.parseInt(rank);
                break;
            case "A":
                this.value = 1;
                break;
            default:
                System.out.println("Error: Unknown rank.");
        }
    }

    public Integer getCardValue() {
        return value;
    }


    public static Card createCard(String suit, String rank) {

        if (acceptableSuits.contains(suit) && acceptableRanks.contains(rank)) {

            Card newCard = new Card(suit, rank);
            return newCard;
        } else {
            System.out.println("Invalid suit or rank.");
            return null;
        }
    }

    public String getSuit() {
        return suit;
    }

    public String getRank() {
        return rank;
    }

    public static Set<String> getAcceptableSuits() {
        return acceptableSuits;
    }

    public static Set<String> getAcceptableRanks() {
        return acceptableRanks;
    }

    @Override
    public String toString() {
        return "Card{" + "suit = '" + suit + '\'' + ", rank = '" + rank + '\'' + '}' + "\n";
    }
}
