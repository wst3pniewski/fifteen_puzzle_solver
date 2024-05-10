package org.example;

import org.apache.commons.lang3.time.StopWatch;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;


public class Main {
    public static void main(String[] args) {
        // args
        String algorithm = args[0];
        String algorithmOpt = args[1];
        String puzzleFile = args[2];
        String solutionFile = args[3];
        String statsFile = args[4];
        // gameboard features
        int rows = 0;
        int columns = 0;
        ArrayList<Integer> numbers = new ArrayList<>();
        char[] movesOrder;
        List<Character> solution = new ArrayList<>();
        // stats
        int visitedStates = 0;
        int processedStates = 0;
        int depth = 0;
        String executionTime = "0.0";
        StopWatch stopWatch = new StopWatch();
        DecimalFormat df = new DecimalFormat("#." + "0".repeat(3));
        // read puzzle file
        try (Scanner scanner = new Scanner(new File(puzzleFile))) {
            rows = scanner.nextInt();
            columns = scanner.nextInt();
            for (int i = 0; i < rows * columns; i++) {
                numbers.add(scanner.nextInt());
            }
        } catch (FileNotFoundException e) {
            System.out.println("Nie znaleziono pliku ukladanki: " + e.getMessage());
        }

        Node rootNode = new Node(numbers, columns, rows);
        switch (algorithm) {
            case "bfs":
                movesOrder = algorithmOpt.toCharArray();
                BFS bfs = new BFS(movesOrder);
                stopWatch.start();
                bfs.startBFS(rootNode);
                stopWatch.stop();
                executionTime = df.format((float) stopWatch.getNanoTime() / 1e6);
                processedStates = bfs.getProcessedCount();
                visitedStates = bfs.getVisitedCount();
                depth = bfs.getSolvedBoard().getDepth();
                solution = bfs.getMoves();
                break;
            case "dfs":
                movesOrder = algorithmOpt.toCharArray();
                DFS dfs = new DFS(movesOrder);
                stopWatch.start();
                dfs.startDFS(rootNode);
                stopWatch.stop();
                executionTime = df.format((float) stopWatch.getNanoTime() / 1e6);
                processedStates = dfs.getProcessedCount();
                visitedStates = dfs.getVisitedCount();
                depth = dfs.getSolvedBoard().getDepth();
                solution = dfs.getMoves();
                break;
            case "astr":
                String heurestic = algorithmOpt;
                System.out.println("astr");
                break;
            default:
                throw new IllegalArgumentException("Podano nieprawidlowy algorytm: " + algorithm);
        }

        // save solution
        try (FileWriter fileWriter = new FileWriter(solutionFile)) {
            fileWriter.write(String.valueOf(solution.size()));
            fileWriter.append('\n');
            for (char c : solution) {
                fileWriter.append(c);
            }
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }
        // save stats
        try (FileWriter fileWriter = new FileWriter(statsFile)) {
            fileWriter.write(String.valueOf(solution.size()));
            fileWriter.append('\n');
            fileWriter.write(String.valueOf(visitedStates));
            fileWriter.append('\n');
            fileWriter.write(String.valueOf(processedStates));
            fileWriter.append('\n');
            fileWriter.write(String.valueOf(depth));
            fileWriter.append('\n');
            fileWriter.write(executionTime);
            fileWriter.append(" ms");
        } catch (IOException e) {
            System.err.println("Error writing to the file: " + e.getMessage());
        }


    }
}