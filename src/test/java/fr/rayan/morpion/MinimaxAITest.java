package fr.rayan.morpion;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.List;
import java.util.Optional;
import java.util.Random;
import org.junit.jupiter.api.Test;

class MinimaxAITest {
    private final MinimaxAI ai = new MinimaxAI();

    @Test
    void takesTheWinningMove() {
        // X X .
        // O O .
        // . . .   -> X gagne en 2
        assertEquals(2, ai.bestMove(Board.of("XX.OO....")));
    }

    @Test
    void blocksTheOpponent() {
        // X X .
        // . O .
        // . . .   -> O doit bloquer en 2
        assertEquals(2, ai.bestMove(Board.of("XX..O....")));
    }

    @Test
    void prefersWinningOverBlocking() {
        // O joue : il peut gagner en 5 au lieu de bloquer X en 2
        assertEquals(5, ai.bestMove(Board.of("XX.OO.X..")));
    }

    @Test
    void perfectPlayersAlwaysDraw() {
        Board b = new Board();
        while (!b.isGameOver()) b.play(ai.bestMove(b));
        assertEquals(Optional.empty(), b.winner());
    }

    @Test
    void neverLosesAgainstRandomPlayer() {
        Random random = new Random(42);
        for (int game = 0; game < 300; game++) {
            char aiPlays = game % 2 == 0 ? 'X' : 'O';
            Board b = new Board();
            while (!b.isGameOver()) {
                if (b.currentPlayer() == aiPlays) {
                    b.play(ai.bestMove(b));
                } else {
                    List<Integer> moves = b.availableMoves();
                    b.play(moves.get(random.nextInt(moves.size())));
                }
            }
            char opponent = aiPlays == 'X' ? 'O' : 'X';
            assertNotEquals(Optional.of(opponent), b.winner(), "partie " + game + "\n" + b);
        }
    }

    @Test
    void boardRejectsIllegalMoves() {
        Board b = new Board();
        b.play(4);
        assertThrows(IllegalStateException.class, () -> b.play(4));
        assertThrows(IllegalArgumentException.class, () -> b.play(9));
        Board won = Board.of("XXXOO....");
        assertTrue(won.isGameOver());
        assertThrows(IllegalStateException.class, () -> won.play(8));
        assertThrows(IllegalArgumentException.class, () -> Board.of("XXX......"));
    }
}
