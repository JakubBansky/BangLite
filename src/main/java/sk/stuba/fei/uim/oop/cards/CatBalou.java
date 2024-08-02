package sk.stuba.fei.uim.oop.cards;

import sk.stuba.fei.uim.oop.board.Board;
import sk.stuba.fei.uim.oop.player.Player;
import sk.stuba.fei.uim.oop.utility.KeyboardInput;

import java.util.ArrayList;


public class CatBalou extends Card {

    private static final String CARD_NAME = "CatBalou";

    public CatBalou(Board board) {
        super(CARD_NAME, board);
        this.canBePlayed = true;
    }


    @Override
    public boolean isBlue() {
        return false;
    }


    @Override
    public void playCard(Player player, Player[] players) {


        Player aimedPlayer = players[super.choosePlayer(player, players, this)];
        ArrayList<Card> cards = handOrTable(aimedPlayer);
        if (cards == null) {
            System.out.println("!!! You can't play this card on player " + aimedPlayer.getName() + " !!!");
            this.wasPlayed = false;
        } else {

            System.out.println("::: Choose a card which you want to throw away :::");
            if (aimedPlayer.getBlueCards().equals(cards)) {
                printCards(cards);
            } else {
                for (int i = 1; i <= cards.size(); i++) {
                    System.out.println(i + ". ???");
                }
            }


            int cardIndex;
            while (true) {
                cardIndex = KeyboardInput.readInt("::") - 1;
                if (cardIndex < 0 || cardIndex > cards.size() - 1) {
                    System.out.println("--- Enter a valid card ---");
                } else {
                    //player.setCard(aimedPlayer.getCards().get(cardIndex));
                    if (aimedPlayer.getBlueCards().equals(cards)) {
                        if (cards.get(cardIndex) instanceof Jail) {
                            aimedPlayer.setCanPlay(true);
                        }

                        this.board.addCardToUsedDeck(cards.get(cardIndex));
                        aimedPlayer.delBlueCard(cards.get(cardIndex), this.board);
                    } else {
                        System.out.println("--- The card was " + cards.get(cardIndex).getName() + " ---");
                        aimedPlayer.delCard(cardIndex, this.board);


                    }
                    break;
                }
            }
        }
    }

    private ArrayList<Card> handOrTable(Player player) {
        if (player.getBlueCards().isEmpty() && player.getCards().isEmpty()) {
            return null;
        }
        char tableOrHand;
        System.out.println("Do you want to take a card from a table or from a hand 't'/'h' ?");
        while (true) {
            tableOrHand = KeyboardInput.readChar();
            if (Character.toLowerCase(tableOrHand) == 't') {
                if (player.getBlueCards().isEmpty()) {
                    System.out.println("--- " + player.getName() + " has no cards placed on the table, you will take from the hand ---\n");
                    return player.getCards();
                } else {
                    return player.getBlueCards();
                }

            } else if (Character.toLowerCase(tableOrHand) == 'h') {
                if (player.getCards().isEmpty()) {
                    System.out.println("--- " + player.getName() + " has no cards on his hand, you will take from the table ---\n");
                    return player.getBlueCards();
                } else {
                    return player.getCards();
                }


            } else {
                System.out.println("'t'/'h'");
            }
        }
    }
}
