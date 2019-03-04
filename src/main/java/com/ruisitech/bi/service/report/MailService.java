//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.ruisitech.bi.service.report;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ruisi.ext.engine.view.context.MVContext;
import com.ruisitech.bi.entity.report.MailSenderInfo;
import com.ruisitech.bi.entity.report.MyAuthenticator;
import com.ruisitech.bi.service.portal.PortalPageService;
import com.ruisitech.bi.service.portal.PortalService;
import com.ruisitech.bi.util.CompPreviewService;
import com.ruisitech.bi.util.RSBIUtils;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Date;
import java.util.Properties;
import java.util.UUID;
import javax.activation.DataHandler;
import javax.activation.FileDataSource;
import javax.mail.Address;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.Message.RecipientType;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class MailService {
    @Value("${mail.host}")
    private String host;
    @Value("${mail.port}")
    private Integer port;
    @Value("${mail.userName}")
    private String userName;
    @Value("${mail.password}")
    private String password;
    @Autowired
    private PortalPageService pageService;
    @Autowired
    private PortalService portalService;

    public MailService() {
    }

    public void shareByMail(MailSenderInfo mailInfo, HttpServletRequest req, HttpServletResponse res) throws Exception {
        String cfg = this.portalService.getPortalCfg(mailInfo.getRid());
        JSONObject json = (JSONObject)JSON.parse(cfg);
        MVContext mv = this.pageService.json2MV(json, false, false);
        CompPreviewService ser = new CompPreviewService(req, res, req.getServletContext());
        ser.setParams(this.pageService.getMvParams());
        ser.initPreview();
        String ret = ser.buildMV(mv, req.getServletContext());
        String html = RSBIUtils.htmlPage(ret, RSBIUtils.getConstant("resPath"), "report");
        mailInfo.setMailServerHost(this.host);
        mailInfo.setMailServerPort(String.valueOf(this.port));
        mailInfo.setValidate(true);
        mailInfo.setUserName(this.userName);
        mailInfo.setPassword(this.password);
        mailInfo.setFromAddress(this.userName);
        String fname = RSBIUtils.getUploadFilePath();
        String file = fname + UUID.randomUUID().toString() + ".tmp";
        File f = new File(file);
        FileUtils.writeStringToFile(f, html, "UTF-8");
        mailInfo.setFile(f);
        String protocol = "smtp";
        this.sendEmail(mailInfo, this.getTextMessage(mailInfo, this.getSession(protocol)), protocol);
    }

    public boolean sendTextMail(MailSenderInfo mailInfo) throws MessagingException {
        MyAuthenticator authenticator = null;
        Properties pro = mailInfo.getProperties();
        if (mailInfo.isValidate()) {
            authenticator = new MyAuthenticator(mailInfo.getUserName(), mailInfo.getPassword());
        }

        Session sendMailSession = Session.getDefaultInstance(pro, authenticator);
        Message mailMessage = new MimeMessage(sendMailSession);
        Address from = new InternetAddress(mailInfo.getFromAddress());
        mailMessage.setFrom(from);
        Address to = new InternetAddress(mailInfo.getToAddress());
        mailMessage.setRecipient(RecipientType.TO, to);
        mailMessage.setSubject(mailInfo.getSubject());
        mailMessage.setSentDate(new Date());
        String mailContent = mailInfo.getContent();
        mailMessage.setText(mailContent);
        Transport.send(mailMessage);
        return true;
    }

    private void sendEmail(MailSenderInfo mailInfo, MimeMessage message, String protocol) throws MessagingException {
        Transport transport = this.getSession(protocol).getTransport();
        transport.connect(mailInfo.getMailServerHost(), mailInfo.getUserName(), mailInfo.getPassword());
        transport.sendMessage(message, message.getRecipients(javax.mail.internet.MimeMessage.RecipientType.TO));
        transport.close();
    }

    private Session getSession(String protocol) {
        Properties mailProps = new Properties();
        mailProps.put("mail.smtp.auth", "true");
        mailProps.put("mail.transport.protocol", protocol);
        Session session = Session.getDefaultInstance(mailProps);
        return session;
    }

    private MimeMessage getTextMessage(MailSenderInfo mailInfo, Session session) throws AddressException, MessagingException, UnsupportedEncodingException {
        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress(mailInfo.getFromAddress()));
        message.setRecipient(javax.mail.internet.MimeMessage.RecipientType.TO, new InternetAddress(mailInfo.getToAddress()));
        message.setSubject(mailInfo.getSubject());
        message.setSentDate(new Date());
        MimeBodyPart textBodyPart = this.getTextBodyPart(mailInfo.getContent());
        MimeBodyPart attached1BodyPart = this.getAttachedBodyPart(mailInfo);
        MimeMultipart mmp = new MimeMultipart("mixed");
        mmp.addBodyPart(textBodyPart);
        mmp.addBodyPart(attached1BodyPart);
        message.setContent(mmp);
        message.saveChanges();
        return message;
    }

    private MimeBodyPart getTextBodyPart(String content) throws MessagingException {
        MimeBodyPart contentPart = new MimeBodyPart();
        MimeMultipart mmp = new MimeMultipart("alternative");
        MimeBodyPart contented = new MimeBodyPart();
        contented.setContent(content, "text/html;charset=gb2312");
        mmp.addBodyPart(contented);
        contentPart.setContent(mmp);
        return contentPart;
    }

    private MimeBodyPart getAttachedBodyPart(MailSenderInfo mailInfo) throws MessagingException, UnsupportedEncodingException {
        MimeBodyPart attached = new MimeBodyPart();
        FileDataSource fds = new FileDataSource(mailInfo.getFile());
        attached.setDataHandler(new DataHandler(fds));
        attached.setFileName(MimeUtility.encodeWord("睿思BI分享的报表.htm"));
        return attached;
    }
}
