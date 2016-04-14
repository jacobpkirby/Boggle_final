package edu.up.cs301.boggle;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Jacob on 4/12/2016.
 */
public class BoggleComputerSubmitScoreAction extends GameAction{
    public String word;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BoggleComputerSubmitScoreAction(GamePlayer player, String word) {
        super(player);
        this.word = word;

    }
    public String curWord(){return word;}

}
