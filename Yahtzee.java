// Yahtzee.java
// Author: Joseph Cornell
// Date: [Date you started working]
// Purpose: This program implements a multiplayer Yahtzee game for 1 to 6 players.

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class Yahtzee {
    private static final int MAX_PLAYERS = 6;
    private static final int MIN_PLAYERS = 1;
    private static final int NUM_DICE = 5;
    private static final int MAX_ROLLS = 3;

    private ArrayList<Player> players;
    private Dice dice;
    private Scanner scanner;

    public Yahtzee() {
        players = new ArrayList<>();
        dice = new Dice();
        scanner = new Scanner(System.in);
    }

    // Entry point of the program
    public static void main(String[] args) {
        Yahtzee game = new Yahtzee();
        game.setupGame();
        game.playGame();
    }

    // Sets up the game by asking for the number of players and creating Player objects
    private void setupGame() {
        System.out.print("Enter the number of players (1-6): ");
        int numPlayers = scanner.nextInt();
        scanner.nextLine(); // Consume leftover newline
        while (numPlayers < MIN_PLAYERS || numPlayers > MAX_PLAYERS) {
            System.out.print("Invalid number. Enter the number of players (1-6): ");
            numPlayers = scanner.nextInt();
            scanner.nextLine(); // Consume leftover newline
        }

        for (int i = 1; i <= numPlayers; i++) {
            System.out.print("Enter name for Player " + i + ": ");
            String name = scanner.nextLine();
            players.add(new Player(name));
        }
    }

    // Manages the flow of the game
    private void playGame() {
        boolean gameEnded = false;
        while (!gameEnded) {
            for (Player player : players) {
                System.out.println("\n" + player.getName() + "'s turn.");
                takeTurn(player);
                
                if (player.isScorecardComplete()) {
                    System.out.println(player.getName() + " has completed their scorecard!");
                }
            }

            System.out.print("Do you want to end the game? (yes/no): ");
            gameEnded = scanner.next().equalsIgnoreCase("yes");
        }
        endGame();
    }

    // Handles a single player's turn
    private void takeTurn(Player player) {
        dice.resetDice();
        int rollCount = 0;

        while (rollCount < MAX_ROLLS) {
            dice.roll();
            System.out.println("Roll " + (rollCount + 1) + ": " + dice);

            if (rollCount < MAX_ROLLS - 1) {
                System.out.print("Do you want to reroll? (yes/no): ");
                if (scanner.next().equalsIgnoreCase("no")) break;

                System.out.print("Enter dice positions to reroll (1-5, separated by space): ");
                String rerollInput = scanner.next();
                dice.reroll(rerollInput);
            }
            rollCount++;
        }

        // Show current dice values
        System.out.println("Your final dice values: " + Arrays.toString(dice.getDiceValues()));
        player.displayScorecard();
        scanner.nextLine(); // Consume newline left from previous input
        System.out.print("Choose a category to score in: ");
        String category = scanner.nextLine();

        int points = calculateScore(dice.getDiceValues(), category);
        if (points != -1) {
            player.addScore(points, category);
            System.out.println("You scored " + points + " points in the " + category + " category.");
        } else {
            System.out.println("Invalid category or scoring rules.");
        }
    }

    // Ends the game and displays final scores
    private void endGame() {
        System.out.println("\nFinal Scores:");
        for (Player player : players) {
            System.out.println(player.getName() + ": " + player.getScore() + " points");
        }
    }

    // Method to calculate score based on category
    private int calculateScore(int[] diceValues, String category) {
        int[] counts = new int[6];
        for (int value : diceValues) {
            counts[value - 1]++;
        }
        Arrays.sort(diceValues);

        switch (category.toLowerCase()) {
            case "aces":
                return counts[0] * 1;
            case "twos":
                return counts[1] * 2;
            case "threes":
                return counts[2] * 3;
            case "fours":
                return counts[3] * 4;
            case "fives":
                return counts[4] * 5;
            case "sixes":
                return counts[5] * 6;
            case "three of a kind":
                for (int count : counts) {
                    if (count >= 3) return Arrays.stream(diceValues).sum();
                }
                return 0;
            case "four of a kind":
                for (int count : counts) {
                    if (count >= 4) return Arrays.stream(diceValues).sum();
                }
                return 0;
            case "full house":
                boolean three = false;
                boolean two = false;
                for (int count : counts) {
                    if (count == 3) three = true;
                    if (count == 2) two = true;
                }
                return (three && two) ? 25 : 0;
            case "small straight":
                if (hasSequence(diceValues, 4)) return 30;
                return 0;
            case "large straight":
                if (hasSequence(diceValues, 5)) return 40;
                return 0;
            case "yahtzee":
                for (int count : counts) {
                    if (count == 5) return 50;
                }
                return 0;
            case "chance":
                return Arrays.stream(diceValues).sum();
            default:
                return -1;
        }
    }

    // Helper function to check for sequences
    private boolean hasSequence(int[] diceValues, int length) {
        int consecutive = 1;
        for (int i = 1; i < diceValues.length; i++) {
            if (diceValues[i] == diceValues[i - 1] + 1) {
                consecutive++;
                if (consecutive == length) return true;
            } else if (diceValues[i] != diceValues[i - 1]) {
                consecutive = 1;
            }
        }
        return false;
    }
}