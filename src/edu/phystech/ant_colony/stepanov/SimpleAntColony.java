package edu.phystech.ant_colony.stepanov;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TreeSet;

/**
 * Created by andrew on 17.05.15.
 */
public class SimpleAntColony {
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
                return - 1;
            } else if (totalWeight == o.getTotalWeight()) {
                return 0;
            } else {
                return 1;
            }
        }
    }

    private Graph graph;
    private long destinationVertex;
    private Ant[] ants;
    private Path[] paths;
    private double greediness;
    private double perseverance;
    private TreeSet<Path> accepted;
    private long startVertex;
    private long noPathsFound = 0;
    private double dampingFactor;

    public SimpleAntColony(Graph graph, long startVertex,
                           long destinationVertex, int antCount, double greediness, double perseverance, double dampingFactor) {
        this.graph = graph;
        this.destinationVertex = destinationVertex;
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

    protected boolean acceptAnt(Path path) {
        return path.currentVertex() == destinationVertex;
    }

    public Path run(int minStepsCount) throws IOException {
        for (int step = 0; step < minStepsCount || accepted.size() == 0; step++) {
            if (step % 100 == 0) {
                //System.in.read();
                System.out.println(step);
                System.out.println(noPathsFound);
            }
            for (int antIndex = 0; antIndex < ants.length; antIndex++) {
                Ant ant = ants[antIndex];
                Path path = paths[antIndex];
                long vertex = path.currentVertex();
                Graph.Edge antDecision = ant.decide(graph.edgesFrom(vertex));
                //System.out.printf("Ant %d: %s\n", antIndex, antDecision);
                path.addEdge(antDecision);
                antDecision.pheromoneLevel += 1;
                if (acceptAnt(path)) {
                    ant.reset();
                    accepted.add(path);
                    noPathsFound++;
                    paths[antIndex] = new Path(startVertex);
                }
            }
            Map<Long, List<Graph.Edge>> mapping = graph.getGraph();
            for (List<Graph.Edge> edges: mapping.values()) {
                for (Graph.Edge e: edges) {
                    e.pheromoneLevel *= (1 - dampingFactor);
                }
            }
        }
        return accepted.first();
    }


}
