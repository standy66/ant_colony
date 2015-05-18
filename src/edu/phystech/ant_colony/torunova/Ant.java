package edu.phystech.ant_colony.torunova;

import java.util.*;

/**
 * Created by nastya on 17.05.15.
 */
public class Ant {
    private static final float GREEDYNESS = 0.5f;
    private static final float PERSEVERANCE = 0.5f;
    private static final Random RANDOM = new Random();
    private Set<Integer> tabooList;
    private int currentVertex;
    private int homeVertex;
    private Graph graph;

    public Ant(int homeVertex, Graph graph) {
        this.homeVertex = homeVertex;
        this.currentVertex = homeVertex;
        this.graph = graph;
        tabooList = new HashSet<>();
    }

    public Edge decide(Set<Edge> possibleEdges) {
        List<Pair<Double, Edge>> weights = new ArrayList<>();
        for (Edge edge : possibleEdges) {
            if (!tabooList.contains(edge.getToVertex())) {
                weights.add(new Pair<>(Math.pow(edge.getPheromones(), PERSEVERANCE) * Math.pow(edge.getLength(), GREEDYNESS), edge));
            }
        }
        if (weights.size() == 0) {
            return null;
        }
        double sum = 0;
        for (Pair<Double, Edge> weight : weights) {
            sum += weight.first();
        }
        for (Pair<Double, Edge> weight : weights) {
            weight.setFirst(weight.first() / sum);
        }
        double random = RANDOM.nextDouble();
        double probability = 0;
        for (Pair<Double, Edge> weight : weights) {
            probability += weight.first();
            if (probability > random) {
                return weight.second();
            }
        }
        return weights.get(0).second();
    }

    public List<Edge> go() {
        ArrayList<Edge> fullRoute = new ArrayList<>();
        tabooList.add(currentVertex);
        while (tabooList.size() != graph.size()) {
            Edge newEdge = decide(graph.getEdges(currentVertex));
            if (newEdge == null) {
                fullRoute.clear();
                return fullRoute;
            }
            currentVertex = newEdge.getToVertex();
            tabooList.add(currentVertex);
            fullRoute.add(newEdge);
            newEdge.vaporizePheromones();
            newEdge.addPheromones(1.0f / newEdge.getLength());
        }
        tabooList.remove(homeVertex);
        while (currentVertex != homeVertex) {
            Edge newEdge = decide(graph.getEdges(currentVertex));
            if (newEdge == null) {
                fullRoute.clear();
                return fullRoute;
            }
            currentVertex = newEdge.getToVertex();
            tabooList.add(currentVertex);
            fullRoute.add(newEdge);
            newEdge.vaporizePheromones();
            newEdge.addPheromones(1.0f / newEdge.getLength());
        }
        tabooList.clear();
        return fullRoute;
    }
}
