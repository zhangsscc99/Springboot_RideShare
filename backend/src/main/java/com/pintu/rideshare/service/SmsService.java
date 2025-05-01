package com.pintu.rideshare.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

/**
 * 该类已被 EmailService 替代
 * @deprecated 请使用 {@link EmailService} 代替
 */
@Deprecated
@Service
public class SmsService {
    private static final Logger logger = LoggerFactory.getLogger(SmsService.class);
    
    // 简单实现，避免编译错误，实际不会被调用
    public void sendVerificationCode(String phone, String code) {
        logger.warn("SmsService 已被弃用，请使用 EmailService");
    }
} 