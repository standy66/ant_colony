package edu.phystech.ant_colony.torunova;

import java.util.*;

/**
 * Created by nastya on 17.05.15.
 */
public class Main {
    private static final Random RANDOM = new Random();
    private static final int NUMBER_OF_ANTS = 500;
    private static final int NUMBER_OF_ITERATIONS = 600;
    private static final float MAX_SUM_WEIGHT = 2000.0f;
    private static final int NUMBER_OF_VERTICES = 15;

    public static Edge generateEdge(int from, int to) {
        return new Edge(from, to, RANDOM.nextFloat() * 100, 1);
    }

    public static Pair<List<Edge>, Float> findRoute(Graph graph, int currentVertex, List<Integer> tabooList) {
        tabooList.add(currentVertex);
        Set<Edge> possibleEdges = graph.getEdges(currentVertex);
        List<Edge> edges = new ArrayList<>();
        for (Edge edge : possibleEdges) {
            if (!tabooList.contains(edge.getToVertex())) {
                edges.add(edge);
            }
        }
        if (edges.size() == 0) {
            ArrayList<Edge> edges1 = new ArrayList<>();
            edges1.add(graph.getEdge(currentVertex, 1));
            return new Pair(edges1, graph.getEdge(currentVertex, 1).getLength());
        }
        List<Edge> minweights = new ArrayList<>();
        float minweight = MAX_SUM_WEIGHT;
        Pair<List<Edge>, Float> computed;
        for (Edge edge : edges) {
            computed = findRoute(graph, edge.getToVertex(), new ArrayList<>(tabooList));
            if (edge.getLength() + computed.second() < minweight) {
                minweight = edge.getLength() + computed.second();
                minweights = new ArrayList<>();
                minweights.add(edge);
                minweights.addAll(computed.first());
            }
        }
        return new Pair(minweights, minweight);
    }

    public static void main(String[] args) {
        HashSet<Edge> edges = new HashSet<>();
        for (int i = 0; i < NUMBER_OF_VERTICES; i++) {
            for (int j = 0; j < NUMBER_OF_VERTICES; j++) {
                if (i != j) {
                    edges.add(generateEdge(i, j));
                }
            }
        }
        Graph graph = new Graph(edges, true);
        AntColony antColony = new AntColony(NUMBER_OF_ANTS, 1, graph);
        List<Edge> route = antColony.findRoute(NUMBER_OF_ITERATIONS);
        float sum = 0;
        for (Edge edge : route) {
            sum += edge.getLength();
        }
        System.out.println(sum);
        for (Edge edge : route) {
            System.out.printf("%d %d %f\n", edge.getFromVertex(), edge.getToVertex(), edge.getLength());
        }
        Pair<List<Edge>, Float> computed = findRoute(graph, 1, new ArrayList<>());
        List<Edge> anotherRoute = computed.first();
        System.out.println(computed.second());
        for (Edge edge : anotherRoute) {
            System.out.printf("%d %d %f\n", edge.getFromVertex(), edge.getToVertex(), edge.getLength());
        }
    }
}
