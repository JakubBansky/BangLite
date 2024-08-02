package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.Iterator;

public class BangCard extends Card {
    private static final String CARD_NAME = "Bang";

    public BangCard(Board board) {

        super(CARD_NAME, board);
        this.canBePlayed = true;

    }

    @Override
    public boolean isBlue() {
        return false;
    }

    @Override
    public void playCard(Player player, Player[] players) {
        int barrelCheck;
        boolean barrelFlag = true;
        boolean missedFlag = false;
        boolean flag = false;
        this.wasPlayed = true;

        super.playCard(player, players);

        Player aimedPlayer = players[choosePlayer(player, players)];
        barrelCheck = aimedPlayer.getLives();
        for (Card card : aimedPlayer.getBlueCards()) {
            if (card instanceof Barrel) {
                card.checkCard(aimedPlayer, players);
                flag = true;
                barrelFlag = aimedPlayer.getLives() >= barrelCheck;
                break;

            }
        }

        if (!barrelFlag || !flag) {

            Iterator<Card> cardIterator = aimedPlayer.getCards().iterator();
            Card tmpCard;
            while (cardIterator.hasNext()) {
                tmpCard = cardIterator.next();
                if (tmpCard instanceof Missed) {
                    if (flag) {
                        aimedPlayer.addLife();
                    }

                    tmpCard.playCard(aimedPlayer, players);
                    cardIterator.remove();
                    missedFlag = true;
                    break;
                }
            }

        }
        if ((!missedFlag && !flag)) {
            aimedPlayer.looseLife();
            message(aimedPlayer);

        } else if (!barrelFlag && !missedFlag) {
            message(aimedPlayer);
        }
        if (!aimedPlayer.isAlive()) {
            System.out.println("+++ " + aimedPlayer.getName() + " died +++");
        }


    }

    private int choosePlayer(Player player, Player[] players) {
        int aimedPlayer;
        while (true) {
            System.out.println(":::Choose a player to shoot at:::");
            super.printPlayers(players);
            aimedPlayer = KeyboardInput.readInt(":") - 1;
            aimedPlayer = super.getCorrectPlayer(aimedPlayer, players);

            if (aimedPlayer < 0 || aimedPlayer > players.length - 1) {
                System.out.println("::: Player n. " + (aimedPlayer + 1) + " is invalid:::");
            } else if (players[aimedPlayer].equals(player)) {
                System.out.println("--- You can't shoot yourself ---");
            } else {
                if (players[aimedPlayer].isAlive()) {
                    return aimedPlayer;
                } else {
                    System.out.println("!!! You can't shoot dead players !!!");
                }

            }
        }
    }

    private void message(Player aimedPlayer) {
        System.out.println("--- " + aimedPlayer.getName() + " loses life [HP: " + aimedPlayer.getLives() + "]---");
    }
}

