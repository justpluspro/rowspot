package org.qwli.rowspot.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StopWatch;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author qwli7
 * @date 2021/2/7 14:12
 * 功能：ProcessUtil
 **/
public class ProcessUtil {

    private static final Logger logger = LoggerFactory.getLogger(ProcessUtil.class);

    private static final String FFMPEG_PATH = "";
    private static final String FFPROBE_PATH = "";

    private static final String INFO_FMT = "%s -i %s -select_streams v:0 -show_entries stream=width,height,duration:stream_tags=rotate:format_tags -v quiet -of json";
    private static final long PROCESS_TIMEOUT = 5000;

    public static Map<String, Object> getVideoInfo(String videoPath) throws IOException {
        StopWatch stopWatch = new StopWatch();
        stopWatch.start();
        String infoCmd = String.format(INFO_FMT, FFPROBE_PATH, videoPath);
        Process process = Runtime.getRuntime().exec(infoCmd);

        try {
            process.waitFor(PROCESS_TIMEOUT, TimeUnit.MILLISECONDS);
        } catch (InterruptedException e) {
            e.printStackTrace();
            process.destroyForcibly();
            throw new IOException(String.format("get video info timeout: [%s]", e.getMessage()));
        }

        if(process.isAlive()) {
            process.destroyForcibly();
            throw new IOException(String.format("get video info timeout: [%d]", PROCESS_TIMEOUT));
        }

        if(process.exitValue() != 0) {
            String verbose = String.format("get video info error: %s", StreamUtils.copyToString(process.getErrorStream(), Charset.defaultCharset()));
            throw new IOException(verbose);
        }
        logger.info("It takes [{} seconds] to process video:[{}]", stopWatch.getTotalTimeSeconds(), videoPath);

        String resultString = StreamUtils.copyToString(process.getInputStream(), Charset.defaultCharset());

        logger.info("get video info process result [{}]", resultString);

        return new HashMap<>();
    }
}
