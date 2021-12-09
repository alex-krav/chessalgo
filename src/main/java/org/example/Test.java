package org.example;

import java.util.Arrays;

public class Test {
    public static void main(String[] args) {
        for (int i = 0; i < 20; i++) {
            int[] board = new int[8];

            for (int j = 0; j < board.length; j++) {
                board[j] = (int) (Math.random() * 8);
            }

            System.out.println("experiment " + (i + 1));
            System.out.println("input state " + printBoard(board));
//            boardVisualise(board);
            System.out.println();

            long start = System.nanoTime();
            int[] solvedLDFS = LDFS.search(board).state;
            long end = System.nanoTime();
            long ldfsRes = end - start;
//            System.out.println("LDFS Iterations: " + LDFS.iterations);
            System.out.println("LDFS states: " + LDFS.states);
//            System.out.println("Depth limit is reached " + LDFS.limitReachedCounter + " times");
            System.out.print("solved " + printBoard(solvedLDFS));
            System.out.println(", moves = " + movesNumber(board, solvedLDFS));
            System.out.println("time spent for LDFS search: " + (double) ldfsRes / 1000000000 + " s");
            System.out.println();

            start = System.nanoTime();
            int[] solvedAStar = AStar.search(board).state;
            end = System.nanoTime();
            long aStarRes = end - start;
//            System.out.println("A* iterations: " + AStar.iterations);
            System.out.println("A* states: " + AStar.states);
            System.out.print("solved " + printBoard(solvedAStar));
            System.out.println(", moves = " + movesNumber(board, solvedAStar));
            System.out.println("time spent for A* search: " + (double) aStarRes / 1000000000 + " s");
            System.out.println("_______________________________________________________");
        }
    }

    public static String printBoard(int[] board) {
        String print = "[";
        for (int num : board) {
            print = print.concat(num + "");
        }
        return print + "]";
    }

    public static int movesNumber(int[] start, int[] goal) {
        int number = 0;
        for (int i = 0; i < start.length; i++) {
            if (start[i] != goal[i]) {
                number++;
            }
        }
        return number;
    }

    public static void boardVisualise(int[] board) {
        int[][] board2D = AStar.transform1Dto2D(board);
//        for (int i = 0; i < board.length; i++) {
//            System.out.println(Arrays.toString(board2D[i]));
//        }
        for (int i = board.length-1; i >= 0; i--) {
            System.out.println(Arrays.toString(board2D[i]));
        }
    }
}
