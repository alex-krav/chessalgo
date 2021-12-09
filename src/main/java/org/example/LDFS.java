package org.example;

import java.util.Stack;

public class LDFS {
    static int iterations;
    static int states;
    static int limitReachedCounter;

    public static final int LIMIT = 8;

    static class Node {
        int[] state;
        Node parent;
        Stack<Node> successors = new Stack<>();
        boolean visited;

        public Node(int[] state) {
            this.state = state;
        }

        public boolean isGoal() { //checks if a node is the answer
            for (int i = 0; i < state.length; i++) { //checking vertical and diagonal lines for collisions
                for (int j = 0; j < state.length; j++) {
                    if (i == j) {
                        continue;
                    }
                    if (state[i] == state[j] || state[i] + (j - i) == state[j] || state[i] - (j - i) == state[j]) {
                        return false;
                    }
                }
            }
            return true;
        }
    }

    public static Node search(int[] state) {
        Node current = new Node(state);
        int depth = 0;

        while (!current.isGoal()) {
            iterations++;
            if (depth == LIMIT) {
                current = current.parent;
                depth--;
                limitReachedCounter++;
                continue;
            }

            if (!current.visited) {
                for (int i = 1; i < state.length; i++) {
                    int[] nextState = new int[LIMIT];
                    System.arraycopy(current.state, 0, nextState, 0, LIMIT);

                    nextState[depth] += i;
                    if (nextState[depth] >= LIMIT) {
                        nextState[depth] -= LIMIT;
                    }

                    Node next = new Node(nextState);
                    next.parent = current;
                    current.successors.push(next);
                    states++;
                }
                current.visited = true;
            }
            if (!current.successors.empty()) {
                current = current.successors.pop();
                depth++;
            } else {
                current = current.parent;
                depth--;
            }
        }
        return current;
    }
}
