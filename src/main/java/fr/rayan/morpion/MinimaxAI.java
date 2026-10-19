package fr.rayan.morpion;

/**
 * IA imbattable : explore tout l'arbre des coups possibles (minimax) avec élagage alpha-bêta.
 * Une victoire rapide vaut plus qu'une victoire lente, une défaite lente vaut mieux qu'une défaite rapide.
 */
public class MinimaxAI {
    private static final int WIN = 10;
    private long nodes;

    public int bestMove(Board board) {
        if (board.isGameOver()) throw new IllegalStateException("aucun coup possible");
        Board b = board.copy();
        char me = b.currentPlayer();
        int bestScore = Integer.MIN_VALUE, best = -1;
        nodes = 0;
        for (int move : b.availableMoves()) {
            b.play(move);
            int score = minimax(b, me, 1, Integer.MIN_VALUE, Integer.MAX_VALUE);
            b.undo(move);
            if (score > bestScore) {
                bestScore = score;
                best = move;
            }
        }
        return best;
    }

    public long nodesExplored() {
        return nodes;
    }

    private int minimax(Board b, char me, int depth, int alpha, int beta) {
        nodes++;
        var winner = b.winner();
        if (winner.isPresent()) return winner.get() == me ? WIN - depth : depth - WIN;
        var moves = b.availableMoves();
        if (moves.isEmpty()) return 0;

        boolean maximizing = b.currentPlayer() == me;
        int best = maximizing ? Integer.MIN_VALUE : Integer.MAX_VALUE;
        for (int move : moves) {
            b.play(move);
            int score = minimax(b, me, depth + 1, alpha, beta);
            b.undo(move);
            if (maximizing) {
                best = Math.max(best, score);
                alpha = Math.max(alpha, score);
            } else {
                best = Math.min(best, score);
                beta = Math.min(beta, score);
            }
            if (beta <= alpha) break;
        }
        return best;
    }
}
