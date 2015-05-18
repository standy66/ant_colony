package edu.phystech.ant_colony.torunova;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by nastya on 17.05.15.
 */
public class AntColony {
    private List<Ant> ants;
    private int homeVertex;
    private Graph graph;
    private List<List<Edge>> routes;

    public AntColony(int numberOfAnts, int homeVertex, Graph graph) {
        ants = new ArrayList<>();
        routes = new ArrayList<>(new ArrayList<>());
        this.homeVertex = homeVertex;
        this.graph = graph;
        spawnAnts(numberOfAnts);
    }

    private void spawnAnts(int numberOfAnts) {
        ants.clear();
        for (int i = 0; i < numberOfAnts; i++) {
            ants.add(new Ant(homeVertex, graph));
        }
    }

    private int getLength(List<Edge> route) {
        int length = 0;
        for (Edge edge : route) {
            length += edge.getLength();
        }
        return length;
    }

    public List<Edge> findRoute(int numberOfIterations) {
        for (int j = 0; j < numberOfIterations; j++) {
            for (Ant ant : ants) {
                routes.add(ant.go());
            }
            spawnAnts(ants.size());
        }
        List<List<Edge>> newRoutes = new ArrayList<>();
        for (List<Edge> list : routes) {
            if (list.size() >= graph.size()) {
                newRoutes.add(list);
            }
        }
        Collections.sort(newRoutes, (list1, list2) -> getLength(list1) - getLength(list2));
        return newRoutes.get(0);
    }


}
