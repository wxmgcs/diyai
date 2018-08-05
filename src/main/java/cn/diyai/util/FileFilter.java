package cn.diyai.util;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Arrays;

/**
 * 文件过滤器
 */
public class FileFilter implements FilenameFilter {


    private String type;

    public FileFilter(String type) {
        this.type = type;
    }

    public boolean accept(File dir, String name) {
        return name.endsWith(type);
    }

    public static void filterfile(String dir, String suffix, ArrayList<String> fileList) {
        File f = new File(dir);
        File[] myList = f.listFiles();
        FileFilter filter = new FileFilter(suffix);
        String[] files = f.list(filter);
        fileList.addAll(Arrays.asList(files));
        for (File a : myList) {
            if (a.isDirectory()) {
                filterfile(a.toString(), suffix, fileList);
            }
        }
    }
}
