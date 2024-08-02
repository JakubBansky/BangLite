package sk.stuba.fei.uim.oop.board;


import sk.stuba.fei.uim.oop.cards.*;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.ArrayList;
import java.util.Collections;

public class Board {
    private ArrayList<Card> deck = new ArrayList<>();
    private ArrayList<Card> usedDeck = new ArrayList<>();

    public ArrayList<Card> getDeck() {
        return this.deck;
    }

    public ArrayList<Card> getUsedDeck() {
        return this.usedDeck;
    }

    public void addCardToUsedDeck(Card card) {
        this.usedDeck.add(card);
    }

    public void addCardToDeck(Card card) {
        this.deck.add(card);
    }

    public Board(Player[] players) {

        for (int i = 0; i < 2; i++) {
            this.deck.add(new Barrel(this));
        }
        this.deck.add(new Dynamite(this));
        for (int i = 0; i < 3; i++) {
            this.deck.add(new Jail(this));
        }
        for (int i = 0; i < 30; i++) {
            this.deck.add(new BangCard(this));
        }
        for (int i = 0; i < 15; i++) {
            this.deck.add(new Missed(this));
        }
        for (int i = 0; i < 8; i++) {
            this.deck.add(new Beer(this));
        }
        for (int i = 0; i < 6; i++) {
            this.deck.add(new CatBalou(this));
        }
        for (int i = 0; i < 4; i++) {
            this.deck.add(new Stagecoach(this));
        }
        for (int i = 0; i < 2; i++) {
            this.deck.add(new Indians(this));
        }
        Collections.shuffle(this.deck);

        for (Player player : players) {

            for (int i = 0; i < 4; i++) {
                player.setCard(this.deck.get(0));
                this.deck.remove(0);
            }


        }


    }


}
