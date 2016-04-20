package edu.up.cs301.boggle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**

 *@author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * Action class for when the time runs out

 */
public class BoggleTimerOutAction extends GameAction {
    /**
     * constructor for GameAction
     *
     * @param player the player who created the action
     */
    public BoggleTimerOutAction(GamePlayer player) {
        super(player);
    }
}
