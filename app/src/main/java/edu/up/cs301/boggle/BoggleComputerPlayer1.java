package edu.up.cs301.boggle;

import java.io.IOException;
import java.util.ArrayList;

import edu.up.cs301.game.GameComputerPlayer;
import edu.up.cs301.game.infoMsg.GameInfo;
import edu.up.cs301.game.util.Tickable;

import java.util.HashSet;
import java.util.Random;

/**
 * The Dumb AI
 * 
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleComputerPlayer1 extends GameComputerPlayer implements BogglePlayer, Tickable {
    BoggleState state;
    BoggleComputerSubmitScoreAction submitScore; //action for computer scoring
    boolean[][] visited = new boolean[4][4];  //array to see if the tile has been locked at yet
    String[][] board = new String[4][4];//array of all the letters on the board
    ArrayList<String> found = null; // list of all the words found by the computer
    String builtWord; // the word that gets built from recursive method
    int index; //determines what position in the array of dictionary words we want to pull a word
                //from.
    /**
     * constructor
     *
     * @param name the player's name
     */
    public BoggleComputerPlayer1(String name) {
        super(name);
        index = 0; //sets the index to 0 to start at the beginning of array
    }

    @Override
    protected void receiveInfo(GameInfo info) {
        if (info instanceof BoggleState) {
            state = (BoggleState) info;
            visited = getVisited(); //array to see if the tile has been locked at yet
            board = state.getGameBoard(playerNum); //array of all letters on board
            // found = getFound(); //list of all words found by computer

            //if the array of found words is null, then the dictionary will be created and
            //a recursive method will be run to find all the words on the board, thus making
            //found no longer null
            if (found == null) {
                found = new ArrayList<String>();
                try {
                    //initiates the dictionary. A random word needs to be passed in to make it
                    boolean l = state.inDictionary("start");
                } catch (IOException e) {
                }
                for (int row = 0; row < 4; row++)// goes through all the rows
                {
                    for (int col = 0; col < 4; col++)  // goes through all the columns
                    {
                        //resets all the visited to false
                        for (int i = 0; i < 4; i++) {
                            for (int j = 0; j < 4; j++) {
                                visited[i][j] = false;
                            }
                        }
                        //makes the current location true
                        visited[row][col] = true;
                        //calls the recursive method
                        findWords(state.getDictionary(), board, row, col, board[row][col], visited, found);
                    }
                }
            }
            //if the index number is already at the end of the array just return, cannot sumbit
            //anymore words
            if (index >= found.size()) {return;}

            String word = found.get(index); // the word the computer will submit
            System.out.println(word);
            Random rand = new Random();
            int random = rand.nextInt(aiSmartness());
            System.out.println("Random Number: " + random);
                if (random == 0) {
                    submitScore = new BoggleComputerSubmitScoreAction(this, word);
                    game.sendAction(submitScore);
                    index++;
                } else {
                    return;
                }
            }
            //}
            return;
        }

    protected int aiSmartness() {return 20;}


    /**
     * This method recursively finds all the words on the board and puts them in an list "Found"
     * @param dict
     * @param board
     * @param row
     * @param col
     * @param currWord
     * @param visited
     * @param found
     */
    public void findWords(HashSet<String> dict, String[][] board, int row, int col, String currWord, boolean[][] visited, ArrayList<String> found) {


        for (int x = row-1; x <= row+1 ; x++) {
            for (int y = col-1; y <= col+1; y++) {

//            if (row >= 0 && col >= 0) {
                try {
                    if (visited[x][y]) continue;  //makes sure tile is not visited twice
                    visited[x][y] = true;
                } catch (ArrayIndexOutOfBoundsException e) {
                    continue;
                }
                builtWord = currWord + board[x][y];
                if (found.contains(builtWord)) continue; //if word already exsists in list dont add
                //if word is over 3 letters and in dictionary add to found list
                if (builtWord.length() > 2 && dict.contains(builtWord.toLowerCase())) {
                    setFound(builtWord);
                }
                boolean[][] copy = new boolean[4][4]; // copy of visited tiles
                for(int i = 0; i < 4; i++){
                    for(int j = 0; j < 4; j++){
                        copy[i][j] = visited[i][j];
                    }
                }
                ArrayList<String> copy2 = new ArrayList<String>(); //copy of found list
                for(int i = 0; i< found.size(); i++){
                    copy2.add(found.get(i));
                }
                findWords(dict, board, x, y, builtWord, copy, copy2);

                /**
                 * External Citation
                 *
                 * Date: 3 April 2016
                 * Problem: Didn't know how to find all the words on the game board.
                 * Resource: Professor Nuxoll
                 * Solution: Helped us write a recursive method to seach all paths on board
                 */
            }
        }

        return;
    }
    public void setFound(String s){this.found.add(s);}
    public ArrayList<String> getFound(){return found;}
    public boolean[][] getVisited() {return visited;}
}




