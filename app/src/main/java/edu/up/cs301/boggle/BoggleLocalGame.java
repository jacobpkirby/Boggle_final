package edu.up.cs301.boggle;

import java.io.IOException;

import edu.up.cs301.game.GamePlayer;
import edu.up.cs301.game.LocalGame;
import edu.up.cs301.game.actionMsg.GameAction;
import edu.up.cs301.game.infoMsg.GameState;

/**
 * A class that represents the sate of the game
 *
 * As extra to our game we:
 *              1. added a rotate button to allow the player to rotate the board to see
 *                 different angles for words
 *              2. Added a soundtrack to the game and gave sounds when the player sumbits a bad word
 *              3. !!IMPORTANT NOTE!! Our game is functional in both layouts but the user must decide
 *                     the layout before the game starts. otherwise it will lock.
 *
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleLocalGame extends LocalGame implements BoggleGame {
    private BoggleState state;

    /**
     * Constuctor for Local Class
     */
    public BoggleLocalGame() {
        state = new BoggleState();
        this.getTimer().setInterval(1000);
        this.getTimer().start();
    }

    @Override
    protected void sendUpdatedStateTo(GamePlayer p) {
        GameState copy = new BoggleState(state);
        p.sendInfo(copy);
    }

    @Override
    protected boolean canMove(int playerIdx) {
        return true;
    }

    /**
     * checkIfGameOver
     * Checks if the game is indeed over
     *
     * @return winTxt, the message being printed to the user at the end
     */
    @Override
    protected String checkIfGameOver() {



        if (state.getGameOver() == 1) {
            int winner = state.getWinner();
            String winTxt = "";

            if (winner == 1) {
                winTxt = "Game over! " + playerNames[0] + " wins!";
            } else if (winner == 2) {
                winTxt = "Game over! " + playerNames[1] + " wins!";
            } else if (winner == 3) {
                winTxt = "It's a draw! " + playerNames[0] + " and " + playerNames[1] + " tie!";
            }
            return winTxt;
        } else {
            return null;
        }

    }

    /**
     * Where all the actions are configured
     *
     * @param action The move that the player has sent to the game
     * @return
     */
    @Override
    protected boolean makeMove(GameAction action) throws IOException {
        //action used for when the computer needs to score
        GamePlayer player = action.getPlayer();
        int playerIdx = this.getPlayerIdx(player);
        if (action instanceof BoggleComputerSubmitScoreAction) {
            BoggleComputerSubmitScoreAction BCSA = (BoggleComputerSubmitScoreAction) action;
            String word = BCSA.curWord(); //gets list of all possible words comp can use
            state.setWordBank(word, playerIdx); //puts the words used by the computer in array
            int score = state.compUpdateScore(word); //calculates the score for the word
            if (playerIdx == 0) {
                state.setPlayer1Score(state.getPlayer1Score() + score);
            } else {
                state.setPlayer2Score(state.getPlayer2Score() + score);
            }//sets the comps score
            return true;
        } else if (action instanceof BoggleSelectTileAction) {
            BoggleSelectTileAction BSTA = (BoggleSelectTileAction) action;
            int curRow = BSTA.curLetterRow;
            int curCol = BSTA.curLetterCol;
            String currentWord = state.getCurrentWord(playerIdx); //pre exsisting word
            //array of all words choosen already and their positions
            int[][] selectedLetters = state.getSelectedLetters(playerIdx);
            String[][] gameBoard = state.getGameBoard(playerIdx);
            //adds letter from tile to exsisitng word
            currentWord = state.addToWord(currentWord, gameBoard[curRow][curCol]);
            state.setCurrentWord(currentWord, playerIdx);
            state.setSelectedLetters(selectedLetters,playerIdx);

            return true;
        } else if (action instanceof BoggleDeSelectTileAction) {
            String currentWord = state.getCurrentWord(playerIdx);
            int[][] selectedLetters = state.getSelectedLetters(playerIdx);
            currentWord = state.removeFromWord(currentWord);
            state.setCurrentWord(currentWord, playerIdx);
            state.setSelectedLetters(selectedLetters,playerIdx);



            return true;

        } else if (action instanceof BoggleSubmitScoreAction) {
            BoggleSubmitScoreAction BSSA = (BoggleSubmitScoreAction) action;
            String word = BSSA.currentWord;
            //if word is more then 3 letters and in dictionary
            if (state.wordLength(word) && state.inDictionary(word)) {
                if (state.getWordBank(playerIdx).contains(word)) {
                    state.setCurrentWord("", playerIdx);
                    return false;
                } else {
                    int score = state.updateScore(word,playerIdx);
                    if (playerIdx == 0) {
                        state.setPlayer1Score(state.getPlayer1Score() + score);
                    } else {
                        state.setPlayer2Score(state.getPlayer2Score() + score);
                    }
                    state.addToWordBank(word, playerIdx);
                    state.setCurrentWord("", playerIdx);
                    int[][] selectedLetters = state.getSelectedLetters(playerIdx);

                    state.setSelectedLetters(selectedLetters, playerIdx);

                    return true;
                }
            }
            state.setCurrentWord("", playerIdx);
            return true;
        } else if (action instanceof BoggleRotateAction) {
            state.rotateBoard(state.getGameBoard(playerIdx), playerIdx);
            return true;
        } else if (action instanceof BoggleTimerOutAction) {
            return true;

        }
        return false;
    }

    /**
     * Invoked whenever the game's timer has ticked. It is expected
     * that this will be overridden in many games.
     */
    protected void timerTicked() {
        int seconds = state.getSecondsLeft();

        if (seconds == 1) {
            state.setGameOver(1);
        }
        if (seconds > 0) {
            seconds--;
        }
        checkIfGameOver();
        state.setSecondsLeft(seconds);
        sendAllUpdatedState();
    }
}// class BoggleLocalGame
