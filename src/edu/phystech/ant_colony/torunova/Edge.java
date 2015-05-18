package edu.phystech.ant_colony.torunova;

/**
 * Created by nastya on 17.05.15.
 */
public class Edge {
    private static final float PHEROMONES_VAPORIZATION_VELOCITY = 0.1f;
    private int fromVertex;
    private int toVertex;
    private float length;
    private float pheromones;

    public Edge(int fromVertex, int toVertex, float length, float pheromones) {
        this.fromVertex = fromVertex;
        this.toVertex = toVertex;
        this.length = length;
        this.pheromones = pheromones;
    }

    public int getToVertex() {
        return toVertex;
    }

    public int getFromVertex() {
        return fromVertex;
    }

    public float getLength() {
        return length;
    }

    public float getPheromones() {
        return pheromones;
    }

    public void addPheromones(float newPheromones) {
        pheromones += newPheromones;
    }

    public void vaporizePheromones() {
        pheromones *= 1 - PHEROMONES_VAPORIZATION_VELOCITY;
    }

    public Edge getInverted() {
        return new Edge(toVertex, fromVertex, length, pheromones);
    }
}
