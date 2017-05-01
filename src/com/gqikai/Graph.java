package com.gqikai;

import java.io.*;
import java.util.*;

public class Graph {
    public static final String NEWLINE = System.getProperty("line.separator");

    public int V;
    public int E;
    public ArrayList<Edge> edges;
    public ArrayList<Integer>[] adj;
    public LinkedHashMap<Edge, Integer> P4Centrality;
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

    public void addEdge(int v, int w) {
        adj[v].add(w);
        adj[w].add(v);
        Edge edge = v < w ? new Edge(v, w) : new Edge(w, v);
        edges.add(edge);
        P4Centrality.put(edge, 0);
    }

    public String toString() {
        StringBuilder s = new StringBuilder();
        s.append(V + " vertices, " + E + " edges " + NEWLINE);
//        for (int v = 0; v < V; v++) {
//            s.append(v + ": ");
//            for (int w : adj[v]) {
//                s.append(w + " ");
//            }
//            s.append(NEWLINE);
//        }
        for (Map.Entry<Edge, Integer> entry : P4Centrality.entrySet()) {
            s.append(entry.getKey() + " : " + entry.getValue() + NEWLINE);
        }
        return s.toString();
    }

    public void EPCA() {
        calcP4Centrality();
        sortP4centrality();

        while (true) {
            System.out.println("P4 centrality:" + P4Centrality);

            if (P4Centrality.size() == 0) {
                break;
            }
            Iterator it = P4Centrality.keySet().iterator();
            Edge next = (Edge) it.next();
            if (P4Centrality.get(next) == 0) {
                break;
            }
            removeEdge(next);

            resetP4Centrality();
            calcP4Centrality();

            sortP4centrality();
        }

        System.out.println("Edges in cograph are :" + P4Centrality.keySet());
        System.out.println("there are " + V + " nodes and " + E + " edges.");
    }

    public void removeEdge(Edge edge) {
        RE ++;
        System.out.println("Remove edge:" + edge + "RE:" + RE);
        P4Centrality.remove(edge);
        edges.remove(edge);
        E -= 1;
        adj[edge.vertex1].remove((Integer) edge.vertex2);
        adj[edge.vertex2].remove((Integer) edge.vertex1);
    }

    public void sortP4centrality() {
        List<Map.Entry<Edge, Integer>> list =
                new ArrayList<Map.Entry<Edge, Integer>>(this.P4Centrality.entrySet());

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

        this.P4Centrality = sorted;
    }

    public void CCN(){
        ArrayList traversed = new ArrayList();
        ArrayList<ArrayList> components = new ArrayList();

        for (int i = 1; i <= V; i ++){
            if (traversed.contains(i)) continue;
            ArrayList<Integer> currentComponent = new ArrayList();
            ArrayList<Integer> nodesToTraverse = new ArrayList();
            currentComponent.add(i);
            nodesToTraverse.add(i);
            while (!nodesToTraverse.isEmpty()){
                ArrayList temp = new ArrayList();
                for (int item : nodesToTraverse){
                    temp.addAll(adj[item]);
                }
                nodesToTraverse = temp;

                ArrayList<Integer> itemsToDelete = new ArrayList();
                for (int item : nodesToTraverse){
                    if(currentComponent.contains(item)){
                        itemsToDelete.add(item);
                    }
                }
                for (int item : itemsToDelete){
                    nodesToTraverse.remove((Integer) item);
                }

                currentComponent.addAll(nodesToTraverse);
                currentComponent = new ArrayList<>(new HashSet<>(currentComponent));

            }
            traversed.addAll(currentComponent);
            components.add(currentComponent);
        }
        System.out.println("This network contains " + components.size() + "components");
        for (ArrayList<Integer> component : components){
            for (Integer item : component){
                System.out.print(item + " ");
            }
            System.out.println();
        }
    }

    public void calcP4Centrality() {

        Iterator it = this.edges.iterator();
        while (it.hasNext()) {
            Edge current = (Edge) it.next();
            DepthFirstSearch dfs = new DepthFirstSearch(this, current);
            ArrayList result = dfs.search();
//            System.out.println(result);

            Iterator it2 = result.iterator();
            while (it2.hasNext()) {
                ArrayList<Integer> quadruple = (ArrayList<Integer>) it2.next();

                if (isP4(quadruple)) {
                    P4Centrality.put(current, P4Centrality.get(current) + 1);

                }
            }


        }
    }

    public void resetP4Centrality() {
        for (Edge edge : this.P4Centrality.keySet()) {
            this.P4Centrality.put(edge, 0);
        }

    }

    public ArrayList<Edge> findEdges(ArrayList<Integer> vertices) {
        ArrayList<Edge> edges = new ArrayList<>();
        for (int i : vertices) {
            for (int j : vertices) {
                if (adj[i].contains(j)) {
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
                if (adj[vertices.get(i)].contains(vertices.get(j))) {
                    degree[i]++;
                }
            }
        }
        return degree;
    }

    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/src/com/gqikai/" + args[0]);

        Graph G = new Graph(file);
        G.EPCA();
        G.CCN();
    }

}
