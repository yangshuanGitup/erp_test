package com.singsong.erp.component.message.email;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.mail.DefaultAuthenticator;
import org.apache.commons.mail.EmailAttachment;
import org.apache.commons.mail.EmailException;
import org.apache.commons.mail.HtmlEmail;
import org.slf4j.Logger;

import com.singsong.erp.base.util.CommonLogger;
import com.singsong.erp.framework.init.ReadConf;

public class EmailTransport {

    private static final Logger  logger = CommonLogger.getLogger(EmailTransport.class);
    public static final String EMAIL_ERROR="EMAIL_ERROR";
    public static void main(String[] args) {
    	ReadConf.init();
        sendMail("<div><font color='red'>BBB我是 在-323^^%6565**!e@划是</font></div>","测试报表","singsong_system@singsong.com.cn","杨树安",new String[]{"ysan@singsong.com.cn","singsong_system@singsong.com.cn","382539659@qq.com"},null,null,null);
    	System.out.println("a,b".split("\\,")[1]);
    }
    /**
     * 这里的发件人必须是singsong_system@singsong.com.cn,不然会发不出去,因为如果能发出可以直接以别人的名义发送邮件了
     * 这里如果这些接收人邮箱中有一个与发送人邮箱服务器一样的帐号地址错误,就会导致整个邮件失败,如果是其他服务器的
     * 邮件帐号就不会存在这个问题
     * @param Email_Text
     * @param subject
     * @param from
     * @param fromTitle
     * @param to
     * @param cc
     * @param bcc
     * @param filePath
     */
    public static void sendMail(String emailText, String subject, String from, String fromTitle, String[] to,
                                 String[] cc, String[] bcc, String[] filePath) {
        sendEmailCommon(emailText,subject,from,fromTitle,to,cc,bcc,filePath);
    }
    private static void sendEmailCommon(String emailText, String subject, String from, String fromTitle, String[] to,
                                        String[] cc, String[] bcc, String[] filePath){
        try {
        	HtmlEmail  email = new HtmlEmail ();
            email.setHostName(ReadConf.getCommonProperty("email_host"));
            email.setSmtpPort(Integer.parseInt(ReadConf.getCommonProperty("email_host_port")));
            email.setSslSmtpPort(ReadConf.getCommonProperty("email_host_port") + "");
            email.setAuthenticator(new DefaultAuthenticator(ReadConf.getCommonProperty("email_auth_user"),ReadConf.getCommonProperty("email_auth_password")));
            email.setSSLOnConnect(false);
            email.setStartTLSEnabled(false);
            email.setFrom(from, fromTitle);
            email.setSubject(subject);
            email.setMsg(emailText);
            if (to != null) {
                for (String str : to) {
                    email.addTo(str);
                }
            }
            if (cc != null) {
                for (String str : cc) {
                    email.addCc(str);
                }
            }
            if (bcc != null) {
                for (String str : bcc) {
                    email.addBcc(str);
                }
            }
            if (filePath != null) {
                for (String file : filePath) {
                    try {
                        EmailAttachment attachment = new EmailAttachment();
                        attachment.setPath(file);
                        attachment.setDisposition(EmailAttachment.ATTACHMENT);
                        email.attach(attachment);
                    } catch (Exception e) {
                        logger.error("文件=" +file+"没有找到", e);
                    }
                }
            }
            email.setCharset("UTF-8");
            email.send();
        } catch (EmailException e) {
            logger.error("EMAIL_ERROR:邮件主题="+subject+",发送邮件失败,收件人=" + StringUtils.join(to, ","), e);
        }
    }
}
