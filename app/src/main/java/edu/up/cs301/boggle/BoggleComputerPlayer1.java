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
    int tested;
    String word;
    int index;

    /**
     * constructor
     *
     * @param name the player's name
     */
    public BoggleComputerPlayer1(String name) {
        super(name);
        this.tested = 0;
        index = 0;

    }

    @Override
    protected void receiveInfo(GameInfo info) {

        if (info instanceof BoggleState) {
            state = (BoggleState) info;
            visited = getVisited(); //array to see if the tile has been locked at yet
            board = state.getGameBoard();
            // found = getFound(); //list of all words found by computer

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
                if (index >= found.size()) {
                    System.out.println("Here");
                    return;
                }
                String word = found.get(index); // the word the computer will sumbit
                System.out.println(word);
                Random rand = new Random();
                int random = rand.nextInt(aiSmartness());
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

    protected int aiSmartness() {
        return 5;
    }


    /**
     * This method recursivally finds all the words on the board and puts them in an list "Found"
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
                word = currWord + board[x][y];
                if (found.contains(word)) continue; //if word already exsists in list dont add
                //if word is over 3 letters and in dictionary add to found list
                if (word.length() > 2 && dict.contains(word.toLowerCase())) {
                    setFound(word);
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
                findWords(dict, board, x, y, word, copy, copy2);
            }
        }

        return;
    }
    public void setFound(String s){this.found.add(s);}
    public ArrayList<String> getFound(){return found;}
    public boolean[][] getVisited() {return visited;}
}




