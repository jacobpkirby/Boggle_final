package edu.up.cs301.boggle;

import java.util.ArrayList;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.actionMsg.GameAction;

/**

 *@author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * Action class for the computer submitting a score
 */

public class BoggleComputerSubmitScoreAction extends GameAction{
    public String word;

    /**
     * The constructor
     * @param player
     * @param word //word that being passed in for points
     */
    public BoggleComputerSubmitScoreAction(GamePlayer player, String word) {
        super(player);
        this.word = word;

    }
    public String curWord(){return word;}

}
