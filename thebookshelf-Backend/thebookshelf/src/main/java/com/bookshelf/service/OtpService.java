package com.bookshelf.service;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
public class OtpService {

	
	private final Map<String, String> otpStore = new HashMap<>();
    private final Random random = new Random();

    @Autowired
    private JavaMailSender mailSender;
    
    
    public void sendOtp(String email) throws Exception {
        // Generate a random 6-digit OTP
        String otp = generateOtp();

        // Store the OTP temporarily (consider using a cache like Redis for production)
        otpStore.put(email, otp);

        // Create a simple email message
        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(email);
        message.setSubject("Your OTP for The Bookshelf");
        message.setText("Your One-Time Password (OTP) is: " + otp);

        // Send the email
        mailSender.send(message);

        System.out.println("OTP sent to " + email + ": " + otp); // For debugging purposes
    }
    
    
    // Method to generate a 6-digit OTP
    private String generateOtp() {
        Random random = new Random();
        int otp = 100000 + random.nextInt(900000); // Generate a 6-digit number
        return String.valueOf(otp);
    }

    public boolean verifyOtp(String email, String otp) {
        return otp.equals(otpStore.get(email));
    }
}
