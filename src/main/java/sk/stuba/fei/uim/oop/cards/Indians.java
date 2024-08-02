package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

import java.util.Iterator;

public class Indians extends Card {

    private static final String CARD_NAME = "Indians";

    public Indians(Board board) {

        super(CARD_NAME, board);
        this.canBePlayed = true;

    }

    @Override
    public boolean isBlue() {
        return false;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        boolean flag;
        for (Player player1 : players) {
            if (player1.equals(player)) {
                continue;
            }
            if (player1.isAlive()) {
                flag = false;
                Iterator<Card> cardIterator = player1.getCards().iterator();
                Card tmpCard;
                while (cardIterator.hasNext()) {
                    tmpCard = cardIterator.next();
                    if (tmpCard instanceof BangCard) {
                        cardIterator.remove();
                        flag = true;
                        break;
                    }
                }
                if (flag) {
                    System.out.println("--- " + player1.getName() + " had Bang [hp:" + player1.getLives() + "] ---");

                } else {
                    player1.looseLife();
                    if (!player1.isAlive()) {
                        System.out.println("+++ " + player1.getName() + " died +++");
                    }
                    System.out.println("--- " + player1.getName() + " didn't have Bang [hp:" + player1.getLives() + "] ---");
                }
            }

        }
    }
}
