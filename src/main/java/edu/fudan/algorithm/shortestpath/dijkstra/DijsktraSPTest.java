package edu.fudan.algorithm.shortestpath.dijkstra;

import org.junit.Test;

import java.util.Stack;

public class DijsktraSPTest {
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
        DijkstraAlgorithmSP sp = new DijkstraAlgorithmSP(G, s);
        for (int t = 0; t < G.V(); t++) {
            System.out.print(s + " to " + t);
            System.out.printf(" (%4.2f): ", sp.distTo(t));
            if (sp.hasPathTo(t)) {
//                for (DirectedEdge e : sp.pathTo(t)) {
//                    System.out.print(e + "   ");
//                }
                for (Stack<DirectedEdge> stack = sp.pathTo(t); !stack.empty(); ) {
                    System.out.print(stack.pop() + "   ");
                }
            }
            System.out.println();
        }
    }

    @Test
    public void StackTest() {
        // 为什么不是先进后出？
        Stack<String> stack = new Stack<>();
        stack.push("a");
        stack.push("b");
        stack.push("c");
        for (String s : stack) {
            System.out.println(s);
        }
    }
}
