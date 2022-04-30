import controller.TicTacToeController;
import controller.ThreeInARowController;

public class RowGameApp 
{
    /**
     * Starts a new game in the GUI.
     */
    public static void main(String[] args) throws IllegalArgumentException{
        if (args.length != 4){
            System.out.println("Four arguments requires: Board Width, Board Height, Line Length to Win, and Game " +
                    "Mode. Line Length must be at least 2 and cannot exceed both Width and Height. All arguments " +
                    "must be positive Integers. Game Mode is either 0 (TicTacToe) or 1 (Three In A Row).");
            throw new IllegalArgumentException();
        }

        if (!isInteger(args[0]) || !isInteger(args[1]) || !isInteger(args[2]) || !isInteger(args[3])){
            System.out.println("All inputs must be integers!");
            throw new IllegalArgumentException();
        }

        int width = Integer.parseInt(args[0]);
        int height = Integer.parseInt(args[1]);
        int toWin = Integer.parseInt(args[2]);
        int mode = Integer.parseInt(args[3]);

        if (width < 1 || height < 1){
            System.out.println("Width and height must be positive!");
            throw new IllegalArgumentException();
        }

        if (toWin < 2 || (toWin > width && toWin > height)){
            System.out.println("Line length needs to be at least 2 and can't be larger than both width and height");
            throw new IllegalArgumentException();
        }

        if (mode != 0 && mode != 1){
            System.out.println("Game Mode must either be 0 (TicTacToe) or 1 (Three In A Row)!");
            throw new IllegalArgumentException();
        }

        System.out.println("Creating board of size (" + args[0] + ", " + args[1] + ")...");

        if (mode == 0){
            TicTacToeController game = new TicTacToeController(width, height, toWin);
            game.setGUIVisibility(true);
        } else {
            ThreeInARowController game = new ThreeInARowController(width, height, toWin);
            game.setGUIVisibility(true);
        }

    }

    /**
     * Simple method that checks if a String is an integer
     *
     * @param s: String to analyze
     * @return True if the String is an integer, False otherwise
     */

    private static boolean isInteger(String s) {
        try {
            Integer.parseInt(s);
        } catch(NumberFormatException e) {
            return false;
        } catch(NullPointerException e) {
            return false;
        }
        // only got here if we didn't return false
        return true;
    }
}
