package com.api.test.logger;

import cn.hutool.core.date.DateUtil;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.ErrorCode;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * @author jinglv
 * @date 2021/5/31 6:02 下午
 */
public class PerRunRollingFileAppender extends FileAppender {
    public static String LoggerNamePrefix = DateUtil.format(DateUtil.date(), "yyyyMMddHHmmss");
    private static final String DOT = ".";
    private static final String UNDERLINE = "_";

    public PerRunRollingFileAppender() {
    }

    public PerRunRollingFileAppender(Layout layout, String filename, boolean append, boolean bufferedIO, int bufferSize) throws IOException {
        super(layout, filename, append, bufferedIO, bufferSize);
    }

    public PerRunRollingFileAppender(Layout layout, String filename, boolean append)
            throws IOException {
        super(layout, filename, append);
    }

    public PerRunRollingFileAppender(Layout layout, String filename)
            throws IOException {
        super(layout, filename);
    }

    @Override
    public void activateOptions() {
        if (fileName != null) {
            try {
                fileName = getNewLogFileName();
                setFile(fileName, fileAppend, bufferedIO, bufferSize);
            } catch (Exception e) {
                errorHandler.error("Error while activating log options", e, ErrorCode.FILE_OPEN_FAILURE);
            }
        }
    }

    private String getNewLogFileName() {
        if (fileName != null) {
            final String LEFT_PARENTHESIS = "(";
            final String RIGHT_PARENTHESIS = ")";
            final File logFile = new File(fileName);
            final String fileName = logFile.getName();
            final int dotIndex = fileName.indexOf(DOT);
            String newFileName = "";
            int number = -1;
            File[] files = logFile.getParentFile().listFiles(new CustomFilter());
            Pattern pattern = Pattern.compile("(?<=\\()[\\d]+");
            if (files != null && files.length > 0) {
                number++;
                for (File file : files) {

                    Matcher matcher = pattern.matcher(file.getName());
                    if (matcher.find()) {
                        if (number < Integer.parseInt(matcher.group(0))) {

                            number = Integer.parseInt(matcher.group(0));
                        }
                    }
                }
            }
            if (dotIndex != -1) {
                // the file name has an extension. so, insert the time stamp
                // between the file name and the extension
                String tempFileName = fileName.substring(0, dotIndex);
                final int parenthesis = tempFileName.indexOf(LEFT_PARENTHESIS);
                if (parenthesis != -1) {
                    tempFileName = tempFileName.substring(parenthesis);
                }

                if (number > -1) {
                    newFileName = LoggerNamePrefix + UNDERLINE + tempFileName
                            + LEFT_PARENTHESIS + (++number) + RIGHT_PARENTHESIS
                            + fileName.substring(dotIndex);
                } else {
                    newFileName = LoggerNamePrefix + UNDERLINE + tempFileName
                            + fileName.substring(dotIndex);
                }
            } else {
                //是否存在文件名中存在（）
                if (number > -1) {
                    newFileName = LoggerNamePrefix
                            + UNDERLINE
                            + fileName
                            + LEFT_PARENTHESIS + (++number) + RIGHT_PARENTHESIS;
                } else {
                    newFileName = LoggerNamePrefix + UNDERLINE + fileName;
                }
            }
            return logFile.getParent() + File.separator + newFileName;
        }
        return null;
    }

    class CustomFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            File logFile = new File(fileName);
            String fileName = logFile.getName();
            int indexDot = fileName.lastIndexOf(DOT);
            if (indexDot != -1) {
                return name.startsWith(LoggerNamePrefix + UNDERLINE + fileName.substring(0, indexDot));
            } else {
                return name.startsWith(LoggerNamePrefix + UNDERLINE + fileName);
            }
        }
    }
}
