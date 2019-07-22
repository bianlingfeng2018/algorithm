package edu.fudan.algorithm.shortestpath.dijkstra;

import edu.fudan.algorithm.shortestpath.common.IndexMinPQ;

import java.util.Stack;

public class DijkstraAlgorithmSP {
    private DirectedEdge[] edgeTo;
    private double[] distTo;
    private IndexMinPQ<Double> pq;

    public DijkstraAlgorithmSP(EdgeWeightedDiGraph G, int s) {
        this.edgeTo = new DirectedEdge[G.V()];
        this.distTo = new double[G.V()];
        this.pq = new IndexMinPQ<Double>(G.V());
        for (int v = 0; v < G.V(); v++) {
            distTo[v] = Double.POSITIVE_INFINITY;
        }
        distTo[s] = 0;
        pq.insert(s, 0.0);
        while (!pq.isEmpty()) {
            relax(G, pq.delMin());
        }
    }

    public double distTo(int v) {
        return distTo[v];
    }

    public boolean hasPathTo(int v) {
        return distTo[v] < Double.POSITIVE_INFINITY;
    }

    public Stack<DirectedEdge> pathTo(int v) {
        if (!hasPathTo(v)) {
            return null;
        }
        Stack<DirectedEdge> path = new Stack<>();
        for (DirectedEdge e = edgeTo[v]; e != null; e = edgeTo[e.from()]) {
            path.push(e);
        }
        return path;
    }

    /**
     * 顶点的松弛，即放松一个顶点指出的所有边。
     * 所谓边松弛，是借鉴了皮筋的概念，将一根紧绷的皮筋转移到一条更短的路径上，从而缓解了皮筋的压力。
     *
     * @param G 带权有向图
     * @param v 图的顶点总数
     */
    private void relax(EdgeWeightedDiGraph G, int v) {
        for (DirectedEdge e : G.adj(v)) {
            int w = e.to();
            if (distTo[v] + e.weight() < distTo[w]) {
                distTo[w] = distTo[v] + e.weight();
                edgeTo[w] = e;
                if (pq.contains(w)) {
                    pq.changeKey(w, distTo[w]);
                } else {
                    pq.insert(w, distTo[w]);
                }
            }
        }
    }
}
