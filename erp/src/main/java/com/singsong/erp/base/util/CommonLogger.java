package com.singsong.erp.base.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.PrintWriter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;

import com.singsong.erp.component.message.email.EmailTransport;
import com.singsong.erp.framework.init.ReadConf;

public class CommonLogger implements Logger {

    private Logger slf4jLogger;

    private CommonLogger(Class<?> clazz) {
        slf4jLogger = LoggerFactory.getLogger(clazz);
    }

    public void error(String message) {
        slf4jLogger.error(message);
        dealErrorMessage(message);
    }

    public void error(String message, Throwable e) {
        slf4jLogger.error(message, e);
        dealErrorExceptionMessage(message, e);
    }

    private void dealErrorMessage(String fromMessage) {
        dealMesage(fromMessage);
    }

    private void dealErrorExceptionMessage(String message, Throwable e) {
        String msg = message + "\r\n" + getExceptionMessage(e);
        dealMesage(msg);
    }

    private String getExceptionMessage(Throwable e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(buf, true);
        e.printStackTrace(writer);
        String expMessage = buf.toString();
        if (buf != null) {
            try {
                buf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (writer != null) {
            writer.close();
        }
        return expMessage;
    }

    public static String getExceptionMessage(String message, Exception e) {
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        PrintWriter writer = new PrintWriter(buf, true);
        e.printStackTrace(writer);
        String expMessage = buf.toString();
        if (buf != null) {
            try {
                buf.close();
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
        if (writer != null) {
            writer.close();
        }
        return "<p>" + message + "</p><p><font>" + expMessage + "</font></p>";
    }

    private void dealMesage(String message) {
        // 发送邮件
        if (Boolean.parseBoolean(ReadConf.getCommonProperty("error_send_flag"))) {
            if(!message.startsWith(EmailTransport.EMAIL_ERROR)){
                EmailTransport.sendMail(message, "erp系统异常", "bi_report@happyfi.com", "异常报告",new String[] { ReadConf.getCommonProperty("system_error_email") }, null, null, null);
            }
        }
    }

    public static Logger getLogger(Class<?> clazz) {
        return new CommonLogger(clazz);
    }

    public void debug(String arg0) {

    }

    public void debug(String arg0, Object arg1) {

    }

    public void debug(String arg0, Object[] arg1) {

    }

    public void debug(String arg0, Throwable arg1) {

    }

    public void debug(Marker arg0, String arg1) {

    }

    public void debug(String arg0, Object arg1, Object arg2) {

    }

    public void debug(Marker arg0, String arg1, Object arg2) {

    }

    public void debug(Marker arg0, String arg1, Object[] arg2) {

    }

    public void debug(Marker arg0, String arg1, Throwable arg2) {

    }

    public void debug(Marker arg0, String arg1, Object arg2, Object arg3) {

    }

    public void error(String arg0, Object arg1) {

    }

    public void error(String arg0, Object[] arg1) {

    }

    public void error(Marker arg0, String arg1) {

    }

    public void error(String arg0, Object arg1, Object arg2) {

    }

    public void error(Marker arg0, String arg1, Object arg2) {

    }

    public void error(Marker arg0, String arg1, Object[] arg2) {

    }

    public void error(Marker arg0, String arg1, Throwable arg2) {

    }

    public void error(Marker arg0, String arg1, Object arg2, Object arg3) {

    }

    public String getName() {

        return null;
    }

    public void info(String arg0) {

        slf4jLogger.info(arg0);
    }

    public void info(String arg0, Object arg1) {

        slf4jLogger.info(arg0, arg1);
    }

    public void info(String arg0, Object[] arg1) {

        slf4jLogger.info(arg0, arg1);
    }

    public void info(String arg0, Throwable arg1) {

        slf4jLogger.info(arg0, arg1);
    }

    public void info(Marker arg0, String arg1) {

        slf4jLogger.info(arg0, arg1);
    }

    public void info(String arg0, Object arg1, Object arg2) {

        slf4jLogger.info(arg0, arg1, arg2);
    }

    public void info(Marker arg0, String arg1, Object arg2) {

        slf4jLogger.info(arg0, arg1, arg2);
    }

    public void info(Marker arg0, String arg1, Object[] arg2) {

        slf4jLogger.info(arg0, arg1, arg2);
    }

    public void info(Marker arg0, String arg1, Throwable arg2) {

        slf4jLogger.info(arg0, arg1, arg2);
    }

    public void info(Marker arg0, String arg1, Object arg2, Object arg3) {

        slf4jLogger.info(arg0, arg1, arg2, arg3);

    }

    public boolean isDebugEnabled() {

        return false;
    }

    public boolean isDebugEnabled(Marker arg0) {

        return false;
    }

    public boolean isErrorEnabled() {

        return false;
    }

    public boolean isErrorEnabled(Marker arg0) {

        return false;
    }

    public boolean isInfoEnabled() {

        return false;
    }

    public boolean isInfoEnabled(Marker arg0) {

        return false;
    }

    public boolean isTraceEnabled() {

        return false;
    }

    public boolean isTraceEnabled(Marker arg0) {

        return false;
    }

    public boolean isWarnEnabled() {

        return false;
    }

    public boolean isWarnEnabled(Marker arg0) {

        return false;
    }

    public void trace(String arg0) {

    }

    public void trace(String arg0, Object arg1) {

    }

    public void trace(String arg0, Object[] arg1) {

    }

    public void trace(String arg0, Throwable arg1) {

    }

    public void trace(Marker arg0, String arg1) {

    }

    public void trace(String arg0, Object arg1, Object arg2) {

    }

    public void trace(Marker arg0, String arg1, Object arg2) {

    }

    public void trace(Marker arg0, String arg1, Object[] arg2) {

    }

    public void trace(Marker arg0, String arg1, Throwable arg2) {

    }

    public void trace(Marker arg0, String arg1, Object arg2, Object arg3) {

    }

    public void warn(String arg0) {

    }

    public void warn(String arg0, Object arg1) {

    }

    public void warn(String arg0, Object[] arg1) {

    }

    public void warn(String arg0, Throwable arg1) {

    }

    public void warn(Marker arg0, String arg1) {

    }

    public void warn(String arg0, Object arg1, Object arg2) {

    }

    public void warn(Marker arg0, String arg1, Object arg2) {

    }

    public void warn(Marker arg0, String arg1, Object[] arg2) {

    }

    public void warn(Marker arg0, String arg1, Throwable arg2) {

    }

    public void warn(Marker arg0, String arg1, Object arg2, Object arg3) {

    }
}
