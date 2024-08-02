package sk.stuba.fei.uim.oop.player;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.cards.Card;

import java.util.ArrayList;
import java.util.Iterator;

public class Player {

    private boolean canPlay;
    private int lives;
    private final String name;
    private ArrayList<Card> cards;
    private ArrayList<Card> blueCards;

    public Player(String name) {
        this.name = name;
        this.lives = 1;
        this.cards = new ArrayList<>();
        this.blueCards = new ArrayList<>();
        this.canPlay = true;

    }


    public void setCanPlay(boolean canPlay) {
        this.canPlay = canPlay;
    }

    public boolean getCanPlay() {
        return canPlay;
    }

    public int getLives() {
        return lives;
    }

    public String getName() {
        return name;
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    public void setCard(Card card) {
        this.cards.add(card);
    }

    public void setBlueCard(Card blueCard) {
        this.blueCards.add(blueCard);
    }

    public ArrayList<Card> getBlueCards() {
        return blueCards;
    }

    public void addLife() {
        this.lives++;
    }

    public void looseLife() {
        this.lives--;
    }

    public boolean isAlive() {
        return this.lives > 0;
    }

    public void delCard(int index, Board board) {
        if (this.cards.get(index).isBlue()) {
            this.cards.remove(index);
        } else {
            board.addCardToUsedDeck(this.cards.get(index));
            this.cards.remove(index);
        }
    }

    public void delBlueCard(Card blueCard, Board board) {
        Iterator<Card> it = this.blueCards.iterator();
        while (it.hasNext()) {
            if (it.next().equals(blueCard)) {
                board.addCardToUsedDeck(blueCard);
                it.remove();
                break;
            }
        }
    }
}
