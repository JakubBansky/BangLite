package sk.stuba.fei.uim.oop.bang;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Bang {
    private Board board;
    private int currentPlayer;
    private Player[] players;


    public Bang() {
        System.out.println("--- Welcome to FEI BANG ---");
        int numberOfPlayers = 0;
        while (numberOfPlayers < 2 || numberOfPlayers > 4) {
            numberOfPlayers = KeyboardInput.readInt("::: Enter number of players <2-4> ::");
            if (numberOfPlayers < 2 || numberOfPlayers > 4) {
                System.out.println("--- Please enter a valid number of players <2-4> ---");
            }
        }
        this.players = new Player[numberOfPlayers];
        for (int i = 0; i < numberOfPlayers; i++) {
            this.players[i] = new Player(KeyboardInput.readString("::: Enter " + (i + 1) + ". player name ::"));
        }

        this.board = new Board(this.players);
        this.startGame();
    }

    private void startGame() {
        System.out.println("--- Game Started ---");

        while (getPlayersAlive() > 1) {
            Player activePlayer = players[this.currentPlayer];
            if (activePlayer.isAlive()) {
                System.out.println("==== " + activePlayer.getName() + "'s turn [hp:" + activePlayer.getLives() + "] ====");
                if (!activePlayer.getBlueCards().isEmpty()) {
                    System.out.println("---- " + activePlayer.getName() + "'s blue cards ----");
                    for (Card blueCard : activePlayer.getBlueCards()) {
                        System.out.println("### " + blueCard.getName() + " ###");
                    }
                }


                playTurn(activePlayer, players);
            }


            this.incrementCounter();

        }
        System.out.println("***Game finished***");
        getWinner();
    }

    private void discardPlayer(Player activePlayer) {
        for (Player player : this.players) {
            if (!player.isAlive()) {
                if (!(player.getLives() > 0)) {
                    Iterator<Card> delCard = player.getCards().iterator();
                    Card tmpCard;
                    while (delCard.hasNext()) {
                        tmpCard = delCard.next();
                        this.board.addCardToUsedDeck(tmpCard);
                        delCard.remove();
                        //this.alivePlayers[i].delCard(board.getDeck().indexOf(card), this.board);
                    }
                    delCard = player.getBlueCards().iterator();
                    while (delCard.hasNext()) {
                        tmpCard = delCard.next();
                        this.board.addCardToUsedDeck(tmpCard);
                        delCard.remove();
                        //this.alivePlayers[i].delCard(board.getDeck().indexOf(card), this.board);
                    }
                }
            }
        }

        if (!activePlayer.isAlive()) {
            this.incrementCounter();
        }
    }

    private void playTurn(Player activePlayer, Player[] players) {
        checkBlueCards(activePlayer, players);
        if (activePlayer.getCanPlay()) {
            draw2Cards(activePlayer);
            playCards(activePlayer);
            if (this.getPlayersAlive() == 1) {
                return;
            }
            if (activePlayer.getLives() < activePlayer.getCards().size()) {
                throwCardsAway(activePlayer);
            }
        } else {
            activePlayer.setCanPlay(true);
        }


    }

    private void checkBlueCards(Player activePlayer, Player[] players) {
        int counter = 0;
        for (Card card : activePlayer.getBlueCards()) {
            if (card instanceof Dynamite) {
                counter++;

            } else if (card instanceof Jail) {
                counter++;
            }
        }
        Iterator<Card> cardIterator = activePlayer.getBlueCards().iterator();
        if (counter != 2) {
            Card tmpCard;
            while (cardIterator.hasNext()) {
                tmpCard = cardIterator.next();
                if (tmpCard.getCanBeChecked()) {
                    tmpCard.checkCard(activePlayer, players);
                    break;
                }
            }

        } else {
            Card tmpCard;
            while (cardIterator.hasNext()) {
                tmpCard = cardIterator.next();
                if (counter == 2 && tmpCard instanceof Dynamite) {
                    tmpCard.checkCard(activePlayer, players);
                    counter--;
                } else if (counter == 1 && tmpCard instanceof Jail) {
                    tmpCard.checkCard(activePlayer, players);
                    counter--;
                }
            }
        }

    }

    private void playCards(Player activePlayer) {
        System.out.println("--- " + activePlayer.getName() + "'s cards ---");
        while (activePlayer.getCards().size() > 0) {
            int numberOfCard = choosingCard(activePlayer.getCards());
            if (numberOfCard == -1) {
                break;
            } else if (!activePlayer.getCards().get(numberOfCard).getCanBePlayed()) {
                System.out.println("!!! You can't play card " + activePlayer.getCards().get(numberOfCard).getName() + " !!!");
            } else {
                activePlayer.getCards().get(numberOfCard).playCard(activePlayer, this.players);

                discardPlayer(activePlayer);

                if (activePlayer.getCards().get(numberOfCard).getWasPlayed()) {
                    activePlayer.delCard(numberOfCard, this.board);
                }
                if (this.getPlayersAlive() == 1) {
                    return;
                }


            }

        }
    }


    private int choosingCard(ArrayList<Card> cards) {
        for (int i = 0; i < cards.size(); i++) {
            System.out.println("Card " + (i + 1) + ":" + cards.get(i).getName());
        }
        int numberCard;
        while (true) {
            numberCard = KeyboardInput.readInt("::: Enter a number of the card you want to play, if you don't want to play any card enter 0 ::") - 1;
            if (numberCard == -1) {
                return -1;

            } else if ((numberCard < 0 || numberCard > cards.size() - 1)) {
                System.out.println(" !!! Please enter a valid card number !!!");
            } else {
                break;
            }
        }
        return numberCard;
    }


    private void throwCardsAway(Player activePlayer) {
        int counter = 0;
        for (Player player : players) {
            if (player.isAlive()) {
                counter++;
            }
        }
        if (counter < 2) {
            return;
        }
        System.out.println(":::You can have only " + activePlayer.getLives() + " cards, choose which one you want to throw away::");
        int numberCard;
        while (activePlayer.getLives() < activePlayer.getCards().size()) {
            for (int i = 0; i < activePlayer.getCards().size(); i++) {
                System.out.println("Card " + (i + 1) + ":" + activePlayer.getCards().get(i).getName());
            }
            numberCard = KeyboardInput.readInt("::") - 1;
            if ((numberCard < 0 || numberCard > activePlayer.getCards().size() - 1)) {
                System.out.println(" !!! Please enter a valid card !!!");
            } else {
                activePlayer.delCard(numberCard, this.board);
            }
        }

    }

    private void draw2Cards(Player activePlayer) {
        if (this.board.getDeck().size() + this.board.getUsedDeck().size() < 2) {
            System.out.println("--- There are no cards left to draw ---");
            return;
        }
        if (board.getDeck().size() < 2) {
            for (Card card : board.getUsedDeck()) {
                board.addCardToDeck(card);
            }
            Collections.shuffle(board.getDeck());
            board.getUsedDeck().clear();
        }
        activePlayer.setCard(board.getDeck().get(0));
        board.getDeck().remove(0);
        activePlayer.setCard(board.getDeck().get(0));
        board.getDeck().remove(0);
    }

    private void incrementCounter() {
        this.currentPlayer++;
        this.currentPlayer %= this.players.length;
    }


    private int getPlayersAlive() {
        int counter = 0;
        for (Player player : players) {
            if (!(player == null)) {
                if (player.isAlive()) {
                    counter++;
                }
            }
        }
        return counter;
    }

    private void getWinner() {
        for (Player player : players) {

            if (player.isAlive()) {
                System.out.println("***Congratulations, the winner is " + player.getName() + " ***");
            }
        }

    }
}
