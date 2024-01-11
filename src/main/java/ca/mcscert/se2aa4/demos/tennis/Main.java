package ca.mcscert.se2aa4.demos.tennis;

import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;
import org.apache.commons.cli.ParseException;

import java.util.Random;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Main {

    public static int PLAYER1_STRENGTH;
    public static int PLAYER2_STRENGTH;
    protected static final Logger logger = LogManager.getLogger();

    public static void main(String[] args) {
        System.out.println("** Starting Tennis Counter Assistant");
        System.out.println("**** Reading Command-Line Arguments");
        Options options = new Options();
        options.addOption("p1", true, "Strength of Player 1 in [0,100]");
        options.addOption("p2", true, "Strength of Player 2 in [0,100]");
        CommandLineParser parser = new DefaultParser();
        try {
            CommandLine cmd = parser.parse(options, args);
            PLAYER1_STRENGTH = Integer.parseInt(cmd.getOptionValue("p1","50"));
            System.out.println("****** P1's Strength is " + PLAYER1_STRENGTH+"/100");
            PLAYER2_STRENGTH = Integer.parseInt(cmd.getOptionValue("p2","50"));
            System.out.println("****** P2's Strength is " + PLAYER2_STRENGTH+"/100");
        } catch (ParseException pe) {
            System.err.println("An error has occurred");
        }
        System.out.println("**** Starting game");
        Player player1 = new Player("player1", PLAYER1_STRENGTH);
        Player player2 = new Player("player2", PLAYER2_STRENGTH);
        Random rand = new Random();
        boolean flip;
        boolean playing = true;
        int i = 0;
        while (i < 5){ //!(Math.abs(player1.score - player2.score) < 2) || !((player1.score == 3) && (player2.score == 3))
            player1.setPoint(0);
            dispScore(player1, player2);
            flip = rand.nextBoolean();
            System.out.println(flip);

            if (flip == true) {
                player1.scorePoint();
            } else {
                player2.scorePoint();
            }

            System.out.println(player1.point);
            System.out.println(player2.point);

            dispScore(player1, player2);
            i++;
        }
        System.out.println("** Closing Tennis Counter Assistant");
    }
    private static void dispScore(Player player1, Player player2) {
        String p1Points = "" + player1.point;
        String p2Points = "" + player2.point;
        String advantagePlayer = null;
        int pointSum = player1.point + player2.point;

        if (player1.point == player2.point) {       //if scores are equal => no winner, either 'score' all or deuce
            if (pointSum < 6) {
                System.out.printf("%d all%n", player1.point);
            } else {
                System.out.printf("deuce%n");
            }
        } else {                                    //if scores are unequal
            if ((player1.point - 2) == player2.point) { // check for winner
                System.out.printf("%s wins the set!%n", player1);
                player1.setPoint(0);
                player2.setPoint(0);
            } else if ((player2.point - 2) == player1.point){
                System.out.printf("%s wins the set!%n", player2);
                player1.setPoint(0);
                player2.setPoint(0);
            } else if (pointSum < 6) {              //no winner and not in advantage
                p1Points = getString(player1, p1Points);
                p2Points = getString(player2, p2Points);
                System.out.printf("%s, %s1%n", p1Points, p2Points);
            } else {                                // in advantage
                if (player1.point > player2.point) {
                    advantagePlayer = player1.name;
                } else {
                    advantagePlayer = player2.name;
                }
                System.out.printf("advantage %s%n", advantagePlayer);
            }
        }
    }

    private static String getString(Player player, String points) {
        String switchChar = "" + player.point;
        switch (player.point) {
            case '0':
                points = "love";
            case '1':
                points = "15";
                break;
            case '2':
                points = "30";
            case '3':
                points = "40";
        }
        return points;
    }
}
