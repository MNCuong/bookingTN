package com.example.booking.Service.Impl;

import com.example.booking.DTO.Request.FlightRequestPackage.ContactMessageRequest;
import com.example.booking.Entity.User;
import com.example.booking.Exception.BookingException;
import com.example.booking.Service.EmailService;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.Map;
import java.util.UUID;

@Service
public class EmailServiceImpl implements EmailService {
    @Autowired
    private JavaMailSender emailSender;
    @Autowired
    private RedisService verificationService;
    @Value("${abstractapi.key}")
    private String apiKey;
    @Override
    public void sendSimpleMessage(String to, String subject, String text) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cuongll9103@gmail.com");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        emailSender.send(message);
    }

    @Override
    public void sendCusSimpleMessageConfirmRegister(User user, String token) {

        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("cuongll9103@gmail.com");
        message.setTo(user.getEmail());
        message.setSubject("Account Verification");
        message.setText("Hi " + user.getFullName() + ",\n\nPlease click the link below to verify your account:\n\n"
                + "/http://localhost:5173/verify?userId=" + user.getId() + "&token=" + token);
        emailSender.send(message);


    }

    @Override
    public void sendVerificationEmail(User user) {
        String token = UUID.randomUUID().toString();
        user.setVerificationToken(token);
        user.setTokenExpiryDate(new Date(System.currentTimeMillis() + 60000));
        verificationService.saveVerificationToken(user.getId().toString(), token);

        // Send email
        sendCusSimpleMessageConfirmRegister(user, token);
    }

    @Override
    public void replyEmail(ContactMessageRequest request) {
        try {
            String message = String.format("""
                        Kính gửi Quý khách <b>%s</b>,<br><br>
                        Chúng tôi là <b>Đại lý Ánh Dương</b>.<br>
                        Xin chân thành cảm ơn Quý khách đã liên hệ và quan tâm đến dịch vụ của chúng tôi.<br><br>
                        %s<br><br>
                        Nếu Quý khách cần thêm thông tin hoặc hỗ trợ, xin vui lòng liên hệ qua:<br>
                        📞 Hotline: <b>0965509515</b><br>
                        📧 Email: <b>cuong.mai@vconnex.vn</b><br>
                        🌐 Website: <a href="https://www.anhduong.vn">www.anhduong.vn</a><br><br>
                        Một lần nữa, Đại lý Ánh Dương xin cảm ơn và chúc Quý khách một ngày tốt lành!<br><br>
                        Trân trọng,<br>
                        <b>Đại lý Ánh Dương</b>
                    """, request.getEmail(), request.getMessage());
            sendSimpleMessage(request.getEmail(), request.getSubject(), message);
        } catch (Exception e) {
            throw new BookingException("Error", "Lỗi khi gửi email phản hồi");
        }
    }

    public void sendBookingSuccessEmailWithQR(User user, String flightCode, String departureAirport, String arrivalAirport,
                                              LocalDateTime departureTime, BigDecimal totalAmount, byte[] qrImage) {

        try {
            MimeMessage message = emailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");

            helper.setFrom("cuongll9103@gmail.com");
            helper.setTo(user.getEmail());
            helper.setSubject("Xác nhận đặt vé thành công - Có kèm mã QR");
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm dd/MM/yyyy");
            String formattedDepartureTime = departureTime.format(formatter);
            String content = String.format("""
                            <p>Xin chào <strong>%s</strong>,</p>
                            <p>Bạn đã đặt vé thành công trên hệ thống Booking!</p>
                            <p><strong>Thông tin chuyến bay:</strong></p>
                            <ul>
                                <li>Mã chuyến bay: %s</li>
                                <li>Nơi đi: %s</li>
                                <li>Nơi đến: %s</li>
                                <li>Thời gian khởi hành: %s</li>
                                <li>Tổng tiền: %,.0f VND</li>
                            </ul>
                            <p>Vui lòng đưa mã QR cho nhân viên khi làm thủ tục:</p>
                            <img src='cid:qrCodeImage' width='250' height='250'/>
                            <p>Cảm ơn bạn đã sử dụng dịch vụ của chúng tôi.<br/>Ánh Dương Booking</p>
                            """,
                    user.getFullName(),
                    flightCode,
                    departureAirport,
                    arrivalAirport,
                    formattedDepartureTime,
                    totalAmount
            );

            helper.setText(content, true);

            helper.addInline("qrCodeImage", new ByteArrayResource(qrImage), "image/png");

            emailSender.send(message);
        } catch (MessagingException e) {
            throw new RuntimeException("Lỗi khi gửi email có mã QR: " + e.getMessage(), e);
        }
    }

}


