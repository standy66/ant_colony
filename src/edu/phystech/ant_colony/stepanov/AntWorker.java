package edu.phystech.ant_colony.stepanov;

import java.util.HashSet;
import java.util.List;
import java.util.Random;
import java.util.Set;

/**
 * Created by andrew on 17.05.15.
 */
public class AntWorker implements Ant {
    private final static Random randomGenerator = new Random();
    private double greediness;
    private double perseverance;
    private Set<Long> tabooVertices;
    private double[] probabilities = new double[400];

    public AntWorker(double greediness, double perseverance) {
        this.greediness = greediness;
        this.perseverance = perseverance;
        this.tabooVertices = new HashSet<>();
    }

    public static double pow(final double a, final double b) {
        //return Math.pow(a, b);
        final long tmp = Double.doubleToLongBits(a);
        final long tmp2 = (long) (b * (tmp - 4606921280493453312L)) + 4606921280493453312L;
        return Double.longBitsToDouble(tmp2);
    }

    @Override
    public Graph.Edge decide(List<Graph.Edge> edges) {
        int n = edges.size();
        double prevProb = 0;
        for (int i = 0; i < n; i++) {
            Graph.Edge edge = edges.get(i);
            double currentProb = 0;
            if (!tabooVertices.contains(edge.to)) {
                currentProb = pow(edge.pheromoneLevel, perseverance) * pow(edge.attractiveness, greediness);
            }
            probabilities[i] = prevProb + currentProb;
            prevProb += currentProb;
        }
        double fairDiceRoll = randomGenerator.nextDouble() * probabilities[n - 1];
        for (int i = 0; i < n; i++) {
            if (probabilities[i] >= fairDiceRoll) {
                Graph.Edge edge = edges.get(i);
                tabooVertices.add(edge.to);
                return edge;
            }
        }
        Graph.Edge edge = edges.get(n - 1);
        tabooVertices.add(edge.to);
        return edges.get(n - 1);
        /*ArrayList<Pair<Graph.Edge, Double>> reduced = edges.stream().filter(e -> !tabooVertices.contains(e)).reduce
                (new ArrayList<>(), (list, edge) ->
                {
                    double previousProbability = 0;
                    if (list.size() > 0) {
                        previousProbability = list.get(list.size() - 1).getValue();
                    }
                    double currentProbability =
                    list.add(new Pair<>(edge, previousProbability + currentProbability));
                    return list;
                }, (l1, l2) -> {
                    l1.addAll(l2);
                    return l1;
                });
        if (reduced.size() == 0)
            return null;
        double overallProbability = reduced.get(reduced.size() - 1).getValue();
        double dice = randomGenerator.nextDouble();
        for (int i = 0; i < reduced.size(); i++) {
            if (reduced.get(i).getValue() / overallProbability >= dice) {
                return reduced.get(i).getKey();
            }
        }
        Graph.Edge chosenEdge = reduced.get(reduced.size() - 1).getKey();
        tabooVertices.add(chosenEdge.to);
        return chosenEdge;*/
    }

    @Override
    public void reset() {
        tabooVertices.clear();
    }
}
