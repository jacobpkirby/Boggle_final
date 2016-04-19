package edu.up.cs301.boggle;

import junit.framework.TestCase;

import org.junit.Test;

import java.io.IOException;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;


/**
 * Created by Jacob on 4/18/2016.
 */
public class BoggleStateTest extends TestCase {
    BoggleState state = new BoggleState();
    @Test
    public void testWordLength() throws Exception {

        boolean lengthLongEnough1 = state.wordLength("hello"); //tests a word long enough
        assertTrue(lengthLongEnough1);
        boolean lengthLongEnough2 = state.wordLength(""); //tests no word
        assertFalse(lengthLongEnough2);
        boolean lengthLongEnough3 = state.wordLength("hi"); //tests a short word
        assertFalse(lengthLongEnough3);

    }

    @Test
    public void testCanRemove() throws Exception{
        int[][] selectedLetters = new int[16][2];
        for (int i = 0; i < 16; i++) {
            selectedLetters[i][0] = 4; //4 repersents null
            selectedLetters[i][1] = 4;// 4 repersents null
        }
        selectedLetters[0][0] = 3;
        selectedLetters[0][1] = 3;

        boolean choice1;
        choice1 = state.canRemove(selectedLetters,3,3,1,1);
        assertTrue(choice1);

        boolean choice2;
        choice2 = state.canRemove(selectedLetters,1,2,1,1);
        assertFalse(choice2);
    }

    @Test
    public void testAddToWord() throws Exception {
        state.setCurrentWord("foo",1);
        String letter = "b";
        String word = state.addToWord(state.getCurrentWord(1), letter);
        assertEquals(word, "foob");

        state.setCurrentWord("",1);
        letter = "a";
        word = state.addToWord(state.getCurrentWord(1),letter);
        assertEquals(word, "a");
    }
    @Test
    public void testRemoveFromWord() throws Exception {
        String word1 = "hello";
        word1 = state.removeFromWord(word1);
        assertEquals(word1,"hell");

        String word2 = "";
        word2 = state.removeFromWord(word2);
        assertEquals(word2,"");

        String word3 = "a";
        word3 = state.removeFromWord(word3);
        assertEquals(word3,"");

    }
    @Test
    public void testCanAdd() throws Exception {
        int[][] selectedLetters = new int[16][2];
        for(int i = 0; i < 16; i++){
            selectedLetters[i][0] = 4;
            selectedLetters[i][1] = 4;
        }
        boolean trueOrFalse = state.canAdd(selectedLetters, 3, 3, 3, 3);
        assertEquals(trueOrFalse, true);

        selectedLetters[0][0] = 1;
        selectedLetters[0][1] = 2;

        trueOrFalse = state.canAdd(selectedLetters, 1, 2, 3, 3);
        assertEquals(trueOrFalse, false);
    }
    @Test
    public void testUpdateScore() throws Exception {
        String longWord = "pneumonoultramicroscopicsilicovolcanoconiosis";
        int score1 = state.updateScore(longWord);
        assertEquals(score1, 11);

        String littleWord = "hue";
        int score2 = state.updateScore(littleWord);
        assertEquals(score2, 1);

        String fourLetter = "goku";
        int score3 = state.updateScore(fourLetter);
        assertEquals(score3, 1);

        String fiveLetter = "hello";
        int score4 = state.updateScore(fiveLetter);
        assertEquals(score4, 2);

        String sixLetter = "pizzaz";
        int score5 = state.updateScore(sixLetter);
        assertEquals(score5, 3);

        String sevenLetter = "jacuzzi";
        int score6 = state.updateScore(sevenLetter);
        assertEquals(score6, 5);


        assertEquals(state.getSelectedLetters()[13][1], 4);
        assertEquals(state.getSelectedLetters()[11][0], 4);

    }
    @Test
    public void testCompUpdateScore() throws Exception {
        String longWord = "pneumonoultramicroscopicsilicovolcanoconiosis";
        int score1 = state.updateScore(longWord);
        assertEquals(score1, 11);

        String littleWord = "hue";
        int score2 = state.updateScore(littleWord);
        assertEquals(score2, 1);

        String fourLetter = "goku";
        int score3 = state.updateScore(fourLetter);
        assertEquals(score3, 1);

        String fiveLetter = "hello";
        int score4 = state.updateScore(fiveLetter);
        assertEquals(score4, 2);

        String sixLetter = "pizzaz";
        int score5 = state.updateScore(sixLetter);
        assertEquals(score5, 3);

        String sevenLetter = "jacuzzi";
        int score6 = state.updateScore(sevenLetter);
        assertEquals(score6, 5);

    }
    @Test
    public void testRotateBoard() throws Exception {

        String[][] gameBoard = state.getGameBoard(1);
        state.rotateBoard(gameBoard,1);
        gameBoard[0][0] = "a";
        gameBoard[0][1] = "a";
        gameBoard[0][2] = "a";
        gameBoard[0][3] = "a";
        gameBoard[1][0] = "b";
        gameBoard[1][1] = "b";
        gameBoard[1][2] = "b";
        gameBoard[1][3] = "b";
        gameBoard[2][0] = "c";
        gameBoard[2][1] = "c";
        gameBoard[2][2] = "c";
        gameBoard[2][3] = "c";
        gameBoard[3][0] = "d";
        gameBoard[3][1] = "d";
        gameBoard[3][2] = "d";
        gameBoard[3][3] = "d";

        assertEquals(gameBoard[3][3], "d");
        assertEquals(gameBoard[2][2], "c");
        assertEquals(gameBoard[1][1], "b");
        assertEquals(gameBoard[0][0], "a");
    }

    @Test
    public void testIsCurrentAdjacentToLast() throws Exception {
        assertEquals(state.isCurrentAdjacentToLast(0, 1, 0, 0), 1);
        assertEquals(state.isCurrentAdjacentToLast(1, 1, 1, 1), 0);

    }
    @Test
    public void testAddToWordBank() throws Exception {
        String longWord = "pneumonoultramicroscopicsilicovolcanoconiosis";
        state.addToWordBank(longWord,1);
        assertEquals(state.getWordBank(1).get(0), longWord);

        String testString = "test";
        state.addToWordBank(testString,1);
        assertEquals(state.getWordBank(1).get(1), testString);

        String testString1 = "test";
        state.addToWordBank(testString1,1);
        assertEquals(state.getWordBank(1).get(2), testString1);

        String testString2 = "test";
        state.addToWordBank(testString2,1);
        assertEquals(state.getWordBank(1).get(3), testString2);

        assertEquals(state.getWordBank(1).size(), 4);
    }



}