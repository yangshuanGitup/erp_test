package com.singsong.erp.framework.init;

import java.io.InputStream;
import java.util.Properties;

import org.slf4j.Logger;

import com.singsong.erp.base.util.CommonLogger;

public class ReadConf {
    private static Logger     logger     = CommonLogger.getLogger(ReadConf.class);
    private static String     commonFile = "property/common.properties";
    private static Properties commPropertics;
    public static void init(){
        try {
            InputStream inputStream = ReadConf.class.getClassLoader().getResourceAsStream(commonFile);
            commPropertics =  new  Properties();
            if (inputStream != null) {
                commPropertics.load(inputStream);
            }
            else{
                logger.error("property file '" + commonFile + "' not found in the classpath");
                System.exit(1);
            }
            inputStream.close();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            System.exit(1);
        }
    }
    public static String getCommonProperty(String key) {
        if (commPropertics.containsKey(key)) {
            return commPropertics.getProperty(key);
        }
        logger.error("未包含当前Key:" + key);
        return null;
    }
}
