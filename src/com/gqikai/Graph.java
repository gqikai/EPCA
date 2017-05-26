package com.gqikai;

import java.io.*;
import java.util.*;

public class Graph {
    public static final String NEWLINE = System.getProperty("line.separator");

    public int V;//节点数量
    public int E;//边数量
    public ArrayList<Edge> edges;//边信息
    public ArrayList<Integer>[] adj;//邻接表
    public LinkedHashMap<Edge, Integer> P4Centrality;//P4中心性表
    public ArrayList<ArrayList<Integer>> Quadruples;

    public int RE = 0;

    public Graph(File file) {
        try {

            FileInputStream fis = new FileInputStream(file);
            Scanner in = new Scanner(new BufferedInputStream(fis));
            ArrayList<Integer> list = new ArrayList();
            while (in.hasNext()) {
                int a = in.nextInt();
                int b = in.nextInt();
                list.add(a);
                list.add(b);
                E += 1;
            }

            List<Integer> listWithoutDup = new ArrayList<Integer>(new HashSet<Integer>(list));

            V = listWithoutDup.size();
            adj = (ArrayList<Integer>[]) new ArrayList[V + 1];
            for (int v = 0; v < V + 1; v++) {
                adj[v] = new ArrayList<Integer>();
            }

            edges = new ArrayList<Edge>(E);
            P4Centrality = new LinkedHashMap<Edge, Integer>();

            in.close();
            fis.close();
            FileInputStream fis1 = new FileInputStream(file);
            Scanner in1 = new Scanner(new BufferedInputStream(fis1));

            for (int i = 0; i < E; i++) {
                int v = in1.nextInt();
                int w = in1.nextInt();
                addEdge(v, w);
            }
            System.out.println("there are " + V + " nodes and " + E + " edges.");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        Edge edge = v < w ? new Edge(v, w) : new Edge(w, v);
        edges.add(edge);
        P4Centrality.put(edge, 0);
    }
}
