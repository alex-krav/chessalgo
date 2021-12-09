package org.example;

import java.util.*;

public class AStar {
    static int iterations;
    static int states;
    public static final int LIMIT = 8;

    static class Node {
        int[] state;
        Node parent;
        ArrayList<Node> accessors = new ArrayList<>();
        int heuristic;
        int depth;

        public Node(int[] state) {
            this.state = state;
            heuristic = calcHeuristic();
        }

        private int calcHeuristic() {
            return AStar.calcHeuristicF1(transform1Dto2D(this.state));
//            return AStar.calcHeuristicF2(this.state);
        }
    }

    public static int[][] transform1Dto2D(int[] input) {
        int[][] output = new int[LIMIT][LIMIT];
        for (int i = 0; i < input.length; i++) {
            int value = input[i];
            output[value][i] = 1;
        }
        return output;
    }

    public static int calcHeuristicF2(int[] state) {
        int counter = 0;
        for (int i = 0; i < state.length; i++) { //checking vertical and diagonal lines for collisions
            for (int j = 0; j < state.length; j++) {
                if (i == j) {
                    continue;
                }
                if (state[i] == state[j] || state[i] + (j - i) == state[j] || state[i] - (j - i) == state[j]) {
                    counter++;
                }
            }
        }
        return counter / 2;
    }

    public static int calcHeuristicF1(int[][] field) {
        int counter = 0;
        int size = field.length;

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (field[i][j] == 1) {
                    // check horizontal and vertical directions
                    for (int k = j; k >= 0; k--) { // left
                        if (field[i][k] == 1 && k != j) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = j; k < size; k++) { // right
                        if (field[i][k] == 1 && k != j) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = i; k < size; k++) { // up
                        if (field[k][j] == 1 && k != i) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = i; k >= 0; k--) { // down
                        if (field[k][j] == 1 && k != i) {
                            counter++;
                            break;
                        }
                    }

                    // check diagonal directions
                    for (int k = i, p = j; k >= 0 && p >= 0; k--, p--) { // left down
                        if (field[k][p] == 1 && k != i && p != j) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = i, p = j; k < size && p < size; k++, p++) { // right up
                        if (field[k][p] == 1 && k != i && p != j) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = i, p = j; k < size && p >= 0; k++, p--) { // left up
                        if (field[k][p] == 1 && k != i && p != j) {
                            counter++;
                            break;
                        }
                    }
                    for (int k = i, p = j; k >= 0 && p < size; k--, p++) { // right down
                        if (field[k][p] == 1 && k != i && p != j) {
                            counter++;
                            break;
                        }
                    }
                }
            }
        }
        return counter;
    }

    public static Node search(int[] state) {
        Node start = new Node(state);
        Node current = start;
        start.depth = 0;
        PriorityQueue<Node> open = new PriorityQueue<>(Comparator.comparingInt(a -> a.heuristic));
        HashSet<Node> closed = new HashSet<>();

        while (current.heuristic != 0) {
            iterations++;
            for (int i = 1; i < state.length; i++) {
                int[] nextState = new int[LIMIT];
                System.arraycopy(current.state, 0, nextState, 0, LIMIT);
                if (current.depth >= LIMIT) {
                    current.depth -= LIMIT;
                }

                nextState[current.depth] += i;
                if (nextState[current.depth] >= LIMIT) {
                    nextState[current.depth] -= LIMIT;
                }

                Node next = new Node(nextState);
                next.depth = current.depth + 1;
                next.parent = current;
                current.accessors.add(next);
                closed.add(current);
                states++;
            }
            open.addAll(current.accessors);
            Node next;
            do {
                next = open.poll();
            } while (closed.contains(next));
            current = next;
        }
        return current;
    }
}
