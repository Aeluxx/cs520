import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.junit.Assert.*;

import model.RowBlockModel;
import controller.RowGameController;

/**
 * An example test class, which merely shows how to write JUnit tests.
 */
public class TestExample {
    private RowGameController game;

    @Before
    public void setUp() {
	    game = new RowGameController(5, 5, 3);
    }

    @After
    public void tearDown() {
	    game = null;
    }

    /**
     * Test that creates a new board and ensures the default values are correct
     */
    @Test
    public void testNewGame() {
        game = new RowGameController(5, 5, 3);
        assertEquals (1, game.getModel().getPlayer());
        assertEquals (25, game.getModel().getMovesLeft());
        for (int i = 0; i < 5; i++){
            for (int q = 0; q < 5; q++){
                assertEquals("", game.getModel().getAtPos(i, q).getContents());
            }
        }
    }

    /**
     * Test that checks the board state after several moves
     */
    @Test
    public void testGameProgress() {
        game = new RowGameController(5, 5, 3);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(1, 1); //Player 2 moves at (1, 1)
        game.move(2, 2); //Player 1 moves at (2, 2)
        game.move(3, 3); //Player 2 moves at (3, 3)
        game.move(4, 4); //Player 1 moves at (4, 4)

        for (int i = 0; i < 5; i++){
            if (i % 2 == 0){
                assertEquals("X", game.getModel().getAtPos(i, i).getContents());
            } else {
                assertEquals("O", game.getModel().getAtPos(i, i).getContents());
            }
        }

        assertEquals(false, game.getModel().isGameOver());
    }

    /**
     * Test to ensure a victory is calculated correctly
     */
    @Test
    public void testGameVictory() {
        game = new RowGameController(5, 5, 3);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(1, 1); //Player 2 moves at (1, 1)
        game.move(2, 2); //Player 1 moves at (2, 2)
        game.move(3, 3); //Player 2 moves at (3, 3)
        game.move(4, 4); //Player 1 moves at (4, 4)
        game.move(4, 1); //Player 2 moves at (4, 1)
        game.move(4, 0); //Player 1 moves at (4, 0)
        game.move(3, 1); //Player 2 moves at (3, 1)
        game.move(3, 0); //Player 1 moves at (3, 0)
        game.move(2, 1); //Player 2 moves at (2, 1)

        for (int i = 0; i < 2; i++){
            assertEquals("X", game.getModel().getAtPos(4-i, 0).getContents());
            assertEquals("O", game.getModel().getAtPos(3-i, 1).getContents());
        }

        assertEquals(true, game.getModel().isGameOver());
        assertEquals(2, game.getModel().getPlayer());
    }

    /**
     * Test that ensures repeated (and as such illegal) moves do not change the board state
     */
    @Test
    public void testRepeatedMoves() {
        game = new RowGameController(5, 5, 3);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 0); //Player 2 tries a move at (0, 0) - doesn't work since already taken

        assertEquals("X", game.getModel().getAtPos(0, 0).getContents());
        assertEquals(2, game.getModel().getPlayer());
    }

    /**
     * Test a tied board state
     */
    @Test
    public void testTiedGame() {
        game = new RowGameController(3, 3, 3);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 2); //Player 2 moves at (1, 1)
        game.move(0, 1); //Player 1 moves at (2, 2)
        game.move(1, 0); //Player 2 moves at (0, 2)
        game.move(2, 0); //Player 1 moves at (0, 1)
        game.move(1, 1); //Player 2 moves at (1, 0)
        game.move(1, 2); //Player 1 moves at (1, 2)
        game.move(2, 1); //Player 2 moves at (1, 0)
        game.move(2, 2); //Player 1 moves at (1, 2)

        String[] expectedState = new String[]{
                "| X | X | O |",
                "| O | O | X |",
                "| X | O | X |"
        };

        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Its a draw!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());
    }

    /**
     * Runs a game, resets game, runs separate game
     * For each game and reset, validates game state and on reset ensures that the board is cleared
     */
    @Test
    public void testResetGame() {
        game = new RowGameController(2, 2, 2);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 1); //Player 2 moves at (0, 1)
        game.move(1, 1); //Player 1 moves at (1, 1) and wins

        String[] expectedState = new String[]{
                "| X | O |",
                "|   | X |"
        };

        //Validate that the above game runs correctly
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Player 1 Wins!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());

        game.resetGame();

        //Validate that game is cleared
        expectedState = new String[]{
                "|   |   |",
                "|   |   |"
        };

        //Validate that the above game runs correctly
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(false, game.getModel().isGameOver());
        assertEquals("'X': Player 1", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());

        game.move(1, 0); //Player 1 moves at (1, 0)
        game.move(0, 0); //Player 2 moves at (0, 0)
        game.move(0, 1); //Player 1 moves at (0, 1) and wins

        expectedState = new String[]{
                "| O | X |",
                "| X |   |"
        };

        //Validate that the above game runs correctly
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Player 1 Wins!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());
    }

    /**
     * Tries to make moves after the game is over
     */
    @Test
    public void testMoveAfterGame() {
        game = new RowGameController(2, 2, 2);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 1); //Player 2 moves at (0, 1)
        game.move(1, 1); //Player 1 moves at (1, 1) and wins

        String[] expectedState = new String[]{
                "| X | O |",
                "|   | X |"
        };

        //Validate that the above game runs correctly
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Player 1 Wins!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());

        //Futile Move
        game.move(1, 0); //Player 1 moves at (1, 0)

        //Check board state again
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Player 1 Wins!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());
    }

    /**
     * Tries to take moves outside of the game boundaries
     */
    @Test
    public void testMoveBoundaries() {
        game = new RowGameController(2, 2, 2);
        game.move(-1, -1); //Player 1 tries to move at (3, 3)
        game.move(3, 3); //Player 1 tries to move at (3, 3)

        String[] expectedState = new String[]{
                "|   |   |",
                "|   |   |"
        };

        //Validate that the above game runs correctly
        assertEquals(1, game.getModel().getPlayer());
        assertEquals(false, game.getModel().isGameOver());
        assertEquals("'X': Player 1", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());
    }

    /**
     * Tests that the game boundaries are set correctly
     */
    @Test
    public void testBoardSize() {
        for (int i = 2; i < 9; i++){
            for (int q = 2; q < 9; q++){
                game = new RowGameController(i, q, i);
                assertEquals(i, game.getModel().getWidth());
                assertEquals(q, game.getModel().getHeight());
            }

        }
    }

    /**
     * Tests a game move-by-move
     */
    @Test
    public void testMoveByMove() {
        game = new RowGameController(3, 3, 3);

        String[] expectedState = new String[]{
                "|   |   |   |",
                "|   |   |   |",
                "|   |   |   |"
        };

        String used = "X";
        boolean stop = false;
        for (int i = 0; i < 3; i++){
            for (int q = 0; q < 3; q++){
                game.move(i, q);
                expectedState[i] = expectedState[i].substring(0, 2 + (4 * q)) + used + expectedState[i].substring(2 + (4 * q) + 1);
                if (i == 2 && q == 0){
                    assertEquals(true, game.getModel().isGameOver());
                    stop = true;
                    break;
                }
            }
            if (stop){
                break;
            }
        }
    }

    /**
     * Tests a game where Player 2 wins
     */
    @Test
    public void testPlayer2Win() {
        game = new RowGameController(3, 3, 3);

        //Moves Taken
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 1); //Player 2 moves at (0, 1)
        game.move(1, 0); //Player 1 moves at (1, 0)
        game.move(1, 1); //Player 2 moves at (1, 1)
        game.move(2, 2); //Player 1 moves at (2, 2)
        game.move(2, 1); //Player 2 moves at (2, 1)

        String[] expectedState = new String[]{
                "| X | O |   |",
                "| X | O |   |",
                "|   | O | X |"
        };

        //Validate that the above game runs correctly
        assertEquals(2, game.getModel().getPlayer());
        assertEquals(true, game.getModel().isGameOver());
        assertEquals("Player 2 Wins!", game.getGUI().getText());
        assertEquals(expectedState, game.getModel().getBoardState());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	    RowBlockModel block = new RowBlockModel(null);
    }
}
