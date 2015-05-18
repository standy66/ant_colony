package edu.phystech.ant_colony.stepanov;

/**
 * Created by andrew on 18.05.15.
 */
public class SimpleAntColonyTSPSolver extends SimpleAntColony{
    public SimpleAntColonyTSPSolver(Graph graph, long startVertex, int antCount, double greediness, double perseverance, double dampingFactor) {
        super(graph, startVertex, antCount, greediness, perseverance, dampingFactor);
    }

    @Override
    protected boolean acceptAnt(Path path) {
        return path.getEdges().size() == graph.getVertexCount();
    }
}
