package cn.diyai.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * 命令行工具
 */
public class CommandUtil {
    /**
     * 需求：执行cmd命令，且输出信息到控制台
     *
     * @param cmd
     */
    public static void execCmd(String cmd) {
        System.out.println("----execCmd:  " + cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            // 正确输出流
            InputStream input = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = "";
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
            // 错误输出流
            InputStream errorInput = p.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput));
            String eline = "";
            while ((eline = errorReader.readLine()) != null) {
                System.out.println(eline);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 执行命令并返回值
     * @param cmd
     * @return
     */
    public static String getExecCmdRet(String cmd) {
        System.out.println("----execCmd:  " + cmd);
        try {
            Process p = Runtime.getRuntime().exec(cmd);
            StringBuffer sb = new StringBuffer();
            InputStream input = p.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(input));
            String line = "";
            while ((line = reader.readLine()) != null) {
                sb.append(line);
            }
            // System.out.println(sb.toString());
            line = sb.toString();
            if (line != "" && line != null) {
                return line;
            }
            sb = null;
            InputStream errorInput = p.getErrorStream();
            BufferedReader errorReader = new BufferedReader(new InputStreamReader(errorInput));
            String eline = "";

            while ((eline = errorReader.readLine()) != null) {
                sb.append(eline);
            }
            return sb.toString();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
