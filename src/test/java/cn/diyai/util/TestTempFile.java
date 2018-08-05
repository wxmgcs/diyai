package cn.diyai.util;

import java.io.File;

public class TestTempFile {
    static String projectDir = null;

    //1
    public void setProjectDir(String projectDir) {
        this.projectDir = projectDir;
    }

    private static String getFileName(String EID) {
        return projectDir + File.separator + "recoverorder_" + EID + ".txt";
    }

    public static String get(String EID) {
        return TempFile.get(getFileName(EID));
    }

    public static void save(String EID, String log) {
        TempFile.save(getFileName(EID), log);

    }

    public static void del(String EID) {
        TempFile.del(getFileName(EID));
    }

    public static void main(String[] args) {

    }
}
