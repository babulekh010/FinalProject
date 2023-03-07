package kg.megacom.NatvProject.services;

import kg.megacom.NatvProject.models.dtos.AuthRequestDTO;
import kg.megacom.NatvProject.models.dtos.AuthResponseDTO;
import kg.megacom.NatvProject.models.dtos.ClientDto;

public interface AuthenticationService {
    AuthResponseDTO authenticate(AuthRequestDTO request);
    ClientDto register(String clientEmail, String clientFIO, String clientPhone);
}
