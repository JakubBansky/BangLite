package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Barrel extends Card {

    private static final String CARD_NAME = "Barrel";

    public Barrel(Board board) {

        super(CARD_NAME, board);
        this.canBePlayed = true;
        this.canBeChecked = false;

    }


    @Override
    public boolean isBlue() {
        return true;
    }


    @Override
    public void playCard(Player player, Player[] players) {
        boolean flag = true;

        for (Card card : player.getBlueCards()) {
            if (card instanceof Barrel) {
                flag = false;
                break;
            }
        }
        if (flag) {
            super.playCard(player, players);
            player.setBlueCard(this);
            this.wasPlayed = true;
        } else {
            System.out.println("!!! You already have this card laid down !!!");
            this.wasPlayed = false;
        }

    }


    @Override
    public void checkCard(Player player, Player[] players) {
        //super.checkCard(player, players);
        if (rand.nextInt(4) == 0) {
            System.out.println("--- " + player.getName() + " hid behind a barrel ---");
        } else {
            player.looseLife();
            System.out.println("--- " + player.getName() + " was too slow to hide behind a barrel ---");
        }
    }


}
