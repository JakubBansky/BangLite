package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Stagecoach extends Card {

    private static final String CARD_NAME = "Stagecoach";

    public Stagecoach(Board board) {

        super(CARD_NAME, board);
        this.canBePlayed = true;
    }

    @Override
    public boolean isBlue() {
        return false;
    }


    @Override
    public void playCard(Player player, Player[] players) {

        if (this.board.getDeck().size() < 2) {
            System.out.println("--- You can't play this card ---");
            this.wasPlayed = false;
            return;
        }
        super.playCard(player, players);
        player.setCard(this.board.getDeck().get(0));
        this.board.getDeck().remove(0);
        player.setCard(this.board.getDeck().get(0));
        this.board.getDeck().remove(0);
        this.wasPlayed = true;
        System.out.println("--- You got " + player.getCards().get(player.getCards().size() - 2).getName() + " and " +
                player.getCards().get(player.getCards().size() - 1).getName() + "---");
    }
}
