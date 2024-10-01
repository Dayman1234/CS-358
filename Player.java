// Player.java
import java.util.HashMap;
import java.util.Map;

public class Player {
    private String name;
    private int score;
    private Map<String, Integer> scorecard;

    public Player(String name) {
        this.name = name;
        this.score = 0;
        scorecard = new HashMap<>();
        
        // Initialize scorecard with categories, set to -1 to indicate unfilled
        String[] categories = {
            "Aces", "Twos", "Threes", "Fours", "Fives", "Sixes",
            "Three of a Kind", "Four of a Kind", "Full House",
            "Small Straight", "Large Straight", "Yahtzee", "Chance"
        };
        for (String category : categories) {
            scorecard.put(category, -1); // -1 means the category hasn't been used yet
        }
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public Map<String, Integer> getScorecard() {
        return scorecard;
    }

    public void addScore(int points, String category) {
        if (scorecard.containsKey(category) && scorecard.get(category) == -1) {
            scorecard.put(category, points);
            score += points;
        } else {
            System.out.println("Category already filled or invalid.");
        }
    }

    public boolean isScorecardComplete() {
        return !scorecard.containsValue(-1);
    }

    public void displayScorecard() {
        System.out.println("\nScorecard for " + name + ":");
        for (Map.Entry<String, Integer> entry : scorecard.entrySet()) {
            String category = entry.getKey();
            int value = entry.getValue();
            System.out.println(category + ": " + (value == -1 ? "Not filled" : value));
        }
    }
}