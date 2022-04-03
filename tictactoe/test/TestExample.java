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
     * Test that ensures repeated moves do not change the board state
     */
    @Test
    public void testRepeatedMoves() {
        game = new RowGameController(5, 5, 3);
        game.move(0, 0); //Player 1 moves at (0, 0)
        game.move(0, 0); //Player 2 tries a move at (0, 0) - doesn't work since already taken

        assertEquals("X", game.getModel().getAtPos(0, 0).getContents());
        assertEquals(2, game.getModel().getPlayer());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNewBlockViolatesPrecondition() {
	    RowBlockModel block = new RowBlockModel(null);
    }
}
