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
    
    public int getPlayer(){
        return player;
    }
    
    public int getMovesLeft(){
        return movesLeft;
    }
    
    public void modMovesLeft(int value){
        movesLeft += value;
        if (movesLeft == 0){
            gameOver = true;
        }
    }

    public boolean isGameOver(){
        return gameOver;
    }

    public RowBlockModel getAtPos(int x, int y){
        return blocksData[x][y];
    }

    private String getPlayerString(){
        if (player == 1){
            return "X";
        } else {
            return "O";
        }
    }

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

    public int getWidth(){
        return m;
    }

    public int getHeight(){
        return n;
    }
}
