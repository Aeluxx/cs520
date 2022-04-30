package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public abstract class RowGameController {
    protected RowGameModel gameModel;
    protected RowGameGUI gameView;
    protected int lastMoveRow = -1;
    protected int lastMoveColumn = -1;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController(int m, int n, int toWin) {
		gameModel = new RowGameModel(m, n, toWin);
		gameView = new RowGameGUI(this, m, n, gameModel);
    }

    /**
     * Tells the model to update itself at a certain position
     *
     * @param block The block to be moved to by the current player
     */
    public void move(int x, int y) {
        //Checks whether the move is invalid, and doesn't process it if so
        if (!isLegal(x, y) || gameModel.isGameOver() || gameModel.getAtPos(x, y).getContents().length() > 0){
            return;
        }

        //Decrements the move count
        gameModel.modMovesLeft(-1);

        //Makes the model update at the position, and check for victory
        int result = gameModel.takeMove(x, y);
        gameView.updateBlock(gameModel, x, y);

        //Result = -1, 1, or 2 for No Winner, Player 1 Wins, and Player 2 Wins
        if (result == -1){
            //No winner is found
            if (gameModel.getMovesLeft() == 0){
                //No moves can be made
                gameView.setText("Its a draw!");
            } else {
                //Next player's turn
                if(gameModel.getMovesLeft()%2 == 1) {
                    gameView.setText("'X': Player 1");
                } else{
                    gameView.setText("'O': Player 2");
                }
            }

            lastMoveRow = x;
            lastMoveColumn = y;
        } else {
            //Player #result wins
            gameView.setText("Player " + result + " Wins!");
        }
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        gameModel.reset();
        gameView.setText("'X': Player 1");
        gameView.resetBlocks(gameModel, gameModel.getHeight(), gameModel.getWidth());
    }

    /**
     * Updates the GUI Visibility
     *
     * @param value on whether GUI is visible (true) or not (false)
     */
    public void setGUIVisibility(boolean value){
        gameView.setGUIVisibility(value);
    }

     /**
     * Simple get method to return the RowGameModel
     *
     * @return gameModel
     */
    public RowGameModel getModel(){
        return gameModel;
    }

    /**
     * Simple get method to return the RowGameGUI
     *
     * @return gameView
     */
    public RowGameGUI getGUI(){
        return gameView;
    }

    /**
     * Boolean method that returns true if a specified position is legal
     *
     * @return True if legal, False otherwise
     */
    public abstract boolean isLegal(int x, int y);

    /**
     * Tells the model to undo the last move taken
     **/
    public void undoMove() {
        //Checks whether there is a last move that can be undone
        if (lastMoveRow == -1 || gameModel.isGameOver()){
            return;
        }

        //Increments the move count
        gameModel.modMovesLeft(1);

        //Makes the model update at the position, and check for victory
        gameModel.clearAt(lastMoveRow, lastMoveColumn);
        gameView.updateBlock(gameModel, lastMoveRow, lastMoveColumn);

        //Reset last move row and column
        lastMoveRow = -1;
        lastMoveColumn = -1;

        //Set text to display new turn taker
        if(gameModel.getMovesLeft()%2 == 1) {
            gameView.setText("'X': Player 1");
        } else{
            gameView.setText("'O': Player 2");
        }
    }
}
