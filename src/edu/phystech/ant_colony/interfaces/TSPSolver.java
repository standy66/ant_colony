package edu.phystech.ant_colony.interfaces;


import java.util.List;

/**
 * Created by nastya on 18.05.15.
 */
public interface TSPSolver {
    List<Edge> solve(Graph graph);
    double solveWeightOnly(Graph graph);
}
