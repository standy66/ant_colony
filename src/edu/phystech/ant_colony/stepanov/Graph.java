package edu.phystech.ant_colony.stepanov;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Created by andrew on 17.05.15.
 */
public class Graph {
    public static class Edge {
        public long from;
        public long to;
        public double pheromoneLevel;
        public double attractiveness;
        public double weight;

        public Edge(long from, long to, double pheromoneLevel, double weight) {
            this.from = from;
            this.to = to;
            this.pheromoneLevel = pheromoneLevel;
            this.weight = weight;
            this.attractiveness = 1 / weight;
        }

        @Override
        public String toString() {
            return String.format("(%d %d %f %f)", from, to, pheromoneLevel, attractiveness);
        }
    }

    private Map<Long, List<Edge>> mapping;

    public Map<Long, List<Edge>> getGraph() {
        return mapping;
    }

    public List<Edge> edgesFrom(long vertex) {
        return mapping.get(vertex);
    }

    public Graph(Map<Long, List<Edge>> mapping) {
        this.mapping = mapping;
    }

    public Graph() {
        mapping = new HashMap<>();
    }

    public int getVertexCount() {
        return mapping.size();
    }

    public void addEdge(Edge e) {
        if (mapping.get(e.from) == null) {
            mapping.put(e.from, new ArrayList<>());
        }
        mapping.get(e.from).add(e);
    }

    @Override
    public String toString() {
        StringBuilder stringBuilder = new StringBuilder();
        for (long vertex : mapping.keySet()) {
            stringBuilder.append("vertex: ");
            for (Edge edge : mapping.get(vertex)) {
                stringBuilder.append(edge.toString());
            }
            stringBuilder.append('\n');
        }
        return stringBuilder.toString();
    }
}
