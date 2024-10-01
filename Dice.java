// Dice.java
// Represents the dice used in Yahtzee
import java.util.Random;

class Dice {
    private static final int NUM_DICE = 5;
    private int[] diceValues;
    private Random random;

    public Dice() {
        diceValues = new int[NUM_DICE];
        random = new Random();
    }

    // Resets dice values
    public void resetDice() {
        for (int i = 0; i < NUM_DICE; i++) {
            diceValues[i] = 0;
        }
    }

    // Rolls all dice
    public void roll() {
        for (int i = 0; i < NUM_DICE; i++) {
            diceValues[i] = random.nextInt(6) + 1;
        }
    }

    // Rerolls selected dice based on user input
    public void reroll(String input) {
        String[] positions = input.split(" ");
        for (String pos : positions) {
            int index = Integer.parseInt(pos) - 1;
            if (index >= 0 && index < NUM_DICE) {
                diceValues[index] = random.nextInt(6) + 1;
            }
        }
    }

    // Returns a string representation of the dice values
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int value : diceValues) {
            sb.append(value).append(" ");
        }
        return sb.toString().trim();
    }

    // Getter to access dice values
    public int[] getDiceValues() {
        return diceValues;
    }
}