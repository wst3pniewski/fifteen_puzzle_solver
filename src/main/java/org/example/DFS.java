package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class DFS {

    private final int maxDepth;
    private List<Node> visitedNodes;
    private List<Node> processedNodes;
    private final char[] movesOrder;
    private Node solvedBoard;
    private boolean solved;
    private List<Character> moves;

    // Constructors
    public DFS(char[] movesOrder) {
        this.maxDepth = 30;
        this.visitedNodes = new ArrayList<>();
        this.processedNodes = new ArrayList<>();
        this.movesOrder = movesOrder;
        this.moves = new ArrayList<>();
    }

    public Node startDFS(Node root) {
        processedNodes.add(root);
        dfsRecursive(root);
        return solvedBoard;
    }

    private void dfsRecursive(Node node) {
        visitedNodes.add(node);
        if (node.getDepth() >= this.maxDepth) {
            return;
        }
        if (node.isBoardSolved()) {
            this.solvedBoard = node;
            findTheSolvingPath(node);
            solved = true;
            return;
        }

        node.createChildrenInOrder(movesOrder);
        for (int i = 0; i < node.getChildrenCount(); i++) {

            if (!processedNodes.contains(node.getChildrenByIndex(i))) {
                if (!solved) {
                    processedNodes.add(node.getChildrenByIndex(i));
                    dfsRecursive(node.getChildrenByIndex(i));
                }
            }
        }
    }

    private void findTheSolvingPath(Node winner) {
        while (winner.getDepth() != 0) {
            moves.add(winner.getDirectionLetter());
            winner = winner.getParent();
        }
    }

    // Getters and Setters
    public Node getSolvedBoard() {
        return solvedBoard;
    }


    public List<Character> getMoves() {
        List<Character> reversedMoves = new ArrayList<>(moves);
        Collections.reverse(reversedMoves);
        return reversedMoves;
    }

    public int getVisitedCount() {
        return visitedNodes.size();
    }

    public int getProcessedCount() {
        return processedNodes.size();
    }
}
