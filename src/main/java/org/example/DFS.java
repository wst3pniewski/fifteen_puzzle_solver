package org.example;

import java.util.*;

public class DFS {

    private final int maxDepth;
    //    private List<Node> visitedNodes;
//    private List<Node> processedNodes;
    private int visitedCount;
    private int processedCount;
    private final char[] movesOrder;
    private Node solvedBoard;
    private boolean solved;
    private List<Character> moves;

    // Constructors
    public DFS(char[] movesOrder) {
        this.maxDepth = 20;
//        this.visitedNodes = new ArrayList<>();
//        this.processedNodes = new ArrayList<>();
        this.movesOrder = movesOrder;
        this.moves = new ArrayList<>();
        this.visitedCount = 0;
        this.processedCount = 0;
    }

    public Node startDFS(Node root) {
//        processedNodes.add(root);
        processedCount++;
        dfsRecursive(root);
        return solvedBoard;
    }

    public Node startDFSIterative(Node root) {
        dfsIterative(root);
        return solvedBoard;
    }

    private void dfsIterative(Node node){
        LinkedList<Node> stack = new LinkedList<>();
        stack.add(node);
        while (!stack.isEmpty()){
            Node currentNode = stack.removeLast();
            processedCount++;
            visitedCount++;
            if (currentNode.getDepth() >= this.maxDepth) {
                currentNode = stack.removeFirst();
//                return;
            }
            if (currentNode.isBoardSolved()) {
                this.solvedBoard = currentNode;
                findTheSolvingPath(currentNode);
                solved = true;
                return;
            }
            currentNode.createChildrenInOrder(movesOrder);
            for (int i = 0; i < currentNode.getChildrenCount(); i++) {
                stack.add(currentNode.getChildrenByIndex(i));
            }
        }
    }

    private void dfsRecursive(Node node) {
//        visitedNodes.add(node);
        visitedCount++;
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

            if (!solved) {
//                processedNodes.add(node.getChildrenByIndex(i));
                processedCount++;
                dfsRecursive(node.getChildrenByIndex(i));
            }
//            if (!processedNodes.contains(node.getChildrenByIndex(i))) {
//            }
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

        return visitedCount;
//        return visitedNodes.size();
    }

    public int getProcessedCount() {

        return processedCount;
//        return processedNodes.size();
    }
}
