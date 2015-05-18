package edu.phystech.ant_colony.interfaces;

/**
 * Created by nastya on 18.05.15.
 */
public interface Edge {
    long getFromVertex();
    long getToVertex();
    double getPheromones();
    double getLength();
}
