package edu.phystech.ant_colony.stepanov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by andrew on 17.05.15.
 */
public abstract class SimpleAntColony {
    protected Graph graph;
    protected Ant[] ants;
    protected Path[] paths;
    protected double greediness;
    protected double perseverance;
    protected TreeSet<Path> accepted;
    protected long startVertex;
    protected double dampingFactor;
    public SimpleAntColony(Graph graph, long startVertex, int antCount, double greediness, double perseverance, double dampingFactor) {
        this.graph = graph;
        this.ants = new Ant[antCount];
        this.paths = new Path[antCount];
        this.greediness = greediness;
        this.perseverance = perseverance;
        this.startVertex = startVertex;
        for (int i = 0; i < antCount; i++) {
            ants[i] = new AntWorker(greediness, perseverance);
            paths[i] = new Path(startVertex);
        }
        accepted = new TreeSet<>();
        this.dampingFactor = dampingFactor;
    }

    protected abstract boolean acceptAnt(Path path);

    protected void onAntWalksEdge(Graph.Edge antDecision, Ant ant) {
        antDecision.pheromoneLevel += 1;
    }

    protected void onAntAccepted(Ant ant, Path path) {

    }

    protected void onWorldIterationEnds() {
        Map<Long, List<Graph.Edge>> mapping = graph.getGraph();
        for (List<Graph.Edge> edges : mapping.values()) {
            for (Graph.Edge e : edges) {
                e.pheromoneLevel *= (1 - dampingFactor);
            }
        }
    }

    public Path run(int minStepsCount) throws IOException {
        for (int step = 0; step < minStepsCount || accepted.size() == 0; step++) {

            for (int antIndex = 0; antIndex < ants.length; antIndex++) {
                Ant ant = ants[antIndex];
                Path path = paths[antIndex];
                long vertex = path.currentVertex();
                Graph.Edge antDecision = ant.decide(graph.edgesFrom(vertex));
                if (antDecision == null) {
                    ant.reset();
                    continue;
                }
                path.addEdge(antDecision);
                onAntWalksEdge(antDecision, ant);
                if (acceptAnt(path)) {
                    onAntAccepted(ant, path);
                    ant.reset();
                    accepted.add(path);
                    paths[antIndex] = new Path(startVertex);

                }
            }
            onWorldIterationEnds();
        }
        return accepted.first();
    }

    public class Path implements Comparable<Path> {
        private ArrayList<Graph.Edge> edges;
        private double totalWeight;
        private long currentVertex;

        public Path(long startVertex) {
            edges = new ArrayList<>();
            totalWeight = 0;
            currentVertex = startVertex;
        }

        public long currentVertex() {
            return currentVertex;
        }

        public ArrayList<Graph.Edge> getEdges() {
            return edges;
        }

        public double getTotalWeight() {
            return totalWeight;
        }

        public void addEdge(Graph.Edge e) {
            if (e.from != currentVertex()) {
                throw new IllegalArgumentException("edge.from != currentVertex()");
            }
            edges.add(e);
            totalWeight += e.weight;
            currentVertex = e.to;
        }

        @Override
        public int compareTo(Path o) {
            if (totalWeight < o.getTotalWeight()) {
                return -1;
            } else if (totalWeight == o.getTotalWeight()) {
                return 0;
            } else {
                return 1;
            }
        }
    }


}
