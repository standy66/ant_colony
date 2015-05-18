package edu.phystech.ant_colony.stepanov;

/**
 * Created by andrew on 18.05.15.
 */
public class SimpleAntColonyDistanceFinder extends SimpleAntColony {
    protected long destVertex;

    public SimpleAntColonyDistanceFinder(Graph graph, long startVertex, long destVertex, int antCount, double greediness, double perseverance, double dampingFactor) {
        super(graph, startVertex, antCount, greediness, perseverance, dampingFactor);
        this.destVertex = destVertex;
    }

    @Override
    protected boolean acceptAnt(Path path) {
        return path.currentVertex() == destVertex;
    }
}
