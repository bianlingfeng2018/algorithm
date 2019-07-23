package edu.fudan.algorithm.shortestpath.windowBasedDijkstra;

import edu.fudan.algorithm.shortestpath.constant.BDKEAGV;

import java.util.*;

public class BDKEWindowMatrix {


    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, List<DirectedEdgeWrapper>> entry : windowMatrix.entrySet()) {
            sb.append(entry.getKey() + ": " + entry.getValue().toString() + "\r\n");
        }
        return sb.toString();
    }

    public HashMap<String, List<DirectedEdgeWrapper>> getWindowMatrix() {
        return windowMatrix;
    }

    private HashMap<String, List<DirectedEdgeWrapper>> windowMatrix = new HashMap<>();

    /**
     * 如果是单车道双向，时间窗就必须留有余量，例如：
     * 小车A 1~2s[a -> b], 小车B 2~3s[a -> b] 无冲突
     * 小车A 1~2s[a -> b], 小车B 2~3s[b -> a] 在 b 点冲突
     *
     * @param newWindow 待调整时间窗
     * @return 调整后时间窗
     */
    public DirectedEdgeWrapper insertWindow(DirectedEdgeWrapper newWindow) {
        long timeIn = 0L;
        long timeOut = 0L;
        String key = extractKey(newWindow);
        if (windowMatrix.containsKey(key)) {  // 该边已有小车占用，从前至后选择第一个合适的位置插入时间窗
            List<DirectedEdgeWrapper> list = windowMatrix.get(key);
            int insertIndex = 0;
            for (int i = 0; i < list.size(); i++) {
                if (i == 0) {
                    timeOut = list.get(i).getTimeIn() - BDKEAGV.REDUNDANCY;
                    timeIn = timeOut - newWindow.getTimeCost();
                    boolean confirmed = confirmWindow(timeIn, timeOut, list.get(i), newWindow);
                    if (confirmed) {
                        insertIndex = i;
                        break;
                    }
                }
                if (i != list.size() - 1) {
                    timeIn = list.get(i).getTimeOut() + BDKEAGV.REDUNDANCY;
                    timeOut = timeIn + newWindow.getTimeCost();
                    boolean confirmed = confirmWindow(timeIn, timeOut, list.get(i + 1), newWindow);
                    if (confirmed) {
                        insertIndex = i + 1;
                        break;
                    }
                } else {
                    timeIn = list.get(i).getTimeOut() + BDKEAGV.REDUNDANCY >= newWindow.getTimeIn() ?
                            list.get(i).getTimeOut() + BDKEAGV.REDUNDANCY : newWindow.getTimeIn();
                    timeOut = timeIn + newWindow.getTimeCost();
                    insertIndex = i + 1;
                    break;
                }
            }
            newWindow.setTimeIn(timeIn);
            newWindow.setTimeOut(timeOut);
            list.add(insertIndex, newWindow);
            return newWindow;
        } else {  // 该边尚未有小车占用，新建时间窗数组
            ArrayList<DirectedEdgeWrapper> l = new ArrayList<>();
            l.add(newWindow);
            windowMatrix.put(key, l);
            return newWindow;
        }
    }

    private String extractKey(DirectedEdgeWrapper newWindow) {
        int from = newWindow.getEdge().from();
        int to = newWindow.getEdge().to();
        return from >= to ? from + "-" + to : to + "-" + from;
    }

    private boolean confirmWindow(long timeIn, long timeOut, DirectedEdgeWrapper oldWindow, DirectedEdgeWrapper newWindow) {
        return (timeIn >= newWindow.getTimeIn()) && ((timeOut + BDKEAGV.REDUNDANCY) <= oldWindow.getTimeIn());
    }
}
