package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public class ThreeInARowController extends RowGameController {
    /**
     * Creates a new game initializing the GUI.
     */
    public ThreeInARowController(int m, int n, int toWin) {
		super(m, n, toWin);
    }
    public ThreeInARowController(int m, int n, int toWin, boolean flag) {
        super(m, n, toWin, flag);
    }

    /**
     * Boolean method that returns true if a specified position is legal
     *
     * @return True if legal, False otherwise
     */
    public boolean isLegal(int x, int y){
        if (x >= 0 && x < gameModel.getWidth() && y >= 0 && y < gameModel.getHeight()){
            return (x == gameModel.getWidth() - 1 || (gameModel.getAtPos(x + 1, y).getContents().length() > 0));
        }
        return false;
    }
}
