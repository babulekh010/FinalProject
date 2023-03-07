package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.ClientMapper;
import kg.megacom.NatvProject.models.dtos.AuthRequestDTO;
import kg.megacom.NatvProject.models.dtos.AuthResponseDTO;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.dtos.EmailDataDto;
import kg.megacom.NatvProject.models.entities.Client;
import kg.megacom.NatvProject.models.enums.Role;
import kg.megacom.NatvProject.repositories.ClientRepo;
import kg.megacom.NatvProject.services.AuthenticationService;
import kg.megacom.NatvProject.services.JwtService;
import kg.megacom.NatvProject.services.NotificationServiceFeignClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Random;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {

    private final ClientRepo repo;
    private final ClientMapper clientMapper;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    private final NotificationServiceFeignClient notificationService;

    public ClientDto register(String clientEmail, String clientFIO, String clientPhone) {

        Client client = repo.findByEmail(clientEmail);

        if (Objects.nonNull(client)) {
            return clientMapper.toDto(client);
        } else {
            // Генерация пароля клиента
            String clientPassword = getGeneratedPassword();

            // Письмо с логином и паролем от личного кабинета
            sendAuthenticationMessage(clientEmail, clientPassword);

            log.info("Клиент c email «" + clientEmail + "» сохранен");
            return clientMapper.toDto(repo.save(
                    Client.builder()
                            .fio(clientFIO)
                            .email(clientEmail)
                            .password(passwordEncoder.encode(clientPassword))
                            .phone(clientPhone)
                            .role(Role.USER)
                            .startDate(LocalDateTime.now())
                            .endDate(LocalDateTime.now().plusYears(1000))
                            .build()));
        }
    }

    public AuthResponseDTO authenticate(AuthRequestDTO request) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()));
        Client client = repo.findByEmail(request.getEmail());
        var jwtToken = jwtService.generateToken(client);
        return AuthResponseDTO.builder()
                .token(jwtToken)
                .build();
    }

    private String getGeneratedPassword() {
        Random rand = new Random();
        return rand.ints(48, 123)
                .filter(num -> (num < 58 || num > 64) && (num < 91 || num > 96))
                .limit(15)
                .mapToObj(c -> (char) c).collect(StringBuffer::new, StringBuffer::append, StringBuffer::append)
                .toString();
    }

    private void sendAuthenticationMessage(String clientEmail, String generatedPassword) {
        notificationService.send(new EmailDataDto(clientEmail,
                "Authentication message",
                "Регистрация прошла успешно!\nДля входа в личный кабинет используйте email в качестве логина и этот пароль:  " + generatedPassword));
    }
}
