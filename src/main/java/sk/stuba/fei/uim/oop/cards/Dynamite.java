package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;

public class Dynamite extends Card {


    private static final String CARD_NAME = "Dynamite";

    public Dynamite(Board board) {
        super(CARD_NAME, board);
        this.canBePlayed = true;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        super.playCard(player, players);
        player.setBlueCard(this);
        this.wasPlayed = true;

    }

    @Override
    public boolean isBlue() {
        return true;
    }

    @Override
    public void checkCard(Player player, Player[] players) {

        if (rand.nextInt(8) == 0) {
            System.out.println("--- BOOM, you loose 3 lives ---");
            for (int i = 0; i < 3; i++) {
                player.looseLife();
            }
            if (!player.isAlive()) {
                System.out.println("+++ " + player.getName() + " died +++");
            }
            player.delBlueCard(this, this.board);

        } else {
            System.out.println("--- Dynamite did not explode ---");
            this.shiftCard(players, player);
        }
    }

    public void shiftCard(Player[] players, Player player) {
        for (int i = 0; i < players.length; i++) {
            if (players[i] == player) {
                if (i - 1 >= 0) {
                    if (players[i - 1].isAlive()) {
                        players[i - 1].setBlueCard(this);
                    } else {
                        while (!players[i - 1].isAlive()) {
                            i--;
                        }
                    }

                } else {
                    int counter = 0;
                    if (players[players.length - 1].isAlive()) {
                        players[players.length - 1].setBlueCard(this);
                    } else {
                        while (!players[players.length - 1 - counter].isAlive()) {
                            counter++;
                        }
                        players[players.length - 1 - counter].setBlueCard(this);
                    }

                }

                players[i].delBlueCard(this, board);
                break;
            }
        }
    }


}
