package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.dijkstra.DirectedEdge;
import edu.fudan.algorithm.shortestpath.dijkstra.EdgeWeightedDiGraph;
import edu.fudan.algorithm.shortestpath.dijkstra.WindowBasedRouter;

import java.util.ArrayList;

public class WindowBasedDijsktraSPTest {
    private static EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(8);
    private static ArrayList<String> vehicles = new ArrayList<>();

    static {
        /**
         * 0 to 0 (0.00):
         * 0 to 1 (1.05): 0 -> 4 0.38   4 -> 5 0.35   5 -> 1 0.32
         * 0 to 2 (0.26): 0 -> 2 0.26
         * 0 to 3 (0.99): 0 -> 2 0.26   2 -> 7 0.34   7 -> 3 0.39
         * 0 to 4 (0.38): 0 -> 4 0.38
         * 0 to 5 (0.73): 0 -> 4 0.38   4 -> 5 0.35
         * 0 to 6 (1.51): 0 -> 2 0.26   2 -> 7 0.34   7 -> 3 0.39   3 -> 6 0.52
         * 0 to 7 (0.60): 0 -> 2 0.26   2 -> 7 0.34
         */
        G.addEdge(new DirectedEdge(4, 5, 0.35));
        G.addEdge(new DirectedEdge(5, 4, 0.35));
        G.addEdge(new DirectedEdge(4, 7, 0.37));
        G.addEdge(new DirectedEdge(5, 7, 0.28));
        G.addEdge(new DirectedEdge(7, 5, 0.28));
        G.addEdge(new DirectedEdge(5, 1, 0.32));
        G.addEdge(new DirectedEdge(0, 4, 0.38));
        G.addEdge(new DirectedEdge(0, 2, 0.26));
        G.addEdge(new DirectedEdge(7, 3, 0.39));
        G.addEdge(new DirectedEdge(1, 3, 0.29));
        G.addEdge(new DirectedEdge(2, 7, 0.34));
        G.addEdge(new DirectedEdge(6, 2, 0.40));
        G.addEdge(new DirectedEdge(3, 6, 0.52));
        G.addEdge(new DirectedEdge(6, 0, 0.58));
        G.addEdge(new DirectedEdge(6, 4, 0.93));
        vehicles.add("Vehicle-01");
        vehicles.add("Vehicle-02");
        vehicles.add("Vehicle-03");
        vehicles.add("Vehicle-04");
    }

    public static void main(String[] args) {
        WindowBasedRouter router = new WindowBasedRouter(G, vehicles);
        router.route(0, 0, "Vehicle-01", "1");
        router.route(0, 1, "Vehicle-02", "2");
        router.route(0, 2, "Vehicle-03", "3");
        router.route(0, 3, "Vehicle-04", "4");
        router.route(0, 4, "Vehicle-01", "5");
        router.route(0, 5, "Vehicle-02", "6");
        router.route(0, 6, "Vehicle-03", "7");
        router.route(0, 7, "Vehicle-04", "8");
        router.printWindowMetrix();
    }
}
