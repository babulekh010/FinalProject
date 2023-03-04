package kg.megacom.notificationservice.controllers;

import kg.megacom.notificationservice.dtos.EmailDataDto;
import kg.megacom.notificationservice.services.EmailService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    @PostMapping("/sendEmail")
    public void send(@RequestBody EmailDataDto dataDto){
        emailService.send(dataDto.getTo(), dataDto.getSubject(), dataDto.getText());
    }


}
