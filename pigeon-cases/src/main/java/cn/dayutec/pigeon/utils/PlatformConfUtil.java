package cn.dayutec.pigeon.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 平台的相关配置
 **/
public class PlatformConfUtil {
    private static final Logger LOG = LoggerFactory.getLogger(PlatformConfUtil.class);
    private static final String PLATFORM_PROPS_FILE_NAME = "platform.properties";
    private static final HashMap<String, String> MAP = new HashMap<>();

    static {
        loadProperties(PLATFORM_PROPS_FILE_NAME);
    }

    private static void loadProperties(String fileName) {
        Properties props = new Properties();

        try (InputStream inputStream = PlatformConfUtil.class.getClassLoader().getResourceAsStream(fileName)) {
            props.load(new InputStreamReader(inputStream, "UTF-8"));
            for (Map.Entry<Object, Object> entry : props.entrySet()) {
                MAP.put((String) entry.getKey(), (String) entry.getValue());
            }
        } catch (Exception exception) {
            LOG.error("Load properties occurs exception!");
        }

    }

    public static String getString(String key) {
        return MAP.get(key);
    }


    public static int getInt(String key) {
        return Integer.parseInt(MAP.get(key));
    }


    public static double getDouble(String key) {
        return Double.parseDouble(MAP.get(key));
    }

    public static long getLong(String key) {
        return Long.parseLong(MAP.get(key));
    }
}
