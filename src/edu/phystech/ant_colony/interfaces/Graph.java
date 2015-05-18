package edu.phystech.ant_colony.interfaces;

import java.util.Collection;
import java.util.List;

/**
 * Created by andrew on 18.05.15.
 */
public interface Graph {
    List<Edge> getEdgesFrom(long vertex);
    void addEdge(Edge edge);
    Collection<Long> getVertices();
}
