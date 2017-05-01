package com.gqikai;

public class Edge {
    int vertex1;
    int vertex2;

    public Edge(int vertex1, int vertex2) {
        this.vertex1 = vertex1;
        this.vertex2 = vertex2;
    }

    public boolean equals(Object obj) {
        int v1 = ((Edge) obj).vertex1;
        int v2 = ((Edge) obj).vertex2;
        if ((v1 == vertex1 && v2 == vertex2) || (v1 == vertex2 && v2 == vertex1)) {
            return true;
        } else {
            return false;
        }
    }

    public int hashCode() {

        return Integer.parseInt(vertex1 > vertex2 ? vertex2 + "" + vertex1 : vertex1 + "" + vertex2);
    }

    public String toString() {
        return "[" + vertex1 + "," + vertex2 + "]";
    }
}
