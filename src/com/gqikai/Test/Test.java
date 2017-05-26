package com.gqikai.Test;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;

public class Test {
    //写文件，支持中文字符，在linux redhad下测试过
    public static void writeLog(String str) {
        try {
            String path = "/root/test/testfilelog.log";
            File file = new File(path);
            if (!file.exists())
                file.createNewFile();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            FileOutputStream out = new FileOutputStream(file, false); //如果追加方式用true
            StringBuffer sb = new StringBuffer();
            sb.append("-----------" + sdf.format(new Date()) + "------------\n");
            sb.append(str + "\n");
            out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
            out.close();
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
    }

    public static String readLog() {
        StringBuffer sb = new StringBuffer();
        String tempstr = null;
        try {
            String path = "/root/test/testfilelog.log";
            File file = new File(path);
            if (!file.exists())
                throw new FileNotFoundException();
//            BufferedReader br=new BufferedReader(new FileReader(file));
//            while((tempstr=br.readLine())!=null)
//                sb.append(tempstr);
            //另一种读取方式
            FileInputStream fis = new FileInputStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(fis));
            while ((tempstr = br.readLine()) != null)
                sb.append(tempstr);
        } catch (IOException ex) {
            System.out.println(ex.getStackTrace());
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        writeLog("this is a test log");
        writeLog("中文测试看看");
        System.out.println(readLog());
    }

}

//
//import java.io.File;
//import java.util.*;
//
///**
// * Created by gaoqikai on 17/3/3.
// */
//public class Test {
//
//    public static void main(String[] args) {
//        int V = 0;
//        int E = 0;
//
//        try {
//            File file = new File(System.getProperty("user.dir") + "/src/com/gqikai/data/test");
//            Scanner sc = new Scanner(file);
//            sc.nextInt();
//        } catch (Exception e) {
//            System.out.println(e);
//        }
//
//        System.out.println(System.getProperty("user.dir"));//user.dir指定了当前的路径
//        File directory = new File("");//设定为当前文件夹
//        try {
//            System.out.println(directory.getCanonicalPath());//获取标准的路径
//            System.out.println(directory.getAbsolutePath());//获取绝对路径
//        } catch (Exception e) {
//        }
//
////        File test = new File("test");
////        long fileLength = test.length();
////        LineNumberReader rf = null;
////        try {
////            rf = new LineNumberReader(new FileReader(test));
////            if (rf != null) {
////                int lines = 0;
////                rf.skip(fileLength);
////                lines = rf.getLineNumber();
////                System.out.println(lines);
////                rf.close();
////            }
////        } catch (IOException e) {
////            System.out.println(e);
////            if (rf != null) {
////                try {
////                    rf.close();
////                } catch (IOException ee) {
////                }
////            }
////        }
//
//    }
////        Map<String, String> map = new HashMap<String, String>();
////        map.put("1", "value1");
////        map.put("2", "value2");
////        map.put("3", "value3");
////
////        //第一种：普遍使用，二次取值
////        System.out.println("通过Map.keySet遍历key和value：");
////        for (String key : map.keySet()) {
////            System.out.println("key= "+ key + " and value= " + map.get(key));
////        }
////
////        //第二种
////        System.out.println("通过Map.entrySet使用iterator遍历key和value：");
////        Iterator<Map.Entry<String, String>> it = map.entrySet().iterator();
////        while (it.hasNext()) {
////            Map.Entry<String, String> entry = it.next();
////            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
////        }
////
////        //第三种：推荐，尤其是容量大时
////        System.out.println("通过Map.entrySet遍历key和value");
////        for (Map.Entry<String, String> entry : map.entrySet()) {
////            System.out.println("key= " + entry.getKey() + " and value= " + entry.getValue());
////        }
////
////        //第四种
////        System.out.println("通过Map.values()遍历所有的value，但不能遍历key");
////        for (String v : map.values()) {
////            System.out.println("value= " + v);
////        }
////    }
//
//
////
////
////    public static void main(String[] args) {
////        ArrayList edges = new ArrayList();
////        Edge e1 = new Edge(1,2);
////        Edge e2 = new Edge(2,1);
////
////        System.out.println(e1.equals(e2));
////        edges.add(e1);
////        edges.add(e2);
////        HashSet<Integer> hashset_temp = new HashSet<Integer>(edges);
////        edges  = new ArrayList<Integer>(hashset_temp);
////        System.out.println(edges);
////    }
//////    public static void main(String[] args) {
////        HashMap<Edge, Integer> map = new HashMap<>();
////        Edge e1 = new Edge(1,2);
////        Edge e2 = new Edge(1,2);
////        map.put(e1,3);
////        map.put(e2,4);
////        System.out.println(map.get(e1));
////        System.out.println(map);
////    }
//
////
////    public ArrayList<ArrayList<Integer>> Quadruples = new ArrayList<ArrayList<Integer>>();
////    public int V = 6;
////    public int[][] setQuadruples() {
////        getQuadruples(0, new ArrayList());
////        return null;
////    }
////
////    public void getQuadruples(int m, ArrayList current) {
////        if (m == V && current.size() < 4) {
////        } else if (current.size() == 4) {
////            Quadruples.add(current);
////        } else {
////            getQuadruples(m + 1, current);
////
////            ArrayList newCurrent = (ArrayList) current.clone();
////            newCurrent.add(m);
////            getQuadruples(m + 1, newCurrent);
////        }
////
////    }
////
////    public static void main(String[] args) {
////        Test t = new Test();
////        t.setQuadruples();
////        System.out.println(t.Quadruples.size());
////    }
////public static void main(String[] args) {
////    ArrayList list = new ArrayList();
////    list.add(1);
////    System.out.println(list.get(0));
////}
////
////    public static void main(String[] args) {
//////        先声明一个HashMap对象：
////        Map<String, Integer> map = new HashMap<String, Integer>();
//////        然后我们可以将Map集合转换成List集合中，而List使用ArrayList来实现如下：
////
////        List<Map.Entry<String,Integer>> list =
////                new ArrayList<Map.Entry<String,Integer>>(map.entrySet());
//////        最后通过Collections.sortP4centrality(List l, Comparator c)方法来进行排序，代码如下：
////
////        Collections.sortP4centrality(list, new Comparator<Map.Entry<String, Integer>>() {
////            public int compare(Map.Entry<String, Integer> o1,
////                               Map.Entry<String, Integer> o2) {
////                return (o2.getValue() - o1.getValue());
////            }
////        });
////    }
//
//}
