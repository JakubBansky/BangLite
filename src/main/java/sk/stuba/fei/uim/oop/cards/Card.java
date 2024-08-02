package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;
import java.util.Random;

public abstract class Card {

    protected String name;
    protected Board board;
    protected boolean wasPlayed;
    protected boolean canBePlayed;
    protected boolean canBeChecked;

    protected final Random rand = new Random();

    public Card(String name, Board board) {
        this.name = name;
        this.board = board;
        this.wasPlayed = true;
        this.canBeChecked = true;
    }

    public abstract boolean isBlue();

    public boolean getCanBePlayed() {
        return this.canBePlayed;
    }

    public boolean getCanBeChecked() {
        return canBeChecked;
    }

    public String getName() {
        return name;
    }

    public boolean getWasPlayed() {
        return wasPlayed;
    }


    public void playCard(Player player, Player[] players) {
        System.out.println("--- " + player.getName() + " chose to play " + this.name + " ---");
    }

    public void checkCard(Player player, Player[] players) {
        System.out.println("### Player " + player.getName() + " has " + this.name + " ###");
    }

    public void printPlayers(Player[] players) {
        int counter = 1;
        for (Player player : players) {
            if (player.isAlive()) {
                System.out.println(counter + ". " + player.getName() + " [hp:" + player.getLives() + "]");
                counter++;
            }

        }
    }

    public void printCards(ArrayList<Card> cards) {
        int counter = 1;
        for (Card card : cards) {
            System.out.println(counter + ". " + card.getName());
            counter++;
        }
    }

    public int choosePlayer(Player player, Player[] players, Card card) {
        int aimedPlayer;
        while (true) {
            System.out.println(":::Choose a player to use a card " + card.getName() + " at:::");
            printPlayers(players);
            aimedPlayer = KeyboardInput.readInt("::") - 1;
            aimedPlayer = getCorrectPlayer(aimedPlayer, players);

            if (aimedPlayer < 0 || aimedPlayer > players.length - 1) {
                System.out.println("::: Player n. " + (aimedPlayer + 1) + " is invalid:::");
            } else if (players[aimedPlayer].equals(player)) {
                System.out.println("--- You can't use a card " + card.getName() + " on yourself ---");
            } else {
                return aimedPlayer;
            }
        }
    }

    public int getCorrectPlayer(int index, Player[] players) {
        int counter = 0;
        int i;
        for (i = 0; i < players.length; i++) {

            if (counter == index && players[i].isAlive()) {
                break;
            }
            if (players[i].isAlive()) {
                counter++;
            }
        }
        return i;
    }
}
