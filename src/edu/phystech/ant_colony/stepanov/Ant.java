package edu.phystech.ant_colony.stepanov;

import java.util.Collection;
import java.util.List;

/**
 * Created by andrew on 17.05.15.
 */
public interface Ant {
    Graph.Edge decide(List<Graph.Edge> edges);

    void reset();
}
