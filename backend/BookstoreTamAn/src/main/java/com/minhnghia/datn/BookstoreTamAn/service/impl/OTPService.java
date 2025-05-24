package com.minhnghia.datn.BookstoreTamAn.service.impl;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

@Service
@RequiredArgsConstructor
public class OTPService {
    private final Map<String, String> otpStorage = new ConcurrentHashMap<>();
    private final Map<String, LocalDateTime> otpExpiry = new ConcurrentHashMap<>();

    private final EmailService emailService;

    public void generateAndSendOTP(String email) {
        String otp = String.format("%06d", new Random().nextInt(999999));

        otpStorage.put(email, otp);
        otpExpiry.put(email, LocalDateTime.now().plusMinutes(5)); // 5 phút

        String subject = "Mã OTP khôi phục mật khẩu";
        String content = "Mã OTP của bạn là: " + otp + ". Có hiệu lực trong 5 phút.";

        emailService.sendEmail(email, subject, content);
    }

    public boolean verifyOTP(String email, String otp) {
        if (!otpStorage.containsKey(email)) return false;
        if (otpExpiry.get(email).isBefore(LocalDateTime.now())) return false;

        boolean match = otpStorage.get(email).equals(otp);
        if (match) {
            otpStorage.remove(email);
            otpExpiry.remove(email);
        }
        return match;
    }
}
