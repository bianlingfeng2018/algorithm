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
    private String missionNumber;
    private long timeToWait;

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

    public String getMissionNumber() {
        return missionNumber;
    }

    public void setMissionNumber(String missionNumber) {
        this.missionNumber = missionNumber;
    }

    public long getTimeToWait() {
        return timeToWait;
    }

    public void setTimeToWait(long timeToWait) {
        this.timeToWait = timeToWait;
    }

    public DirectedEdgeWrapper(DirectedEdge edge, long timeIn, String vehicleId, String missionNumber) {
        this.edge = edge;
        this.timeCost = (long) ((edge.weight() / BDKEAGV.VELOCITY) * (1 + BDKEAGV.CORRECTION) * 1000);  //ms -> s
        this.timeIn = timeIn;
        this.timeOut = this.timeIn + this.timeCost;
        this.vehicleId = vehicleId;
        this.missionNumber = missionNumber;
    }

    @Override
    public String toString() {
        return "DirectedEdgeWrapper{" +
                "vehicleId='" + vehicleId + '\'' +
                ", edge=" + edge +
                ", timeCost=" + timeCost +
                ", timeIn=" + timeIn +
                ", timeOut=" + timeOut +
                ", missionNumber=" + missionNumber +
                ", timeToWait=" + timeToWait +
                '}';
    }
}
