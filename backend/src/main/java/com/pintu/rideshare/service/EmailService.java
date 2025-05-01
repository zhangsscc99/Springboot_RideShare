package com.pintu.rideshare.service;

import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailService;
import com.amazonaws.services.simpleemail.AmazonSimpleEmailServiceClientBuilder;
import com.amazonaws.services.simpleemail.model.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;

@Service
public class EmailService {
    private static final Logger logger = LoggerFactory.getLogger(EmailService.class);
    
    @Value("${aws.ses.access-key}")
    private String accessKey;
    
    @Value("${aws.ses.secret-key}")
    private String secretKey;
    
    @Value("${aws.ses.region}")
    private String region;
    
    @Value("${aws.ses.from-email}")
    private String fromEmail;
    
    private AmazonSimpleEmailService sesClient;
    
    @PostConstruct
    public void init() {
        BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
        sesClient = AmazonSimpleEmailServiceClientBuilder.standard()
                .withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
                .withRegion(Regions.fromName(region))
                .build();
    }
    
    public void sendVerificationCode(String email, String code) {
        int maxRetries = 3;
        int attempt = 0;
        boolean success = false;
        
        while (!success && attempt < maxRetries) {
            try {
                String htmlContent = "<div style='font-family: Arial, sans-serif; max-width: 600px; margin: 0 auto;'>"
                        + "<h2 style='color: #1989fa;'>拼途平台 - 验证码</h2>"
                        + "<p>您好，</p>"
                        + "<p>您正在注册拼途平台账号，验证码为：</p>"
                        + "<div style='background-color: #f5f5f5; padding: 15px; border-radius: 5px; text-align: center;'>"
                        + "<h1 style='color: #1989fa; letter-spacing: 5px;'>" + code + "</h1>"
                        + "</div>"
                        + "<p>该验证码将在5分钟内有效。</p>"
                        + "<p>如果您没有进行此操作，请忽略此邮件。</p>"
                        + "<p>谢谢！<br>拼途平台团队</p>"
                        + "</div>";
                
                String textContent = "您的拼途平台验证码是：" + code + "，该验证码5分钟内有效。";
                
                SendEmailRequest request = new SendEmailRequest()
                        .withDestination(new Destination().withToAddresses(email))
                        .withMessage(new Message()
                                .withBody(new Body()
                                        .withHtml(new Content().withCharset("UTF-8").withData(htmlContent))
                                        .withText(new Content().withCharset("UTF-8").withData(textContent)))
                                .withSubject(new Content().withCharset("UTF-8").withData("拼途平台 - 您的验证码")))
                        .withSource(fromEmail);
                
                sesClient.sendEmail(request);
                logger.info("验证码邮件已通过Amazon SES发送至 {}", email);
                success = true;
            } catch (Exception e) {
                attempt++;
                logger.error("Amazon SES邮件发送失败 (尝试 {}/{}): {}", attempt, maxRetries, e.getMessage());
                
                if (attempt < maxRetries) {
                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException ie) {
                        Thread.currentThread().interrupt();
                    }
                }
            }
        }
        
        if (!success) {
            logger.error("验证码邮件发送失败，已达到最大重试次数");
        }
    }
} 