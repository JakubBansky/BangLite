package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Missed extends Card {

    private static final String CARD_NAME = "Missed";

    public Missed(Board board) {
        super(CARD_NAME, board);
        this.canBePlayed = false;
    }

    @Override
    public boolean isBlue() {
        return false;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        System.out.println("--- Player " + player.getName() + " used " + this.getName() + " he doesn't loose life");
    }
}
