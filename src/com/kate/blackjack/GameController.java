package com.kate.blackjack;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import org.apache.commons.lang3.StringUtils;

public class GameController {

    private Scanner newScanner;
    private Player player1;
    private Dealer dealer;
    private DeckOfCards deck;
    private final Integer blackjack = 21;
    private int currentRound;
    private final Integer betMin = 2;
    private final Integer betMax = 500;
    boolean displayCurrentHand = true;



    public void play() {
        for(int i = 0; i < 3; i++){
            System.out.println("Round " + currentRound);
            while (resolveCards(currentRound  , deck, dealer, player1)) {
                checkTheStateOfTheGame(dealer.getDealersHand(), new ArrayList<>(Arrays.asList(player1)));
            }
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

    public Boolean hitOrStand() {
        System.out.println("Your move: do you want to hit?");
        Boolean playerMove = parseBoolean(newScanner.nextLine());
        return playerMove;
    }


//  CALCULATING THE HAND VALUE

    public Integer calculateHandValue(ArrayList<Card> hand) {
        Integer total = 0;
        for (Card card: hand) {
            total = total + card.getCardValue();
        }
        return total;
    }


//  DEALING THE CORRECT NUMBER OF CARDS

    public Boolean resolveCards(Integer currentRound, DeckOfCards deck, Dealer dealer, Player player1){//da fak is this name
        ArrayList<Card> playersHand = player1.getPlayersHand();
        ArrayList<Card> dealersHand = dealer.getDealersHand();
        ArrayList<Card> shuffledDeck = deck.getDeck();
        if(currentRound == 1){
            dealer.dealCards(playersHand,shuffledDeck, 2);
            dealer.dealCards(dealersHand,shuffledDeck, 2);
            //TODO: check if A is 11 or 1 - initial evaluation
            checkTheStateOfTheGame(dealer.getDealersHand(), new ArrayList<>(Arrays.asList(player1)));
            return false;
        } else if (currentRound == 2) {
            if(displayCurrentHand) {
                displayPlayersHand(playersHand);
                displayCurrentHand = false;
            }
            Boolean playerResponse = hitOrStand();
            if (playerResponse) {
                dealer.dealCards(playersHand, shuffledDeck, 1);
                //TODO: check if A is 11 or 1 - ask a player
                if (calculateHandValue(playersHand) < blackjack) {
                    displayPlayersHand(playersHand);
                }
                return true;
            } else {
                return false;
            }

        } else if (currentRound == 3) {
            if (calculateHandValue(dealersHand) < 17) {
                dealer.dealCards(dealersHand,shuffledDeck, 1);
                //TODO: check if A is 11 or 1 - dealer logic
                return true;
            } else {
                checkTheStateOfTheGame(dealer.getDealersHand(), new ArrayList<>(Arrays.asList(player1)));
                return false;
            }
        } else {
            return null;
        }
    }


//   CHECKING THE STATE OF THE GAME


    public void checkTheStateOfTheGame(ArrayList<Card> dealersHand, ArrayList<Player> players) {
        Integer total;
        Integer dealersTotal = calculateHandValue(dealersHand);
        for (Player player: players) {
            ArrayList<Card> hand = player.getPlayersHand();
            total = calculateHandValue(hand);
            if (total == blackjack) {
                youWon(player);
                deleteHand(hand);
            } else if (total > 21) {
                youLost(player);
                deleteHand(hand);
            } else if (dealersTotal == blackjack ) {
                youLost(player);
                deleteHand(hand);
            } else if (currentRound == 3) {
                if  (dealersTotal > 21) {
                    youWon(player);
                    deleteHand(hand);
                } else if (total > dealersTotal) {
                    youWon(player);
                    deleteHand(hand);
                } else if (total < dealersTotal) {
                    youLost(player);
                    deleteHand(hand);
                } else if (total == dealersTotal) {
                    itsATie(hand);
                }
            }

        }
        System.out.println("Dealer's hand value: " + calculateHandValue(dealersHand));
        removePlayersWithEmptyHands(players);
        checkIfDealerWins(players);
    }

    public void displayPlayersHand(ArrayList<Card> hand) {
        System.out.println("Current hand value: " + calculateHandValue(hand));
    }

    public void deleteHand(ArrayList<Card> hand) {
        displayPlayersHand(hand);
        int stableHandSize = hand.size();
        for (int i = 0; i < stableHandSize; i++){
            hand.remove(hand.get(0));
        }
    }

    public void removePlayersWithEmptyHands(ArrayList<Player> players){
        for(Player player : players)
        {
            if(player.getPlayersHand().size() == 0){
                player.setPlayersHand(null);
            }
        }
    }

    public void checkIfDealerWins(ArrayList<Player> players){
        ArrayList<Boolean> isHandEmpty = new ArrayList<>();
        ArrayList<Boolean> didAnyoneLose = new ArrayList<>();
        for (Player player : players)
        {
            if (!(null == player.getPlayersHand()))
            {
                isHandEmpty.add(false);
            }
            else {
                isHandEmpty.add(true);
            }

            if (player.getDidIWin() == false)
            {
                didAnyoneLose.add(true);
            }
            else{
                didAnyoneLose.add(false);
            }

        }

        boolean gameOver = true;
        for (boolean result : isHandEmpty){
            if (result == false) {
                gameOver = false;
            }
        }

        if (gameOver)
        {
            boolean anyLosers = false;
            for (boolean result : didAnyoneLose)
            {
                if(result) {
                    anyLosers = true;
                }
            }

            if(anyLosers){
                dealerWins();
                System.exit(0);
            }
            System.exit(0);
        }
    }


    public void dealerWins() {
        System.out.println("Dealer won!");
    }

    public void youWon(Player player) {
        System.out.println("You've won!");
        player.setDidIWin(true);
    }

    public void youLost(Player player) {
        System.out.println("You've lost!");
        player.setDidIWin(false);
    }

    public void itsATie(ArrayList<Card> hand) {
        System.out.println("It's a tie!");
        displayPlayersHand(hand);
    }

//   VALIDATING THE USER INPUT


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
            while (userInput != null) {
                System.out.println("Please enter a valid input. Y/yes or N/no.");
                System.out.println("Your move: do you want to hit?");
                userInput = parseBoolean(newScanner.nextLine());
            }
        } else {
            System.out.println("Unknown input.");
        }

        return userInput;
    }


    public Integer parseInteger(String allegedInteger){
        try{
            return Integer.parseInt(allegedInteger);
        } catch(NumberFormatException e) {
            return 0;
        }
    }

    public Boolean parseBoolean(String allegedBoolean) {
        try {
            if (allegedBoolean.startsWith("y") || allegedBoolean.startsWith("Y") || allegedBoolean.equalsIgnoreCase("yes")) {
                return true;
            } else if (allegedBoolean.startsWith("n") || allegedBoolean.startsWith("N") || allegedBoolean.equalsIgnoreCase("no")) {
                return false;
            } else {
                return null;
            }
        } catch(Exception e) {
            return null;
        }
    }

}
