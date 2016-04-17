package edu.up.cs301.boggle;


import android.app.Activity;
import android.util.Log;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.Random;

import edu.up.cs301.game.infoMsg.GameState;

import static edu.up.cs301.boggle.BoggleHumanPlayer.*;

/**
 * This is our Game State class. This is where all the methods are for Boggle to run.
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleState extends GameState {

    private int playerTurn; //tells which players turn it is
    private int player1Score; //tracks the score of player1
    private int player2Score; //tracks the score of player2
    //NETWORK
    private String[] currentWord = new String[2]; //the current word the player is making
    private boolean timer; //true if the timer is running, false if timer has stopped

    private ArrayList<String> wordBank; //the current words in the word bank
    private String[][] gameBoard = new String[4][4];
    private boolean[][] visited = new boolean[4][4];
    private int[][] selectedLetters = new int[20][2];
//    private ArrayList<String> found = new ArrayList<String>();
   // private ArrayList<String> copy = new ArrayList<String>();
    private ArrayList<String> compUsedWords = new ArrayList<String>();

    private static HashSet<String> dictionary = null;

    //private String compCurrentWord = "";
    private String curLetter;
    private int curLetterRow;
    private int curLetterCol;
    private int secondsLeft;

    //list of all the words possible for computer to use

    //list of all the words used by computer for points

    private String compPrevWord = "";
    private int gameOver; //determines if game is over
    public String word;
    public int arrayIndex;//int for the index used the Local Game
    public int tested;



    public ArrayList<String> humanUsedWords;

    /**
     * The BoggleState constructor. The heart and soul of Boggle. Constructs the gameState of Boggle.
     */
    public BoggleState() {
        //this.found = new ArrayList<String>();
        for (int x = 0; x < 4; x++) {
            for (int y = 0; y < 4; y++) {
                visited[x][y] = false;
            }
        }

        gameOver = 0;
        arrayIndex = 1;
        tested = 0;
        playerTurn = 0;
        player1Score = 0;
        player2Score = 0;
        //NETWORK
        for(int i = 0; i < 2; i++){
            currentWord[i] = "";
        }

        timer = true;
        curLetter = "a";
        curLetterRow = 4;
        curLetterCol = 4;
        secondsLeft = 10;


        Random r1 = new Random();
        char c1 = (char)(r1.nextInt(26) + 'A');
        Random r2 = new Random();
        char c2 = (char)(r2.nextInt(26) + 'A');
        Random r3 = new Random();
        char c3 = (char)(r3.nextInt(26) + 'A');
        Random r4 = new Random();
        char c4 = (char)(r4.nextInt(26) + 'A');
        Random r5 = new Random();
        char c5 = (char)(r5.nextInt(26) + 'A');
        Random r6 = new Random();
        char c6 = (char)(r6.nextInt(26) + 'A');
        Random r7 = new Random();
        char c7 = (char)(r7.nextInt(26) + 'A');
        Random r8 = new Random();
        char c8 = (char)(r8.nextInt(26) + 'A');
        Random r9 = new Random();
        char c9 = (char)(r9.nextInt(26) + 'A');
        Random r10 = new Random();
        char c10 = (char)(r10.nextInt(26) + 'A');
        Random r11 = new Random();
        char c11 = (char)(r11.nextInt(26) + 'A');
        Random r12 = new Random();
        char c12 = (char)(r12.nextInt(26) + 'A');
        Random r13 = new Random();
        char c13 = (char)(r13.nextInt(26) + 'A');
        Random r14 = new Random();
        char c14 = (char)(r14.nextInt(26) + 'A');
        Random r15 = new Random();
        char c15 = (char)(r15.nextInt(26) + 'A');
        Random r16 = new Random();
        char c16 = (char)(r16.nextInt(26) + 'A');

        gameBoard[0][0] = ("" + c1);
        gameBoard[1][0] = ("" + c2);
        gameBoard[2][0] = ("" + c3);
        gameBoard[3][0] = ("" + c4);
        gameBoard[0][1] = ("" + c5);
        gameBoard[1][1] = ("" + c6);
        gameBoard[2][1] = ("" + c7);
        gameBoard[3][1] = ("" + c8);
        gameBoard[0][2] = ("" + c9);
        gameBoard[1][2] = ("" + c10);
        gameBoard[2][2] = ("" + c11);
        gameBoard[3][2] = ("" + c12);
        gameBoard[0][3] = ("" + c13);
        gameBoard[1][3] = ("" + c14);
        gameBoard[2][3] = ("" + c15);
        gameBoard[3][3] = ("" + c16);


        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j].equals("Q")) {
                    gameBoard[i][j] = ("Qu");
                }

            }
        }

        double randomVowelDouble = Math.random()*5;
        int randomVowelInt = (int)randomVowelDouble;
        if (randomVowelInt == 5) {
            randomVowelInt = 4;
        }
        String randomVowel = "";
        if (randomVowelInt == 0) {
            randomVowel = "A";
        }else if (randomVowelInt == 1) {
            randomVowel = "E";
        }else if (randomVowelInt == 2) {
            randomVowel = "I";
        }else if (randomVowelInt == 3) {
            randomVowel = "O";
        }else if (randomVowelInt == 4) {
            randomVowel = "U";
        }
        int randomRow = (int)(Math.random()*4);
        if (randomRow == 4) {
            randomRow = 3;
        }
        int randomCol = (int)(Math.random()*4);
        if (randomCol == 4) {
            randomCol = 3;
        }
        int vowelCount = 0;
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (gameBoard[i][j].equals("A") || gameBoard[i][j].equals("E") ||
                        gameBoard[i][j].equals("I") || gameBoard[i][j].equals("O") || gameBoard[i][j].equals("U")) {
                    vowelCount++;
                }
            }
        }

        if (vowelCount == 0) {
            gameBoard[randomRow][randomCol] = randomVowel;
        }
       for (int k = 0; k < 20; k++) {
           selectedLetters[k][0] = 4;
          selectedLetters[k][1] = 4;
        }
        wordBank = new ArrayList<String>();
        compUsedWords = new ArrayList<String>();

       //for(int i = 0; i < compUsedWords.size(); i++){
      //     compUsedWords.clear();
      // }

    }


    /**
     * DEEP COPY
     * The BoggleState copy constructor. Constructs a deep copy of a passed in gameState.
     *
     * @param state the old boggleState to be copied
     */
    public BoggleState(BoggleState state) {
        playerTurn = state.playerTurn;
        player1Score = state.player1Score;
        player2Score = state.player2Score;
        //currentWord = state.currentWord;
        curLetter = state.curLetter;
        curLetterRow = state.curLetterRow;
        curLetterCol = state.curLetterCol;
        secondsLeft = state.secondsLeft;
        gameOver = state.gameOver;
        wordBank = state.wordBank;
        compUsedWords = state.compUsedWords;
        timer = state.timer;
        tested = state.tested;
        gameBoard = Arrays.copyOf(state.gameBoard, state.gameBoard.length);
        visited = Arrays.copyOf(state.visited,state.gameBoard.length);
        selectedLetters = Arrays.copyOf(state.selectedLetters, state.selectedLetters.length);

        //compCurrentWord = state.compCurrentWord;
        compPrevWord = state.compPrevWord;
        //for(int i = 0; i < this.compUsedWords.size(); i++){
        //  state.compUsedWords.add(this.compUsedWords.get(i));
       // }
        //NETWORK
        this.currentWord = new String[2];
        for(int i = 0; i < 2;i ++){
            this.currentWord[i]= state.currentWord[i];
        }
    }

    //--------------------------- Getter/Setter End -----------------------------


    public boolean isTimer() {return timer;}
    public ArrayList<String> getCompUsedWords() {return compUsedWords;}
    public void setCompUsedWords(String word){compUsedWords.add(word);}
    //public String getCompCurWord() {return compCurrentWord;}
    //public void setCompCurWord(String word) {this.compCurrentWord = word;}
    public String getCompPrevWord() {return compPrevWord;}
    public void setCompPrevWord(String word) {this.compPrevWord = word;}
    public int getTested() {return tested;}
    public void setTested(int tested) {this.tested = tested;}
    public void setTimer(boolean timer) {this.timer = timer;}
    public String[][] getGameBoard() {return Arrays.copyOf(gameBoard, gameBoard.length);}
    public void setGameBoard(String[][] gameBoard){
        this.gameBoard = Arrays.copyOf(gameBoard, gameBoard.length);
    }
    public int getArrayIndex() {return arrayIndex;}
    public int getWinner() {
        if (getPlayer1Score() > getPlayer2Score()) {return 1;}
        else if (getPlayer2Score()>getPlayer1Score()) {return 2;}
        else {return 3;}
    }
    public void setArrayIndex(int arrayIndex) {this.arrayIndex = arrayIndex;}
    public int getPlayerTurn() {return playerTurn;}
    public void setPlayerTurn(int playerTurn) {this.playerTurn = playerTurn;}
    public int getPlayer1Score() {return player1Score;}
    public void setPlayer1Score(int player1Score) {this.player1Score = player1Score;}
    public int getPlayer2Score() {return player2Score;}
    public void setPlayer2Score(int player2Score) {this.player2Score = player2Score;}
    //NETWORK
    public String getCurrentWord(int playerNum) {return currentWord[playerNum];}
    public void setCurrentWord(String currentWord, int playerNum) {this.currentWord[playerNum] = currentWord;}

    public ArrayList<String> getWordBank() {return wordBank;}
    public void setWordBank(ArrayList<String> wordBank) {this.wordBank = wordBank;}
    public HashSet<String> getDictionary(){return dictionary;}
   // public ArrayList<String> getFound(){return found;}
    //public void setFound(String s){this.found.add(s);}
    public int getGameOver(){return this.gameOver;}
    public void setGameOver(int gameOver) {this.gameOver = gameOver;}
    public int[][] getSelectedLetters() {
        return Arrays.copyOf(selectedLetters, selectedLetters.length);
    }
   // public boolean[][] getVisited() {return visited;}
    public void setSelectedLetters(int[][] selectedLetters){
        this.selectedLetters = Arrays.copyOf(selectedLetters, selectedLetters.length);
    }
    public int getSecondsLeft() {return secondsLeft;}
    public void setSecondsLeft(int seconds) {this.secondsLeft = seconds;}
    public String getCurLetterFromBoard(int curLetterRow, int curLetterCol, String[][] gameBoard) {
        return gameBoard[curLetterRow][curLetterCol];
    }
    public String getCurLetter() {
        return curLetter;
    }
    public void setCurLetter(String curLetter) {
        this.curLetter = curLetter;
    }
    public int getCurLetterRow() {
        return curLetterRow;
    }
    public int getCurLetterCol() {
        return curLetterCol;
    }
    public void setCurLetterRow(int curLetterRow) {
        this.curLetterRow = curLetterRow;
    }
    public void setCurLetterCol(int curLetterCol) {
        this.curLetterCol = curLetterCol;
    }
    public int getLastLetterCol(int [][] selectedLetters) {
        return selectedLetters[getLastLetterIndex(selectedLetters)][1];
    }
    public int getLastLetterRow(int [][] selectedLetters) {
        return selectedLetters[getLastLetterIndex(selectedLetters)][0];
    }
    public int getLastLetterIndex(int[][] selectedLetters) {
        int counter = 0;
        while (selectedLetters[counter + 1][0] != 4) {
            counter++;
        }
        return counter;
    }
//--------------------------- End Getter/Setter End -----------------------------
    /**
     * Determines if the word is more then 3 letters, which means its playable
     *
     * @param word the word being checked
     */
    public boolean wordLength(String word) {
        if (word.length() < 3) {
            return false;
        } else {
            return true;
        }
    }



    /**
     * Checks if tile can be removed based on the grounds that it is the last checked tile.
     *
     * @param word            word to be edited
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     *                        the array is 16 ints long to represent each tile. 1 -16. each tile is has a 2
     *                        dimension array to correspond to its row and column. When a tile's row or column is 4,
     *                        it means that it is null.
     */
    public void removeLetter(String word, int[][] selectedLetters) {

        int index = getLastLetterIndex(selectedLetters);




        selectedLetters[index][0] = 4;
        selectedLetters[index][1] = 4;


    }

    /**
     * Checks if a word is in the dictionary
     *
     * @param word the word being checked
     * @return a boolean dictating if it is a legal word
     * @throws IOException
     */
    public Boolean inDictionary(String word) throws IOException {
        BufferedReader reader;

        word = word.toLowerCase();

        if(dictionary == null){
            dictionary = new HashSet<String>();
            int count = 0;
            try {
                Activity myActivity = BoggleMainActivity.activity;
                InputStream ins = myActivity.getResources().openRawResource(myActivity.getResources().getIdentifier("words","raw", myActivity.getPackageName()));
                reader = new BufferedReader(new InputStreamReader(ins));
                String line;

                while ((line = reader.readLine()) != null) {
                    dictionary.add(line.toLowerCase());
                    count++;
                }
              //  Log.i("count is ", "" + count);
            }
            catch(IOException e){
                return false;
            }

        }
        Log.i("looking up", word);
        boolean rtnVal = dictionary.contains(word);
        Log.i("lookup found: ", ""+rtnVal);
        return rtnVal;

        /**
         * External Citation
         *
         * Date: 15 March 2016
         * Problem: Didn't know how to check for spelling errors.
         * Resource: http://www.java-gaming.org/index.php?topic=28057.0
         * Solution: Used the code from this post.
         */
    }



    /**
     * checks if tile can be removed based on the grounds that it is the last picked tile
     *
     * @param curLetterRow  the row that the new letter is in
     * @param curLetterCol  the col that the new letter is in
     * @param lastLetterRow the row that the last selected letter was in
     * @param lastLetterCol the col that the last selected letter was in
     */
    public Boolean canRemove(int[][] selectedLetters, int curLetterRow, int curLetterCol,
                             int lastLetterRow, int lastLetterCol) {

        int index = getLastLetterIndex(selectedLetters);
        lastLetterRow = selectedLetters[index][0];
        lastLetterCol = selectedLetters[index][1];

        if (curLetterRow == lastLetterRow && curLetterCol == lastLetterCol) {
            return true;
        }
        else {
            return false;
        }
    }



    /**
     * adds letter to word
     *
     * @param word            the word to edit
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     * @param curLetterRow    the row that the new letter is in
     * @param curLetterCol    the col that the new col is in
     * @param letter          letter to add to end of word
     */
    public void addLetter(String word, int[][] selectedLetters, int curLetterRow, int curLetterCol, String letter) {

        int index = getLastLetterIndex(selectedLetters);
     selectedLetters[index + 1][0] = curLetterRow;
        selectedLetters[index + 1][1] = curLetterCol;


    }

    public String addToWord(String currentWord, String letter) {

        if (currentWord.length() == 0 || currentWord == null) {
            currentWord = letter;
        }
        else if (currentWord.length() > 0) {
            currentWord = currentWord.concat(letter);
        }

        return currentWord;

    }

    public String removeFromWord(String currentWord) {

        String lastLetter = currentWord.substring(currentWord.length() - 1);

        if (currentWord.length() > 0 && !lastLetter.equals("u")) {
            currentWord = currentWord.substring(0, currentWord.length() - 1);
        }
        else if (currentWord.length() > 0 && lastLetter.equals("u")) {
            currentWord = currentWord.substring(0, currentWord.length() - 2);
        }



        return currentWord;
    }

    /**
     * checks if tile can be added based on the grounds that it has not already been added and
     * it is adjacent to the last tile picked
     *
     * @param selectedLetters 2d array for the coordinates of each letter tiles
     * @param curLetterRow    the row that the new letter is in
     * @param curLetterCol    the col that the new letter is in
     * @param lastLetterRow   the row that the last selected letter was in
     * @param lastLetterCol   the col that the last selected letter was in
     */
    public Boolean canAdd(int[][] selectedLetters, int curLetterRow, int curLetterCol,
                          int lastLetterRow, int lastLetterCol) {

        if (getLastLetterRow(selectedLetters) == 4 ) {
            return true;
        }
        else if (isCurrentAdjacentToLast
                (lastLetterRow, lastLetterCol, curLetterRow, curLetterCol) == 1) {

            return true;
        } else {
            return false;
        }
    }


    /**
     * Updates the score based on the length of the word entered
     * Words of size 3 and 4 = 1 point
     * 5 = 2 points
     * 6 = 3 points
     * 7 = 5 points
     * 8 or more = 11 points
     * <p/>
     * CAVEAT: Does not take into account who made the move. This is a feature that will be added
     * in later versions of this class.
     *
     * @param word the word the user has submitted
     */
    public int compUpdateScore(String word){
        int score = 0;

        //Check to see how long the word is
        if (word.length() <= 4 && word.length() >= 3) {
            score = 1;
        } else if (word.length() == 5) {
            score = 2;
        } else if (word.length() == 6) {
            score = 3;
        } else if (word.length() == 7) {
            score = 5;
        } else if (word.length() >= 8) {
            score = 11;
        }
        return score;

    }
    public int updateScore(String word) {
        int score = 0;

        //Check to see how long the word is
        if (word.length() <= 4 && word.length() >= 3) {
            score = 1;
        } else if (word.length() == 5) {
            score = 2;
        } else if (word.length() == 6) {
            score = 3;
        } else if (word.length() == 7) {
            score = 5;
        } else if (word.length() >= 8) {
            score = 11;
        }
        //Update the player's score
        //player1Score = score;

        //resets the values of the selectedLetters array
        for (int i = 0; i < 20; i++) {
            selectedLetters[i][0] = 4;
            selectedLetters[i][1] = 4;
        }
        return score;
    }

    /**
     * Rotates the board
     *
     * @param board two dimensional array representing the board
     */
    public void rotateBoard(String[][] board) {
        String[][] tmp = new String[4][4];
        for (int i = 0; i < 4; ++i) {
            for (int j = 0; j < 4; ++j) {
                tmp[i][j] = board[4 - j - 1][i]; //Rotates the board
            }
        }
        /**
         * External Citation
         *
         * Date: 15 March 2016
         * Problem: Didn't know how to rotate a two dimensional array.
         * Resource: http://stackoverflow.com/questions/42519/how-do-you-rotate-
         * a-two-dimensional-array
         * Solution: Used the sample code from this post.
         */

        //Copies the rotated board to the existing board
        gameBoard = tmp;
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
//public void findWords(HashSet<String> dict, String[][] board, int row, int col, String currWord, boolean[][] visited, ArrayList<String> found) {
//
//
//    for (int x = row-1; x <= row+1 ; x++) {
//        for (int y = col-1; y <= col+1; y++) {
//
////            if (row >= 0 && col >= 0) {
//                try {
//                    if (visited[x][y]) continue;  //makes sure tile is not visited twice
//                    visited[x][y] = true;
//                } catch (ArrayIndexOutOfBoundsException e) {
//                    continue;
//                }
//                word = currWord + board[x][y];
//                if (found.contains(word)) continue; //if word already exsists in list dont add
//                //if word is over 3 letters and in dictionary add to found list
//                if (word.length() > 2 && dict.contains(word.toLowerCase())) {
//                    setFound(word);
//                }
//            boolean[][] copy = new boolean[4][4]; // copy of visited tiles
//               for(int i = 0; i < 4; i++){
//                    for(int j = 0; j < 4; j++){
//                        copy[i][j] = visited[i][j];
//                    }
//                }
//            ArrayList<String> copy2 = new ArrayList<String>(); //copy of found list
//            for(int i = 0; i< found.size(); i++){
//                copy2.add(found.get(i));
//            }
//                findWords(dict, board, x, y, word, copy, copy2);
//        }
//    }
//
//    return;
//}


    /**
     * checks if tile to select is adjacent to last tile picked.
     *
     * @param lastLetterRow the row that the last selected letter was in
     * @param lastLetterCol the col that the last selected letter was in
     * @param curLetterRow  the row that the new letter is in
     * @param curLetterCol  the col that the new letter is in
     */
    public int isCurrentAdjacentToLast(int lastLetterRow,
                                       int lastLetterCol, int curLetterRow, int curLetterCol) {


        int trueOrFalse = 0; //false is 0, true is 1
        //didn't use boolean because it wasn't working


        if (curLetterRow == lastLetterRow - 1) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }

        if (curLetterRow == lastLetterRow) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }

        if (curLetterRow == lastLetterRow + 1) {
            if (curLetterCol == lastLetterCol - 1 || curLetterCol == lastLetterCol || curLetterCol == lastLetterCol + 1) {
                trueOrFalse = 1;
            }
        }


        return trueOrFalse;
    }

    /**
     * @param word Adds a sumbitted correct word to the word bank
     */
    public void addToWordBank(String word) {
        //Adds it to the arrayList
        wordBank.add(word);
    }
}








