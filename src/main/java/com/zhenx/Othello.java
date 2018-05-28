package main.java.com.zhenx;

import java.util.*;

/**
 * An implementation for Othello game created for UBS
 */
public class Othello {

    /**
     * Check if the specified step is valid
     * A valid step must be a String whose length is 2, one chararacter is between '1' and '8',
     * the other character is between 'a' and 'h'.
     *
     * @param step  the specified step
     * @return      if the step is valid then return true, otherwise return false
     */
    private static boolean isValidStep(String step) {

        if (step == null || step.length() != 2) {
            System.out.println("step cannot be null, and length of move must be exactly 2, your move:" + step);
            return false;
        }

        boolean result = false;
        char[] chars = step.toCharArray();
        if (chars[0] <= 'h' && chars[0] >= 'a') {
            if (chars[1] <= '8' && chars[1] >= '1') {
                result = true;
            }
        } else {
            if (chars[0] <= '8' && chars[0] >= '1' && chars[1] >= 'a' && chars[1] <= 'h') {
                result = true;
            }
        }
        return result;
    }

    /**
     * Check if the player has further moves
     *
     * @param matrix Current board status
     * @param player The player who will make the next move
     * @return if the player has further moves then return true, otherwise return false
     */

    private static boolean hasFurtherMoves(char[][] matrix, String player) {

        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if (matrix[i][j] != '-') {
                    continue;
                }
                char[][] copy = new char[8][8];
                for (int x = 0; x < 8; x++) {
                    System.arraycopy(matrix[x], 0, copy[x], 0, 8);
                }

                if (isValidMove(copy, player, i, j)) {
                    return true;
                }

            }
        }

        return false;
    }

    /**
     * Switch to the other player, only "X" and "O" are valid players
     *
     * @param player The current player
     * @return The opposite player
     */
    private static String switchPlayer(String player) {
        return "X".equals(player) ? "O" : "X";
    }

    /**
     * Get a String of current board status
     *
     * @param board Current board status
     * @return String of current board
     */

    private static String getStatus(char[][] board) {

        if (board == null) {
            throw new IllegalArgumentException("input array cannot be null");
        }

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 8; i++) {
            sb.append((i + 1)).append(" ");
            for (int j = 0; j < 8; j++) {
                sb.append(board[i][j]);
            }
            sb.append("\n");
        }
        sb.append("  abcdefgh\n");

        return sb.toString();
    }

    /**
     * Create a new 8x8 board for the game
     *
     * @return A 8x8 char array filled with '-'
     */
    private static char[][] createBoard() {

        char[][] board = new char[8][8];
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                board[i][j] = '-';
            }
        }
        board[3][4] = 'X';
        board[4][3] = 'X';
        board[3][3] = 'O';
        board[4][4] = 'O';

        return board;
    }

    /**
     * Flip the VERTICAL pieces by the specified position to up/down direction
     *
     * @param matrix Current board status
     * @param x      x axis value
     * @param y      y axis value
     * @param player piece that put on the x,y position
     * @return if successfully flip any pieces then return true, otherwise return false
     */
    private static boolean flipVertical(char[][] matrix, int x, int y, char player) {

        boolean flipped = false;

        char theOther = player == 'X' ? 'O' : 'X';
        if (x - 1 >= 0 && matrix[x - 1][y] == theOther) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(x - 1);
            boolean match = false;
            for (int i = x - 2; i >= 0; i--) {
                if (matrix[i][y] == player) {
                    match = true;
                    break;
                } else if (matrix[i][y] == theOther) {
                    list.add(i);
                } else {
                    break;
                }
            }
            if (match) {
                flipped = true;
                for (int i : list) {
                    matrix[i][y] = player;
                }
            }
        }

        if (x + 1 < 8 && matrix[x + 1][y] == theOther) {
            List<Integer> list = new ArrayList<Integer>();
            list.add(x + 1);
            boolean match = false;
            for (int i = x + 2; i < 8; i++) {
                if (matrix[i][y] == player) {
                    match = true;
                    break;
                } else if (matrix[i][y] == theOther) {
                    list.add(i);
                } else {
                    break;
                }

            }
            if (match) {
                flipped = true;
                for (int ele : list) {
                    matrix[ele][y] = player;
                }
            }
        }

        return flipped;
    }

    /**
     * Flip the HORIZONTAL pieces by the specified position to left/right direction
     *
     * @param matrix Current board status
     * @param x      x axis value
     * @param y      y axis value
     * @param player piece that put on the x,y position
     * @return if successfully flip any pieces then return true, otherwise return false
     */
    private static boolean flipHorizontal(char[][] matrix, int x, int y, char player) {

        boolean flipped = false;

        char theOther = player == 'X' ? 'O' : 'X';
        if (y - 1 >= 0 && matrix[x][y - 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(y - 1);
            boolean match = false;
            for (int i = y - 2; i >= 0; i--) {
                if (matrix[x][i] == player) {
                    match = true;
                    break;
                } else if (matrix[x][i] == theOther) {
                    list.add(i);
                } else {
                    break;
                }
            }
            if (match) {
                flipped = true;
                for (int i : list) {

                    matrix[x][i] = player;
                }
            }
        }

        if (y + 1 < 8 && matrix[x][y + 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(y + 1);
            boolean match = false;
            for (int i = y + 2; i < 8; i++) {
                if (matrix[x][i] == player) {
                    match = true;
                    break;
                } else if (matrix[x][i] == theOther) {
                    list.add(i);
                } else {
                    break;
                }

            }
            if (match) {
                flipped = true;
                for (int i : list) {
                    matrix[x][i] = player;
                }
            }
        }

        return flipped;
    }

    /**
     * Flip the DIAGONAL pieces by the specified position to upper/lower/left/right directions
     *
     * @param matrix Current board status
     * @param x      x axis value
     * @param y      y axis value
     * @param player piece that put on the x,y position
     * @return if successfully flip any pieces then return true, otherwise return false
     */
    private static boolean flipDiagonal(char[][] matrix, int x, int y, char player) {

        boolean flipped = false;

        char theOther = player == 'X' ? 'O' : 'X';
        if (x + 2 < 8 && y + 2 < 8 && matrix[x + 1][y + 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(x + 1);
            list.add(y + 1);

            boolean match = false;
            for (int i = x + 2, j = y + 2; i < 8 && j < 8; i++, j++) {
                if (matrix[i][j] == player) {
                    match = true;
                    break;
                } else if (matrix[i][j] == theOther) {
                    list.add(i);
                    list.add(j);
                } else {
                    break;
                }
            }

            if (match) {
                flipped = true;
                for (int k = 0; k < list.size(); k = k + 2) {
                    int m = list.get(k);
                    int n = list.get(k + 1);

                    matrix[m][n] = player;
                }
            }

        }

        if (x - 2 >= 0 && y - 2 >= 0 && matrix[x - 1][y - 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(x - 1);
            list.add(y - 1);

            boolean match = false;
            for (int i = x - 2, j = y - 2; i >= 0 && j >= 0; i--, j--) {
                if (matrix[i][j] == player) {
                    match = true;
                    break;
                } else if (matrix[i][j] == theOther) {
                    list.add(i);
                    list.add(j);
                } else {
                    break;
                }
            }

            if (match) {
                flipped = true;
                for (int k = 0; k < list.size(); k = k + 2) {
                    int m = list.get(k);
                    int n = list.get(k + 1);

                    matrix[m][n] = player;
                }
            }

        }

        if (x + 2 < 8 && y - 2 >= 0 && matrix[x + 1][y - 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(x + 1);
            list.add(y - 1);

            boolean match = false;
            for (int i = x + 2, j = y - 2; i < 8 && j >= 0; i++, j--) {
                if (matrix[i][j] == player) {
                    match = true;
                    break;
                } else if (matrix[i][j] == theOther) {
                    list.add(i);
                    list.add(j);
                } else {
                    break;
                }
            }

            if (match) {
                flipped = true;
                for (int k = 0; k < list.size(); k = k + 2) {
                    int m = list.get(k);
                    int n = list.get(k + 1);

                    matrix[m][n] = player;
                }
            }

        }

        if (x - 2 >= 0 && y + 2 < 8 && matrix[x - 1][y + 1] == theOther) {

            List<Integer> list = new ArrayList<Integer>();
            list.add(x - 1);
            list.add(y + 1);

            boolean match = false;
            for (int i = x - 2, j = y + 2; i >= 0 && j < 8; i--, j++) {
                if (matrix[i][j] == player) {
                    match = true;
                    break;
                } else if (matrix[i][j] == theOther) {
                    list.add(i);
                    list.add(j);
                } else {
                    break;
                }
            }

            if (match) {
                flipped = true;
                for (int k = 0; k < list.size(); k = k + 2) {
                    int m = list.get(k);
                    int n = list.get(k + 1);

                    matrix[m][n] = player;
                }
            }

        }


        return flipped;
    }

    /**
     * Count the pieces for both sides and print the winner
     *
     * @param board Current board status
     * @return
     */
    private static String endGame(char[][] board) {
        if (board == null) {
            throw new IllegalArgumentException("the input cannot be NULL");
        }
        int countX = 0;
        int countO = 0;
        for (int i = 0; i < 8; i++) {
            for (int j = 0; j < 8; j++) {
                if ('X' == board[i][j]) {
                    countX++;
                } else if ('O' == board[i][j]) {
                    countO++;
                } else {
                }
            }
        }
        String winner = "X";
        if (countX < countO) {
            winner = "O";
        }

        return "Player '" + winner + "' wins (" + countX + " vs " + countO + ")";
    }

    /**
     * Flip all the pieces by the specified position to all directions
     *
     * @param matrix Current board status
     * @param x      x axis value
     * @param y      y axis value
     * @param player piece that put on the x,y position
     * @return if successfully flip any pieces then return true, otherwise return false
     */
    private static boolean flip(char[][] matrix, int x, int y, char player) {


        boolean horizontal = flipHorizontal(matrix, x, y, player);
        boolean vertical = flipVertical(matrix, x, y, player);
        boolean diagonal = flipDiagonal(matrix, x, y, player);

        return horizontal || vertical || diagonal;
    }

    /**
     * Check if the specified move is valid
     *
     * @param matrix     Current board status
     * @param currPlayer piece that put on x,y position
     * @param x          x axis value
     * @param y          y axis value
     * @return if this move is valid then return true, otherwise return false
     */
    private static boolean isValidMove(char[][] matrix, String currPlayer, int x, int y) {

        char player = currPlayer.toCharArray()[0];
        matrix[x][y] = player;

        return flip(matrix, x, y, player);
    }

    /**
     * Takes a csv String of moves and returns the resulting board as a String
     *
     * @param input CSV String of moves
     * @return String of the resulting board
     */
    public static String playGame(String input) {
        if (input == null) {
            throw new IllegalArgumentException("input cannot be null!");
        }

        String[] moves = input.split(",");
        for (String m : moves) {
            m = m.trim();
            if (!isValidStep(m)) {
                throw new IllegalArgumentException("input contains invalid move");
            }
        }

        char[][] board = createBoard();

        String player = "X";
        for (String m : moves) {

            m = m.trim();

            if (playStep(board, player, m)) {

                player = switchPlayer(player);
            } else {
                System.out.println("Invalid move. Please try again.");
            }

        }

        return getStatus(board);
    }

    /**
     * Play the step on the board
     *
     * @param board  Current board status
     * @param player Current player
     * @param step   String of the step
     * @return If successfully play the step then return true, otherwise return false
     * Put piece on an occupied position is invalid;
     * Play an invalid move(not capturing any pieces) is invalid;
     */
    private static boolean playStep(char[][] board, String player, String step) {

        step = step.trim();
        char[] chars = step.toCharArray();

        int i = 0;
        int j = 0;

        if (chars[0] >= '1' && chars[0] <= '8') {
            i = chars[0] - '1';
            j = chars[1] - 'a';
        } else {
            j = chars[0] - 'a';
            i = chars[1] - '1';
        }

        char original = board[i][j];
        if (original != '-') {
            return false;
        }
        if (!isValidMove(board, player, i, j)) {
            board[i][j] = original;
            return false;
        }

        return true;
    }

    public static void main(String[] args) {

        String game = Othello.playGame("f5, 6f, f7, 4f, f3, 3e, d3, c5");
        System.out.println(game);

        game = Othello.playGame("3d, c5, e7, e6, 5f");
        System.out.println(game);


        char[][] board = createBoard();
        System.out.println(getStatus(board));

        String player = "X";
        while (true) {

            if (!hasFurtherMoves(board, player)) {
                String theOther = switchPlayer(player);
                if (hasFurtherMoves(board, theOther)) {
                    System.out.println("No further moves for " + player + ", passes back to " + theOther);
                    player = theOther;
                    continue;
                } else {
                    System.out.println("No further moves available");
                    System.out.println(endGame(board));
                    break;
                }
            }

            System.out.print("Player '" + player + "' move: ");
            String step = System.console().readLine();
            step = step.replace("\n", "");
            step = step.replaceAll(" ", "");

            if (!isValidStep(step)) {

                System.out.println("Invalid move, please try again.");
                continue;
            }

            if (playStep(board, player, step)) {
                System.out.println(getStatus(board));
                player = switchPlayer(player);
            } else {
                System.out.println("Invalid move. Please try again.");
            }

        }

        System.out.println("Game finished...");
    }
}
