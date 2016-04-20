package edu.up.cs301.boggle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 *@author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * Action class for when a score is submited
 */

public class BoggleSubmitScoreAction extends GameAction {
    public String currentWord;
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BoggleSubmitScoreAction(GamePlayer player, String currentWord) {
        super(player);
        this.currentWord = currentWord;
    }
}
