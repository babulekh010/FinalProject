package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.ClientDto;

import java.util.List;

public interface ClientService {
    ClientDto saveClient(String clientEmail, String clientFIO, String clientPhone);
    ClientDto findById(Long id);
    List<ClientDto> findAll();
    ClientDto update(ClientDto clientDto);
    void deactivate(Long id);
}
