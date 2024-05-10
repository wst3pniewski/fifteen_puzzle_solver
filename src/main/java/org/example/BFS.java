package org.example;

import java.util.*;

public class BFS {
    private final char[] movesOrder;
    private Node solvedBoard;
    private List<Character> moves;
    private int visitedCount;
    private int processedCount;

    public BFS(char[] movesOrder) {
        this.movesOrder = movesOrder;
        moves = new ArrayList<>();

    }

    private void findTheSolvingPath(Node winner) {
        while (winner.getDepth() != 0) {
            moves.add(winner.getDirectionLetter());
            winner = winner.getParent();
        }
    }

    public void startBFS(Node root) {
        Queue<Node> queue = new LinkedList<>();
        List<Node> visited = new ArrayList<>();
        List<Node> processedNodes = new ArrayList<>();

        queue.add(root);
        visited.add(root);
        if (root.isBoardSolved()) {
            this.solvedBoard = root;
            findTheSolvingPath(root);
            visitedCount = 1;
            processedCount = 0;
            return;
        }

        while (!queue.isEmpty()) {
            Node processedNode = queue.poll();
            processedNodes.add(processedNode);

            processedNode.createChildrenInOrder(movesOrder);
            for (int i = 0; i < processedNode.getChildrenCount(); i++) {
                if (!visited.contains(processedNode.getChildrenByIndex(i))) {
                    visited.add(processedNode.getChildrenByIndex(i));
                    if (processedNode.getChildrenByIndex(i).isBoardSolved()) {
                        this.solvedBoard = processedNode.getChildrenByIndex(i);
                        findTheSolvingPath(processedNode.getChildrenByIndex(i));
                        visitedCount = visited.size();
                        processedCount = processedNodes.size();
                        return;
                    }
                    queue.add(processedNode.getChildrenByIndex(i));
                }

            }
        }
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public List<Character> getMoves() {
        List<Character> reversedMoves = new ArrayList<>(moves);
        Collections.reverse(reversedMoves);
        return reversedMoves;
    }

    public Node getSolvedBoard() {
        return solvedBoard;
    }

    public int getProcessedCount() {
        return processedCount;
    }
}
