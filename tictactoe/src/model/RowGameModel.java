package model;


public class RowGameModel 
{
    private RowBlockModel[][] blocksData;

    /**
     * The current player taking their turn
     */
    private int player = 1;

    //Number of Moves left
    private int movesLeft;

    //Length of line needed to win
    private int winLength;

    //Board size
    private int m; //height
    private int n; //width

    //Whether or not the game is over
    private boolean gameOver = false;

    /**
     * Constructor that initializes the model
     *
     * @param m: height of board
     * @param n: width of board
     * @param toWin: length of line needed to win
     */
    public RowGameModel(int m, int n, int toWin) {
        super();

        this.m = m;
        this.n = n;

        blocksData = new RowBlockModel[m][n];
        winLength = toWin;

        for (int row = 0; row < m; row++) {
            for (int col = 0; col < n; col++) {
                blocksData[row][col] = new RowBlockModel(this);
                blocksData[row][col].setContents("");
            } // end for col
        } // end for row

        movesLeft = m * n;
    }

    /**
     * Simple get method for private variable 'player'
     *
     * @return player whose turn it currently is
     */
    public int getPlayer(){
        return player;
    }

    /**
     * Simple get method for private variable 'movesLeft'
     *
     * @return number of possible moves left in the game
     */
    public int getMovesLeft(){
        return movesLeft;
    }

    /**
     * Simple set method for private variable
     * Also sets game over if the number of moves left is zero
     *
     * @param value by which to modify 'movesLeft'
     */
    public void modMovesLeft(int value){
        movesLeft += value;
        if (movesLeft == 0){
            gameOver = true;
        }
    }

    /**
     * Simple get method for private variable 'gameOver'
     *
     * @return whether or not the game is over
     */
    public boolean isGameOver(){
        return gameOver;
    }

    /**
     * Retrieves the RowBlockModel at a specified location in blocksData
     *
     * @param x: first index of blocksData to find
     * @param y: second index of blocksData to find
     * @return RowBlockModel object stored at position (x, y) in blocksData
     */
    public RowBlockModel getAtPos(int x, int y){
        return blocksData[x][y];
    }

    /**
     * Returns 'X' if the current player is Player 1, and 'O' otherwise
     *
     * @return String that matches the current player
     */
    private String getPlayerString(){
        if (player == 1){
            return "X";
        } else {
            return "O";
        }
    }

    /**
     * Called when a user clicks on a position on the board, setting its value if legal and checking for victory
     *
     * @param x: first index to set
     * @param y: second index to set
     * @return -1 if no winner, otherwise 1 or 2 for Player 1 or 2 winning respectively
     */
    public int takeMove(int x, int y){
        blocksData[x][y].setContents(getPlayerString());

        boolean victory = false;

        //Check horizontal and diagonal victory
        for (int q = -1; q <= 1; q++){
            for (int i = 0; i < winLength; i++) {
                if (allMatch(x, y, 1 - winLength + i, i, q, false)) {
                    victory = true;
                    break;
                }
            }
            if (victory){
                break;
            }
        }

        //Checking vertical victory if no horizontal or diagonal victory found
        if (!victory){
            for (int i = 0; i < winLength; i++){
                if (allMatch(x, y, 1 - winLength + i, i, 0, true)) {
                    victory = true;
                    break;
                }
            }
        }

        if (victory){
            gameOver = true;
            return player;
        } else {
            if (player == 1){
                player = 2;
            } else {
                player = 1;
            }
            return -1;
        }
    }

    /**
     * Method that takes a minimum and maximum number, and searches if all elements on a line for those x values
     * match the value at the origin. Note that the origin can be on the edge of a line or the middle, depending on
     * the values of min and max. The 'diff' argument is used to check for diagonals, searching with y + 1 or y - 1
     * at each step for upwards and downwards diagonals respectively.
     *
     * Vertical lines are checked differently, with no change in x-values.
     *
     * @param x: First index of starting position
     * @param y: Second index of starting position
     * @param min: Lowest x-value difference to check
     * @param max: Highest x-value difference to check
     * @param diff: How much to increment y-value while continuing
     * @param vertical: Flag that does a different check for no x-difference (i.e. vertical line)
     * @return
     */
    private boolean allMatch(int x, int y, int min, int max, int diff, boolean vertical){
        //Gets value to compare rest of line
        String value = blocksData[x][y].getContents();

        //Goes through the line, halts on edge cases and inequalities
        for (int i = min; i <= max; i++){
            if (i == 0){
                continue;
            }
            if (vertical){
                if (y+i < 0 || y+i >= n || !blocksData[x][y+i].getContents().equals(value)){
                    return false;
                }
            } else {
                if (x + i < 0 || x + i >= m || y+(diff * i) < 0 || y+(diff*i) >= n){
                    return false;
                }
                boolean test = (value.equals(blocksData[x+i][y+(diff * i)].getContents()));
                if (!test){
                    return false;
                }
            }
        }

        //True is only returned if all elements on line are equal and there are no out-of-bounds indices
        return true;
    }

    /**
     * Resets the game, setting basic parameters to run again
     */
    public void reset(){
        for (int i = 0; i < m; i++){
            for (int q = 0; q < n; q++){
                blocksData[i][q].reset();
            }
        }
        gameOver = false;
        movesLeft = m * n;
        player = 1;
    }

    /**
     * Simple get method that returns the height of the board
     * @return m
     */
    public int getWidth(){
        return m;
    }

    /**
     * Simple get method that returns the width of the board
     * @return n
     */
    public int getHeight(){
        return n;
    }

    /**
     * Testing method that returns an array of strings, each representing a line on the board
     * 
     * Notation is | Value | Value | Value |, with a number of values equal to the number of columns
     * and with Value representing X, O, or " " if the space has yet to be selected.
     * 
     * @return String array where each element represents one row of the Tic Tac Toe board
     */
    public String[] getBoardState(){
        String[] total = new String[m];
        for (int i = 0; i < m; i++){
            String line = "|";
            for (int q = 0; q < n; q++){
                String toAdd = getAtPos(i, q).getContents();
                if (toAdd.length() == 0){
                    toAdd = " ";
                }
                line += " " + toAdd + " |";
            }
            total[i] = line;
        }
        return total;
    }
    
    /**
     * Method that clears the content of a block at a given location
     * 
     * @param x: The row of the cleared tile
     * @param y: The column of the cleared tile
     */
    public void clearAt(int x, int y){
        blocksData[x][y].setContents("");
        player = 3 - player;
    }
}
