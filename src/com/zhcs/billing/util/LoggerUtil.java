// $Id: Logger.java,v 1.16 2005/12/02 07:38:33 wangxz Exp $
package com.zhcs.billing.util;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;


/**
 * �������log���Ƶİ�װ������ӿ��ڲ���ʵ�����ⲿ�޹ء�
 * Ŀǰ��log4j��ʵ�֡����Ҳ������չΪ����ʵ�֡�
 * <p>
 * ʹ���߿���ֻ����������á����һ��ϵͳ�У���Ҷ�������ķ�������log���ã�
 * �Ϳ��Ա�֤��Щ����֮���Ǽ��ݵģ����������������Appender Closed����
 * <p>
 * ���⣬�����ṩ��һ��ȱʡ�����á�
 * <p>
 * ʹ���߿��԰�������һ����׼��Logger��Ҳ���Բ�����������commons-logging
 * @author liuli
 */

public class LoggerUtil {

    private org.apache.log4j.Logger log;
    private static final String DEFAULT_CONFIG_FILE = System.getProperty("user.dir") + "/bin/log4j.properties";

    // ��loadһ��ȱʡ�������ļ��������еĶ������������׼���
    static {
        try {
        	
        	PropertyConfigurator.configure(DEFAULT_CONFIG_FILE);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    
    public static LoggerUtil getLogger(String name) {
        return new LoggerUtil(name);
    }

    public static LoggerUtil getRootLogger() {
        return new LoggerUtil();
    }
    
    /**
     * Same as calling <code>getLogger(clazz.getName())</code>.
     */
    public static LoggerUtil getLogger(Class clazz) {
        return new LoggerUtil(clazz.getName());
    }

    protected LoggerUtil() {
        log = org.apache.log4j.Logger.getRootLogger();
    }
    
    protected LoggerUtil(String name) {
        log = org.apache.log4j.Logger.getLogger(name);
    }

    // ----------------------------------------------------- Logging Properties
    public boolean isDebugEnabled() {
        return log.isDebugEnabled();
    }

    public boolean isInfoEnabled() {
        return log.isInfoEnabled();
    }
    
    public boolean isWarnEnabled() {
        return log.isEnabledFor(Level.WARN);
    }
    
    public boolean isErrorEnabled() {
        return log.isEnabledFor(Level.ERROR);
    }
    
    public void debug(Object message) {
        log.debug(message);
    }

    public void debug(Object message, Throwable t) {
        log.debug(message, t);
    }

    public void info(Object message) {
        log.info(message);
    }

    public void info(Object message, Throwable t) {
        log.info(message, t);
    }

    public void warn(Object message) {
        log.warn(message);
    }

    public void warn(Object message, Throwable t) {
        log.warn(message, t);
    }

    public void error(Object message) {
        log.error(message);
    }

    public void error(Object message, Throwable t) {
        log.error(message, t);
    }

    public void fatal(Object message) {
        log.fatal(message);
    }

    public void fatal(Object message, Throwable t) {
        log.fatal(message, t);
    }

    // ���·�����log4jר�õģ�����һЩ����������������
    public void addAppender(Appender newAppender) {
        log.addAppender(newAppender);
    }
    public Enumeration getAllAppenders() {
        return log.getAllAppenders();
    }
    public Appender getAppender(String name) {
        return log.getAppender(name);
    }
    public void removeAllAppenders() {
        log.removeAllAppenders();
    }
    public void removeAppender(String name) {
        log.removeAppender(name);
    }
    public void removeAppender(Appender appender) {
        log.removeAppender(appender);
    }
    public void setLevel(Level level) {
        log.setLevel(level);
    }
}
