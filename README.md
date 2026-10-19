# Morpion avec IA imbattable (Java)

Un jeu de morpion (tic-tac-toe) dans le terminal contre une intelligence artificielle qui **ne perd jamais**.

## L'IA

L'algorithme **minimax** explore toutes les parties possibles à partir de la position actuelle :
- l'IA choisit le coup qui maximise son score,
- en supposant que l'adversaire joue le coup qui le minimise.

Optimisations :
- **Élagage alpha-bêta** : on arrête d'explorer une branche dès qu'on sait qu'elle ne sera pas choisie
- Le score tient compte de la profondeur : l'IA gagne le plus vite possible et retarde au maximum une défaite

## Jouer

```bash
mvn package
java -jar target/morpion-ia-1.0.0.jar
```

```
 1 | 2 | 3
---+---+---
 4 | X | 6
---+---+---
 7 | 8 | 9

L'IA joue en 1
```

## Tests

```bash
mvn test
```

Les tests vérifient notamment que deux IA parfaites font toujours match nul et que l'IA ne perd aucune de 300 parties contre un joueur aléatoire.

## Notions abordées

Récursivité, théorie des jeux, backtracking (`play` / `undo`), `Optional`, tests JUnit 5.
