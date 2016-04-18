package edu.up.cs301.boggle;

import android.graphics.Color;
import android.text.Spannable;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;

import edu.up.cs301.game.GameHumanPlayer;
import edu.up.cs301.game.GameMainActivity;
import edu.up.cs301.game.R;
import edu.up.cs301.game.infoMsg.GameInfo;

/**
 * A GUI of a boggle player
 *
 * @author Charles Rayner
 * @author Michael Waitt
 * @author Jacob Kirby
 * @version March 2016
 */
public class BoggleHumanPlayer extends GameHumanPlayer implements BogglePlayer, OnClickListener {
    // the most recent game state, as given to us by the CounterLocalGame
    private BoggleState state;
    Boolean chosen = false;

    // The TextViews that displays the current counter value
    protected TextView letterDisplayTextView, timer, yourScoreNumberTextView, usedWordsTextView,
            opponentScoreNumberTextView,compWordTextView;
    public static String buttonLetter;

    public static String tile1ButtonLetter, tile2ButtonLetter, tile3ButtonLetter, tile4ButtonLetter,
            tile5ButtonLetter, tile6ButtonLetter, tile7ButtonLetter, tile8ButtonLetter,
            tile9ButtonLetter, tile10ButtonLetter, tile11ButtonLetter, tile12ButtonLetter,
            tile13ButtonLetter, tile14ButtonLetter, tile15ButtonLetter, tile16ButtonLetter;

    public static Boolean tile1ButtonPushed, tile2ButtonPushed, tile3ButtonPushed, tile4ButtonPushed,
            tile5ButtonPushed, tile6ButtonPushed, tile7ButtonPushed, tile8ButtonPushed,
            tile9ButtonPushed, tile10ButtonPushed, tile11ButtonPushed, tile12ButtonPushed,
            tile13ButtonPushed, tile14ButtonPushed, tile15ButtonPushed, tile16ButtonPushed;
    BoggleTimerOutAction gameOver;
    protected Button tile1Button, tile2Button, tile3Button, tile4Button, tile5Button, tile6Button,
            tile7Button, tile8Button, tile9Button, tile10Button, tile11Button, tile12Button, tile13Button,
            tile14Button, tile15Button, tile16Button, rotateButton, submitScoreButton;

    // the android activity that we are running
    private GameMainActivity myActivity;


    /**
     * constructor
     * @param name the name of a player
     */
    public BoggleHumanPlayer(String name) {
        super(name);
    }

    public View getTopView() {
        return myActivity.findViewById(R.id.AppLayout);
    }

    @Override
    public void receiveInfo(GameInfo info) {

        if (info instanceof BoggleState) {

            chosen = true;
            state = (BoggleState) info;
            int[][] selectedLetters = state.getSelectedLetters();
            if(this.playerNum == 0) {
                yourScoreNumberTextView.setText("" + state.getPlayer1Score());
                opponentScoreNumberTextView.setText("" + state.getPlayer2Score());
                letterDisplayTextView.setText("" + state.getCurrentWord(playerNum));
            }
            else{
                yourScoreNumberTextView.setText("" + state.getPlayer2Score());
                opponentScoreNumberTextView.setText("" + state.getPlayer1Score());
                letterDisplayTextView.setText("" + state.getCurrentWord(playerNum));
            }


            if (state.getWordBank(playerNum) != null) {
                for (int i = 0; i < state.getWordBank(playerNum).size(); i++) {
                    if (state.getWordBank(playerNum).get(i) != null &&
                            !usedWordsTextView.getText().toString().contains(state.getWordBank(playerNum).get(i))) {
                        usedWordsTextView.setText("" + state.getWordBank(playerNum).get(i) +
                                "\n" + usedWordsTextView.getText());
                    }
                }
            }
//            if (!(state.getCompCurWord().equals(state.getCompPrevWord()))) {
//                compWordTextView.setText("" + state.getCompCurWord() + "\n" + compWordTextView.getText());
//                state.getCompUsedWords().add(state.getCompCurWord());
//                state.setCompPrevWord(state.getCompCurWord());
//                state.setCompCurWord("");
//            }

            String[][] gameBoard = state.getGameBoard();
            int seconds1 = state.getSecondsLeft();


            tile1Button.setText(state.getCurLetterFromBoard(0, 0, gameBoard));
            tile2Button.setText(state.getCurLetterFromBoard(1, 0, gameBoard));
            tile3Button.setText(state.getCurLetterFromBoard(2, 0, gameBoard));
            tile4Button.setText(state.getCurLetterFromBoard(3, 0, gameBoard));
            tile5Button.setText(state.getCurLetterFromBoard(0, 1, gameBoard));
            tile6Button.setText(state.getCurLetterFromBoard(1, 1, gameBoard));
            tile7Button.setText(state.getCurLetterFromBoard(2, 1, gameBoard));
            tile8Button.setText(state.getCurLetterFromBoard(3, 1, gameBoard));
            tile9Button.setText(state.getCurLetterFromBoard(0, 2, gameBoard));
            tile10Button.setText(state.getCurLetterFromBoard(1, 2, gameBoard));
            tile11Button.setText(state.getCurLetterFromBoard(2, 2, gameBoard));
            tile12Button.setText(state.getCurLetterFromBoard(3, 2, gameBoard));
            tile13Button.setText(state.getCurLetterFromBoard(0, 3, gameBoard));
            tile14Button.setText(state.getCurLetterFromBoard(1, 3, gameBoard));
            tile15Button.setText(state.getCurLetterFromBoard(2, 3, gameBoard));
            tile16Button.setText(state.getCurLetterFromBoard(3, 3, gameBoard));

            String seconds = ""+state.getSecondsLeft() % 60;
            String minutes = ""+state.getSecondsLeft() / 60;
            if (seconds.length()<2) {
                seconds = "0"+seconds;
            }

            String time = (minutes + ":" + seconds);
            timer.setText(time);
//            if (!state.isTimer()) {
//                state.setGameOver(1);
//            }

            if(state.getSecondsLeft() == 0){

                    compWordTextView.setText("Opponents Words: \n");

                    for (int i = 0; i<state.getCompUsedWords().size(); i++) {
                        if(state.getWordBank(playerNum).contains(state.getCompUsedWords().get(i))) {
                            int points = 0;
                            if(state.getCompUsedWords().get(i).length() == 3 || state.getCompUsedWords().get(i).length() == 4)
                            {
                                points = 1;
                            }else if(state.getCompUsedWords().get(i).length() == 5){
                                points = 2;
                            }else if(state.getCompUsedWords().get(i).length() == 6){
                                points = 3;
                            }else if(state.getCompUsedWords().get(i).length() == 7){
                                points = 5;
                            }else if(state.getCompUsedWords().get(i).length() >= 8 ){
                                points = 11;
                            }compWordTextView.append(state.getCompUsedWords().get(i) + "      -" + points + "\n");

                                yourScoreNumberTextView.setText("" + (state.getPlayer1Score() - points));
                                opponentScoreNumberTextView.setText("" + (state.getPlayer2Score() - points));
                                submitScoreButton.setEnabled(false);
                                rotateButton.setEnabled(false);

                               //SET ALL THE BUTTONS TO DISABLED ONCE THE TIMER ISSUE IS SOLVED



                        }
                        else{
                          //  compWordTextView.setTextColor(Color.BLACK);
                            compWordTextView.append(state.getCompUsedWords().get(i) + "\n");
                        }

                }
                gameOver = new BoggleTimerOutAction(this);
                game.sendAction(gameOver);
            }
        }
    }

    public void setAsGui(GameMainActivity activity) {

        // remember the activity
        myActivity = activity;

        // Load the layout resource for our GUI
        activity.setContentView(R.layout.boggle_human_player);

        // if we have a game state, "simulate" that we have just received
        // the state from the game so that the GUI values are updated

        String[][] gameBoard = new String[4][4];
        for (int i = 0; i < 3; i++) {
            gameBoard[i][0] = "a";
            gameBoard[i][1] = "a";
            gameBoard[i][2] = "a";
            gameBoard[i][3] = "a";
        }


        if (state != null) {
            receiveInfo(state);
            gameBoard = state.getGameBoard();
        }
        tile1ButtonPushed = false;
        tile2ButtonPushed = false;
        tile3ButtonPushed = false;
        tile4ButtonPushed = false;
        tile5ButtonPushed = false;
        tile6ButtonPushed = false;
        tile7ButtonPushed = false;
        tile8ButtonPushed = false;
        tile9ButtonPushed = false;
        tile10ButtonPushed = false;
        tile11ButtonPushed = false;
        tile12ButtonPushed = false;
        tile13ButtonPushed = false;
        tile14ButtonPushed = false;
        tile15ButtonPushed = false;
        tile16ButtonPushed = false;


        tile1Button = (Button) activity.findViewById(R.id.tile1Button);
        tile1Button.setOnClickListener(this);


        tile2Button = (Button) activity.findViewById(R.id.tile2Button);
        tile2Button.setOnClickListener(this);


        tile3Button = (Button) activity.findViewById(R.id.tile3Button);
        tile3Button.setOnClickListener(this);


        tile4Button = (Button) activity.findViewById(R.id.tile4Button);
        tile4Button.setOnClickListener(this);


        tile5Button = (Button) activity.findViewById(R.id.tile5Button);
        tile5Button.setOnClickListener(this);


        tile6Button = (Button) activity.findViewById(R.id.tile6Button);
        tile6Button.setOnClickListener(this);


        tile7Button = (Button) activity.findViewById(R.id.tile7Button);
        tile7Button.setOnClickListener(this);


        tile8Button = (Button) activity.findViewById(R.id.tile8Button);
        tile8Button.setOnClickListener(this);


        tile9Button = (Button) activity.findViewById(R.id.tile9Button);
        tile9Button.setOnClickListener(this);


        tile10Button = (Button) activity.findViewById(R.id.tile10Button);
        tile10Button.setOnClickListener(this);


        tile11Button = (Button) activity.findViewById(R.id.tile11Button);
        tile11Button.setOnClickListener(this);


        tile12Button = (Button) activity.findViewById(R.id.tile12Button);
        tile12Button.setOnClickListener(this);


        tile13Button = (Button) activity.findViewById(R.id.tile13Button);
        tile13Button.setOnClickListener(this);


        tile14Button = (Button) activity.findViewById(R.id.tile14Button);
        tile14Button.setOnClickListener(this);


        tile15Button = (Button) activity.findViewById(R.id.tile15Button);
        tile15Button.setOnClickListener(this);


        tile16Button = (Button) activity.findViewById(R.id.tile16Button);
        tile16Button.setOnClickListener(this);


        submitScoreButton = (Button) activity.findViewById(R.id.submitScoreButton);
        submitScoreButton.setOnClickListener(this);

        yourScoreNumberTextView = (TextView) activity.findViewById(R.id.yourScoreNumberTextView);
        letterDisplayTextView = (TextView) activity.findViewById(R.id.letterDisplayTextView);
        opponentScoreNumberTextView = (TextView) activity.findViewById(R.id.opponentScoreNumberTextView);
        compWordTextView = (TextView) activity.findViewById((R.id.compWordTextView));
        rotateButton = (Button) activity.findViewById(R.id.rotateBackwardButton);
        rotateButton.setOnClickListener(this);

        timer = (TextView) activity.findViewById(R.id.timerText);
        usedWordsTextView = (TextView)activity.findViewById(R.id.usedWordsTextView);
    }


    public void onClick(View v) {
        BoggleSelectTileAction select;
        BoggleDeSelectTileAction deSelect;
        BoggleSubmitScoreAction submitScore;
        BoggleRotateAction rotateAction;

        if(this.state == null){
            return;
        }

        int[][] selectedLetters = state.getSelectedLetters();
        int lastLetterRow = state.getLastLetterRow(selectedLetters);
        int lastLetterCol = state.getLastLetterCol(selectedLetters);
        String currentWord = state.getCurrentWord(playerNum);

        String[][] gameBoard = state.getGameBoard();


        if (v == tile1Button && !tile1ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 0;

            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile1Button.setBackgroundColor(0x86090404);
                tile1ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);
                }

            }

        } else if (v == tile1Button && tile1ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 0;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile1Button.setBackgroundResource(R.mipmap.wood1);
                tile1ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile2Button && !tile2ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 0;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile2Button.setBackgroundColor(0x86090404);
                tile2ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);
                }

            }

        } else if (v == tile2Button && tile2ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 0;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile2Button.setBackgroundResource(R.mipmap.wood1);
                tile2ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile3Button && !tile3ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 0;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile3Button.setBackgroundColor(0x86090404);
                tile3ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);
                }

            }

        } else if (v == tile3Button && tile3ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 0;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile3Button.setBackgroundResource(R.mipmap.wood1);
                tile3ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }


        } else if (v == tile4Button && !tile4ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 0;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile4Button.setBackgroundColor(0x86090404);
                tile4ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);
                }

            }

        } else if (v == tile4Button && tile4ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 0;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile4Button.setBackgroundResource(R.mipmap.wood1);
                tile4ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile5Button && !tile5ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 1;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile5Button.setBackgroundColor(0x86090404);
                tile5ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile5Button && tile5ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 1;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile5Button.setBackgroundResource(R.mipmap.wood1);
                tile5ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile6Button && !tile6ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 1;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile6Button.setBackgroundColor(0x86090404);
                tile6ButtonPushed = true;
                select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                game.sendAction(select);
                state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

            }

        } else if (v == tile6Button && tile6ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 1;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile6Button.setBackgroundResource(R.mipmap.wood1);
                tile6ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile7Button && !tile7ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 1;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile7Button.setBackgroundColor(0x86090404);
                tile7ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile7Button && tile7ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 1;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile7Button.setBackgroundResource(R.mipmap.wood1);
                tile7ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile8Button && !tile8ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 1;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile8Button.setBackgroundColor(0x86090404);
                tile8ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile8Button && tile8ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 1;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile8Button.setBackgroundResource(R.mipmap.wood1);
                tile8ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile9Button && !tile9ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 2;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile9Button.setBackgroundColor(0x86090404);
                tile9ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile9Button && tile9ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 2;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile9Button.setBackgroundResource(R.mipmap.wood1);
                tile9ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile10Button && !tile10ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 2;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile10Button.setBackgroundColor(0x86090404);
                tile10ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile10Button && tile10ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 2;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile10Button.setBackgroundResource(R.mipmap.wood1);
                tile10ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile11Button && !tile11ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 2;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile11Button.setBackgroundColor(0x86090404);
                tile11ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile11Button && tile11ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 2;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile11Button.setBackgroundResource(R.mipmap.wood1);
                tile11ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile12Button && !tile12ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 2;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile12Button.setBackgroundColor(0x86090404);
                tile12ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile12Button && tile12ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 2;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile12Button.setBackgroundResource(R.mipmap.wood1);
                tile12ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile13Button && !tile13ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 3;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile13Button.setBackgroundColor(0x86090404);
                tile13ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile13Button && tile13ButtonPushed) {
            int curLetterRow = 0;
            int curLetterCol = 3;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile13Button.setBackgroundResource(R.mipmap.wood1);
                tile13ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile14Button && !tile14ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 3;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile14Button.setBackgroundColor(0x86090404);
                tile14ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile14Button && tile14ButtonPushed) {
            int curLetterRow = 1;
            int curLetterCol = 3;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile14Button.setBackgroundResource(R.mipmap.wood1);
                tile14ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile15Button && !tile15ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 3;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile15Button.setBackgroundColor(0x86090404);
                tile15ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile15Button && tile15ButtonPushed) {
            int curLetterRow = 2;
            int curLetterCol = 3;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile15Button.setBackgroundResource(R.mipmap.wood1);
                tile15ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }

        } else if (v == tile16Button && !tile16ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 3;
            boolean canAdd = state.canAdd(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canAdd) {
                tile16Button.setBackgroundColor(0x86090404);
                tile16ButtonPushed = true;
                if(chosen) {
                    chosen = false;
                    select = new BoggleSelectTileAction(this, curLetterRow, curLetterCol);
                    game.sendAction(select);
                    state.addLetter(currentWord, selectedLetters, curLetterRow, curLetterCol, gameBoard[curLetterRow][curLetterCol]);

                }
            }

        } else if (v == tile16Button && tile16ButtonPushed) {
            int curLetterRow = 3;
            int curLetterCol = 3;
            boolean canRemove = state.canRemove(selectedLetters, curLetterRow, curLetterCol, lastLetterRow, lastLetterCol);
            state.setCurLetter(state.getCurLetterFromBoard(curLetterRow, curLetterCol, gameBoard));

            if (canRemove) {
                tile16Button.setBackgroundResource(R.mipmap.wood1);
                tile16ButtonPushed = false;
                if(chosen) {
                    chosen = false;
                    deSelect = new BoggleDeSelectTileAction(this);
                    game.sendAction(deSelect);
                    state.removeLetter(currentWord, selectedLetters);
                }

            }
        }


        if (v == submitScoreButton) {

            submitScore = new BoggleSubmitScoreAction(this, state.getCurrentWord(playerNum));
            game.sendAction(submitScore);

            for (int i = 0; i < 20; i++) {
               selectedLetters[i][0] = 4;
               selectedLetters[i][1] = 4;

           }
            state.setSelectedLetters(selectedLetters);
            tile1Button.setBackgroundResource(R.mipmap.wood1);
            tile2Button.setBackgroundResource(R.mipmap.wood1);
            tile3Button.setBackgroundResource(R.mipmap.wood1);
            tile4Button.setBackgroundResource(R.mipmap.wood1);
            tile5Button.setBackgroundResource(R.mipmap.wood1);
            tile6Button.setBackgroundResource(R.mipmap.wood1);
            tile7Button.setBackgroundResource(R.mipmap.wood1);
            tile8Button.setBackgroundResource(R.mipmap.wood1);
            tile9Button.setBackgroundResource(R.mipmap.wood1);
            tile10Button.setBackgroundResource(R.mipmap.wood1);
            tile11Button.setBackgroundResource(R.mipmap.wood1);
            tile12Button.setBackgroundResource(R.mipmap.wood1);
            tile13Button.setBackgroundResource(R.mipmap.wood1);
            tile14Button.setBackgroundResource(R.mipmap.wood1);
            tile15Button.setBackgroundResource(R.mipmap.wood1);
            tile16Button.setBackgroundResource(R.mipmap.wood1);

            tile1ButtonPushed = false;
            tile2ButtonPushed = false;
            tile3ButtonPushed = false;
            tile4ButtonPushed = false;
            tile5ButtonPushed = false;
            tile6ButtonPushed = false;
            tile7ButtonPushed = false;
            tile8ButtonPushed = false;
            tile9ButtonPushed = false;
            tile10ButtonPushed = false;
            tile11ButtonPushed = false;
            tile12ButtonPushed = false;
            tile13ButtonPushed = false;
            tile14ButtonPushed = false;
            tile15ButtonPushed = false;
            tile16ButtonPushed = false;
        }


        if (v == rotateButton) {
            if (!tile1ButtonPushed && !tile2ButtonPushed && !tile3ButtonPushed && !tile4ButtonPushed &&
                    !tile5ButtonPushed && !tile6ButtonPushed && !tile7ButtonPushed && !tile8ButtonPushed &&
                            !tile9ButtonPushed && !tile10ButtonPushed && !tile11ButtonPushed && !tile12ButtonPushed &&
                                    !tile13ButtonPushed && !tile14ButtonPushed && !tile15ButtonPushed &&
                                            !tile16ButtonPushed) {
                rotateAction = new BoggleRotateAction(this);
                game.sendAction(rotateAction);
            }
            else {
                Toast.makeText(myActivity, "Cannot Rotate While Letters Are Selected", Toast.LENGTH_SHORT).show();
            }
        }
    }

}


// class BoggleHumanPlayer






