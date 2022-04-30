package view;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JPanel;
import java.awt.*;
import java.awt.event.*;

import model.RowGameModel;
import controller.RowGameController;
import java.util.*;

public class RowGameGUI {
    private JFrame gui = new JFrame("Tic Tac Toe");
    private ArrayList<ArrayList<JButton>> blocks = new ArrayList<ArrayList<JButton>>();
    private JButton reset = new JButton("Reset");
    private JButton undo = new JButton("Undo");
    private JTextArea playerturn = new JTextArea();

    /**
     * Creates a new game initializing the GUI.
     */
    public RowGameGUI(RowGameController controller, int m, int n, RowGameModel gameModel) {
        gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gui.setSize(new Dimension(275 + (75 * n), 125 + (75 * m)));
        gui.setResizable(true);

        JPanel gamePanel = new JPanel(new FlowLayout());
        JPanel game = new JPanel(new GridLayout(m,n));
        gamePanel.add(game, BorderLayout.CENTER);

        JPanel options = new JPanel(new FlowLayout());
        options.add(reset);
        options.add(undo);
        JPanel messages = new JPanel(new FlowLayout());
        messages.setBackground(Color.white);

        gui.add(gamePanel, BorderLayout.NORTH);
        gui.add(options, BorderLayout.CENTER);
        gui.add(messages, BorderLayout.SOUTH);

        messages.add(playerturn);
        playerturn.setText("'X': Player 1");

        reset.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.resetGame();
            }
        });

        undo.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                controller.undoMove();
            }
        });

        // Initialize a JButton for each cell of the mxn game board.
        for(int row = 0; row<m; row++) {
            for(int column = 0; column<n ;column++) {
                if (column == 0){
                    blocks.add(new ArrayList<JButton>());
                }
                blocks.get(row).add(new JButton());
                blocks.get(row).get(column).setPreferredSize(new Dimension(75,75));
                game.add(blocks.get(row).get(column));
                blocks.get(row).get(column).addActionListener(new ActionListener() {
                    public void actionPerformed(ActionEvent e) {
                        JButton toFind = (JButton)e.getSource();
                        int[] pos = new int[2];
                        for (ArrayList<JButton> row : blocks) {
                            int index = row.indexOf(toFind);
                            if (index >= 0){
                                pos[0] = blocks.indexOf(row);
                                pos[1] = index;
                                break;
                            }
                        }
                        controller.move(pos[0], pos[1]);
                    }
                });
            }
        }

        //Updates all of the model's blocks
        for(int row = 0; row<m; row++) {
            for(int column = 0; column<n ;column++) {
                updateBlock(gameModel,row,column);
            }
        }
    }

    /**
     * Updates the block at the given row and column 
     * after one of the player's moves.
     *
     * @param gameModel The RowGameModel containing the block
     * @param row The row that contains the block
     * @param column The column that contains the block
     */
    public void updateBlock(RowGameModel gameModel, int row, int column) {
        blocks.get(row).get(column).setText(gameModel.getAtPos(row, column).getContents());
    }

    /**
     * Reset method that clears the text from all blocks, called after setting their contents to an empty string
     *
     * @param gameModel: Model that is storing the blocks
     * @param m: Height of the board
     * @param n: Width of the board
     */
    public void resetBlocks(RowGameModel gameModel, int m, int n){
        for (int i = 0; i < m; i++){
            for (int q = 0; q < n; q++){
                updateBlock(gameModel, q, i);
            }
        }
    }

    /**
     * Sets the text stored in playerturn
     *
     * @param value: String to be stored in text
     */
    public void setText(String value){
        playerturn.setText(value);
    }

    /**
     * Sets the visibility of the GUI based on the value
     *
     * @param value: Sets GUI to visible (true) or not visible (false)
     */
    public void setGUIVisibility(boolean value){
        gui.setVisible(value);
    }

    /**
     * Gets the text stored in playerturn
     */
    public String getText(){
        return playerturn.getText();
    }
}
