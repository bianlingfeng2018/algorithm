package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.dijkstra.DijkstraAlgorithmSP;
import edu.fudan.algorithm.shortestpath.dijkstra.DirectedEdge;
import edu.fudan.algorithm.shortestpath.dijkstra.EdgeWeightedDiGraph;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Stack;

public class WindowBasedDijsktraSPTest {
    @Test
    public void traverseTest() {
        EdgeWeightedDiGraph G = new EdgeWeightedDiGraph(8);
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
        int s = 0;
        int t = 1;
        DijkstraAlgorithmSP sp = new DijkstraAlgorithmSP(G, s);
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
        int missionNumber = 0;
        String vehicleId = "vehicle-01";

        ArrayList<DirectedEdgeWrapper> windowPath = new ArrayList<>();
        if (sp.hasPathTo(t)) {
            Stack<DirectedEdge> path = sp.pathTo(t);
            long requestTime = new Date().getTime();
            int i = 0;
            DirectedEdgeWrapper pre = null;
            for (; !path.empty(); ) {
                DirectedEdge e = path.pop();
//                System.out.println(e.toString());
                if (++i == 1){
                    pre = new DirectedEdgeWrapper(e, requestTime, vehicleId, missionNumber);
                    windowPath.add(pre);
                }else {
                    pre = new DirectedEdgeWrapper(e, pre.getTimeOut(), vehicleId, missionNumber);
                    windowPath.add(pre);
                }
            }
        }
        for (DirectedEdgeWrapper wp : windowPath) {
            System.out.println(wp.toString());
        }
    }
}
