package com.gqikai;

import java.io.*;
import java.util.*;

/**
 * Created by gaoqikai on 2017/5/20.
 */
public class EPCA {
    public Graph g;
    public void preprocessing(File file) {

        File test = new File("test");
        long fileLength = test.length();
        LineNumberReader rf = null;
        try {
            rf = new LineNumberReader(new FileReader(test));
            if (rf != null) {
                int lines = 0;
                rf.skip(fileLength);
                lines = rf.getLineNumber();
                rf.close();
            }
        } catch (IOException e) {
            if (rf != null) {
                try {
                    rf.close();
                } catch (IOException ee) {
                }
            }
        }

    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(g.V + " vertices, " + g.E + " edges " + g.NEWLINE);
//        for (int v = 0; v < V; v++) {
//            s.append(v + ": ");
//            for (int w : adj[v]) {
//                s.append(w + " ");
//            }
//            s.append(NEWLINE);
//        }
        for (Map.Entry<Edge, Integer> entry : g.P4Centrality.entrySet()) {
            s.append(entry.getKey() + " : " + entry.getValue() + g.NEWLINE);
        }
        return s.toString();
    }

    public EPCA(Graph graph) {
        this.g = graph;
        calcP4Centrality();

        while (true) {
            System.out.println("P4 centrality:" + g.P4Centrality);

            if (g.P4Centrality.size() == 0) {
                break;
            }
            Iterator it = g.P4Centrality.keySet().iterator();
            Edge next = (Edge) it.next();
            if (g.P4Centrality.get(next) == 0) {
                break;
            }

            recalcP4Centrality(next);
        }

        System.out.println("Edges in cograph are :" + g.P4Centrality.keySet());
        System.out.println("there are " + g.V + " nodes and " + g.E + " edges.");
    }

    public void removeEdge(Edge edge) {
        g.RE++;
        System.out.println("Remove edge:" + edge + "RE:" + g.RE);
        g.P4Centrality.remove(edge);
        g.edges.remove(edge);
        g.E -= 1;
        g.adj[edge.vertex1].remove((Integer) edge.vertex2);
        g.adj[edge.vertex2].remove((Integer) edge.vertex1);
    }

    public void sortP4Centrality() {
        List<Map.Entry<Edge, Integer>> list =
                new ArrayList<Map.Entry<Edge, Integer>>(this.g.P4Centrality.entrySet());

        Collections.sort(list, new Comparator<Map.Entry<Edge, Integer>>() {
            public int compare(Map.Entry<Edge, Integer> o1,
                               Map.Entry<Edge, Integer> o2) {
                return (o2.getValue() - o1.getValue());
            }
        });

        LinkedHashMap sorted = new LinkedHashMap();
        for (Map.Entry item : list) {
            sorted.put(item.getKey(), item.getValue());
        }

        this.g.P4Centrality = sorted;
    }

    public void CCN() {
        ArrayList traversed = new ArrayList();
        ArrayList<ArrayList> components = new ArrayList();

        for (int i = 1; i <= g.V; i++) {
            if (traversed.contains(i)) continue;
            ArrayList<Integer> currentComponent = new ArrayList();
            ArrayList<Integer> nodesToTraverse = new ArrayList();
            currentComponent.add(i);
            nodesToTraverse.add(i);
            while (!nodesToTraverse.isEmpty()) {
                ArrayList temp = new ArrayList();
                for (int item : nodesToTraverse) {
                    temp.addAll(g.adj[item]);
                }
                nodesToTraverse = temp;

                ArrayList<Integer> itemsToDelete = new ArrayList();
                for (int item : nodesToTraverse) {
                    if (currentComponent.contains(item)) {
                        itemsToDelete.add(item);
                    }
                }
                for (int item : itemsToDelete) {
                    nodesToTraverse.remove((Integer) item);
                }

                currentComponent.addAll(nodesToTraverse);
                currentComponent = new ArrayList<>(new HashSet<>(currentComponent));

            }
            traversed.addAll(currentComponent);
            components.add(currentComponent);
        }
        System.out.println("This network contains " + components.size() + "components");
        StringBuilder sb = new StringBuilder();

        for (ArrayList<Integer> component : components) {
            for (Integer item : component) {
                System.out.print(item + " ");
                sb.append(item + " ");
            }
            System.out.println();
            sb.append("\n");
        }

        SaveFile.save(sb.toString(),g);
    }

    public void calcP4Centrality() {

        Iterator it = g.edges.iterator();
        while (it.hasNext()) {
            Edge current = (Edge) it.next();
            DFS dfs = new DFS(g, current);
            ArrayList result = dfs.search();
            Iterator it2 = result.iterator();
            while (it2.hasNext()) {
                ArrayList<Integer> quadruple = (ArrayList<Integer>) it2.next();

                if (isP4(quadruple)) {
                    g.P4Centrality.put(current, g.P4Centrality.get(current) + 1);

                }
            }
        }
        sortP4Centrality();
    }

    public void recalcP4Centrality(Edge edgeToRemove) {

        DFS dfs = new DFS(g, edgeToRemove);
        ArrayList result = dfs.search();
        ArrayList<Edge> edgesAffected = new ArrayList<Edge>();

        Iterator it1 = result.iterator();
        while (it1.hasNext()) {
            ArrayList<Integer> quadruple = (ArrayList<Integer>) it1.next();

            for (int i : quadruple) {
                for (int j : g.adj[i]) {
                    if (quadruple.contains(j)) {
                        Edge edge = new Edge(i, j);
                        if (edgesAffected.contains(edge)) continue;
                        else edgesAffected.add(edge);
                    }
                }
            }
        }
        edgesAffected.remove(edgeToRemove);
        System.out.println("edgesAffected: " + edgesAffected);
        resetP4Centrality(edgesAffected);
        removeEdge(edgeToRemove);

        Iterator it2 = edgesAffected.iterator();
        while (it2.hasNext()) {
            Edge current = (Edge) it2.next();
            DFS dfs2 = new DFS(g, current);
            ArrayList result2 = dfs2.search();
            Iterator it3 = result2.iterator();
            while (it3.hasNext()) {
                ArrayList<Integer> quadruple = (ArrayList<Integer>) it3.next();

                if (isP4(quadruple)) {
                    g.P4Centrality.put(current, g.P4Centrality.get(current) + 1);

                }
            }
        }
        sortP4Centrality();

    }

    public void resetP4Centrality(ArrayList<Edge> edges) {
        for (Edge edge : edges) {
            this.g.P4Centrality.put(edge, 0);
        }
    }

    public ArrayList<Edge> findEdges(ArrayList<Integer> vertices) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i : vertices) {
            for (int j : vertices) {
                if (g.adj[i].contains(j)) {
                    edges.add(new Edge(i, j));
                }
            }
        }

//        System.out.println(edges);
        HashSet<Edge> hashset_temp = new HashSet<Edge>(edges);
        edges = new ArrayList<Edge>(hashset_temp);
//        System.out.println(edges);

        return edges;
    }

    public boolean isP4(ArrayList<Integer> elements) {
        if (elements.size() != 4) throw new IllegalArgumentException("the number of vertex in quadruple must be 4");

        int[] degree = getDegree(elements);

        int oneCount = 0, twoCount = 0;
        for (int i = 0; i < 4; i++) {
            switch (degree[i]) {
                case 1: {
                    oneCount++;
                    break;
                }
                case 2: {
                    twoCount++;
                    break;
                }
            }
        }

        if (oneCount == 2 && twoCount == 2) {
            return true;
        } else {
            return false;
        }
    }

    public int[] getDegree(ArrayList<Integer> vertices) {

        int[] degree = new int[vertices.size()];
        for (int i = 0; i < vertices.size(); i++) {
            degree[i] = 0;
            for (int j = 0; j < vertices.size(); j++) {
                if (g.adj[vertices.get(i)].contains(vertices.get(j))) {
                    degree[i]++;
                }
            }
        }
        return degree;
    }

}
