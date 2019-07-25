package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.dijkstra.DijkstraAlgorithmSP;
import edu.fudan.algorithm.shortestpath.dijkstra.DirectedEdge;
import edu.fudan.algorithm.shortestpath.dijkstra.EdgeWeightedDiGraph;

import java.util.*;

public class WindowBasedRouter {
    // 带权有向图
    private EdgeWeightedDiGraph G;
    // 时间窗矩阵初始化
    private BDKEWindowMatrix windowMatrix = new BDKEWindowMatrix();
    private long baseLine;
    // 记录小车上个 s -> t 任务的完成时间
    private HashMap<String, Long> lastCompleted = new HashMap<>();

    public WindowBasedRouter(EdgeWeightedDiGraph G, ArrayList<String> vehicles) {
        this.G = G;
        // 小车初始化
        for (String vehicle : vehicles) {
            lastCompleted.put(vehicle, 0L);
        }
        baseLine = new Date().getTime();
    }

    // 将一个完整的 s -> t 的任务插入时间窗矩阵
    public void route(int s, int t, String vehicleId, String missionNumber) {
        // 路径规划
        DijkstraAlgorithmSP sp = new DijkstraAlgorithmSP(G, s);
        ArrayList<DirectedEdgeWrapper> windowPath = new ArrayList<>();
        ArrayList<DirectedEdgeWrapper> candicatePath = new ArrayList<>();
        if (sp.hasPathTo(t)) {
            Stack<DirectedEdge> path = sp.pathTo(t);
            long requestTime = new Date().getTime();
            int count = 0;
            DirectedEdgeWrapper pre = null;
            for (; !path.empty(); ) {
                DirectedEdge e = path.pop();
                if (++count == 1) {
                    pre = new DirectedEdgeWrapper(e, requestTime, vehicleId, missionNumber);
                    windowPath.add(pre);
                } else {
                    pre = new DirectedEdgeWrapper(e, pre.getTimeOut(), vehicleId, missionNumber);
                    windowPath.add(pre);
                }
            }  // 路径包装
            DirectedEdgeWrapper insertedWindow = null;
            for (int i = 0; i < windowPath.size(); i++) {
                DirectedEdgeWrapper ew = windowPath.get(i);
                String vid = ew.getVehicleId();
                if (insertedWindow == null) {
                    long firstWindowIn = (lastCompleted.get(vid) == 0L ? ew.getTimeIn() : lastCompleted.get(vid));
                    long firstWindowOut = firstWindowIn + ew.getTimeCost();
                    ew.setTimeIn(firstWindowIn);
                    ew.setTimeOut(firstWindowOut);
                } else {
                    ew.setTimeIn(insertedWindow.getTimeOut());
                    ew.setTimeOut(insertedWindow.getTimeOut() + ew.getTimeCost());
                }
                insertedWindow = windowMatrix.insertWindow(ew);
                lastCompleted.put(insertedWindow.getVehicleId(), insertedWindow.getTimeOut());
                // 将调整后的时间窗组装成任务
                candicatePath.add(insertedWindow);
                // 计算等待时间
                if (i == 0) {
                    ew.setTimeToWait(0L);
                } else {
                    ew.setTimeToWait(insertedWindow.getTimeIn() - windowPath.get(i - 1).getTimeOut());
                }
            }
            BDKEAGVMission m = new BDKEAGVMission(s + "", t + "", requestTime, candicatePath, vehicleId, 0);
            dispatch(m);
        }
    }

    public void printWindowMetrix() {
        System.out.println("====================================== 打印时间窗矩阵 ======================================");
//        System.out.println(windowMatrix.toString());
        System.out.println(format(windowMatrix, baseLine));
        System.out.println(formatPretty(windowMatrix, baseLine));
    }

    private void dispatch(BDKEAGVMission m) {
        System.out.println("发送任务：" + m.toString());
    }

    private String format(BDKEWindowMatrix windowMatrix, long baseLine) {
        HashMap<String, List<DirectedEdgeWrapper>> map = windowMatrix.getWindowMatrix();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<DirectedEdgeWrapper>> entry : map.entrySet()) {
            List<DirectedEdgeWrapper> list = entry.getValue();
            StringBuilder sbv = new StringBuilder();
            for (DirectedEdgeWrapper ew : list) {
                long in = ew.getTimeIn() - baseLine;
                long out = ew.getTimeOut() - baseLine;
                String id = ew.getVehicleId();
                sbv.append("[" + id + ": " + in + ", " + out + "]");
            }
            sb.append(entry.getKey() + "(or " +
                    entry.getKey().split("-")[1] + "-" + entry.getKey().split("-")[0] + "): " +
                    sbv + "\r\n");
        }
        return sb.toString();
    }

    private String formatPretty(BDKEWindowMatrix windowMatrix, long baseLine) {
        HashMap<String, List<DirectedEdgeWrapper>> map = windowMatrix.getWindowMatrix();
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<DirectedEdgeWrapper>> entry : map.entrySet()) {
            List<DirectedEdgeWrapper> list = entry.getValue();
            StringBuilder sbv = new StringBuilder();
            long pre = 0L;
            for (DirectedEdgeWrapper ew : list) {
                long in = ew.getTimeIn() - baseLine;
                long out = ew.getTimeOut() - baseLine;
                for (int i = 0; i < (in - pre) / 10; i++) {
                    sbv.append("_");
                }
                for (int i = 0; i < (out - in) / 10; i++) {
                    sbv.append(ew.getVehicleId().charAt(ew.getVehicleId().length() - 1));
                    pre = out;
                }
            }
            sb.append(entry.getKey() + "(or " +
                    entry.getKey().split("-")[1] + "-" + entry.getKey().split("-")[0] + "): " +
                    sbv + "\r\n");
        }
        return sb.toString();
    }
}
