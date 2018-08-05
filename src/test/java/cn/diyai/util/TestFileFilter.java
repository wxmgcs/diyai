package cn.diyai.util;

import java.util.ArrayList;

public class TestFileFilter {
    	public static void main(String[] args) {
		ArrayList<String> fileList = new ArrayList<String>();
		FileFilter.filterfile("download_images", "png", fileList);
		for (String s : fileList) {
			System.out.println(s);
		}
	}
}
