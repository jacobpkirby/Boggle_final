package edu.up.cs301.boggle;

import android.app.Activity;
import android.os.Handler;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * The Smart AI
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
*/
public class BoggleComputerPlayer2 extends BoggleComputerPlayer1 {


	/**
	 * constructor
	 *
	 * @param name the player's name
	 */
	public BoggleComputerPlayer2(String name) {
		super(name);
	}

	protected int aiSmartness() {
		return 2;
	}

}
