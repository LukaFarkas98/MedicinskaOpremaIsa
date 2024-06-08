package LukaFarkas.MedOpremaBackend.service;

import LukaFarkas.MedOpremaBackend.entity.Appointment;
import LukaFarkas.MedOpremaBackend.entity.User;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import jakarta.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public class EmailService {

    @Autowired
    private JavaMailSender emailSender;


    public void sendSimpleMessage(String to, String subject, String text) throws MessagingException, jakarta.mail.MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text);

        emailSender.send(message);
    }

    public void sendAppointmentConfirmation(User user, Appointment appointment) throws MessagingException, IOException, WriterException, jakarta.mail.MessagingException {
        MimeMessage message = emailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);

        helper.setTo(user.getEmail());
        helper.setSubject("Appointment Confirmation");
        helper.setText("Dear " + user.getFirstName() + ",\n\nYour appointment is confirmed. Details are as follows:\n\n" +
                "Company: " + appointment.getCompany().getCompanyName() + "\n" +
                "Equipment: " + appointment.getEquipment().getEquipment_name() + "\n" +
                "Time: " + appointment.getTimeSlot() + "\n\n" +
                "Please find the QR code attached for your appointment.");

        ByteArrayResource qrCode = generateQRCode(appointment);
        helper.addAttachment("appointment_qr_code.png", qrCode);

        emailSender.send(message);
    }

    private ByteArrayResource generateQRCode(Appointment appointment) throws WriterException, IOException {
        String qrText = "Appointment Details:\n" +
                "Company: " + appointment.getCompany().getCompanyName() + "\n" +
                "Equipment: " + appointment.getEquipment().getEquipment_name() + "\n" +
                "Time: " + appointment.getTimeSlot();

        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        ByteArrayOutputStream pngOutputStream = new ByteArrayOutputStream();
        MatrixToImageWriter.writeToStream(qrCodeWriter.encode(qrText, BarcodeFormat.QR_CODE, 200, 200), "PNG", pngOutputStream);
        return new ByteArrayResource(pngOutputStream.toByteArray());
    }
}
