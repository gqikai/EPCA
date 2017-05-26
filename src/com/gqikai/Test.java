package com.gqikai;

import com.gqikai.Graph;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by gaoqikai on 2017/5/19.
 */
public class test {
    public static void main(String[] args) {
        File file = new File(System.getProperty("user.dir") + "/src/com/gqikai/" + args[0]);
        Graph G = new Graph(file);

        EPCA epca = new EPCA(G);
        epca.CCN();
    }
}
