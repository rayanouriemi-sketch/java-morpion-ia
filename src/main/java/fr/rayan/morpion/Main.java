package fr.rayan.morpion;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        MinimaxAI ai = new MinimaxAI();
        Board board = new Board();
        System.out.println("Morpion contre l'IA. Vous jouez X. Tapez le numéro d'une case (1-9).\n");

        while (!board.isGameOver()) {
            System.out.println(board);
            if (board.currentPlayer() == 'X') {
                System.out.print("Votre coup : ");
                if (!in.hasNextLine()) return;
                String input = in.nextLine().trim();
                try {
                    board.play(Integer.parseInt(input) - 1);
                } catch (NumberFormatException e) {
                    System.out.println("Entrez un nombre entre 1 et 9.\n");
                } catch (IllegalArgumentException | IllegalStateException e) {
                    System.out.println("Coup invalide : " + e.getMessage() + "\n");
                }
            } else {
                int move = ai.bestMove(board);
                board.play(move);
                System.out.println("L'IA joue en " + (move + 1) + " (" + ai.nodesExplored() + " positions analysées)\n");
            }
        }
        System.out.println(board);
        System.out.println(board.winner().map(w -> w == 'X' ? "Bravo, vous avez gagné !" : "L'IA a gagné.")
                .orElse("Match nul."));
    }
}
