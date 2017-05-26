package com.gqikai;

import java.io.File;

public class OpenFile{
	public static File readGraph(String[] args){
		File file = new File(System.getProperty("user.dir") + "/src/com/gqikai/" + args[0]);

		return file;
	}

}