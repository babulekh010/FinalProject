package kg.megacom.notificationservice.services;

public interface EmailService {
    void send(String to, String subject, String text);
}
