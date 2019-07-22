package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.dijkstra.DirectedEdge;

import java.security.Key;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class BDKEWindowMatrix {
    HashMap<String, List<DirectedEdgeWrapper>> windowMatrix;

    public DirectedEdgeWrapper insertWindow(DirectedEdgeWrapper newWindow) {
        String key = newWindow.getEdge().from() + "-" + newWindow.getEdge().to();
        if (windowMatrix.containsKey(key)){
            // 该边已有小车占用，从前至后选择第一个合适的位置插入时间窗
            for (DirectedEdgeWrapper oldWindow : windowMatrix.get(key)) {
                if (!(oldWindow.getTimeOut() < newWindow.getTimeIn())){

                }
            }
        } else {
            // 该边尚未有小车占用，直接插入时间窗
            ArrayList<DirectedEdgeWrapper> l = new ArrayList<>();
            l.add(newWindow);
            windowMatrix.put(key, l);
        }
        return null;
    }
}
