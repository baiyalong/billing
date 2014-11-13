// $Id: Logger.java,v 1.16 2005/12/02 07:38:33 wangxz Exp $
package com.zhcs.billing.util;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.Level;
import org.apache.log4j.PropertyConfigurator;


/**
 * 这个类是log机制的包装。这个接口内部的实现与外部无关。
 * 目前是log4j的实现。今后也可以扩展为其它实现。
 * <p>
 * 使用者可以只用这里的配置。如果一个系统中，大家都用这里的方法进行log配置，
 * 就可以保证这些配置之间是兼容的，而不会产生以往的Appender Closed错误。
 * <p>
 * 另外，这里提供了一个缺省的配置。
 * <p>
 * 使用者可以把它当作一个标准的Logger，也可以不用它，而用commons-logging
 * @author liuli
 */

public class LoggerUtil {

    private org.apache.log4j.Logger log;
    private static final String DEFAULT_CONFIG_FILE = System.getProperty("user.dir") + "/bin/log4j.properties";

    // 先load一个缺省的配置文件，将所有的东西都输出到标准输出
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

    // 以下方法是log4j专用的，用于一些特殊情况，比如测试
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
