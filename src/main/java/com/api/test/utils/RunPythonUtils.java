package com.api.test.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * @author jingLv
 * @date 2020/09/23
 */
public class RunPythonUtils {

    public RunPythonUtils() {
    }

    /**
     * Python执行方法
     *
     * @param path Python代码地址
     * @return 返回执行结果
     */
    public static Object runPython(String path) {
        Process proc;
        try {
            proc = Runtime.getRuntime().exec("python3 " + path);
            BufferedReader in = new BufferedReader(new InputStreamReader(proc.getInputStream()));
            String line;
            while ((line = in.readLine()) != null) {
                return line;
            }
            in.close();
            proc.waitFor();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
