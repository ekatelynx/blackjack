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

    public void resettingTheGame() {
        Boolean reset = playAgain();
        if (reset) {
            currentRound = 1;
            deck = new DeckOfCards();
            deleteHand(dealer.getDealersHand());
            dealer.shuffleDeck(deck.getDeck());
            player1.setPlayersHand(new ArrayList<>());
            player1.setDidIWin(false);
            playersBet();
            play();
        }
        else {
            System.exit(0);
        }
    }

    public Boolean playAgain() {
        System.out.println("Do you want to play again?");
        Boolean playerResponse = parseBoolean(newScanner.nextLine());
        playerResponse = (Boolean)validate(playerResponse);
        return playerResponse;
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
        playerMove = (Boolean)validate(playerMove);
        return playerMove;
    }

    public Integer oneOrEleven() {
        System.out.println("Set the Ace value: 1 or 11.");
        Integer aceValue = parseInteger(newScanner.nextLine());
        aceValue = (Integer) validateAce(aceValue);
        return aceValue;
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
            if (playersHand.get(0).getRank() == "A" && playersHand.get(1).getRank() == "A") {
                playersHand.get(0).setCardValue("1");
            }
            if (dealersHand.get(0).getRank() == "A" && dealersHand.get(1).getRank() == "A") {
                dealersHand.get(0).setCardValue("1");
            }
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
                for (Card card: playersHand) {
                    if (card.getRank() == "A" && card.getCardValue() == 11 && card.isHasAceChangedBefore() == false && calculateHandValue(playersHand) > blackjack) {
                        System.out.println("Swapping the value of your Ace from 11 to 1 to avoid busting.");
                        card.setHasAceChangedBefore(true);
                        card.setCardValue("1");
                    } else if (card.getRank() == "A" && card.getCardValue() == 11 && card.isHasAceChangedBefore() == false) {
                        checkTheStateOfTheGame(dealer.getDealersHand(), new ArrayList<>(Arrays.asList(player1)));
                        System.out.println("You've got an Ace");
                        displayPlayersHand(playersHand);
                        Integer newAce = oneOrEleven();
                        String newAceValue = String.valueOf(newAce);
                        card.setHasAceChangedBefore(true);
                        card.setCardValue(newAceValue);
                    }
                }
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
                for (Card card: playersHand) {
                    if(card.getRank() == "A" && card.getCardValue() == 11 && card.isHasAceChangedBefore() == false && calculateHandValue(dealersHand) > blackjack){
                        System.out.println("Swapping the value of your Ace from 11 to 1 to avoid busting.");
                        card.setHasAceChangedBefore(true);
                        card.setCardValue("1");
                    }
                }
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
                settlement();
                deleteHand(hand);
            } else if (total > 21) {
                youLost(player);
                settlement();
                deleteHand(hand);
            } else if (dealersTotal == blackjack ) {
                youLost(player);
                settlement();
                deleteHand(hand);
            } else if (currentRound == 3) {
                if  (dealersTotal > 21) {
                    youWon(player);
                    settlement();
                    deleteHand(hand);
                } else if (total > dealersTotal) {
                    youWon(player);
                    settlement();
                    deleteHand(hand);
                } else if (total < dealersTotal) {
                    youLost(player);
                    settlement();
                    deleteHand(hand);
                } else if (total == dealersTotal) {
                    itsATie(hand);
                    settlement();
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
                resettingTheGame();
            }
            resettingTheGame();
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


    // SETTLING BETS

    public void settlement() {
        Integer playersTotal = calculateHandValue(player1.getPlayersHand());
        Integer dealersTotal = calculateHandValue(dealer.getDealersHand());
        Double playersBank = player1.getPlayersBank();
        Double dealersBank = dealer.getDealersBank();

        double currentBet = player1.getBet();
        System.out.println(("Current bank: " + playersBank));
        System.out.println(("Current bet: " + currentBet));
        if (currentRound == 1 && playersTotal == blackjack) {
            playersBank = playersBank + (currentBet * 1.5 + currentBet);
            System.out.println(("New bank: " + playersBank));
            System.out.println(("Dealer's bank: " + dealersBank));
        } else if (playersTotal == blackjack || (playersTotal > dealersTotal && playersTotal < blackjack) || dealersTotal > blackjack) {
            playersBank = playersBank + (currentBet * 2);
            System.out.println(("New bank: " + playersBank));
            System.out.println(("Dealer's bank: " + dealersBank));
        } else if (playersTotal > blackjack || (playersTotal < dealersTotal && playersTotal < blackjack)) {
            dealersBank = dealersBank + currentBet;

            if (playersBank > currentBet){
                playersBank = playersBank - currentBet;
            }
            else{
                playersBank = 0.0;
            }
            System.out.println(("New bank: " + playersBank));
            System.out.println(("Dealer's bank: " + dealersBank));
        } else if (playersTotal == dealersTotal) {
            System.out.println(("New bank: " + currentBet));
            System.out.println(("Dealer's bank: " + dealersBank));
        }

        player1.setPlayersBank(playersBank);
        dealer.setDealersBank(dealersBank);
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
        } else if (userInput instanceof Boolean || null == userInput ) {
            while (userInput == null) {
                System.out.println("Please enter a valid input. Y/yes or N/no.");
                System.out.println("Your move: do you want to hit?");
                userInput = parseBoolean(newScanner.nextLine());
            }
        } else {
            System.out.println("Unknown input.");
        }

        return userInput;
    }

    public Object validateAce(Object userAceInput) {
        if (userAceInput instanceof Integer) {
            while (!(((Integer) userAceInput) == 1) && !(((Integer) userAceInput) == 11)) {
                System.out.println("Please enter a valid value. Numbers 1 or 11 only.");
                System.out.println("Set the Ace value: 1 or 11.");
                userAceInput = parseInteger(newScanner.nextLine());
            }
        } else {
            System.out.println("Unknown input.");
        }

        return userAceInput;
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

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public Dealer getDealer() {
        return dealer;
    }

    public void setDealer(Dealer dealer) {
        this.dealer = dealer;
    }
}
