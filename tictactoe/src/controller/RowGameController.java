package controller;

import model.RowGameModel;
import view.RowGameGUI;

public abstract class RowGameController {
    protected RowGameModel gameModel;
    protected int lastMoveRow = -1;
    protected int lastMoveColumn = -1;

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController(int m, int n, int toWin) {
		gameModel = new RowGameModel(m, n, toWin, this);
    }

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameController(int m, int n, int toWin, boolean flag) {
        gameModel = new RowGameModel(m, n, toWin, this);
        gameModel.setGUIVisibility(flag);
    }

    /**
     * Tells the model to update itself at a certain position
     *
     * @param x The row of the chosen tile
     * @param y The column of the chosen tile
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

        //Result = -1 if no winner
        if (result == -1){
            //No winner is found
            lastMoveRow = x;
            lastMoveColumn = y;
        }
    }

    /**
     * Resets the game to be able to start playing again.
     */
    public void resetGame() {
        gameModel.reset();
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
        return gameModel.getGUI();
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

        //Reset last move row and column
        lastMoveRow = -1;
        lastMoveColumn = -1;
    }
}
