package edu.up.cs301.boggle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 *@author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * Action class for removing a letter or deselecting a letter
 */
public class BoggleDeSelectTileAction extends GameAction {
    /**
     * constructor for BoggleDeSelectTileAction
     *
     * @param player the player who created the action
     */
    public BoggleDeSelectTileAction(GamePlayer player) {
        super(player);
    }
}
