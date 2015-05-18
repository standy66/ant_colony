package edu.phystech.ant_colony.utils;

import org.graphstream.graph.Graph;
import org.graphstream.graph.implementations.MultiGraph;
import org.openstreetmap.osmosis.core.container.v0_6.*;
import org.openstreetmap.osmosis.core.domain.v0_6.Node;
import org.openstreetmap.osmosis.core.domain.v0_6.Tag;
import org.openstreetmap.osmosis.core.domain.v0_6.Way;
import org.openstreetmap.osmosis.core.domain.v0_6.WayNode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by andrew on 18.05.15.
 */
public class RoadGraph {

    Map<Long, Node> nodes = new HashMap<>();
    Map<Long, List<Long>> roads = new HashMap<>();

    public void addEdge(long nodeIdFrom, long nodeIdTo) {
        if (roads.get(nodeIdFrom) == null) {
            roads.put(nodeIdFrom, new ArrayList<>());
        }
        roads.get(nodeIdFrom).add(nodeIdTo);
    }

    EntityProcessor getAddingEntityProcessor() {
        return new EntityProcessor() {
            @Override
            public void process(BoundContainer boundContainer) {

            }

            @Override
            public void process(NodeContainer nodeContainer) {
                Node node = nodeContainer.getEntity();
                nodes.put(node.getId(), node);
            }

            @Override
            public void process(WayContainer wayContainer) {
                Way way = wayContainer.getEntity();
                boolean highway = false;
                for (Tag t : way.getTags()) {
                    if (t.getKey().equals("highway")) {
                        highway = true;
                    }
                }
                if (!highway)
                    return;
                List<WayNode> wayNodes = way.getWayNodes();
                for (int i = 0; i < wayNodes.size() - 1; i++) {
                    WayNode wayNode = wayNodes.get(i);
                    WayNode nextWayNode = wayNodes.get(i + 1);
                    addEdge(wayNode.getNodeId(), nextWayNode.getNodeId());
                    addEdge(nextWayNode.getNodeId(), wayNode.getNodeId());
                }
            }

            @Override
            public void process(RelationContainer relationContainer) {

            }
        };
    }

    public Graph toMultiGraph() {
        if (nodes.size() == 0) {
            return new MultiGraph("empty");
        }
        String styleSheet = "node {\n" +
                "    size: 3px;\n" +
                "    fill-color: #777;\n" +
                "    text-mode: hidden;\n" +
                "    z-index: 0;\n" +
                "}\n" +
                "edge {\n" +
                "    shape: line;\n" +
                "    fill-color: #222;\n" +
                "    arrow-size: 3px, 2px;\n" +
                "}";
        Graph g = new MultiGraph("roadGraph");

        for (Node node : nodes.values()) {
            if (roads.get(node.getId()) != null) {
                String nodeId = String.valueOf(node.getId());
                double latitude = node.getLatitude();
                double longitude = node.getLongitude();
                g.addNode(nodeId).setAttribute("xy", latitude, longitude);
            }
        }
        for (long from : nodes.keySet()) {
            if (roads.get(from) == null)
                continue;
            for (long to : roads.get(from)) {
                String nodeFromId = String.valueOf(from);
                String nodeToId = String.valueOf(to);
                String edgeId = nodeFromId + "-" + nodeToId;
                if (g.getEdge(edgeId) == null) {
                    g.addEdge(edgeId, nodeFromId, nodeToId);
                }
            }
        }
        g.addAttribute("ui.stylesheet", styleSheet);
        g.addAttribute("ui.quality");
        g.addAttribute("ui.antialias");
        return g;
    }
}
