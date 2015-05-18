package edu.phystech.ant_colony;

import edu.phystech.ant_colony.stepanov.Graph;
import edu.phystech.ant_colony.stepanov.SimpleAntColony;
import org.graphstream.algorithm.generator.Generator;
import org.graphstream.algorithm.generator.RandomEuclideanGenerator;
import org.graphstream.graph.implementations.SingleGraph;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;
import java.util.Scanner;

/**
 * Created by andre w on 17.05.15.
 */
public class InputTxtRunner {
    protected static String styleSheet =
            "node {" +
                    "   fill-color: black;" +
                    "}" +
                    "node.marked {" +
                    "   fill-color: red;" +
                    "}";

    static int n = 300;
    private static double[][] w = new double[n][n];

    private static Graph generateFull(int n) {
        Random r = new Random();
        Graph g = new Graph();
        for (int i = 0; i < n; i++) {
            for (int j = i + 1; j < n; j++) {
                w[i][j] = r.nextDouble() * 100;
                g.addEdge(new Graph.Edge(i, j, 1, w[i][j]));
                w[j][i] = r.nextDouble() * 100;
                g.addEdge(new Graph.Edge(j, i, 1, w[j][i]));
            }
        }
        return g;
    }

    private static Graph readGraph() throws FileNotFoundException {
        Scanner scanner = new Scanner(new FileInputStream("input.txt"));
        int n = scanner.nextInt();
        int m = scanner.nextInt();
        Graph graph = new Graph();
        for (int i = 0; i < m; i++) {
            int from = scanner.nextInt();
            int to = scanner.nextInt();
            double weight = scanner.nextDouble();
            graph.addEdge(new Graph.Edge(from, to, 1, weight));
        }
        return graph;
        /*
            int startVertex = scanner.nextInt();
            int destVertex = scanner.nextInt();
            */
    }

    public static void main(String[] args) {
        SingleGraph graph = new SingleGraph("random euclidean");
        Generator gen = new RandomEuclideanGenerator();
        gen.addSink(graph);
        gen.begin();
        for (int i = 0; i < 500; i++) {
            gen.nextEvents();
        }
        gen.end();
        graph.display(false);
        graph.getEdge(0).

        try {
            System.in.read();
            for (int i = 0; i < n; i++)
                for (int j = 0; j < n; j++)
                    w[i][j] = Double.POSITIVE_INFINITY;
            Graph g = generateFull(n);
            for (int k = 0; k < n; k++) {
                for (int i = 0; i < n; i++) {
                    for (int j = 0; j < n; j++) {
                        w[i][j] = Math.min(w[i][j], w[i][k] + w[k][j]);
                    }
                }
            }


            SimpleAntColony antColony = new SimpleAntColony(g, 0, 50, 100, 0.5, 0.5, 0.01);
            SimpleAntColony.Path path = antColony.run(150);
            PrintWriter printWriter = new PrintWriter("output.txt");
            System.out.printf("ACO: %f\n", path.getTotalWeight());
            System.out.printf("Floyd: %f\n", w[0][50]);
            printWriter.print(g);
            printWriter.flush();
            printWriter.close();
            /*List<Graph.Edge> edges = path.getEdges();
            for (int i = 0; i < edges.size(); i++) {
                printWriter.print(edges.get(i));
                printWriter.println();
            }
            printWriter.flush();
            printWriter.close();*/
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
