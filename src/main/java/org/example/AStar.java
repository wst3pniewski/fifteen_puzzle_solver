package org.example;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private Node solvedBoard;
    private List<Character> moves;
    private String heuristic;
    private int visitedCount;
    private int processedCount;

    public AStar(String heuristic) {
        this.heuristic = heuristic;
        moves = new ArrayList<>();
        this.visitedCount = 1;
        this.processedCount = 0;

    }

    private void findTheSolvingPath(Node winner) {
        while (winner.getDepth() != 0) {
            moves.add(winner.getDirectionLetter());
            winner = winner.getParent();
        }
    }

    public void startAStar(Node root) {
        PriorityQueue<Node> openList;
        PriorityQueue<Node> helperList;
        if (heuristic.equals("MANH")) {
            openList = new PriorityQueue<>((a, b) -> a.getManhattanDistanceCost() - b.getManhattanDistanceCost());
            helperList = new PriorityQueue<>((a, b) -> a.getManhattanDistanceCost() - b.getManhattanDistanceCost());
        } else {
            openList = new PriorityQueue<>((a, b) -> a.getHammingDistanceCost() - b.getHammingDistanceCost());
            helperList = new PriorityQueue<>((a, b) -> a.getHammingDistanceCost() - b.getHammingDistanceCost());
        }
        List<Node> closedList = new ArrayList<>();
        openList.add(root);
        while (!openList.isEmpty()) {
            processedCount++;
            Node currentNode = openList.poll();
            if (currentNode.isBoardSolved()) {
                solvedBoard = currentNode;
                findTheSolvingPath(solvedBoard);
                return;
            }

            currentNode.createChildren();
            for (Node child : currentNode.getChildren()) {
                this.visitedCount++;
                if (child.isBoardSolved()) {
                    solvedBoard = child;
                    findTheSolvingPath(solvedBoard);
                    return;
                } else {
                    helperList.add(child);
                }
            }
            openList.add(helperList.poll());
            closedList.add(currentNode);
        }
    }

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
    }

    public int getProcessedCount() {
        return processedCount;
    }
}
