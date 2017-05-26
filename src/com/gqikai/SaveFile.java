package com.gqikai;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class SaveFile{
	public static void save(String str,Graph g) {
		try {
			String path = "./result.txt";
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); //如果追加方式用true
			out.write(str.getBytes("utf-8"));//注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}

		try {
			String path = "./network.txt";
			File file = new File(path);
			if (!file.exists())
				file.createNewFile();
			FileOutputStream out = new FileOutputStream(file, false); //如果追加方式用true
			StringBuilder sb = new StringBuilder();

			for (Edge edge : g.edges){
//                int v1 = edge.vertex1;
//                int spaceNum = 20;
//                while (v1 > 0){
//                    v1 = v1 / 10;
//                    spaceNum --;
//                }
//
//                sb.append(edge.vertex1);
//                for (int i = 0; i < spaceNum; i ++){
//                    sb.append(" ");
//                }
				sb.append(edge.vertex1 + "\t" + edge.vertex2 + "\n");
			}
			System.out.println(sb);
			out.write(sb.toString().getBytes("utf-8"));//注意需要转换对应的字符集
			out.close();
		} catch (IOException ex) {
			System.out.println(ex.getStackTrace());
		}
	}

}