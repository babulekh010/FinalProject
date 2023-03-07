package kg.megacom.NatvProject.services.impl;

import kg.megacom.NatvProject.mappers.ClientMapper;
import kg.megacom.NatvProject.models.dtos.ClientDto;
import kg.megacom.NatvProject.models.entities.Client;
import kg.megacom.NatvProject.models.enums.Role;
import kg.megacom.NatvProject.repositories.ClientRepo;
import kg.megacom.NatvProject.services.AuthenticationService;
import kg.megacom.NatvProject.services.ClientService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@Slf4j
@Service
@RequiredArgsConstructor
public class ClientServiceImpl implements ClientService {

    private final ClientRepo clientRepo;
    private final ClientMapper clientMapper;

    @Override
    public ClientDto findById(Long id) {
        return clientMapper.toDto(clientRepo.findById(id).get());
    }

    @Override
    public List<ClientDto> findAll() {
        return clientMapper.toDTOList(clientRepo.findAll());
    }

    @Override
    public ClientDto update(ClientDto clientDto) {
        return clientMapper.toDto(clientRepo.save(clientMapper.toEntity(clientDto)));
    }

    @Override
    public void deactivate(Long id) {

        Client client = clientRepo.findById(id).get();
        client.setEndDate(LocalDateTime.now());
        clientRepo.save(client);

        log.info("Клиент c ID «" + client.getId() + "» деактивирован");
    }

}
