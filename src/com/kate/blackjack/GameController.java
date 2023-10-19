package com.kate.blackjack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.InputMismatchException;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.*;

public class GameController {

    private Scanner newScanner;
    private Player player1;
    private Dealer dealer;
    private DeckOfCards deck;
    private final Integer blackjack = 21;
    private int currentRound;
    private final Integer betMin = 2;
    private final Integer betMax = 500;



    public void play() {
        for(int i = 0; i < 3; i++){
            System.out.println("Round " + currentRound);
            resolveCards(1  , deck, dealer, player1);
            checkForWinners(dealer.getDealersHand(), new ArrayList<>(Arrays.asList(player1.getPlayersHand())));
            currentRound++;
        }
    }

    public GameController() {
        currentRound = 1;
        dealer = new Dealer();
        deck = new DeckOfCards();
        dealer.shuffleDeck(deck.getDeck());
        player1 = new Player();
        newScanner = new Scanner(System.in);
        newPlayer();
        playersBet();
        play();
    }

    public GameController(String playerName, Integer playerBet) {
        currentRound = 1;
        dealer = new Dealer();
        deck = new DeckOfCards();
        dealer.shuffleDeck(deck.getDeck());
        player1 = new Player();
        player1.setName(playerName);
        player1.setBet(playerBet);
        play();
    }

    public Object validate(Object userInput) {

        if (userInput instanceof String) {
            while (!StringUtils.isAlpha((String)userInput)) {
                System.out.println("Please enter a valid name. Characters only.");
                System.out.println("Enter your name");
                userInput = newScanner.nextLine();
            }
        } else if (userInput instanceof Integer) {
            while (!(((Integer) userInput) >= betMin) || !(((Integer) userInput) <= betMax)) {
                System.out.println("Please enter a valid bet. Numbers 2 through 500 only.");
                System.out.println("Enter your bet");
                userInput = parseInteger(newScanner.nextLine());
            }
        } else if (userInput instanceof Boolean) {
            while (!StringUtils.isAlpha((String)userInput)) {
                System.out.println("Please enter a valid name. Characters only.");
                System.out.println("Enter your name");
                userInput = newScanner.nextLine();
            }
        } else {
            System.out.println("What the fuck");
        }

        return userInput;
    }


    public void newPlayer() {
        System.out.println("Enter your name");
        String userName = newScanner.nextLine();
        userName = (String)validate(userName);
        player1.setName(userName);
    }

    public void playersBet() {
        System.out.println("Enter your bet");
        Integer usersBet = parseInteger(newScanner.nextLine());
        usersBet = (Integer) validate(usersBet);
        player1.setBet(usersBet);
    }

    public void checkForWinners(ArrayList<Card> dealersHand, ArrayList<ArrayList<Card>> playersHands) {
        Integer total = 0;
        for (ArrayList<Card> hand: playersHands) {
            for (Card card: hand) {
                total = total + card.getCardValue();
            }
            if (total == blackjack) {
                youWon();
            } else if (total > 21) {
                youLose();
                for (int i = 0; i < hand.size(); i++){
                    hand.remove(hand.get(i));
                }
            } else {
                //round 3
            }

        }

        removeLosers(playersHands);

    }

    public void removeLosers(ArrayList<ArrayList<Card>> playersHands){
        for(int i = 0; i < playersHands.size(); i++)
        {
            if(playersHands.get(i).size() == 0){
                playersHands.remove(i);
            }
        }
        if(playersHands.size() == 0){
            dealerWins();
        }
    }

    public String hitOrStand() {
        System.out.println("Your move: hit or stand?");
        String playerMove = newScanner.nextLine();
        return playerMove;
    }


    public void resolveCards(Integer currentRound, DeckOfCards deck, Dealer dealer, Player player1){    //da fak is this name
        if(currentRound == 1){
            dealer.dealCards(player1.getPlayersHand(),deck.getDeck(), 2);
            dealer.dealCards(dealer.getDealersHand(),deck.getDeck(), 2);
        } else if (currentRound == 2) {

        }
    }

    public void dealerWins() {
        System.out.println("Dealer won!");
    }

    public void youWon() {
        System.out.println("You've won!");
    }

    public void youLose() {
        System.out.println("You've lost!");
    }

    public Integer parseInteger(String allegedInteger){
        try{
            return Integer.parseInt(allegedInteger);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

}
