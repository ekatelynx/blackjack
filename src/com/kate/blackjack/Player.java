package com.kate.blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name;
    private Integer bet;
    private ArrayList<Card> playersHand = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getBet() {
        return bet;
    }

    public void setBet(Integer bet) {
        this.bet = bet;
    }

    public ArrayList<Card> getPlayersHand() {
        return playersHand;
    }

    public void setPlayersHand(ArrayList<Card> playersHand) {
        this.playersHand = playersHand;
    }
}
