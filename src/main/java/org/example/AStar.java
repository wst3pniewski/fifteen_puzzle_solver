package org.example;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class AStar {
    private Node solvedBoard;
    private List<Character> moves;
    private String heuristic;

    public AStar(String heuristic) {
        this.heuristic = heuristic;
        moves = new ArrayList<>();
    }

    private void findTheSolvingPath(Node winner) {
        while (winner.getDepth() != 0) {
            moves.add(winner.getDirectionLetter());
            winner = winner.getParent();
        }
    }

    public void startAStar(Node root) {
        PriorityQueue<Node> openList = new PriorityQueue<>((a, b) -> a.getHammingDistanceCost() - b.getHammingDistanceCost());

        List<Node> closedList = new ArrayList<>();
        openList.add(root);
        while (!openList.isEmpty()) {
            Node currentNode = openList.poll();
            closedList.add(currentNode);

            if (currentNode.isBoardSolved()) {
                this.solvedBoard = currentNode;
                findTheSolvingPath(currentNode);
                return;
            }

            currentNode.createChildren();
            for (int i = 0; i < currentNode.getChildrenCount(); i++) {
                Node child = currentNode.getChildrenByIndex(i);
                if (!closedList.contains(child)) {
                    openList.add(child);
                }
            }
        }
    }
}
