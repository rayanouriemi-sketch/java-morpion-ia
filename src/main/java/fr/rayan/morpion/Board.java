package fr.rayan.morpion;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/** Plateau 3x3. Les cases sont numérotées de 0 à 8, ligne par ligne. */
public class Board {
    public static final char EMPTY = ' ';
    private static final int[][] LINES = {
        {0, 1, 2}, {3, 4, 5}, {6, 7, 8}, // lignes
        {0, 3, 6}, {1, 4, 7}, {2, 5, 8}, // colonnes
        {0, 4, 8}, {2, 4, 6}             // diagonales
    };

    private final char[] cells = new char[9];
    private char current = 'X';

    public Board() {
        java.util.Arrays.fill(cells, EMPTY);
    }

    private Board(Board other) {
        System.arraycopy(other.cells, 0, cells, 0, 9);
        current = other.current;
    }

    /** Crée un plateau à partir d'une chaîne de 9 caractères ('X', 'O' ou '.'). */
    public static Board of(String layout) {
        if (layout.length() != 9) throw new IllegalArgumentException("9 cases attendues");
        Board b = new Board();
        int x = 0, o = 0;
        for (int i = 0; i < 9; i++) {
            char c = layout.charAt(i);
            if (c == 'X') x++;
            else if (c == 'O') o++;
            else if (c != '.') throw new IllegalArgumentException("caractère invalide : " + c);
            b.cells[i] = c == '.' ? EMPTY : c;
        }
        if (x != o && x != o + 1) throw new IllegalArgumentException("nombre de X et de O incohérent");
        b.current = x == o ? 'X' : 'O';
        return b;
    }

    public Board copy() {
        return new Board(this);
    }

    public char currentPlayer() {
        return current;
    }

    public char at(int index) {
        return cells[index];
    }

    public void play(int index) {
        if (index < 0 || index > 8) throw new IllegalArgumentException("case hors du plateau : " + index);
        if (cells[index] != EMPTY) throw new IllegalStateException("case déjà occupée : " + index);
        if (isGameOver()) throw new IllegalStateException("la partie est terminée");
        cells[index] = current;
        current = current == 'X' ? 'O' : 'X';
    }

    void undo(int index) {
        cells[index] = EMPTY;
        current = current == 'X' ? 'O' : 'X';
    }

    public Optional<Character> winner() {
        for (int[] line : LINES) {
            char c = cells[line[0]];
            if (c != EMPTY && c == cells[line[1]] && c == cells[line[2]]) return Optional.of(c);
        }
        return Optional.empty();
    }

    public List<Integer> availableMoves() {
        List<Integer> moves = new ArrayList<>();
        if (winner().isPresent()) return moves;
        for (int i = 0; i < 9; i++) if (cells[i] == EMPTY) moves.add(i);
        return moves;
    }

    public boolean isGameOver() {
        return winner().isPresent() || availableMoves().isEmpty();
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < 3; r++) {
            sb.append(' ');
            for (int c = 0; c < 3; c++) {
                int i = r * 3 + c;
                sb.append(cells[i] == EMPTY ? Character.forDigit(i + 1, 10) : cells[i]);
                if (c < 2) sb.append(" | ");
            }
            sb.append('\n');
            if (r < 2) sb.append("---+---+---\n");
        }
        return sb.toString();
    }
}
