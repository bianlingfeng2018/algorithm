package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.constant.BDKEAGV;
import edu.fudan.algorithm.shortestpath.dijkstra.DirectedEdge;

/**
 * 基于时间窗的带权有向边
 */
public class DirectedEdgeWrapper {
    private String vehicleId;
    private DirectedEdge edge;
    private long timeCost;
    private long timeIn;
    private long timeOut;
    private int missionNumber;
    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public DirectedEdge getEdge() {
        return edge;
    }

    public void setEdge(DirectedEdge edge) {
        this.edge = edge;
    }

    public long getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(long timeCost) {
        this.timeCost = timeCost;
    }

    public long getTimeIn() {
        return timeIn;
    }

    public void setTimeIn(long timeIn) {
        this.timeIn = timeIn;
    }

    public long getTimeOut() {
        return timeOut;
    }

    public void setTimeOut(long timeOut) {
        this.timeOut = timeOut;
    }

    public DirectedEdgeWrapper(DirectedEdge edge, long timeIn, String vehicleId, int missionNumber) {
        this.edge = edge;
        this.timeCost = (long) ((edge.weight() / BDKEAGV.VELOCITY) * (1 + 0.005) * 1000);  //ms -> s
        this.timeIn = timeIn;
        this.timeOut = this.timeIn + this.timeCost;
        this.vehicleId = vehicleId;
        this.missionNumber = missionNumber;
    }

    @Override
    public String toString() {
        return "DirectedEdgeWrapper{" +
                "edge=" + edge +
                ", timeCost=" + timeCost +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                '}';
    }
}
