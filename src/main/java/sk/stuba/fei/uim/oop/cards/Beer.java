package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Beer extends Card {

    private static final String CARD_NAME = "Beer";

    public Beer(Board board) {

        super(CARD_NAME, board);
        this.canBePlayed = true;

    }

    @Override
    public boolean isBlue() {
        return false;
    }


    @Override
    public void playCard(Player player, Player[] players) {
        player.addLife();
        this.wasPlayed = true;
        System.out.println("--- You drank refreshing beer  [hp:" + player.getLives() + "] ---");

    }
}
