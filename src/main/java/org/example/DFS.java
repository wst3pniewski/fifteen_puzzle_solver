package org.example;

import java.util.*;

public class DFS {

    private final int maxDepth;
    private int visitedCount;
    private int processedCount;
    private final char[] movesOrder;
    private Node solvedBoard;
    private boolean solved;
    private List<Character> movesToSolution;

    // Constructors
    public DFS(char[] movesOrder) {
        this.maxDepth = 20;
        this.movesOrder = movesOrder;
        this.movesToSolution = new ArrayList<>();
        this.visitedCount = 0;
        this.processedCount = 0;
    }

    public Node startDFS(Node root) {
        processedCount++;
        dfsRecursive(root);
        return solvedBoard;
    }

    public Node startDFS2(Node root) {
        if (dfs2(root) == 1) {
            this.solvedBoard = root;
            return root;
        }
        return null;
    }

    public Node startDFSIterative(Node root) {
        dfsIterative(root);
        return solvedBoard;
    }

    private void dfsIterative(Node node) {
        LinkedList<Node> toProcessNodes = new LinkedList<>();
        toProcessNodes.add(node);
        ArrayList<Node> children;
        while (!toProcessNodes.isEmpty()) {
            Node currentNode = toProcessNodes.removeLast();
            visitedCount++;
            if (currentNode.getDepth() > this.maxDepth) {
                currentNode = toProcessNodes.removeLast();
                if (currentNode.isBoardSolved()) {
                    this.solvedBoard = currentNode;
                    findTheSolvingPath(currentNode);
                    solved = true;
                    return;
                }
            } else {
                if (currentNode.isBoardSolved()) {
                    this.solvedBoard = currentNode;
                    findTheSolvingPath(currentNode);
                    solved = true;
                    return;
                }
                processedCount++;
                currentNode.createChildrenInOrder(movesOrder);
                children = new ArrayList<>(currentNode.getChildren());
                toProcessNodes.addAll(children);
            }
        }
    }

    private void dfsRecursive(Node node) {
        if (solved) {
            return;
        }
        ArrayList<Node> children;
        visitedCount++;
        if (node.isBoardSolved()) {
            this.solvedBoard = node;
            findTheSolvingPath(node);
            solved = true;
            return;
        }
        if (node.getDepth() == this.maxDepth) {
            return;
        }
        processedCount++;
        node.createChildrenInOrder(movesOrder);
        children = new ArrayList<>(node.getChildren());
        for (Node child : children) {
            if (!solved) {
                dfsRecursive(child);
            }
        }
    }

    private int dfs2(Node node) {
        this.visitedCount++;
        if (node.isBoardSolved()) {
            return 1;
        } else if (node.getDepth() == this.maxDepth) {
            return 0;
        } else {
            this.processedCount++;
            for (int i = 0; i < 4; i++) {
                if (node.makeStep(movesOrder[i])) {
                    if (dfs2(node) == 1) {
                        return 1;
                    }
                    node.undoStep();
                }
            }
        }
        return 0;
    }

    private void findTheSolvingPath(Node winner) {
        while (winner.getDepth() != 0) {
            movesToSolution.add(winner.getDirectionLetter());
            winner = winner.getParent();
        }
    }

    // Getters and Setters
    public Node getSolvedBoard() {
        return solvedBoard;
    }

    public List<Character> getMovesToSolution() {
        List<Character> reversedMoves = new ArrayList<>(movesToSolution);
        Collections.reverse(reversedMoves);
        return reversedMoves;
    }

    public int getVisitedCount() {
        return visitedCount;
    }

    public int getMaxDepth() {
        return maxDepth;
    }

    public int getProcessedCount() {
        return processedCount;
    }
}
