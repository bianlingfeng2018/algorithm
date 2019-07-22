package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import java.util.List;

public class BDKEAGVMission {
    private String source;
    private String destination;
    private long requestTime;
    private List<DirectedEdgeWrapper> candicatePath;
    private String vehicleId;
    private int priority;

    public BDKEAGVMission(String source, String destination, long requestTime,
                          List<DirectedEdgeWrapper> candicatePath, String vehicleId, int priority) {
        this.source = source;
        this.destination = destination;
        this.requestTime = requestTime;
        this.candicatePath = candicatePath;
        this.vehicleId = vehicleId;
        this.priority = priority;
    }
}
