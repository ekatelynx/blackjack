package com.kate.blackjack;

import java.util.ArrayList;
import java.util.Scanner;

public class Player {

    private String name;
    private Integer bet;
    private ArrayList<Integer> playersHand = new ArrayList<>();

    Scanner newScanner = new Scanner(System.in);

    public void newPlayer() {
        System.out.println("Enter your name");
        String userName = newScanner.nextLine();
    }

    public void playersBet() {
        System.out.println("Enter your bet");
        String usersBet = newScanner.nextLine();
    }

}
