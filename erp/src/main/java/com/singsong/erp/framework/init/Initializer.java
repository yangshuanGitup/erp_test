package com.singsong.erp.framework.init;

import org.slf4j.Logger;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.framework.util.SpringContextHolder;

@Component
public class Initializer  implements InitializingBean,ApplicationContextAware {
	private static final Logger logger = CommonLogger.getLogger(Initializer.class);
    @Override
    public void afterPropertiesSet() throws Exception {
        // TODO Auto-generated method stub
        initialize();
    }
    public static void initialize(){
        logger.info("初始化服务开始");
        try {
        	ReadConf.init();
        	InitContext.init();
            logger.info("初始化服务结束");
        } catch (Exception e) {
            logger.error("初始化服务出现异常,请查看...",e);
            System.exit(1);
        }
    }
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		// TODO Auto-generated method stub
		SpringContextHolder.setApplicationContext(applicationContext);
	}
}
