package edu.up.cs301.boggle;

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
		return 10;
	}

}
