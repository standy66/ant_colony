package edu.phystech.ant_colony.torunova;

import java.util.*;

/**
 * Created by nastya on 17.05.15.
 */
public class Main {
    private static final Random RANDOM =new Random();
    public static Edge generateEdge(int from,int to) {
        return new Edge(from,to,RANDOM.nextFloat()*100,1);
    }
    public static Pair<List<Edge>,Float> findRoute(int i,Graph graph,int currentVertex,List<Integer> tabooList) {
        tabooList.add(currentVertex);
        Set<Edge> possibleEdges = graph.getEdges(currentVertex);
        List<Edge> edges = new ArrayList<>();
        for(Edge edge : possibleEdges) {
            if(! tabooList.contains(edge.getToVertex())) {
                edges.add(edge);
            }
        }
        if (edges.size() == 0) {
            ArrayList<Edge> edges1 = new ArrayList<>();
            edges1.add(graph.getEdge(currentVertex,1));
            return new Pair(edges1,graph.getEdge(currentVertex,1).getLength());
        }
        List<Edge> minweights = new ArrayList<>();
        float minweight = 2000.0f;
        Pair<List<Edge>,Float> computed;
        for (Edge edge : edges) {
            computed = findRoute(i+1,graph,edge.getToVertex(),new ArrayList<>(tabooList));
            if(edge.getLength() + computed.second() < minweight) {
                minweight = edge.getLength() + computed.second();
                minweights = new ArrayList<>();
                minweights.add(edge);
                minweights.addAll(computed.first());
            }
        }
        return new Pair(minweights,minweight);
    }
    public static void main(String[] args) {
        HashSet<Edge> edges = new HashSet<>();
        /*edges.add(new Edge(1, 2, 2, 1));
        edges.add(new Edge (2, 3, 100, 1));
        edges.add(new Edge(2,3,1,1));
        edges.add(new Edge(3, 4, 2, 1));
        edges.add(new Edge(3, 4, 300 , 1));
        edges.add(new Edge(4, 1, 2, 1));
        */
        for (int i = 0; i < 10; i++) {
            for (int j = 0;j < 10;j++) {
                if (i != j) {
                    edges.add(generateEdge(i,j));
                }
            }
        }
        Graph graph = new Graph(edges,true);
        AntColony antColony = new AntColony(500,1,graph);
        List<Edge> route = antColony.findRoute(600);
        float sum = 0;
        for(int i=0;i < route.size();i++) {
            sum+=route.get(i).getLength();
        }
        System.out.println(sum);
        for(int i = 0; i < route.size();i++) {
            System.out.printf("%d %d %f\n", route.get(i).getFromVertex(), route.get(i).getToVertex(), route.get(i).getLength());
        }
        System.out.println("Hey");
        Pair<List<Edge>,Float> computed = findRoute(1,graph,1,new ArrayList<>());
        List<Edge> anotherRoute = computed.first();
        for(int i = 0; i < anotherRoute.size();i++) {
            System.out.printf("%d %d %f\n", anotherRoute.get(i).getFromVertex(), anotherRoute.get(i).getToVertex(), anotherRoute.get(i).getLength());
        }
       /* Edge lastEdge = graph.getEdge(anotherRoute.get(anotherRoute.size()-1).getToVertex(),1);
        System.out.printf("%d %d %f\n",lastEdge.getFromVertex(),lastEdge.getToVertex(),lastEdge.getLength());*/
        System.out.println(computed.second());
    }
}
