package sk.stuba.fei.uim.oop.cards;


import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

public class Jail extends Card {

    private static final String CARD_NAME = "Jail";
    private boolean flag = false;

    public Jail(Board board) {
        super(CARD_NAME, board);
        this.canBePlayed = true;
    }

    @Override
    public boolean isBlue() {
        return true;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        int chosenPlayer;
        super.playCard(player, players);
        System.out.println(":::Choose a player you want to put in jail, if you don't want to choose press 0:::");
        super.printPlayers(players);
        while (true) {
            chosenPlayer = KeyboardInput.readInt("::") - 1;
            chosenPlayer = super.getCorrectPlayer(chosenPlayer, players);

            if (chosenPlayer >= -1 && chosenPlayer <= players.length - 1) {
                if (chosenPlayer == -1) {
                    this.wasPlayed = false;
                    break;
                }
                if (players[chosenPlayer] == player) {
                    System.out.println("--- You can't put yourself in a jail, enter valid player ---");
                } else if ((!players[chosenPlayer].getCanPlay()) && (!this.flag)) {
                    System.out.println("--- " + players[chosenPlayer].getName() + " is already in the jail ---");


                } else if (players[chosenPlayer].getCanPlay() || this.flag) {
                    System.out.println("--- You put " + players[chosenPlayer].getName() + " to the jail ---");
                    players[chosenPlayer].setBlueCard(this);
                    players[chosenPlayer].setCanPlay(false);
                    this.wasPlayed = true;


                    break;
                }

            } else {
                System.out.println("--- Enter valid player ---");
            }
        }
    }

    @Override
    public void checkCard(Player player, Player[] players) {

        if (flag) {
            player.setCanPlay(true);
            flag = false;
        } else if (rand.nextInt(4) == 0) {
            player.setCanPlay(true);
            System.out.println("---You can leave the jail now---");
            player.delBlueCard(this, this.board);
            flag = false;
        } else {
            System.out.println("---You stay in the jail---");
            player.delBlueCard(this, this.board);
            player.setCanPlay(false);
            this.flag = true;
        }
    }
}

