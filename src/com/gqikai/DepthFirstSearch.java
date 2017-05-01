package com.gqikai;

import java.util.ArrayList;

public class DepthFirstSearch {
    private boolean[] marked;
    private ArrayList<Integer> trace;
    private Edge edge;
    private Graph g;
    private int depth;
    private ArrayList ret;

    public DepthFirstSearch(Graph g, Edge edge) {
        this.g = g;
        this.edge = edge;
    }

    public ArrayList search() {
        this.ret = new ArrayList();
        searchLeft();
        searchRight();
        searchBothway();
        return ret;
    }

    public void searchLeft() {
        marked = new boolean[g.V + 1];
        marked[edge.vertex2] = true;
        this.trace = new ArrayList<>();
        this.depth = 2;

        dfs(edge.vertex1, 0);
    }

    public void searchRight() {
        marked[edge.vertex1] = true;
        this.trace = new ArrayList<>();
        this.depth = 2;

        dfs(edge.vertex2, 0);
    }

    public void searchBothway() {
        for (int i : g.adj[edge.vertex1]) {
            if (i == edge.vertex2) continue;
            for (int j : g.adj[edge.vertex2]) {
                if (j == edge.vertex1 || j == i || g.adj[i].contains(j)) continue;
                ArrayList route = new ArrayList();
                route.add(edge.vertex1);
                route.add(edge.vertex2);
                route.add(i);
                route.add(j);
                ret.add(route);
            }
        }
    }


    private void dfs(int v, int depth) {
        trace.add(v);
        if (depth == this.depth) {
            ArrayList route = new ArrayList();
            route.add(edge.vertex1);
            route.add(edge.vertex2);
            for (int i = 0; i < this.depth; i++) {
                route.add(trace.get(trace.size() - i - 1));
            }
            ret.add(route);
        } else {
            marked[v] = true;
            for (int w : g.adj[v]) {
                if (!marked[w]) {
                    dfs(w, depth + 1);
                }
            }
        }
        trace.remove(trace.size() - 1);
    }
}