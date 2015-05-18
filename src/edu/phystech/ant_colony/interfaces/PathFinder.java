package edu.phystech.ant_colony.interfaces;

import java.util.List;

/**
 * Created by andrew on 18.05.15.
 */
public interface PathFinder {
    List<Edge> solve(Graph graph, long fromVertex, long toVertex);
    double solveWeightOnly(Graph graph, long fromVertex, long toVertex);
}
