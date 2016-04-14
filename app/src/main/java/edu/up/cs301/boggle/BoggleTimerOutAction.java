package edu.up.cs301.boggle;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**
 * Created by Jacob on 4/12/2016.
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
