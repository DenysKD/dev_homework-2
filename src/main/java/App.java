import java.util.Random;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {

    private static final Random random = new Random();
    private static final Scanner scan = new Scanner(System.in);
    private static final int[][] paterns = {
            {0, 1, 2}, {3, 4, 5}, {6, 7, 8},
            {0, 3, 6}, {1, 4, 7}, {2, 5, 8},
            {0, 4, 8}, {2, 4, 6}};

    public static void main(String[] args) {

        boolean boxAvailable = false;
        byte winner = 0;
        char[] box = {'1', '2', '3', '4', '5', '6', '7', '8', '9'};
        Logger logger = Logger.getLogger("Class App");
        logger.info("Enter box number to select. Enjoy!\n");

        while (true) {
            showBoard(logger, box);

            if (winner > 0 && winner < 4) {
                winCheck(logger, winner);
                break;
            }

            playerMove(scan, logger, box);

            if (isWin(box, 'X')) {
                winner = 1;
            } else {
                boxAvailable = doesHaveSpace(box);

                if (!boxAvailable) {
                    winner = 3;
                } else {

                    botMove(box);

                    if (isWin(box, 'O')) {
                        winner = 2;
                    }
                }
            }
        }
    }

    private static boolean isWin(char[] box, char symbol) {
        for (int[] patern : paterns) {
            if (box[patern[0]] == symbol && box[patern[1]] == symbol && box[patern[2]] == symbol)
                return true;
        }
        return false;
    }

    private static void playerMove(Scanner scanner, Logger logger, char[] box) {
        byte input;

        while (true) {
            if (scanner.hasNextByte()) {
                input = scanner.nextByte();
                if (input > 0 && input < 10) {
                    if (box[input - 1] != 'X' && box[input - 1] != 'O') {
                        box[input - 1] = 'X';
                        break;
                    } else {
                        logger.info("That one is already in use. Enter another.");
                    }
                } else
                    logger.info("Invalid input. Enter again.");

            } else {
                logger.info("Invalid value");
                scanner.next();
            }
        }
    }

    private static void botMove(char[] box) {
        if (!doesHaveSpace(box)) {
            return;
        }

        while (true) {
            int rand = random.nextInt(9);
            if (box[rand] != 'X' && box[rand] != 'O') {
                box[rand] = 'O';
                break;
            }
        }
    }

    private static void winCheck(Logger logger, byte winner) {
        if (winner == 1) {
            logger.info("You won the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else if (winner == 2) {
            logger.info("You lost the game!\nCreated by Shreyas Saha. Thanks for playing!");
        } else if (winner == 3) {
            logger.info("It's a draw!\nCreated by Shreyas Saha. Thanks for playing!");
        }
    }

    private static void showBoard(Logger logger, char[] box) {
        if (logger.isLoggable(Level.INFO)) {
            logger.log(Level.INFO, """
                            
                            %s | %s | %s
                            -----------
                            %s | %s | %s
                            -----------
                            %s | %s | %s
                            """.formatted(box[0], box[1], box[2],
                    box[3], box[4], box[5],
                    box[6], box[7], box[8]));
        }
    }

    private static boolean doesHaveSpace(char[] box) {
        for (byte i = 0; i < 9; i++) {
            if (box[i] != 'X' && box[i] != 'O') {
                return true;
            }
        }
        return false;
    }
}


