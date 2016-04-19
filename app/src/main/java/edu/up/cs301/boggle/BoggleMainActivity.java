package edu.up.cs301.boggle;

import java.util.ArrayList;

import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.config.GameConfig;
import edu.up.cs301.game.config.GamePlayerType;

/**
 * This is the primary activity of a Boggle game
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleMainActivity extends GameMainActivity {

	// the port number that this game will use when playing over the network
	private static final int PORT_NUMBER = 2234;

	public static BoggleMainActivity activity;
	/**
	 * Create the default configuration for this game:
	 * - one human player vs. one computer player
	 * - minimum of 1 player, maximum of 2
	 * - one kind of computer player and one kind of human player available
	 *
	 * @return
	 * 		the new configuration object, representing the default configuration
	 */
	@Override
	public GameConfig createDefaultConfig() {
		activity = this;

		// Define the allowed player types
		ArrayList<GamePlayerType> playerTypes = new ArrayList<GamePlayerType>();

		// a human player player type (player type 0)
		playerTypes.add(new GamePlayerType("Local Human Player") {
			public GamePlayer createPlayer(String name) {
				return new BoggleHumanPlayer(name);
			}});

		// a computer player type (player type 1)
		playerTypes.add(new GamePlayerType("Computer Player (Dumb)") {
			public GamePlayer createPlayer(String name) {
				return new BoggleComputerPlayer1(name);
			}});

		// a computer player type (player type 2)
		playerTypes.add(new GamePlayerType("Computer Player (Smart)") {
			public GamePlayer createPlayer(String name) {
				return new BoggleComputerPlayer2(name);
			}});

		// Create a game configuration class for Counter:
		// - player types as given above
		// - from 1 to 2 players
		// - name of game is "Counter Game"
		// - port number as defined above
		GameConfig defaultConfig = new GameConfig(playerTypes, 2, 2, "Boggle Game",
				PORT_NUMBER);

		// Add the default players to the configuration
		defaultConfig.addPlayer("Human", 0); // player 1: a human player
		defaultConfig.addPlayer("Computer", 1); // player 2: a computer player
		defaultConfig.setRemoteData("Remote Human Player", "", 2);

		// Set the default remote-player setup:
		// - player name: "Remote Player"
		// - IP code: (empty string)
		// - default player type: human player
		defaultConfig.setRemoteData("Remote Player", "", 0);

		//DEBUG
		//defaultConfig.setUserModifiable(false);



		// return the configuration
		return defaultConfig;
	}//createDefaultConfig

	/**
	 * create a local game
	 *
	 * @return
	 * 		the local game, a boggle game
	 */
	@Override
	public LocalGame createLocalGame() {
		return new BoggleLocalGame();
	}

	public void setGameOver(boolean b) {
		super.setGameOver(b);
		if (b){
			//setContentView(R.layout.game_config_main);
		}
	}// setGameOver


}
