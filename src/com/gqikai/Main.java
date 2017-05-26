package com.gqikai;

import java.io.File;

public class Main {

    public static void main(String[] args) {
        Graph G = new Graph(args);

        EPCA epca = new EPCA(G);
        epca.CCN();
    }
}
