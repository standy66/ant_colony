package edu.phystech.ant_colony.torunova;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by nastya on 17.05.15.
 */
public class Graph {
    private Map<Integer, Set<Edge>> graph;

    public Graph(Set<Edge> edges, boolean oriented) {
        graph = new HashMap<>();
        if (!oriented) {
            Set<Edge> oldEdges = new HashSet<>(edges);
            for (Edge edge : oldEdges) {
                edges.add(edge.getInverted());
            }
        }
        int currentVertex;
        for (Edge edge : edges) {
            currentVertex = edge.getFromVertex();
            HashSet<Edge> newEdges = new HashSet<>();
            newEdges.add(edge);
            graph.put(currentVertex, newEdges);
            for (Edge edge1 : edges) {
                if (edge1.getFromVertex() == currentVertex) {
                    graph.get(currentVertex).add(edge1);
                }
            }
        }

    }

    public Set<Edge> getEdges(int Vertex) {
        return graph.get(Vertex);
    }

    public Edge getEdge(int from, int to) {
        Set<Edge> edges = getEdges(from);
        for (Edge edge : edges) {
            if (edge.getToVertex() == to) {
                return edge;
            }
        }
        return null;
    }

    public int size() {
        return graph.size();
    }

}
