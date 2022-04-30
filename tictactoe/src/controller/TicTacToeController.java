package controller;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import view.RowGameGUI;

public class TicTacToeController extends RowGameController {

    /**
     * Creates a new game initializing the GUI.
     */
    public TicTacToeController(int m, int n, int toWin) {
        super(m, n, toWin);
    }

    /**
     * Boolean method that returns true if a specified position is legal
     *
     * @return True if legal, False otherwise
     */
    public boolean isLegal(int x, int y){
        return (x >= 0 && x < gameModel.getWidth() && y >= 0 && y < gameModel.getHeight());
    }
}
